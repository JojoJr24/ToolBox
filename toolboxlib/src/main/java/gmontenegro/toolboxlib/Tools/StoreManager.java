package gmontenegro.toolboxlib.Tools;

import android.content.SharedPreferences;

/**
 * Created by gmontenegro on 28/07/2016.
 */
public class StoreManager extends BaseManager {

    final static String MY_PREFS_NAME = "store";

    public static void putString(String name, String data)
    {
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, context.MODE_PRIVATE).edit();
        editor.putString(name, data);
        editor.commit();
    }


    public static String pullString(String name )
    {
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, context.MODE_PRIVATE);
        return prefs.getString(name, "");
    }

    public static void putInt(String name, int data)
    {
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, context.MODE_PRIVATE).edit();
        editor.putInt(name, data);
        editor.commit();
    }


    public static int pullInt(String name )
    {
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, context.MODE_PRIVATE);
        return prefs.getInt(name, 0);
    }

    public static void putBoolean(String name, boolean data)
    {
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, context.MODE_PRIVATE).edit();
        editor.putBoolean(name, data);
        editor.commit();
    }


    public static boolean pullBoolean(String name )
    {
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, context.MODE_PRIVATE);
        return prefs.getBoolean(name, false);
    }

    public static void removeObject(String name)
    {
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, context.MODE_PRIVATE).edit();
        editor.remove(name);
    }
}
