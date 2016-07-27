package gmontenegro.toolboxlib.Tools;

/**
 * Created by gmontenegro on 26/07/2016.
 */
public class SettingsManager extends BaseManager{

    private static Object state;
    protected static Class clase;

    public static Object getState()//Una vez ejecutado con un context, guarda el dato
    {
        if(state == null && context != null)
        {
            state = (DefaultSettings) AssetsManager.loadObjectFromAsset("stateAsset", clase);

        }
        return state;
    }

    public static DefaultSettings getDefaultState()
    {
        clase = DefaultSettings.class;
        return ((DefaultSettings) getState());
    }


}
