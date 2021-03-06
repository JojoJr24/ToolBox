package gmontenegro.toolboxlib.Tools;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

/**
 * Created by gmontenegro on 27/07/2016.
 */
public class BaseManager {

    protected static Activity mActivity;
    protected static Context mContext;


    public static void initManagers(Activity activity) {
        //Como hay cosas que necesitan activity y otras context,
        //pruebo iniciar ambas
        mActivity = activity;
        mContext = activity;
        initLog();

    }

    public static void initManagers(Context context) {
        mContext = context;
        initLog();
    }


    private static void initLog() {
        //Lo borro para disminuir los riesgos de que quede cargado y sea llamado cuando ya no existe
        //el activity
        LogManager.mCallback = null;
        if (SettingsManager.getDefaultState().debugToasts) {
            Toast.makeText(mContext, mContext.getClass().getCanonicalName(), Toast.LENGTH_SHORT).show();
        }
        if (SettingsManager.getDefaultState().debugLevel >= LogManager.LEVEL_ERRORS) {
            new TopExceptionHandler(SettingsManager.getDefaultState().debugMail);
        }
        //Si hubo un crash grave se guardo el reporte en el sharedpreferences, por lo que al inicio
        //levanto los posibles crashes y, si el envio por mail está activado , lo envio
        String possibleCrash = StoreManager.pullString("crash");
        if (!possibleCrash.equals("")) {
            OtherAppsConnectionManager.sendMail("Stack", possibleCrash, SettingsManager.getDefaultState().debugMailAddress);
            StoreManager.removeObject("crash");
        }
    }


}
