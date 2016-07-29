package gmontenegro.toolboxlib.Tools;

import android.widget.Toast;

/**
 * Created by gmontenegro on 19/02/2016.
 */
public class TopExceptionHandler extends BaseManager implements Thread.UncaughtExceptionHandler {

    private final Thread.UncaughtExceptionHandler defaultUEH;

    private final boolean mSendMail;

    public TopExceptionHandler(boolean sendMail) {
        this.defaultUEH = Thread.getDefaultUncaughtExceptionHandler();
        this.mSendMail = sendMail;
    }

    public void uncaughtException(Thread t, Throwable e) {
        StackTraceElement[] arr = e.getStackTrace();
        String report = e.toString() + "\n\n";
        report += "--------- Stack trace ---------\n\n";
        for (StackTraceElement anArr1 : arr) {
            report += "    " + anArr1.toString() + "\n";
        }
        report += "-------------------------------\n\n";

// If the exception was thrown in a background thread inside
// AsyncTask, then the actual exception can be found with getCause
        report += "--------- Cause ---------\n\n";
        Throwable cause = e.getCause();
        if (cause != null) {
            report += cause.toString() + "\n\n";
            arr = cause.getStackTrace();
            for (StackTraceElement anArr : arr) {
                report += "    " + anArr.toString() + "\n";
            }
        }
        report += "-------------------------------\n\n";

        Toast.makeText(mContext, report, Toast.LENGTH_LONG).show();
        if (mSendMail) {
            StoreManager.putString("crash", report);
        }
        LogManager.error(report);
        defaultUEH.uncaughtException(t, e);
    }
}