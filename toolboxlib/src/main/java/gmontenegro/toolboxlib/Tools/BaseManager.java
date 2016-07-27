package gmontenegro.toolboxlib.Tools;

import android.app.Activity;
import android.content.Context;

/**
 * Created by gmontenegro on 27/07/2016.
 */
public class BaseManager {

    static Activity activity;
    static Context context;

    public static void initManagers(Activity pActivity)
    {
        activity = pActivity;
        context = pActivity;
    }

    public static void initManagers(Context pContext)
    {
        context = pContext;
    }

}
