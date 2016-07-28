package gmontenegro.toolboxlib.Tools;

import android.os.AsyncTask;
import android.widget.TextView;

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
 public abstract class SoapWSManager extends WSManager {

    private String urlAmbiente;
    private String namespace;
    private LinkedHashMap paramsValues;
    private TextView tv ;

    protected SoapWSManager(OnWebServiceResponseCallback callback,String urlAmbiente , String methodName,
                            LinkedHashMap paramsValues )
    {
        this(callback,urlAmbiente,SettingsManager.getDefaultState().defaultNamesapce,methodName,paramsValues);
    }

    protected SoapWSManager(OnWebServiceResponseCallback callback,String urlAmbiente ,String namespace, String methodName,
                            LinkedHashMap paramsValues )
    {
        super(callback);
        this.urlAmbiente = urlAmbiente;
        this.methodName = methodName;
        this.paramsValues = paramsValues;
        this.namespace = namespace;
        execute(true);
    }


    @Override
    protected void execute(boolean async) {
        AsyncTask task = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                String serviceUrl =  urlAmbiente;

                HttpTransportSE transport = new HttpTransportSE(java.net.Proxy.NO_PROXY,
                        serviceUrl, 15000);
                transport.debug = true;


                SoapObject soapObj =  new SoapObject(
                        namespace, methodName);


                if (paramsValues != null) {
                    Iterator i = paramsValues.entrySet().iterator();
                    while(i.hasNext()) {
                        Map.Entry dato = (Map.Entry) i.next();
                        soapObj.addProperty((String) dato.getKey(), dato.getValue());
                    }
                }

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                        SoapEnvelope.VER11);
                // set the dotNet attribute for .net web services, or call bill gates.
                //envelope.dotNet = true;
                envelope.setOutputSoapObject(soapObj);
                try {

                    transport.call(namespace +methodName, envelope);
                    Object retObj = null;
                    retObj = envelope.getResponse();
                    if(retObj == null)
                        retObj = envelope.bodyOut;
                    return retObj;

                }catch (SoapFault soapFault) {
                    LogManager.error(soapFault,methodName);
                    return soapFault.getMessage();
                } catch (IOException e) {
                    LogManager.error(e,methodName);
                    return e.getMessage();
                } catch (XmlPullParserException e) {
                    LogManager.error(e,methodName);
                    return e.getMessage();
                } catch (Exception e) {
                    LogManager.error(e,methodName);
                    return e.getMessage();
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


            }

            @Override
            protected void onPostExecute(Object o) {
                if(o instanceof String)
                {
                    mCallback.onWebServiceFail(((String) o));
                }
                else
                {
                    Object ret = parseObject(o);
                    mCallback.onWebServiceResponse(ret);
                }
            }

        };
        if(async)
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        else
            task.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
    }

    /**
     * Parser de la respuesta del WS
     * @param object
     * @return
     */
    protected abstract Object parseObject(Object object);


    /**
     * Crea un linkedHasmap para enviar al contructor con los parametros
     * @param data lista de parametros del WS en pares(nombre1, valor1,nombre2,valor2,...)
     * @return
     */
    public static LinkedHashMap<String,Object> createParameter(Object... data)
    {
        LinkedHashMap<String,Object> ret = new LinkedHashMap<>();
        for(int i = 0 ; i < data.length-1 ; i+=2)
        {
            ret.put((String) data[i],data[i+1]);
        }

        return ret;
    }


}
