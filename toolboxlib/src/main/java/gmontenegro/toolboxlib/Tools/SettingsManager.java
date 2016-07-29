package gmontenegro.toolboxlib.Tools;

/**
 * Created by gmontenegro on 26/07/2016.
 */
public class SettingsManager extends BaseManager {

    protected static Class mClase;
    private static Object mState;

    public static Object getState()//Una vez ejecutado con un context, guarda el dato
    {
        if (mContext != null) {
            mState = AssetsManager.loadObjectFromAsset("stateAsset", mClase);

        }
        return mState;
    }

    public static DefaultSettings getDefaultState() {
        mClase = DefaultSettings.class;
        return ((DefaultSettings) getState());
    }

}
