package gmontenegro.toolboxlib.Tools;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

import gmontenegro.toolboxlib.R;

/**
 * Created by gmontenegro on 26/07/2016.
 */
public class AssetsManager extends BaseManager {

    public static String loadJSONFromAsset(String filename) {
        String json;
        try {

            InputStream is = mContext.getAssets().open(filename);

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            LogManager.debug(ex);
            return null;
        }
        return json;

    }

    public static Object loadObjectFromAsset(String filename, Type clase) {
        try {
            String json = loadJSONFromAsset(filename);
            Gson gson = new Gson();
            return gson.fromJson(json, clase);
        } catch (Exception e) {
            LogManager.error(mContext.getString(R.string.stateAssetError), e.getMessage());
        }
        return null;
    }
}
