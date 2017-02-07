package vn.com.phongnguyen93.justdoit.utilities;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by tuanhnm on 9/16/16.
 */
public class PreferencesUtility {
    public static final String PRE_KEY_USER = "user";

    public static final String INVALID_CONTEXT_MESSAGE = "Invalid context params";
    public static final String PRE_KEY = "JustDoIt";
    private static volatile PreferencesUtility instance;
    private final Context mContext;
    private final SharedPreferences mPreferences;

    public static PreferencesUtility getInstance(Context context){
        if (instance == null) {
            synchronized (PreferencesUtility.class) {
                if (instance == null) {
                    instance = new PreferencesUtility(context);
                }
            }
        }
        return instance;
    }

    public PreferencesUtility(Context context) {
        this.mContext = context;
        this.mPreferences = context.getSharedPreferences(PRE_KEY, Context.MODE_PRIVATE);
    }

    public SharedPreferences getmPreferences() {
        return mPreferences;
    }

    public void clean() {
        mPreferences.edit().clear().apply();
    }

    public String getString(String key) {
        return mPreferences.getString(key, "");
    }

    public void setString(String key, String values) {
        if (key == "" || values == "") {
            return;
        }
        mPreferences.edit().putString(
            key,
            values
        ).apply();
    }
}
