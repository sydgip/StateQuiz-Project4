package edu.uga.cs.project4;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Date;



public class QuizResultFragment extends Fragment {

    private String quizResult;
    private Date quizDateTime;
    private int selectedAnswer = -1; // Initialize to an invalid value

    public QuizResultFragment() {
        // Required empty public constructor
    }

    public static QuizResultFragment newInstance(String result) {
        QuizResultFragment fragment = new QuizResultFragment();
        Bundle args = new Bundle();
        args.putString("quiz_result", result);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz_result, container, false);

        quizResult = getArguments().getString("quiz_result");
        quizDateTime = new Date(); // Get the current date and time

        // Display the quiz result
        TextView quizResultTextView = view.findViewById(R.id.quizResultTextView);
        quizResultTextView.setText(quizResult);

        // Display the date and time
        TextView quizDateTimeTextView = view.findViewById(R.id.quizDateTimeTextView);
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy, HH:mm:ss");
        quizDateTimeTextView.setText(dateFormat.format(quizDateTime));

        // Save the quiz result to the database when the button is clicked
        Button saveQuizResultButton = view.findViewById(R.id.saveQuizResultButton);
        saveQuizResultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveQuizResultToDatabase();
            }
        });

        return view;
    }

    private void saveQuizResultToDatabase() {
        // Perform database insertion in an AsyncTask
        new SaveQuizResultTask().execute();
    }

    private class SaveQuizResultTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            // Perform database insertion here
            // You can use a database helper class or Room database to insert the quiz result
            // Store quizResult, quizDateTime, and any other necessary information in the database

            // Example using Room database (you'll need to set up Room in your app):

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            // Handle any UI updates or notifications after database insertion
        }
    }
}