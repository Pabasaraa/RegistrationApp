package com.example.registerapp.database;

import android.provider.BaseColumns;

public class DBContract{
    private DBContract(){}

    public static class UserDB implements BaseColumns {
        public static final String TABLE_NAME = "userinfo";
        public static final String COLUMN_1 = "name";
        public static final String COLUMN_2 = "username";
        public static final String COLUMN_3 = "email";
        public static final String COLUMN_4 = "dob";
        public static final String COLUMN_5 = "password";
        public static final String COLUMN_6 = "gender";
    }
}
