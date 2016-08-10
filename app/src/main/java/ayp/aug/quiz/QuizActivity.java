package ayp.aug.quiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private Button mYesButton;
    private Button mNoButton;
    private Button mNextButton;
    private TextView mQuestionTextView;

    private Question[] mQuestionArray = new Question[] {
            new Question(R.string.quiz_question_1, Question.Answer.NO),
            new Question(R.string.quiz_question_2, Question.Answer.YES),
            new Question(R.string.quiz_question_3, Question.Answer.NO)
    };

    private Question mCurrentQuestion;

    private int mCurrentQuestionIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_quiz2);

        // Wiring view object
        mYesButton = (Button) findViewById(R.id.yes_button);
        mNoButton = (Button) findViewById(R.id.no_button);
        mNextButton = (Button) findViewById(R.id.next_question_button);
        mQuestionTextView = (TextView) findViewById(R.id.quiz_text);

        mCurrentQuestion = getNextQuestion();
        updateUI();

        //
        mYesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(Question.Answer.YES);
            }
        });

        mNoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(Question.Answer.NO);
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentQuestion = getNextQuestion();
                updateUI();
            }
        });
    }

    private Question getNextQuestion() {
        mCurrentQuestionIndex++;
        if( mCurrentQuestionIndex >= mQuestionArray.length) { // 3 = IndexOutOfBound
            mCurrentQuestionIndex = 0;
        }

        return mQuestionArray[mCurrentQuestionIndex];
    }

    private void updateUI() {
        int resId = mCurrentQuestion.getQuestionResId();

        mQuestionTextView.setText(resId);
    }

    private void checkAnswer(Question.Answer answer) {
        Question.Answer correctAnswer = mCurrentQuestion.getAnswer();

        int result;
        if(answer == correctAnswer) {
            result = R.string.correct_answer;
        } else {
            result = R.string.incorrect_answer;
        }

        // Correct
        Toast.makeText(QuizActivity.this,
                result,
                Toast.LENGTH_SHORT).show();
    }
}
