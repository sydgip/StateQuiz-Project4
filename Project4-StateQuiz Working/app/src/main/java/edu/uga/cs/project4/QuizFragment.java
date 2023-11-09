package edu.uga.cs.project4;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class QuizFragment extends Fragment {

    private ViewPager viewPager;
    private QuestionPagerAdapter questionPagerAdapter;
    private List<String> quizQuestions = new ArrayList<>();
    private List<String> quizCapitals = new ArrayList<>();
    private List<String> quizAdditionalCities = new ArrayList<>();
    private List<String> quizAdditionalCities2 = new ArrayList<>();
    private int currentQuestionIndex = 0;
    private int userScore = 0;
    private int selectedAnswer = -1; // Initialize to an invalid value
    private boolean answerChecked = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz, container, false);

        DBhelper dbHelper = new DBhelper(getActivity());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBhelper.TABLE_QUESTIONS + " ORDER BY RANDOM() LIMIT 6", null);
        int count = 0;
        while (cursor.moveToNext() && count < 6) {
            String state = cursor.getString(cursor.getColumnIndex(DBhelper.KEY_STATE));
            String capital = cursor.getString(cursor.getColumnIndex(DBhelper.KEY_CAPITAL_CITY));
            String additionalCity = cursor.getString(cursor.getColumnIndex(DBhelper.KEY_ADDITIONAL_CITY2));
            String additionalCityy = cursor.getString(cursor.getColumnIndex(DBhelper.KEY_ADDITIONAL_CITY3));
            quizQuestions.add("Question: What is the capital of " + state + "?");
            quizCapitals.add(capital);
            quizAdditionalCities.add(additionalCity);
            quizAdditionalCities2.add(additionalCityy);
            count++;
        }

        cursor.close();
        db.close();

        // Shuffle the quiz questions
        Collections.shuffle(quizQuestions);

        // Initialize ViewPager and adapter
        viewPager = view.findViewById(R.id.viewPager);
        questionPagerAdapter = new QuestionPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(questionPagerAdapter);

        // Set up the question counter
        final TextView questionCounterTextView = view.findViewById(R.id.questionCounterTextView);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position < quizQuestions.size()) {
                    int answer = ((QuizQuestionFragment) questionPagerAdapter.instantiateItem(viewPager, position)).getSelectedAnswer();
                    // Save the selected answer
                    selectedAnswer = answer;
                }
            }

            @Override
            public void onPageSelected(int position) {
                int questionNumber = position + 1;
                questionCounterTextView.setText("Question " + questionNumber + " of " + quizQuestions.size());

                // Check the selected answer and increment the score
                checkSelectedAnswer(selectedAnswer);
                Log.d("QuizFragment", "Question - Selected Answer: " + selectedAnswer);
                Log.d("QuizFragment", "User Score: " + userScore);

                // Reset the selected answer to an invalid value for the next question
                selectedAnswer = -1;

                if (position == quizQuestions.size()) {
                    // Assuming you want to save the result after the quiz is completed
                    // You may call this wherever you determine that the quiz is finished

                    // Get the current date and time
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String quizDate = dateFormat.format(new Date());

                    // Assuming userScore contains the number of correct answers and quizQuestions.size() contains the total number of questions
                    int questionsAnswered = quizQuestions.size();
                    int quizResult = userScore;

                    // Call the insertQuizResult method
                    DBhelper dbHelper = new DBhelper(getActivity());
                    dbHelper.insertQuizResult(quizDate, quizResult, questionsAnswered);

                    // Save the quiz result to the database
                    saveQuizResultToDatabase();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return view;
    }

    private void checkSelectedAnswer(int selectedAnswer) {
        if (selectedAnswer == 1) {
            userScore++; // Increment the score
        }
    }

    private class QuestionPagerAdapter extends FragmentPagerAdapter {

        public QuestionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public Fragment getItem(int position) {
            if (position < quizQuestions.size()) {
                // Create and return QuizQuestionFragment for questions
                String question = quizQuestions.get(position);
                String capital = quizCapitals.get(position);
                String additionalCity = quizAdditionalCities.get(position);
                String additionalCityy = quizAdditionalCities2.get(position);

                return QuizQuestionFragment.newInstance(question, capital, additionalCity, additionalCityy);
            } else if (position == quizQuestions.size()) {
                // Create and return QuizResultFragment when position matches the number of questions
                return QuizResultFragment.newInstance("Quiz Result: " + userScore + " / " + quizQuestions.size());
            } else {
                return null;
            }
        }

        @Override
        public int getCount() {
            return quizQuestions.size() + 1;
        }
    }

    private void saveQuizResultToDatabase() {
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        int result = userScore;
        int questionsAnswered = quizQuestions.size();

        SaveQuizResultTask saveQuizResultTask = new SaveQuizResultTask(getContext(), date, result, questionsAnswered);
        saveQuizResultTask.execute();
    }

    private class SaveQuizResultTask extends AsyncTask<Void, Void, Void> {
        private Context context;
        private String date;
        private int result;
        private int questionsAnswered;

        public SaveQuizResultTask(Context context, String date, int result, int questionsAnswered) {
            this.context = context;
            this.date = date;
            this.result = result;
            this.questionsAnswered = questionsAnswered;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            // Perform database insertion here
            DBhelper dbHelper = new DBhelper(context);
            dbHelper.insertQuizResult(date, result, questionsAnswered);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            // Handle any UI updates or notifications after database insertion
            // For example, you can display a Toast to notify the user that the quiz result has been saved
            Toast.makeText(context, "Quiz result saved!", Toast.LENGTH_SHORT).show();
        }
    }
}