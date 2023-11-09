package edu.uga.cs.project4;




import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CSVreader {

    public static void readCSV(Context context) {
        AssetManager assetManager = context.getAssets();
        DBhelper dbHelper = new DBhelper(context);

        try {
            InputStream inputStream = context.getAssets().open("StateCapitals.csv");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String state = parts[0].trim();
                String capital = parts[1].trim();
                String additionalCity2 = parts[2].trim();
                String additionalCity3 = parts[3].trim();

                // Insert data into the database
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(DBhelper.KEY_STATE, state);
                values.put(DBhelper.KEY_CAPITAL_CITY, capital);
                values.put(DBhelper.KEY_ADDITIONAL_CITY2, additionalCity2);
                values.put(DBhelper.KEY_ADDITIONAL_CITY3, additionalCity3);

                db.insert(DBhelper.TABLE_QUESTIONS, null, values);


// Call the printTable method to display the content of the table


            }
            printTableQuestions(context);
            reader.close();
        } catch (IOException e) {
            Log.e("CSVReader", "Error reading CSV file", e);
        }
    }


    public static void printTableQuestions(Context context) {
        DBhelper dbHelper = new DBhelper(context);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBhelper.TABLE_QUESTIONS, null);

        if (cursor.moveToFirst()) {
            do {
                long questionId = cursor.getLong(cursor.getColumnIndex(DBhelper.KEY_QUESTION_ID));
                String state = cursor.getString(cursor.getColumnIndex(DBhelper.KEY_STATE));
                String capital = cursor.getString(cursor.getColumnIndex(DBhelper.KEY_CAPITAL_CITY));
                String additionalCity = cursor.getString(cursor.getColumnIndex(DBhelper.KEY_ADDITIONAL_CITY2));
                String additionalCityy = cursor.getString(cursor.getColumnIndex(DBhelper.KEY_ADDITIONAL_CITY3));
                Log.d("Database", "ID" + questionId + "State: " + state + ", Capital: " + capital + "Additional City 1:" + additionalCity + "Additional City 2:" + additionalCityy);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
    }
    public static void printTableSaveQuizzes(Context context) {
        DBhelper dbHelper = new DBhelper(context);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBhelper.TABLE_SAVE_QUIZZES, null);

        if (cursor.moveToFirst()) {
            do {
                long quizId = cursor.getLong(cursor.getColumnIndex(DBhelper.KEY_QUIZ_ID));
                String date = cursor.getString(cursor.getColumnIndex(DBhelper.KEY_QUIZ_DATE));
                int result = cursor.getInt(cursor.getColumnIndex(DBhelper.KEY_QUIZ_RESULT));
                int questionsAnswered = cursor.getInt(cursor.getColumnIndex(DBhelper.KEY_QUESTIONS_ANSWERED));
                Log.d("Database", "Quiz ID: " + quizId + ", Date: " + date + ", Result: " + result + ", Questions Answered: " + questionsAnswered);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
    }
    public static void printTableRelationship(Context context) {
        DBhelper dbHelper = new DBhelper(context);

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBhelper.TABLE_RELATIONSHIP, null);

        if (cursor.moveToFirst()) {
            do {
                long quizId = cursor.getLong(cursor.getColumnIndex(DBhelper.KEY_QUIZ_ID));
                long questionId = cursor.getLong(cursor.getColumnIndex(DBhelper.KEY_QUESTION_ID));
                String userAnswer = cursor.getString(cursor.getColumnIndex(DBhelper.KEY_USER_ANSWER));

                Log.d("Database", "Quiz ID: " + quizId + ", Question ID: " + questionId + ", User Answer: " + userAnswer);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
    }



}