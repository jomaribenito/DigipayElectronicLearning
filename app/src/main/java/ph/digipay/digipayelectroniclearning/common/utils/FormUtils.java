package ph.digipay.digipayelectroniclearning.common.utils;

import android.widget.TextView;

public class FormUtils {
    public static String getTrimmedString(TextView tv){
        return tv.getText().toString().trim();
    }
}
