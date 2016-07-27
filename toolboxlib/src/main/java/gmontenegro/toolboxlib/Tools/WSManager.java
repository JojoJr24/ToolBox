package gmontenegro.toolboxlib.Tools;

/**
 * Created by gmontenegro on 26/07/2016.
 */
public class WSManager extends BaseManager implements OnWebServiceResponseCallback {

    protected OnWebServiceResponseCallback mCallback;

    protected String methodName= "";


    protected WSManager(OnWebServiceResponseCallback callback )
    {
        if(callback == null)
            mCallback = this;
        else
            mCallback = callback;
    }


    protected void execute(boolean async)
    {


    }

    @Override
    public void onWebServiceResponse(Object response) {
        LogManager.debug(response);
    }

    @Override
    public void onWebServiceFail(String e) {

    }

}
