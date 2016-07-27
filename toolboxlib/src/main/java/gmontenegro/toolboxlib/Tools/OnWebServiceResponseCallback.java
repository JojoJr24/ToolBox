package gmontenegro.toolboxlib.Tools;

/**
 * Created by gmontenegro on 27/07/2016.
 */
public interface OnWebServiceResponseCallback {

    public void onWebServiceResponse(Object response);

    public void onWebServiceFail(String e);
}
