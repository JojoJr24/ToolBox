package gmontenegro.toolboxlib.Tools;

import android.text.format.Time;
import android.util.Log;

import java.security.ProviderException;


/**
 * Created by gmontenegro on 26/07/2016.
 */
public class LogManager extends BaseManager{

    private static final String TEXT_LINE_DELIMITER = " - ";

    final static String TAG = "APPLICATIONTAG";
    final static String FILELOG = "app.log";


    /**
     * Muestra el dato pero no lo guarda , solo para debug
     */
    public static final void debug(Object... data) {
        StringBuilder reportData = new StringBuilder();
        reportData.append("Method: ").append(getMethodName()).append(TEXT_LINE_DELIMITER);

        for (int i = 0; i < data.length; i++) {
            reportData.append(data[i]).append(TEXT_LINE_DELIMITER);
        }

        if (SettingsManager.getState().debug)
            Log.d(TAG, reportData.toString());

    }


    /**
     * Muestra el warning y lo guarda solo si esta activado y el viene de un descendinete de context
     */
    public static final void warn(Object... data) {
        StringBuilder reportData = new StringBuilder();
        reportData.append("Method: ").append(getMethodName()).append(TEXT_LINE_DELIMITER);

        for (int i = 0; i < data.length; i++) {
            reportData.append(data[i]).append(TEXT_LINE_DELIMITER);
        }
        if (SettingsManager.getState().debug)
            Log.w(TAG, reportData.toString());
        if (SettingsManager.getState().saveLog && context != null)
            FilesManager.saveText( FILELOG, getTime() + " " + reportData.toString() + "\n", true);
    }


    /**
     * Muestran el error y lo guardan, siempre tiene que tener context(para guardarlo)
     */
    public static final void error( Throwable e, Object... data) {


        StringBuilder reportData = new StringBuilder();
        reportData.append("Method: ").append(getMethodName()).append(TEXT_LINE_DELIMITER);
        if (SettingsManager.getState().debug)
            Log.e(TAG, reportData.toString(), e);
        if (SettingsManager.getState().saveLog)
            FilesManager.saveText( FILELOG, getTime() + " "
                    + reportData.toString() + " "
                    + e.getMessage() + " "
                    + e.getCause() + " "
                    + "\n", true);
    }

    public static final void error( ProviderException e, Object... data) {

        StringBuilder reportData = new StringBuilder();
        reportData.append("Method: ").append(getMethodName()).append(TEXT_LINE_DELIMITER);
        if (SettingsManager.getState().debug)
            Log.e(TAG, reportData.toString(), e);
        if (SettingsManager.getState().saveLog)
            FilesManager.saveText( FILELOG, getTime() + " "
                    + reportData.toString() + " "
                    + e.getMessage() + " "
                    + e.getCause() + " "
                    + "\n", true);
    }

    /**
     * Get the method name for a depth in call stack
     */
    private static String getMethodName() {
        // capture the stack elements
        final StackTraceElement[] stackTraceElement = Thread.currentThread().getStackTrace();

        // initialize index
        int depth = 0;

        // lock for self class in stack
        while (depth != stackTraceElement.length) {
            if (!stackTraceElement[depth].getClassName().equals(LogManager.class.getName())) {
                depth++;
            } else {
                break;
            }
        }

        // lock for the first no self class in stack
        while (depth != stackTraceElement.length) {
            if (stackTraceElement[depth].getClassName().equals(LogManager.class.getName())) {
                depth++;
            } else {
                return stackTraceElement[depth].getClassName() + "." + stackTraceElement[depth].getMethodName();
            }
        }

        return "";
    }


    static private String getTime() {
        Time now = new Time();
        now.setToNow();
        return now.format2445();
    }

    public static String getLog() {
        return FilesManager.loadText( FILELOG);
    }

}