package gmontenegro.toolboxlib.Tools;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by gmontenegro on 27/07/2016.
 */
public abstract class RestWSManager extends WSManager {


    private final OnWebServiceResponseCallback mCallback;
    private final String mUrl;
    private final String mParameters;
    private final HttpMethod mMethod;
    private final LinkedHashMap<String, String> mHeaders = new LinkedHashMap<String, String>();
    private final MultiValueMap<String, String> mBody = new LinkedMultiValueMap<String, String>();


    protected RestWSManager(@Nullable OnWebServiceResponseCallback callback, HttpMethod method,  @Nullable String... parameters) {
        this(callback,method,SettingsManager.getDefaultState().defaultNamespace,parameters);
    }


    protected RestWSManager(@Nullable OnWebServiceResponseCallback callback, HttpMethod method, @NonNull String url, @Nullable String... parameters) {
        super(callback);
        this.mCallback = callback;
        this.mUrl = url;
        this.mMethod = method;
        StringBuilder stringBuilder = new StringBuilder();
        if(parameters != null) {
            for (String parameter : parameters) {
                stringBuilder.append("/").append(parameter);
            }
        }
        this.mParameters = stringBuilder.toString();
    }

    /**
     * Permite ingresar datos al header del WS
     *
     * @param headers Lista de headers (name1,value1,name2,value2...)
     * @return devuelve a this para inicialización tipo .
     */
    public RestWSManager initHeaders(String... headers) {
        for (int i = 0; i < headers.length - 1; i += 2) {
            mHeaders.put(headers[i], headers[i + 1]);
        }

        return this;
    }

    public RestWSManager initBody(String... body) {
        for (int i = 0; i < body.length - 1; i += 2) {
            mBody.add(body[i], body[i + 1]);
        }

        return this;
    }


    @Override
    public void execute(boolean async) {
        AsyncTask<Object, Integer, Object> task = new AsyncTask<Object, Integer, Object>() {
            @Override
            protected Object doInBackground(Object[] params) {
                HttpEntity entity = null;
                HttpEntity<String> response = null;
                try {
                    // The connection URL
                    String url = mUrl + mParameters;
                    // Create a new RestTemplate instance
                    RestTemplate restTemplate = new RestTemplate();
                    // Add the String message converter
                    restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                    HttpHeaders headers = new HttpHeaders();
                    //Ingreso los datos del header, si no hay , mHeaders va a estar vacío y no va a
                    //cargar nada
                    for (Map.Entry<String, String> header : mHeaders.entrySet()) {
                        headers.set(header.getKey(), header.getValue());
                    }

                    if (mBody.isEmpty()) {
                        entity = new HttpEntity(headers);
                        response = restTemplate.exchange(url, mMethod, entity, String.class, "");

                        return response;
                    } else {
                        entity = new HttpEntity(mBody, headers);
                        response = restTemplate.exchange(url, mMethod, entity, String.class, "");
                        return response;
                    }
                } catch (Exception e) {
                    if (entity != null && response != null) {
                        LogManager.error(e,
                                "Request Header : " + entity.getHeaders() + "\n"
                                , "Request Body : " + entity.getBody() + "\n"
                                , "Response Header : " + response.getHeaders() + "\n"
                                , "Response Body : " + response.getBody() + "\n"
                                , mUrl + mParameters);
                    }
                    if (entity != null) {
                        LogManager.error(e,
                                "Request Header : " + entity.getHeaders() + "\n"
                                , "Request Body : " + entity.getBody() + "\n"
                                , mUrl + mParameters);
                    } else {
                        LogManager.error(e);
                    }
                    return e.getMessage();
                }
            }

            @Override
            protected void onPostExecute(Object o) {

                if (o instanceof String) {
                    mCallback.onWebServiceFail(((String) o));
                } else {
                    Object ret = parseObject(o);
                    mCallback.onWebServiceResponse(ret);
                }
            }

        };
        if (async)
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        else
            task.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
    }

    /**
     * Parser de la respuesta del WS
     *

     * @param object El objeto que respondio el WS
     * @return El objeto despues de ser Parseado
     */
    protected abstract Object parseObject(Object object);


}
