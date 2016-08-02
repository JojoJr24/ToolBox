package gmontenegro.toolboxlib.Tools;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by gmontenegro on 27/07/2016.
 */
public abstract class SoapWSManager extends WSManager {


    private final String mUrlAmbiente;
    private final String mMethodName;
    private final String mNamespace;
    private final LinkedHashMap mParamsValues;

    protected SoapWSManager(int wsId, @Nullable OnWebServiceResponseCallback callback, @NonNull String urlAmbiente, @NonNull String methodName,
                            @NonNull LinkedHashMap paramsValues) {
        this(wsId,callback, urlAmbiente, SettingsManager.getDefaultState().defaultNamespace, methodName, paramsValues);
    }

    protected SoapWSManager(int wsId,@Nullable OnWebServiceResponseCallback callback, @NonNull String urlAmbiente, @NonNull String namespace, @NonNull String methodName,
                            @NonNull LinkedHashMap paramsValues) {
        super(callback);
        this.mWsId = wsId;
        this.mUrlAmbiente = urlAmbiente;
        this.mMethodName = methodName;
        this.mParamsValues = paramsValues;
        this.mNamespace = namespace;

    }

    /**
     * Crea un linkedHasmap para enviar al contructor con los parametros
     *
     * @param data lista de parametros del WS en pares(nombre1, valor1,nombre2,valor2,...)
     * @return devuelve la lista de parametros en un formato mas comodo
     */
    public static LinkedHashMap<String, Object> createParameter(Object... data) {
        LinkedHashMap<String, Object> ret = new LinkedHashMap<String, Object>();
        for (int i = 0; i < data.length - 1; i += 2) {
            ret.put((String) data[i], data[i + 1]);
        }

        return ret;
    }

    @Override
    public void execute(boolean async) {
        AsyncTask<Object, Integer, Object> task = new AsyncTask<Object, Integer, Object>() {
            @Override
            protected Object doInBackground(Object[] params) {
                String serviceUrl = mUrlAmbiente;

                HttpTransportSE transport = new HttpTransportSE(java.net.Proxy.NO_PROXY,
                        serviceUrl, 15000);

                if (SettingsManager.getDefaultState().debug)
                    transport.debug = true;


                SoapObject soapObj = new SoapObject(
                        mNamespace, mMethodName);


                if (mParamsValues != null) {
                    for (Object o : mParamsValues.entrySet()) {
                        Map.Entry dato = (Map.Entry) o;
                        soapObj.addProperty((String) dato.getKey(), dato.getValue());
                    }
                }

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                        SoapEnvelope.VER11);
                // set the dotNet attribute for .net web services, or call bill gates.
                //envelope.dotNet = true;
                envelope.setOutputSoapObject(soapObj);
                try {

                    transport.call(mNamespace + mMethodName, envelope);
                    Object retObj;
                    retObj = envelope.getResponse();
                    if (retObj == null)
                        retObj = envelope.bodyOut;
                    return retObj;

                } catch (SoapFault soapFault) {
                    LogManager.error(soapFault
                            , "Request : " + transport.requestDump + "\n"
                            , "Response : " + transport.responseDump + "\n"
                            , mMethodName);
                    return soapFault.getMessage();
                } catch (IOException e) {
                    LogManager.error(e,
                            "Request : " + transport.requestDump + "\n"
                            , "Response : " + transport.responseDump + "\n"
                            , mMethodName);
                    return e.getMessage();
                } catch (XmlPullParserException e) {
                    LogManager.error(e,
                            "Request : " + transport.requestDump + "\n"
                            , "Response : " + transport.responseDump + "\n"
                            , mMethodName);
                    return e.getMessage();
                } catch (Exception e) {
                    LogManager.error(e,
                            "Request : " + transport.requestDump + "\n"
                            , "Response : " + transport.responseDump + "\n"
                            , mMethodName);
                    return e.getMessage();
                } finally {
                    LogManager.debug("Request : " + transport.requestDump + "\n"
                            , "Response : " + transport.responseDump + "\n"
                            , mMethodName);
                }

        /*Vector<String> ret = new Vector<>();
        for (Object dato : (Vector)retObj) {
            if(dato.getClass().equals(SoapPrimitive.class))
                ret.add((String)((SoapPrimitive) dato).getValue());
            else
                ret.add("");
        }*/


            }

            @Override
            protected void onPostExecute(Object o) {
                if (o instanceof String) {
                    mCallback.onWebServiceFail(mWsId,((String) o));
                } else {
                    try {
                        Object ret = parseObject(o);
                        mCallback.onWebServiceResponse(mWsId,ret);
                    }
                catch (Exception e)
                {
                    mCallback.onWebServiceFail(mWsId,e.getMessage());
                }
                }
            }

        };
        if (async)
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        else
            task.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
    }




}
