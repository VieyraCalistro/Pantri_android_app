package com.example.pantri;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static com.example.pantri.View_API_Here.EXTRA_CALORIES;
import static com.example.pantri.View_API_Here.EXTRA_IMAGE_URL;
import static com.example.pantri.View_API_Here.EXTRA_MEAL;
import static com.example.pantri.View_API_Here.EXTRA_NUTRITION;
import static com.example.pantri.View_API_Here.EXTRA_PREPARATION_STEPS;
import static com.example.pantri.View_API_Here.EXTRA_RECIPE;
import static com.example.pantri.View_API_Here.EXTRA_SERVING_SIZE;

public class DetailActivity extends AppCompatActivity {

    private boolean itemIsClickedAlready = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final String imageUrl = getIntent().getStringExtra(EXTRA_IMAGE_URL);
        final String recipe = getIntent().getStringExtra(EXTRA_RECIPE);
        final String meal = getIntent().getStringExtra(EXTRA_MEAL);
        final int calories = getIntent().getIntExtra(EXTRA_CALORIES, 0);
        final String nutrition = getIntent().getStringExtra(EXTRA_NUTRITION);
        final String prepingSteps = getIntent().getStringExtra(EXTRA_PREPARATION_STEPS);
        int servingSize = getIntent().getIntExtra(EXTRA_SERVING_SIZE, 0);


        final Button backButton = findViewById(R.id.BackButton_detail);
        backButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });


        ImageView imageView = findViewById(R.id.image_view_detail);
        TextView textViewMeal = findViewById(R.id.textView_Meal_detail);
        TextView textViewNutrition = findViewById(R.id.textView_view_nutrition);
        TextView textViewRecipe = findViewById(R.id.textView_recipe_detail);
        TextView preparation = findViewById(R.id.Preperation_Steps_detail);
        TextView copyPreparationLink = findViewById(R.id._copyLink);


        copyPreparationLink.setText("Trouble with viewing steps? Copy and paste link from here: " + prepingSteps);
        Picasso.get().load(imageUrl).fit().centerInside().into(imageView);
        textViewMeal.setText(meal);
        textViewRecipe.setText("Serving size: " + servingSize + "\n" + "\n" + "Recipe\t(If possible, scroll down to view entire recipe):" + "\n" + "\n" + recipe);


        SpannableNutrientsLink(textViewNutrition, nutrition, calories, servingSize);
        SpannablePrepingStepsLink(preparation, prepingSteps, imageUrl, recipe, meal, calories, nutrition, servingSize);
    }


    // Create spannable string methods
    public void SpannableNutrientsLink(TextView atextViewNutrition, final String anutrition, final int acalories, final int servingSize)
    {
        SpannableString nutritionSpanString = new SpannableString(atextViewNutrition.getText());

        ClickableSpan clickableNutrition = new ClickableSpan()
        {
            @Override
            public void onClick(View widget)
            {
                /**************************Prevent double clicking into nutrition *************************/

                if(!itemIsClickedAlready) {
                    itemIsClickedAlready = true;

                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            int timer = 2;// Timer to prevent double click into one single item view.

                            while (timer > 0) {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                timer -= 1;
                            }

                            DetailActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    itemIsClickedAlready = false;
                                }
                            });


                        }
                    }).start();


                    /*****************************************************************************************/


                    Intent goToNutrients = new Intent(DetailActivity.this, Nutrients.class);

                    goToNutrients.putExtra(EXTRA_NUTRITION, anutrition);
                    goToNutrients.putExtra(EXTRA_CALORIES, acalories);
                    goToNutrients.putExtra(EXTRA_SERVING_SIZE, servingSize);

                    startActivity(goToNutrients);

                }
            }

            @Override
            public void updateDrawState(TextPaint ds)
            {
                super.updateDrawState(ds);

                ds.setUnderlineText(false);
                ds.setColor(Color.BLUE);
            }
        };
        nutritionSpanString.setSpan(clickableNutrition, 0, 19, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        atextViewNutrition.setText(nutritionSpanString);
        atextViewNutrition.setMovementMethod(LinkMovementMethod.getInstance());
    }


    public void SpannablePrepingStepsLink(final TextView apreparation, final String prepingSteps, final String imageUrl, final String recipe, final String meal, final int calories, final String nutrition, final int servingSize)
    {
        String clickableWords = "Preperation Steps (by clicking this link you will EXIT PANTRI). ";

        ForegroundColorSpan exitColor = new ForegroundColorSpan(Color.RED);

        SpannableString spanClickableWords = new SpannableString(clickableWords);

        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View widget)
            {
                WebView goToWeb = new WebView(DetailActivity.this);

                goToWeb.loadUrl(prepingSteps);
            }

            @Override
            public void updateDrawState(TextPaint ds)
            {
                super.updateDrawState(ds);

                ds.setUnderlineText(false);
                ds.setColor(Color.BLUE);
            }
        };


        spanClickableWords.setSpan(clickableSpan1, 0, 63, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spanClickableWords.setSpan(exitColor, 50, 54, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        apreparation.setText(spanClickableWords);
        apreparation.setMovementMethod(LinkMovementMethod.getInstance());
    }

}