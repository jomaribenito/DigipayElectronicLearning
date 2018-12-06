package ph.digipay.digipayelectroniclearning.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Questionnaire implements Parcelable {
    private String id;
    private String question;
    private Options options;
    private String answer_index;

    public Questionnaire() {
    }

    public Questionnaire(String id) {
        this.id = id;
    }

    protected Questionnaire(Parcel in) {
        id = in.readString();
        question = in.readString();
        options = (Options) in.readSerializable();
        answer_index = in.readString();
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getAnswer_index() {
        return answer_index;
    }

    public void setAnswer_index(String answer_index) {
        this.answer_index = answer_index;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(question);
        dest.writeSerializable(options);
        dest.writeString(answer_index);
    }
}
