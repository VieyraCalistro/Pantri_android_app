package com.example.pantri;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FourthScreen extends AppCompatActivity {

    public static TextView APItext;
    private EditText FoodSearching;
    private TextView NoSearch;
    private String chosenHealth = "alcohol-free";
    public static String PUTEXTRA_CHOSENHEALTH = "chosenHealth";
    public static  String PUTEXTRA_FOODTYPE = "Foodtype";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth_screen);
        //APItext = (TextView) findViewById(R.id.API_info_here);

        Button buttonGo = findViewById(R.id.button_Go);

        Button clearButton = findViewById(R.id.clearButton);

        FoodSearching = findViewById(R.id.Search_Food);

        NoSearch = findViewById(R.id._invalidEntry);

        final CheckBox vegan = findViewById(R.id.VeganCheckBox);

        final Toast veganNote = Toast.makeText(getApplicationContext(), "This modifies most search results to vegan-friendly foods.", Toast.LENGTH_LONG);
        veganNote.setGravity(Gravity.TOP, 15, 240);

        vegan.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(vegan.isChecked())
                {
                    chosenHealth = "vegan";
                    veganNote.show();
                }
                else
                {
                    chosenHealth = "alcohol-free";
                }

            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                FoodSearching.setText("");
                NoSearch.setText("");
                vegan.setChecked(false);
            }
        });

        buttonGo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                StartSearch(v);
            }
        });
    }



    public void s(){



    }

    public void sendMessage (View view){
        finish();
    }


    public void StartSearch(View view)
    {

        //if(FoodType.equals("chicken"))
        //{

        //API_info process = new API_info();
        //process.execute();

        //}

        Intent View_API = new Intent(this, View_API_Here.class);

        View_API.putExtra(PUTEXTRA_FOODTYPE, FoodSearching.getText().toString());
        View_API.putExtra(PUTEXTRA_CHOSENHEALTH, chosenHealth);

        startActivity(View_API);

    }

    public void SearchChicken(View view)
    {
        FoodSearching.setText("Chicken");

        Intent searchChicken = new Intent(this, View_API_Here.class);

        searchChicken.putExtra(PUTEXTRA_FOODTYPE, FoodSearching.getText().toString());
        searchChicken.putExtra(PUTEXTRA_CHOSENHEALTH, chosenHealth);

        startActivity(searchChicken);
    }

    public void SearchPasta(View view)
    {
        FoodSearching.setText("Pasta");

        Intent searchPasta = new Intent(this, View_API_Here.class);

        searchPasta.putExtra(PUTEXTRA_FOODTYPE, FoodSearching.getText().toString());
        searchPasta.putExtra(PUTEXTRA_CHOSENHEALTH, chosenHealth);

        startActivity(searchPasta);
    }

    public void SearchSteak(View view)
    {
        FoodSearching.setText("Steak");

        Intent searchSteak = new Intent(this, View_API_Here.class);

        searchSteak.putExtra(PUTEXTRA_FOODTYPE, FoodSearching.getText().toString());
        searchSteak.putExtra(PUTEXTRA_CHOSENHEALTH, chosenHealth);

        startActivity(searchSteak);
    }

    public void SearchShrimp(View view)
    {
        FoodSearching.setText("Shrimp");

        Intent searchPizza = new Intent(this, View_API_Here.class);

        searchPizza.putExtra(PUTEXTRA_FOODTYPE, FoodSearching.getText().toString());
        searchPizza.putExtra(PUTEXTRA_CHOSENHEALTH, chosenHealth);

        startActivity(searchPizza);
    }

    public void SearchTofu(View view)
    {
        FoodSearching.setText("Tofu");

        Intent searchTofu = new Intent(this, View_API_Here.class);

        searchTofu.putExtra(PUTEXTRA_FOODTYPE, FoodSearching.getText().toString());
        searchTofu.putExtra(PUTEXTRA_CHOSENHEALTH, chosenHealth);

        startActivity(searchTofu);
    }

    public void SearchGarbanzos(View view)
    {
        FoodSearching.setText("Garbanzos");

        Intent searchGarbanzos = new Intent(this, View_API_Here.class);

        searchGarbanzos.putExtra(PUTEXTRA_FOODTYPE, FoodSearching.getText().toString());
        searchGarbanzos.putExtra(PUTEXTRA_CHOSENHEALTH, chosenHealth);

        startActivity(searchGarbanzos);
    }

    public void SearchTempeh(View view)
    {
        FoodSearching.setText("Tempeh");

        Intent searchTempeh = new Intent(this, View_API_Here.class);

        searchTempeh.putExtra(PUTEXTRA_FOODTYPE, FoodSearching.getText().toString());
        searchTempeh.putExtra(PUTEXTRA_CHOSENHEALTH, chosenHealth);

        startActivity(searchTempeh);
    }

    public void SearchBlackBeans(View view)
    {
        FoodSearching.setText("Black beans");

        Intent searchBlackBeans = new Intent(this, View_API_Here.class);

        searchBlackBeans.putExtra(PUTEXTRA_FOODTYPE, FoodSearching.getText().toString());
        searchBlackBeans.putExtra(PUTEXTRA_CHOSENHEALTH, chosenHealth);

        startActivity(searchBlackBeans);
    }


}