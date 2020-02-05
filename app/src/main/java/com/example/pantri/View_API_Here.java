package com.example.pantri;


import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import static android.graphics.Color.TRANSPARENT;

public class View_API_Here extends AppCompatActivity implements Adapter.OnItemClickListener {

    public static String EXTRA_IMAGE_URL = "imageUrl";
    public static String EXTRA_MEAL = "meal";
    public static String EXTRA_RECIPE = "recipe";
    public static String EXTRA_CALORIES = "calories";
    public static String EXTRA_NUTRITION = "nutrition";
    public static String EXTRA_PREPARATION_STEPS = "prepingSteps";
    public static String EXTRA_FOODTYPE = "foodType";
    public static String EXTRA_SERVING_SIZE = "servings";

    private RecyclerView mRecylerView;
    private Adapter mAdapter;
    private ArrayList<Item> mItemList;
    private ArrayList<ParseNutrientsJSON> mItemNutrientList;
    private RequestQueue mRequestQueue;
    private String FoodType;
    private String chosenHealth = "alcohol-free";
    private int searchResultCount = 99;
    private boolean finishedLoading = false;
    private boolean itemIsClickedAlready = false;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view__api__here);

        final TextView searchNotFound = findViewById(R.id.noItemsFound);
        searchNotFound.setTextColor(TRANSPARENT);
        final ProgressBar progress = findViewById(R.id.progressBar);



        /********************* Start runnable threads here ***********************/

        new Thread(new Runnable()
        {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run() {

                int timer = 7;//timer to allow everything to load to page and then check the number of items in the array.

                while(timer > 1)
                {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    timer -= 1;

                }

                if(mItemList.size() < 1)
                {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            int timer = 7;//timer for no searches found message.

                            while(timer > 1)
                            {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                timer -= 1;
                            }

                            progress.setVisibility(View.INVISIBLE);

                            if(mItemList.size() < 1)
                            {
                                searchNotFound.setTextColor(Color.BLACK);
                            }
                            else
                            {
                                finishedLoading = true;
                            }



                        }
                    }).start();
                }
                else
                {
                    progress.setVisibility(View.INVISIBLE);
                    finishedLoading = true;

                }

            }
        }).start();

        /************************* End of runnable threads ******************************/



        mRecylerView = findViewById(R.id.recycler_view);
        mRecylerView.setHasFixedSize(true);
        mRecylerView.setLayoutManager(new LinearLayoutManager(this));

        mItemList = new ArrayList<>();
        mItemNutrientList = new ArrayList<>();

        FoodType = getIntent().getStringExtra(FourthScreen.PUTEXTRA_FOODTYPE);
        chosenHealth = getIntent().getStringExtra(FourthScreen.PUTEXTRA_CHOSENHEALTH);

        mRequestQueue = Volley.newRequestQueue(this);

        parseJSON();
        ParseNutrients();

    }

    public void message(View view)
    {
        finish();
    }

    private void parseJSON()
    {
        String Url = "https://api.edamam.com/search?q=" + FoodType + "&app_id=f523ad6b&app_key=\n" +
                "12d374446d07305624230f7899aba286&from=0&to=" + searchResultCount + "&calories=591-722&health=" + chosenHealth;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Url, null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {
                try {

                    JSONArray jsonArray = response.getJSONArray("hits");

                    String allIngredients = "";

                    for (int index = 0; index < jsonArray.length(); ++index) {
                        JSONObject hit = jsonArray.getJSONObject(index);

                        String meal = hit.getJSONObject("recipe").getString("label");
                        String imageUrl = hit.getJSONObject("recipe").getString("image");
                        int calories = hit.getJSONObject("recipe").getInt("calories");
                        String prepingSteps = hit.getJSONObject("recipe").getString("url");
                        int servingSize = hit.getJSONObject("recipe").getInt("yield");


                        JSONArray recipe = hit.getJSONObject("recipe").getJSONArray("ingredientLines");


                        for (int index2 = 0; index2 < recipe.length(); ++index2) {
                            String eachIngredient = recipe.getString(index2) + "\n";

                            allIngredients += eachIngredient;
                        }


                        mItemList.add(new Item(imageUrl, meal, allIngredients, calories, prepingSteps, servingSize));

                        allIngredients = "";
                    }

                    mAdapter = new Adapter(View_API_Here.this, mItemList);
                    mRecylerView.setAdapter(mAdapter);
                    mAdapter.setOnItemClickListener(View_API_Here.this);

                } catch (JSONException e) {

                    e.printStackTrace();

                }

            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                error.printStackTrace();

            }
        });

        mRequestQueue.add(request);
    }

    void ParseNutrients()
    {
        String Url = "https://api.edamam.com/search?q=" + FoodType + "&app_id=f523ad6b&app_key=12d374446d07305624230f7899aba286&from=0&to=" + searchResultCount + "&calories=591-722&health=" + chosenHealth;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Url, null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject response)
            {

                try {

                    JSONArray jsonArray = response.getJSONArray("hits");


                    for (int index = 0; index < jsonArray.length(); ++index)
                    {


                        JSONObject hit = jsonArray.getJSONObject(index);


                        StringBuilder nutrientsString = new StringBuilder();
                        JSONArray nutrientArray = hit.getJSONObject("recipe").getJSONArray("digest");
                        int servingSize = hit.getJSONObject("recipe").getInt("yield");

                        for (int index3 = 0; index3 < nutrientArray.length(); ++index3)
                        {
                            JSONObject nutrientsObject = nutrientArray.getJSONObject(index3);

                            int amountOfNutrient;

                            nutrientsString.append(nutrientsObject.getString("label"));
                            nutrientsString.append(": ");
                            int totalNutrient = nutrientsObject.getInt("total");
                            amountOfNutrient = totalNutrient / servingSize;
                            nutrientsString.append(amountOfNutrient);
                            nutrientsString.append(" ");
                            nutrientsString.append(nutrientsObject.getString("unit"));
                            nutrientsString.append("\n");

                        }

                        mItemNutrientList.add(new ParseNutrientsJSON(nutrientsString.toString()));

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                error.printStackTrace();

            }
        });

        mRequestQueue.add(request);


    }

    @Override
    public void onItemClick(int position)
    {

        if(finishedLoading && !itemIsClickedAlready)
        {
            itemIsClickedAlready = true;

            new Thread(new Runnable() {
                @Override
                public void run() {

                    int timer = 2;// Timer to prevent double click into one single item view.

                    while(timer > 0)
                    {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        timer -= 1;
                    }

                    View_API_Here.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            itemIsClickedAlready = false;
                        }
                    });


                }
            }).start();

            Intent detailIntent = new Intent(this, DetailActivity.class);

            Item clickedItem = mItemList.get(position);

            ParseNutrientsJSON clickedItemNutrients = mItemNutrientList.get(position);

            detailIntent.putExtra(EXTRA_IMAGE_URL, clickedItem.getImageURL());
            detailIntent.putExtra(EXTRA_MEAL, clickedItem.getMeal());
            detailIntent.putExtra(EXTRA_RECIPE, clickedItem.getRecipe());
            detailIntent.putExtra(EXTRA_CALORIES, clickedItem.getcalories());
            detailIntent.putExtra(EXTRA_NUTRITION, clickedItemNutrients.getNutritionValue());
            detailIntent.putExtra(EXTRA_PREPARATION_STEPS, clickedItem.getPreparationSteps());
            detailIntent.putExtra(EXTRA_FOODTYPE, FoodType);
            detailIntent.putExtra(EXTRA_SERVING_SIZE, clickedItem.getServingSize());

            startActivity(detailIntent);
        }

    }

}
