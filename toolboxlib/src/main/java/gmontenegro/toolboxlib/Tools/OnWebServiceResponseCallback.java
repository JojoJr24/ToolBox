package gmontenegro.toolboxlib.Tools;

/**
 * Created by gmontenegro on 27/07/2016.
 */
public interface OnWebServiceResponseCallback {

    void onWebServiceResponse(int wsId,Object response);

    void onWebServiceFail(int wsId,String e);
}
