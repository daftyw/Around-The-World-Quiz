package ayp.aug.quiz;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AnswerCheatActivity extends AppCompatActivity {

    private static final String KEY_ANSWER_RES = "ANSWER_RESPONSE";

    private Button mGoBackButton;
    private TextView mTextView;
    int mAnswerRes;


    public static Intent newIntent(Context context, @StringRes
                                   int answerResId) {

        Intent i = new Intent(context, AnswerCheatActivity.class);
        i.putExtra(KEY_ANSWER_RES, answerResId);

        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_cheat);

        Intent intent = getIntent();

        mAnswerRes = R.string.no_answer;
        if(intent != null) {
            mAnswerRes = intent.getIntExtra(KEY_ANSWER_RES, 0);
        } else if (savedInstanceState != null) {
            mAnswerRes = savedInstanceState.getInt(KEY_ANSWER_RES, 0);
        }

        mTextView = (TextView) findViewById(R.id.answer_text);
        mTextView.setText(mAnswerRes);

        mGoBackButton = (Button) findViewById(R.id.go_back_button);
        mGoBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(Activity.RESULT_OK);
                finish();
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(KEY_ANSWER_RES, mAnswerRes);
    }
}
