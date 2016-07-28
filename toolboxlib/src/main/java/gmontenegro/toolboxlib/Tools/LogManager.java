package gmontenegro.toolboxlib.Tools;

import android.text.format.Time;
import android.util.Log;
import android.widget.Toast;

import java.security.ProviderException;


/**
 * Created by gmontenegro on 26/07/2016.
 */
public class LogManager extends BaseManager{

    private static final int LEVEL_VERBOSE_TOAST = 4;
    private static final int LEVEL_VERBOSE = 3;
    private static final int LEVEL_PROBLEMS = 2;
    private static final int LEVEL_ERRORS = 1;
    private static final int LEVEL_OFF = 0;

    private static final String DEBUG = "DD";
    private static final String WARNING = "WW";
    private static final String ERROR = "EE";

    private static final String TEXT_LINE_DELIMITER = " - ";


    private final static String FILELOG = "app.log";

    protected static OnLogChangedCallback callback;
    /**
     * Muestra el dato pero no lo guarda , solo para debug
     */
    public static final void debug(Object... data) {
        if(SettingsManager.getDefaultState().debugLevel >= LEVEL_VERBOSE) {
            StringBuilder reportData = new StringBuilder();
            reportData.append(getMethodName()).append(TEXT_LINE_DELIMITER);

            for (int i = 0; i < data.length; i++) {
                reportData.append(data[i]).append(TEXT_LINE_DELIMITER);
            }

            if (SettingsManager.getDefaultState().debug)
                Log.d(SettingsManager.getDefaultState().debugTAG, reportData.toString());
            if (SettingsManager.getDefaultState().saveLog)
                writeFormatedLog(reportData.toString(),DEBUG);
        }

    }


    /**
     * Muestra el warning y lo guarda solo si esta activado y el viene de un descendinete de context
     */
    public static final void warn(Object... data) {
        if(SettingsManager.getDefaultState().debugLevel >= LEVEL_PROBLEMS) {
            StringBuilder reportData = new StringBuilder();
            reportData.append(getMethodName()).append(TEXT_LINE_DELIMITER);

            for (int i = 0; i < data.length; i++) {
                reportData.append(data[i]).append(TEXT_LINE_DELIMITER);
            }
            if (SettingsManager.getDefaultState().debug)
                Log.w(SettingsManager.getDefaultState().debugTAG, reportData.toString());
            if (SettingsManager.getDefaultState().saveLog)
                writeFormatedLog(reportData.toString(),WARNING);
        }
    }


    /**
     * Muestran el error y lo guardan, siempre tiene que tener context(para guardarlo)
     */
    public static final void error( Throwable e, Object... data) {

        if(SettingsManager.getDefaultState().debugLevel >= LEVEL_ERRORS) {
            StringBuilder reportData = new StringBuilder();
            reportData.append(getMethodName()).append(TEXT_LINE_DELIMITER);
            if (SettingsManager.getDefaultState().debug)
                Log.e(SettingsManager.getDefaultState().debugTAG, reportData.toString(), e);
            if (SettingsManager.getDefaultState().saveLog)
                writeFormatedLog(reportData.toString() + " "
                        + e.getMessage() + " "
                        + e.getCause(),ERROR);
        }
    }

    public static final void error( ProviderException e, Object... data) {

        if(SettingsManager.getDefaultState().debugLevel >= LEVEL_ERRORS) {
            StringBuilder reportData = new StringBuilder();
            reportData.append(getMethodName()).append(TEXT_LINE_DELIMITER);
            if (SettingsManager.getDefaultState().debug)
                Log.e(SettingsManager.getDefaultState().debugTAG, reportData.toString(), e);
            if (SettingsManager.getDefaultState().saveLog)
                writeFormatedLog(reportData.toString() + " "
                        + e.getMessage() + " "
                        + e.getCause(),ERROR);
        }
    }

    public static final void error(Object... data) {
        if(SettingsManager.getDefaultState().debugLevel >= LEVEL_ERRORS) {
            StringBuilder reportData = new StringBuilder();
            reportData.append(getMethodName()).append(TEXT_LINE_DELIMITER);

            for (int i = 0; i < data.length; i++) {
                reportData.append(data[i]).append(TEXT_LINE_DELIMITER);
            }
            if (SettingsManager.getDefaultState().debug)
                Log.e(SettingsManager.getDefaultState().debugTAG, reportData.toString());
            if (SettingsManager.getDefaultState().saveLog)
                writeFormatedLog(reportData.toString(),ERROR);
        }
    }



    private static void writeFormatedLog(String text ,String type)
    {
        FilesManager.saveTextRoundFile(FILELOG,type
                + getTime()
                + text
                + " \n");
        if(SettingsManager.getDefaultState().debugLevel >= LEVEL_VERBOSE_TOAST) {
            Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
        }
        if(callback!= null)
        {
            callback.onLogChanged();
        }
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

                return " " + stackTraceElement[depth].getFileName() + ":"
                        + stackTraceElement[depth].getLineNumber() + " M:"
                        + stackTraceElement[depth].getMethodName();
            }
        }

        return "";
    }


    static private String getTime() {
        Time now = new Time();
        now.setToNow();
        return " ["+now.format3339(false)+"] ";
    }

    public static String getLog() {
        return FilesManager.loadText( FILELOG);
    }

    public static void deleteLog()
    {
        FilesManager.deleteFile(FILELOG);
    }

    public static void setCallback(OnLogChangedCallback callBack)
    {
        callback = callBack;
    }

}
