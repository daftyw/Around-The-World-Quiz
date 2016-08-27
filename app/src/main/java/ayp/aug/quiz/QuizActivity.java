package ayp.aug.quiz;

import android.app.Activity;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final String QUIZ_INDEX = "QuizActivity.QUIZ_INDEX";
    private static final int REQUEST_CHEAT = 21099;
    private static final String CHEAT_STATE = "QuizActivity.CHEAT_STATE";

    private Button mYesButton;
    private Button mNoButton;
    private Button mNextButton;
    private Button mCheatButton;
    private TextView mQuestionTextView;

    private Question[] mQuestionArray = new Question[] {
            new Question(R.string.quiz_question_1, Question.Answer.NO),
            new Question(R.string.quiz_question_2, Question.Answer.YES),
            new Question(R.string.quiz_question_3, Question.Answer.NO)
    };

    private Question mCurrentQuestion;

    private int mCurrentQuestionIndex;
    private boolean mCheated;

    @Override
    protected void onStop() {
        super.onStop();

        Log.d(TAG, "On Stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.v(TAG, "On Destroy");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.i(TAG, "On Pause");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.i(TAG, "On Resume");
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.d(TAG, "On Start");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.v(TAG, "On create");
        setContentView(R.layout.activity_quiz2);

        if(savedInstanceState != null) {
            mCurrentQuestionIndex = savedInstanceState.getInt(QUIZ_INDEX, 0);
            mCurrentQuestionIndex--;
        }

        // Wiring view object
        mYesButton = (Button) findViewById(R.id.yes_button);
        mNoButton = (Button) findViewById(R.id.no_button);
        mNextButton = (Button) findViewById(R.id.next_question_button);
        mCheatButton = (Button) findViewById(R.id.open_cheat_button);
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

        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Question.Answer correctAnswer = mCurrentQuestion.getAnswer();

                Intent intent = AnswerCheatActivity.newIntent(QuizActivity.this,
                        (correctAnswer == Question.Answer.YES) ? R.string.answer_is_yes : R.string.answer_is_no );

                startActivityForResult(intent, REQUEST_CHEAT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != Activity.RESULT_OK) {
            return;
        }

        if(requestCode == REQUEST_CHEAT) {
            // cheated
            mCheated = true;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.d(TAG, "Save state already");

        outState.putInt(QUIZ_INDEX, mCurrentQuestionIndex) ;
        outState.putBoolean(CHEAT_STATE, mCheated);
    }

    private Question getNextQuestion() {
        mCurrentQuestionIndex++;

        if( mCurrentQuestionIndex >= mQuestionArray.length) { // 3 = IndexOutOfBound
            mCurrentQuestionIndex = 0;
        }

        mCheated = false;

        return mQuestionArray[mCurrentQuestionIndex];
    }

    private void updateUI() {
        int resId = mCurrentQuestion.getQuestionResId();

        mQuestionTextView.setText(resId);
    }

    private void checkAnswer(Question.Answer answer) {
        Question.Answer correctAnswer = mCurrentQuestion.getAnswer();

        if(mCheated) {
            Toast.makeText(QuizActivity.this,
                    R.string.no_right_to_answer_this,
                    Toast.LENGTH_SHORT).show();
            return;
        }

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
