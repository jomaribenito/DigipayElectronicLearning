package ph.digipay.digipayelectroniclearning.common.utils;

import android.widget.Spinner;
import android.widget.TextView;

public class FormUtils {
    public static String getTrimmedString(TextView tv){
        return tv.getText().toString().trim();
    }
    public static String getSpinnerItemString(Spinner spinner) { return spinner.getSelectedItem().toString().trim(); }
}
