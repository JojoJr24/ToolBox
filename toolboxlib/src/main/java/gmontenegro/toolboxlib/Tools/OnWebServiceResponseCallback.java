package gmontenegro.toolboxlib.Tools;

/**
 * Created by gmontenegro on 27/07/2016.
 */
public interface OnWebServiceResponseCallback {

     void onWebServiceResponse(Object response);

     void onWebServiceFail(String e);
}
