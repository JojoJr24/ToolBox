package gmontenegro.toolboxlib.Tools;

/**
 * Created by gmontenegro on 26/07/2016.
 */
public abstract class WSManager extends BaseManager implements OnWebServiceResponseCallback {

    protected final OnWebServiceResponseCallback mCallback;
    protected int mWsId = 0;

    protected WSManager(OnWebServiceResponseCallback callback) {
        if (callback == null)
            mCallback = this;
        else
            mCallback = callback;
    }

    protected void execute(boolean async) {
    }

    /**
     * Parser de la respuesta del WS
     *

     * @param object El objeto que respondio el WS
     * @return El objeto despues de ser Parseado
     */
    protected abstract Object parseObject(Object object) throws Exception;


    @Override
    public void onWebServiceResponse(int wsId, Object response) {
        LogManager.debug(response);
    }


    @Override
    public void onWebServiceFail(int wsId, String e) {

    }
}
