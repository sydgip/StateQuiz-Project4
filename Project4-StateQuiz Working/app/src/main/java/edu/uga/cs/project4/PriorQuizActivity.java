package edu.uga.cs.project4;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class PriorQuizActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prior_quiz);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new PriorQuizFragment()) // Assuming you have a container with the id 'container' in your layout
                    .commit();
        }
    }
}
