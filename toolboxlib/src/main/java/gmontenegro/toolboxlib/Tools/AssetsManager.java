package gmontenegro.toolboxlib.Tools;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

/**
 * Created by gmontenegro on 26/07/2016.
 */
public class AssetsManager extends BaseManager {

    public static String loadJSONFromAsset(String filename ) {
        String json = null;
        try {

            InputStream is = context.getAssets().open(filename);

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

    public static Object loadObjectFromAsset(String filename, Type clase)
    {
        String json = loadJSONFromAsset(filename);
        Gson gson = new Gson();
        Object ret = gson.fromJson(json,clase);
        return ret;
    }
}
