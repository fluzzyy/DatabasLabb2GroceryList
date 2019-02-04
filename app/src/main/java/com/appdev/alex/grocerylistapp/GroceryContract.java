package com.appdev.alex.grocerylistapp;

import android.provider.BaseColumns;

public class GroceryContract  {

    private GroceryContract(){


    }

    public static final class GroceryEntry implements BaseColumns{
        public static final String  TABLE_NAME =  "grocerylist";
        public static final String COLUMN_NAME = "name";
        public static final String COLUM_AMOUNT = "amount";
        public static final String COLUMN_TIMESTAMP = "timestamp";

    }

}
