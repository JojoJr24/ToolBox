package gmontenegro.toolboxlib.Tools;

import com.google.gson.reflect.TypeToken;



/**
 * Created by gmontenegro on 26/07/2016.
 */
public class SettingsManager extends BaseManager{

    public static DefaultSettings state;

    public static DefaultSettings getState()//Una vez ejecutado con un context, guarda el dato
    {
        if(state == null && context != null)
        {
            state = (DefaultSettings) AssetsManager.loadObjectFromAsset("stateAsset", new TypeToken<DefaultSettings>(){}.getType());

        }
        return state;
    }


}
