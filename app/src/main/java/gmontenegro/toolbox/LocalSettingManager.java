package gmontenegro.toolbox;

import gmontenegro.toolboxlib.Tools.SettingsManager;

/**
 * Created by gmontenegro on 27/07/2016.
 */
public class LocalSettingManager extends SettingsManager{

    public static LocalDefSettings getLocalState()
    {
        clase = LocalDefSettings.class;
        return (LocalDefSettings) getState();
    }
}
