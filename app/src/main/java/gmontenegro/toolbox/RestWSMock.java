package gmontenegro.toolbox;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.springframework.http.HttpMethod;

import gmontenegro.toolboxlib.Tools.OnWebServiceResponseCallback;
import gmontenegro.toolboxlib.Tools.RestWSManager;

/**
 * Created by gmontenegro on 29/07/2016.
 */
public class RestWSMock extends RestWSManager {

    public RestWSMock(@Nullable OnWebServiceResponseCallback callback, HttpMethod method, @NonNull String url, @Nullable String... parameters) {
        super(callback, method, url, parameters);
    }

    @Override
    protected Object parseObject(Object object) {
        return object;
    }
}
