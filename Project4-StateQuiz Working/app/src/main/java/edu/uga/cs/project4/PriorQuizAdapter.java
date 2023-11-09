package edu.uga.cs.project4;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class PriorQuizAdapter extends RecyclerView.Adapter<PriorQuizAdapter.QuizResultViewHolder> {

    private List<QuizResult> quizResultsList;

    public PriorQuizAdapter(List<QuizResult> quizResultsList) {
        this.quizResultsList = quizResultsList;
    }

    @NonNull
    @Override
    public QuizResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_prior_quiz_list, parent, false);
        return new QuizResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizResultViewHolder holder, int position) {
        QuizResult quizResult = quizResultsList.get(position);

        holder.quizDateTimeTextView.setText("Date and Time: " + quizResult.getQuizDate());
        holder.quizScoreTextView.setText("Score: " + quizResult.getQuizResult() + " / " + quizResult.getQuestionsAnswered());
    }

    @Override
    public int getItemCount() {
        return quizResultsList.size();
    }

    public class QuizResultViewHolder extends RecyclerView.ViewHolder {
        public TextView quizDateTimeTextView;
        public TextView quizScoreTextView;

        public QuizResultViewHolder(View itemView) {
            super(itemView);
            quizDateTimeTextView = itemView.findViewById(R.id.quizDateTimeTextView);
            quizScoreTextView = itemView.findViewById(R.id.quizScoreTextView);
        }
    }
}
