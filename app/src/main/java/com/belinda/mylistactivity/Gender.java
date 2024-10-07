package com.belinda.mylistactivity;

public enum Gender
{
    Male, Female, Transgender
}

class GenderUtils{
    public static int setImage(Gender g) {
        switch (g) {
            case Male:
                return R.drawable.male;
            case Female:
                return R.drawable.female;
            case Transgender:
                return R.drawable.transgender;
            default:
                return R.drawable.unknown;
        }
    }
}
