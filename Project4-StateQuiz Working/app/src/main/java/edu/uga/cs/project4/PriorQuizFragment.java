package edu.uga.cs.project4;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class PriorQuizFragment extends Fragment {

    private List<QuizResult> quizResultsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private PriorQuizAdapter adapter;
    private DBhelper dbHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_prior_quiz, container, false);

        // Initialize DBHelper
        dbHelper = new DBhelper(requireContext());

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.priorQuizzesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new PriorQuizAdapter(quizResultsList);
        recyclerView.setAdapter(adapter);

        // Load quiz results from the database
        loadQuizResults();

        return view;
    }

    private void loadQuizResults() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBhelper.TABLE_SAVE_QUIZZES + " ORDER BY " + DBhelper.KEY_QUIZ_DATE + " DESC", null);

        while (cursor.moveToNext()) {
            int quizId = cursor.getInt(cursor.getColumnIndex(DBhelper.KEY_QUIZ_ID));
            String quizDate = cursor.getString(cursor.getColumnIndex(DBhelper.KEY_QUIZ_DATE));
            int quizResult = cursor.getInt(cursor.getColumnIndex(DBhelper.KEY_QUIZ_RESULT));
            int questionsAnswered = cursor.getInt(cursor.getColumnIndex(DBhelper.KEY_QUESTIONS_ANSWERED));

            QuizResult quizResultItem = new QuizResult(quizId, quizDate, quizResult, questionsAnswered);
            quizResultsList.add(quizResultItem);
        }

        cursor.close();
        db.close();

        adapter.notifyDataSetChanged(); // Notify the adapter that the data has changed
    }
}

