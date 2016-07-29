package gmontenegro.toolboxlib.Tools;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by gmontenegro on 19/02/2016.
 */
public class TopExceptionHandler implements Thread.UncaughtExceptionHandler  {

    private final Thread.UncaughtExceptionHandler defaultUEH;

    private final Context app;

    private final boolean sendMail;

    public TopExceptionHandler(Context app, boolean sendMail) {
        this.defaultUEH = Thread.getDefaultUncaughtExceptionHandler();
        this.app = app;
        this.sendMail = sendMail;
    }

    public void uncaughtException(Thread t, Throwable e)
    {
        StackTraceElement[] arr = e.getStackTrace();
        String report = e.toString()+"\n\n";
        report += "--------- Stack trace ---------\n\n";
        for (int i=0; i<arr.length; i++)
        {
            report += "    "+arr[i].toString()+"\n";
        }
        report += "-------------------------------\n\n";

// If the exception was thrown in a background thread inside
// AsyncTask, then the actual exception can be found with getCause
        report += "--------- Cause ---------\n\n";
        Throwable cause = e.getCause();
        if(cause != null) {
            report += cause.toString() + "\n\n";
            arr = cause.getStackTrace();
            for (int i=0; i<arr.length; i++)
            {
                report += "    "+arr[i].toString()+"\n";
            }
        }
        report += "-------------------------------\n\n";

        Toast.makeText(app,report,Toast.LENGTH_LONG).show();
        if(sendMail)
        {
            StoreManager.putString("crash",report);
        }
        LogManager.error(report);
        defaultUEH.uncaughtException(t, e);
    }
}