package edu.uga.cs.project4;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class QuizQuestionFragment extends Fragment {

    private static final String ARG_QUESTION = "question";
    private static final String ARG_CAPITAL = "capital";
    private static final String ARG_ADDITIONAL_CITY = "additionalCity1";
    private static final String ARG_ADDITIONAL_CITYY = "additionalCity2";

    private static String questionText;
    private static String capital;
    private static String additionalCity1;
    private static String additionalCity2;
    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private int selectedAnswer = 0; // Initialize to an invalid value
    public QuizQuestionFragment() {
        // Required empty public constructor
    }



    public static QuizQuestionFragment newInstance(String question, String capital, String additionalCity, String additionalCityy) {
        QuizQuestionFragment fragment = new QuizQuestionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_QUESTION, question);
        args.putString(ARG_CAPITAL, capital);
        args.putString(ARG_ADDITIONAL_CITY, additionalCity);
        args.putString(ARG_ADDITIONAL_CITYY, additionalCityy);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            questionText = getArguments().getString(ARG_QUESTION);
            capital = getArguments().getString(ARG_CAPITAL);
            additionalCity1 = getArguments().getString(ARG_ADDITIONAL_CITY);
            additionalCity2 = getArguments().getString(ARG_ADDITIONAL_CITYY);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz_question, container, false);

        // Find the TextView in your fragment's layout
        TextView questionTextView = view.findViewById(R.id.questionTextView);

        // Set the question text in the TextView
        if (questionText != null) {
            questionTextView.setText(questionText);
        }

        radioButton1 = view.findViewById(R.id.answerOption1);
        radioButton2 = view.findViewById(R.id.answerOption2);
        radioButton3 = view.findViewById(R.id.answerOption3);


        // Set the text for answer choices (RadioButtons)
        radioButton1.setText(capital); // Correct answer
        radioButton2.setText(additionalCity1);
        radioButton3.setText(additionalCity2);
        Log.d("Correct answer", "correct answer " + capital);


        // Set up click listeners for the radio buttons
        radioButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectedAnswer = 1;
                Log.d("RadioButton1", "Selected Answer: " + selectedAnswer);

            }
        });
        radioButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if the selected option is correct (capital)

                selectedAnswer = 2;
                Log.d("RadioButton2", "Selected Answer: " + selectedAnswer);

            }
        });
        radioButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectedAnswer = 3;
                Log.d("RadioButton3", "Selected Answer: " + selectedAnswer);
            }
        });


        return view;
    }
    public int getSelectedAnswer() {
        return selectedAnswer;
    }
}