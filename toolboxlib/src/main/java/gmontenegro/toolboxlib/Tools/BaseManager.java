package gmontenegro.toolboxlib.Tools;

import android.app.Activity;
import android.content.Context;

/**
 * Created by gmontenegro on 27/07/2016.
 */
public class BaseManager {

    protected static Activity activity;
    protected static Context context;



    public static void initManagers(Activity pActivity)
    {
        //Como hay cosas que necesitan activity y otras context,
        //pruebo iniciar ambas
        activity = pActivity;
        context = pActivity;
    }

    public static void initManagers(Context pContext)
    {
        context = pContext;
    }




}
