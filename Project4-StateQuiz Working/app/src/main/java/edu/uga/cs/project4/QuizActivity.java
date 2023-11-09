package edu.uga.cs.project4;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class QuizActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CSVreader.readCSV(this);
        setContentView(R.layout.activity_quiz); // Set the activity layout

        // Load and display the QuizFragment
        QuizFragment quizFragment = new QuizFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, quizFragment);
        transaction.commit();
    }

}