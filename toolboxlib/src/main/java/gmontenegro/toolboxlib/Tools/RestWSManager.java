package gmontenegro.toolboxlib.Tools;

import android.os.AsyncTask;

/**
 * Created by gmontenegro on 27/07/2016.
 */
public class RestWSManager extends WSManager {

    protected RestWSManager(OnWebServiceResponseCallback callback )
    {
        super(callback);
    }

    @Override
    protected void execute(boolean async) {
        AsyncTask task = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
            }

        };
        if(async)
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        else
            task.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);
    }




}
