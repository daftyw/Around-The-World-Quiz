package ayp.aug.quiz;

/**
 * Created by wind on 8/10/2016 AD.
 */
public class Question {
    private int mQuestionResId;
    private Answer answer;

    public Question(int questionResId, Answer answer) {
        mQuestionResId = questionResId;
        this.answer = answer;
    }

    public int getQuestionResId() {
        return mQuestionResId;
    }

    public void setQuestionResId(int questionResId) {
        mQuestionResId = questionResId;
    }

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public enum Answer {
        YES, NO;
    }
}
