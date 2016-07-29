package gmontenegro.toolbox;

import java.util.LinkedHashMap;

import gmontenegro.toolboxlib.Tools.OnWebServiceResponseCallback;
import gmontenegro.toolboxlib.Tools.SoapWSManager;

/**
 * Created by gmontenegro on 28/07/2016.
 */
public class SoapWSMock extends SoapWSManager {

    public SoapWSMock(OnWebServiceResponseCallback callback, String urlAmbiente, String methodName, LinkedHashMap paramsValues) {
        super(callback, urlAmbiente, methodName, paramsValues);
    }

    @Override
    protected Object parseObject(Object object) {
        return object;
    }


}
