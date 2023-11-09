package edu.uga.cs.project4;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBhelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "quiz_database";
    public static final int DATABASE_VERSION = 1;

    // Table names
    public static final String TABLE_QUESTIONS = "quiz_questions";
    public static final String TABLE_RELATIONSHIP = "quiz_question_relationship";
    public static final String TABLE_SAVE_QUIZZES = "user_scores";


    // Table columns for quiz_questions
    public static final String KEY_QUESTION_ID = "question_id";

    public static final String KEY_STATE = "state";
    public static final String KEY_CAPITAL_CITY = "capital_city";
    public static final String KEY_ADDITIONAL_CITY2 = "additional_city1";
    public static final String KEY_ADDITIONAL_CITY3 = "additional_city2";

    // Table columns for quizzes
    public static final String KEY_QUIZ_ID = "quiz_id";
    public static final String KEY_QUIZ_DATE = "quiz_date";
    public static final String KEY_QUIZ_RESULT = "quiz_result";
    public static final String KEY_QUESTIONS_ANSWERED = "questions_answered";

    // Table columns for quiz_question_relationship
    public static final String KEY_USER_ANSWER = "user_answer";

    // Table columns for user_scores
    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_USER_SCORE = "user_score";

    // This table holds the information read in from the CSV .
    private static final String CREATE_TABLE_QUESTIONS = "CREATE TABLE " + TABLE_QUESTIONS + "(" +
            KEY_QUESTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_STATE + " TEXT," +
            KEY_CAPITAL_CITY + " TEXT," +
            KEY_ADDITIONAL_CITY2 + " TEXT," +
            KEY_ADDITIONAL_CITY3+ " TEXT" +
            ")";

    private static final String CREATE_TABLE_SAVE_QUIZZES = "CREATE TABLE " + TABLE_SAVE_QUIZZES + "(" +
            KEY_QUIZ_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_QUIZ_DATE + " DATETIME," +
            KEY_QUIZ_RESULT + " INTEGER," +
            KEY_QUESTIONS_ANSWERED + " INTEGER" +
            ")";
    private static final String CREATE_TABLE_RELATIONSHIP = "CREATE TABLE " + TABLE_RELATIONSHIP + "(" +
            KEY_QUIZ_ID + " INTEGER," +
            KEY_QUESTION_ID + " INTEGER," +
            KEY_USER_ANSWER + " TEXT," +
            "PRIMARY KEY (" + KEY_QUIZ_ID + "," + KEY_QUESTION_ID + ")," +
            "FOREIGN KEY (" + KEY_QUIZ_ID + ") REFERENCES " + TABLE_SAVE_QUIZZES + "(" + KEY_QUIZ_ID + ")," +
            "FOREIGN KEY (" + KEY_QUESTION_ID + ") REFERENCES " + TABLE_QUESTIONS + "(" + KEY_QUESTION_ID + ")" +
            ")";

    public long getExistingQuizId() {
        SQLiteDatabase db = this.getReadableDatabase();
        long quizId = -1; // Default value in case no ID is found

        String query = "SELECT " + KEY_QUIZ_ID + " FROM " + TABLE_SAVE_QUIZZES + " LIMIT 1"; // Change the table name and column name as per your actual schema

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            quizId = cursor.getLong(cursor.getColumnIndex(KEY_QUIZ_ID));
        }

        cursor.close();
        db.close();

        return quizId;
    }

    public long getExistingQuestionId() {
        SQLiteDatabase db = this.getReadableDatabase();
        long questionId = -1; // Default value in case no ID is found

        String query = "SELECT " + KEY_QUESTION_ID + " FROM " + TABLE_QUESTIONS + " LIMIT 1"; // Change the table name and column name as per your actual schema

        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            questionId = cursor.getLong(cursor.getColumnIndex(KEY_QUESTION_ID));
        }

        cursor.close();
        db.close();

        return questionId;
    }






    public void insertRelationship(int quizId, int questionId, String userAnswer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_QUIZ_ID, quizId);
        values.put(KEY_QUESTION_ID, questionId);
        values.put(KEY_USER_ANSWER, userAnswer);

        // Insert the row
        db.insert(TABLE_RELATIONSHIP, null, values);
        db.close(); // Closing database connection
    }

    public DBhelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_QUESTIONS);
        db.execSQL(CREATE_TABLE_RELATIONSHIP);
        db.execSQL(CREATE_TABLE_SAVE_QUIZZES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SAVE_QUIZZES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RELATIONSHIP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SAVE_QUIZZES);
        onCreate(db);
    }

    public void insertQuestion(String country, String state, String capital, String city1, String city2) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_STATE, state);
        values.put(KEY_CAPITAL_CITY, capital);
        values.put(KEY_ADDITIONAL_CITY2, city1);
        values.put(KEY_ADDITIONAL_CITY3, city2);

        // Insert the row
        db.insert(TABLE_QUESTIONS, null, values);
        db.close(); // Closing database connection
    }
    public void insertQuizResult(String date, int result, int questionsAnswered) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_QUIZ_DATE, date);
        values.put(KEY_QUIZ_RESULT, result);
        values.put(KEY_QUESTIONS_ANSWERED, questionsAnswered);

        // Insert the row
        db.insert(TABLE_SAVE_QUIZZES, null, values);
        db.close(); // Closing database connection
    }

    public String getCorrectAnswerForQuestion(int questionId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {KEY_CAPITAL_CITY}; // Assuming KEY_CAPITAL_CITY is the correct answer column in TABLE_QUESTIONS
        String selection = KEY_QUESTION_ID + " = ?";
        String[] selectionArgs = {String.valueOf(questionId)};
        Cursor cursor = db.query(TABLE_QUESTIONS, columns, selection, selectionArgs, null, null, null);
        String correctAnswer = null;
        if (cursor.moveToFirst()) {
            correctAnswer = cursor.getString(cursor.getColumnIndex(KEY_CAPITAL_CITY));
        }
        cursor.close();
        db.close();
        return correctAnswer;
    }

}