package ph.digipay.digipayelectroniclearning.common.constants;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import ph.digipay.digipayelectroniclearning.models.User;

/**
 * Created by jomari on 11/21/2018.
 */

public class SharedPrefManager {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context _context;
    private Gson gson;

    // shared pref mode
    private final int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "digipay";

    private static final String IS_LOGIN = "is_login";
    public static final String LOGIN_USER = "login_user";

    public SharedPrefManager(Context context) {
        this._context = context;
        gson = new Gson();
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLogin(boolean isLogin) {
        editor.putBoolean(IS_LOGIN, isLogin);
        editor.commit();
    }

    public boolean isLogin() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public void setLoginUser(User user){
        String json = gson.toJson(user);
        editor.putString(LOGIN_USER, json);
        editor.commit();
    }

    public User getLoginUser() {
        String json = pref.getString(LOGIN_USER, null);
        User user;
        user = gson.fromJson(json, User.class);
        return user;
    }
}
