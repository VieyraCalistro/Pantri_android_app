package com.example.pantri;

public class Item
{
    private String mIMageUrl;
    private String mMeal;
    private String mrecipe;
    private int mcalories;
    private String mPrepingSteps;
    private int mservingSize;

    public Item(String imageUrl, String meal, String recipe, int calories, String prepingSteps, int servingSize)
    {
        mIMageUrl = imageUrl;
        mMeal = meal;
        mrecipe = recipe;
        mcalories = calories;
        mPrepingSteps = prepingSteps;
        mservingSize = servingSize;

    }

    public String getImageURL()
    {
        return mIMageUrl;
    }

    public String getMeal()
    {
        return mMeal;
    }

    public String getRecipe()
    {
        return mrecipe;

    }

    public int getcalories()
    {
        return mcalories;
    }

    public String getPreparationSteps()
    {
        return mPrepingSteps;
    }

    public int getServingSize()
    {
        return mservingSize;
    }


}