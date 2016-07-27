package gmontenegro.toolboxlib.Tools;

import android.os.AsyncTask;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by gmontenegro on 27/07/2016.
 */
 public class SoapWSManager extends WSManager {

    private String urlAmbiente;
    LinkedHashMap paramsValues;

    protected SoapWSManager(OnWebServiceResponseCallback callback,String urlAmbiente , String methodName,
                            LinkedHashMap paramsValues )
    {
        super(callback);
        this.urlAmbiente = urlAmbiente;
        this.methodName = methodName;
        this.paramsValues = paramsValues;
    }

    @Override
    protected void execute(boolean async) {
        AsyncTask task = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                String serviceUrl =  urlAmbiente+ methodName;

                HttpTransportSE transport = new HttpTransportSE(java.net.Proxy.NO_PROXY,
                        serviceUrl, 15000);
                transport.debug = true;


                SoapObject soapObj =  new SoapObject(
                        "http://tempuri.org/", methodName);


                if (paramsValues != null) {
                    Iterator i = paramsValues.entrySet().iterator();
                    while(i.hasNext()) {
                        Map.Entry dato = (Map.Entry) i.next();
                        soapObj.addProperty((String) dato.getKey(), dato.getValue());
                    }
                }

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                        SoapEnvelope.VER12);
                // set the dotNet attribute for .net web services, or call bill gates.
                envelope.dotNet = true;
                envelope.setOutputSoapObject(soapObj);
                try {

                    transport.call("", envelope);
                    Object retObj = null;
                    retObj = envelope.getResponse();
                    if(retObj == null)
                        retObj = envelope.bodyOut;
                    return retObj;

                }catch (SoapFault soapFault) {
                    LogManager.error(soapFault,methodName);
                } catch (IOException e) {
                    LogManager.error(e,methodName);
                } catch (XmlPullParserException e) {
                    LogManager.error(e,methodName);
                } catch (Exception e) {
                    LogManager.error(e,methodName);
                }
                finally {

                }

        /*Vector<String> ret = new Vector<>();
        for (Object dato : (Vector)retObj) {
            if(dato.getClass().equals(SoapPrimitive.class))
                ret.add((String)((SoapPrimitive) dato).getValue());
            else
                ret.add("");
        }*/

                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                if(o instanceof String)
                {
                    mCallback.onWebServiceFail(((String) o));
                }
                else
                {
                    mCallback.onWebServiceResponse(o);
                }
            }

        };
        if(async)
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        else
            task.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
    }


}
