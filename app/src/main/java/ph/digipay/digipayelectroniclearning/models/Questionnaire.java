package ph.digipay.digipayelectroniclearning.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Questionnaire extends DatabaseObject implements Parcelable {
    private String question;
    private Options options;
    private String answerIndex;
    private String moduleUid;

    public Questionnaire() {
    }


    private Questionnaire(Parcel in) {
        moduleUid = in.readString();
        question = in.readString();
        options = (Options) in.readSerializable();
        answerIndex = in.readString();
    }

    public static final Creator<Questionnaire> CREATOR = new Creator<Questionnaire>() {
        @Override
        public Questionnaire createFromParcel(Parcel in) {
            return new Questionnaire(in);
        }

        @Override
        public Questionnaire[] newArray(int size) {
            return new Questionnaire[size];
        }
    };

    public String getModuleUid() {
        return moduleUid;
    }

    public void setModuleUid(String moduleUid) {
        this.moduleUid = moduleUid;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Options getOptions() {
        return options;
    }

    public void setOptions(Options options) {
        this.options = options;
    }

    public String getAnswerIndex() {
        return answerIndex;
    }

    public void setAnswerIndex(String answerIndex) {
        this.answerIndex = answerIndex;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(moduleUid);
        dest.writeString(question);
        dest.writeSerializable(options);
        dest.writeString(answerIndex);
    }
}
