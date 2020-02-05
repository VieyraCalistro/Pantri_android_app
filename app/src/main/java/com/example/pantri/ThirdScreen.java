package com.example.pantri;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ThirdScreen extends AppCompatActivity {


    Spinner GenderSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_screen);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        GenderSpinner = findViewById(R.id._genderSpinner);
        ArrayAdapter<String> genderSpinnerAdapter = new ArrayAdapter<>(ThirdScreen.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.chooseGender));
        GenderSpinner.setAdapter(genderSpinnerAdapter);


        final EditText weight = findViewById(R.id._weight);
        final EditText heightInFeet = findViewById(R.id._foot);
        final EditText remainingHeightInInches = findViewById(R.id._inches);
        final EditText age = findViewById(R.id._age);


        final TextView calories_result = findViewById(R.id._calories_result);
        final TextView protein_result = findViewById(R.id._protein_result);
        final TextView carbs_result = findViewById(R.id._carbs_result);
        final TextView fats_result = findViewById(R.id._fats_result);


        Button CalculateMacroNutrients = findViewById(R.id._dailyIntakeButton);
        Button ClearButton = findViewById(R.id._clear);


        ClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weight.setText("");
                heightInFeet.setText("");
                remainingHeightInInches.setText("");
                age.setText("");
                protein_result.setText("");
                carbs_result.setText("");
                fats_result.setText("");
                calories_result.setText("");
                weight.setError(null);
                heightInFeet.setError(null);
                remainingHeightInInches.setError(null);
                age.setError(null);

            }
        });


        CalculateMacroNutrients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(weight.getText().toString().trim().length() > 0 && heightInFeet.getText().toString().trim().length() > 0 && remainingHeightInInches.getText().toString().trim().length() > 0 && age.getText().toString().trim().length() > 0)
                {
                    double weightToDouble = ParseEditTextToDouble(weight);
                    int heightInFeetToInt = ParseEditTextToInt(heightInFeet);
                    int remainingHeightInInchesToInt = ParseEditTextToInt(remainingHeightInInches);
                    int totalHeightToInches = TotalHieghtIntoInches(heightInFeetToInt, remainingHeightInInchesToInt);
                    int ageToInt = ParseEditTextToInt(age);


                    if(weightToDouble > 400)
                    {
                        Toast weightErrorToast = Toast.makeText(ThirdScreen.this, "Max for weight is 400.", Toast.LENGTH_LONG);
                        weightErrorToast.setGravity(Gravity.TOP, 330, 300);
                        weightErrorToast.show();
                    }
                    else if(heightInFeetToInt > 7)
                    {
                        Toast heightInFeetErrorToast = Toast.makeText(ThirdScreen.this, "Max for feet is 7.", Toast.LENGTH_LONG);
                        heightInFeetErrorToast.setGravity(Gravity.TOP, 305, 405);
                        heightInFeetErrorToast.show();
                    }
                    else if(remainingHeightInInchesToInt > 12)
                    {
                        Toast remainingHeightInInchesErrorToast = Toast.makeText(ThirdScreen.this, "Max for inches is 12.", Toast.LENGTH_LONG);
                        remainingHeightInInchesErrorToast.setGravity(Gravity.TOP, 318, 405);
                        remainingHeightInInchesErrorToast.show();
                    }
                    else if(ageToInt > 100)
                    {
                        Toast ageErrorToast = Toast.makeText(ThirdScreen.this, "Max for age is 100.", Toast.LENGTH_LONG);
                        ageErrorToast.setGravity(Gravity.TOP, 315, 493);
                        ageErrorToast.show();
                    }
                    else if(weightToDouble < 1)
                    {
                        Toast weightErrorToast = Toast.makeText(ThirdScreen.this, "Min for weight is 1.", Toast.LENGTH_LONG);
                        weightErrorToast.setGravity(Gravity.TOP, 330, 300);
                        weightErrorToast.show();
                    }
                    else if(heightInFeetToInt < 1)
                    {
                        Toast heightInFeetErrorToast = Toast.makeText(ThirdScreen.this, "Min for feet is 1.", Toast.LENGTH_LONG);
                        heightInFeetErrorToast.setGravity(Gravity.TOP, 305, 405);
                        heightInFeetErrorToast.show();
                    }
                    else if(ageToInt < 1)
                    {
                        Toast ageErrorToast = Toast.makeText(ThirdScreen.this, "Min for age is 1.", Toast.LENGTH_LONG);
                        ageErrorToast.setGravity(Gravity.TOP, 315, 493);
                        ageErrorToast.show();
                    }
                    else if(weightToDouble < 401 && heightInFeetToInt < 8 && remainingHeightInInchesToInt < 13 && ageToInt < 101)
                    {
                        int Totalcalories = CalculateCalories(GenderSpinner.getSelectedItemPosition(), weightToDouble, totalHeightToInches, ageToInt);
                        int TotalProtein = CalculateProtein(weightToDouble);
                        int TotalMinFats = CalculateMinFat(Totalcalories, GenderSpinner.getSelectedItemPosition());
                        int TotalMaxFats = CalulateMaxFat(Totalcalories, GenderSpinner.getSelectedItemPosition());
                        int TotalMinCarbs = MinCarbsIntake(Totalcalories, TotalProtein, TotalMaxFats);
                        int TotalMaxCarbs = MaxCarbsIntake(Totalcalories, TotalProtein, TotalMinFats);

                        calories_result.setText(Totalcalories + "");
                        protein_result.setText(TotalProtein + "");
                        carbs_result.setText(TotalMinCarbs + " - " + TotalMaxCarbs);
                        fats_result.setText(TotalMinFats + " - " + TotalMaxFats);
                    }

                }
                else
                {
                    String emptyFieldError = "Cannot be empty";

                    if(weight.getText().toString().trim().length() < 1)
                    {
                        weight.setError(emptyFieldError);
                    }
                    if(heightInFeet.getText().toString().length() < 1)
                    {
                        heightInFeet.setError(emptyFieldError);
                    }
                    if(remainingHeightInInches.getText().toString().length() < 1)
                    {
                        remainingHeightInInches.setError(emptyFieldError);
                    }
                    if(age.getText().toString().trim().length() < 1)
                    {
                        age.setError(emptyFieldError);
                    }
                    if(weight.getText().toString().trim().length() < 1 && heightInFeet.getText().toString().length() < 1)
                    {
                        weight.setError(emptyFieldError);
                        heightInFeet.setError(emptyFieldError);
                    }
                    if(weight.getText().toString().trim().length() < 1 && heightInFeet.getText().toString().length() < 1 && remainingHeightInInches.getText().toString().length() < 1)
                    {
                        weight.setError(emptyFieldError);
                        heightInFeet.setError(emptyFieldError);
                        remainingHeightInInches.setError(emptyFieldError);
                    }
                    if(weight.getText().toString().trim().length() < 1 && heightInFeet.getText().toString().length() < 1 && remainingHeightInInches.getText().toString().length() < 1 && age.getText().toString().trim().length() < 1)
                    {
                        weight.setError(emptyFieldError);
                        heightInFeet.setError(emptyFieldError);
                        remainingHeightInInches.setError(emptyFieldError);
                        age.setError(emptyFieldError);
                    }
                    if(weight.getText().toString().trim().length() < 1 && remainingHeightInInches.getText().toString().length() < 1)
                    {
                        weight.setError(emptyFieldError);
                        remainingHeightInInches.setError(emptyFieldError);
                    }
                    if(heightInFeet.getText().toString().length() < 1 && remainingHeightInInches.getText().toString().length() < 1)
                    {
                        heightInFeet.setError(emptyFieldError);
                        remainingHeightInInches.setError(emptyFieldError);
                    }
                    if(heightInFeet.getText().toString().length() < 1 && remainingHeightInInches.getText().toString().length() < 1 && age.getText().toString().trim().length() < 1)
                    {
                        heightInFeet.setError(emptyFieldError);
                        remainingHeightInInches.setError(emptyFieldError);
                        age.setError(emptyFieldError);
                    }
                    if(remainingHeightInInches.getText().toString().length() < 1 && age.getText().toString().trim().length() < 1)
                    {
                        remainingHeightInInches.setError(emptyFieldError);
                        age.setError(emptyFieldError);
                    }
                    if(weight.getText().toString().trim().length() < 1 && age.getText().toString().trim().length() < 1)
                    {
                        weight.setError(emptyFieldError);
                        age.setError(emptyFieldError);
                    }
                    if(age.getText().toString().trim().length() < 1 && heightInFeet.getText().toString().trim().length() < 1)
                    {
                        heightInFeet.setError(emptyFieldError);
                        age.setError(emptyFieldError);
                    }

                }


            }
        });

    }

    public void sendMessage(View view) {
        finish();
    }

    public int ParseEditTextToInt(EditText intValue)
    {
        int numberResult = Integer.parseInt(intValue.getText().toString());

        return numberResult;
    }

    public double ParseEditTextToDouble(EditText doubleValue)
    {
        double numberResult = Double.parseDouble(doubleValue.getText().toString());

        return numberResult;
    }

    public int CalculateProtein(double weightInLbs)
    {
        int protein = (int) ((weightInLbs / 2.2) * 0.8);

        return protein;
    }

    public int CalculateMinFat(int dailyCalories, int gender)
    {
        int MinimumFatIntake = 0;

        switch (gender)
        {
            case 0: //Male
                MinimumFatIntake = (int) (dailyCalories * .20) / 9;
                break;
            case 1: //Female
                MinimumFatIntake = (int) ((dailyCalories * .20) / 9) - 7;
                break;
            default:
                    break;
        }

        return MinimumFatIntake;
    }

    public int CalulateMaxFat (int dailyCalories, int gender)
    {

        int MaxmimumFatIntake= 0;

        switch (gender)
        {
            case 0: //Male
                MaxmimumFatIntake = (int) (dailyCalories * 0.35) / 9;
                break;
            case 1: //Female
                MaxmimumFatIntake = (int) ((dailyCalories * 0.35) / 9) -7;
                break;
            default:
                    break;
        }


        return MaxmimumFatIntake;
    }

    public int MinCarbsIntake(int calories, int protein, int maxFats)
    {
        int minCarbs = (calories - (protein + maxFats)) / 4;

        return minCarbs;
    }

    public int MaxCarbsIntake(int calories, int protein, int minFats)
    {
        int maxCarbs = (calories - (protein + minFats)) / 4;

        return maxCarbs;
    }

    public int CalculateCalories(int gender, double weight, int heightInInches, int age)
    {
        int dailyCalories = 0;

        double calories;

        switch(gender)
        {
            case 0: //Male
                calories = (weight * 6.23) +  (heightInInches * 12.7) - (age * 6.8) + 66;

                dailyCalories = (int) calories;
                break;
            case 1: //Female
                calories = (weight * 4.35) +  (heightInInches * 4.7) - (age * 4.7) + 655;

                dailyCalories = (int) calories;
                break;
            default:
                    break;
        }

        return dailyCalories;
    }

    public int TotalHieghtIntoInches(int foot, int inches)
    {
        int totalHeightInInches = (foot * 12) + inches;

        return totalHeightInInches;
    }


}