package gmontenegro.toolboxlib.Tools;

import android.os.Environment;
import android.text.format.Time;
import android.util.Log;
import android.widget.Toast;

import java.security.ProviderException;


/**
 * Created by gmontenegro on 26/07/2016.
 */
public class LogManager extends BaseManager{

    protected static final int LEVEL_VERBOSE = 3;
    protected static final int LEVEL_PROBLEMS = 2;
    protected static final int LEVEL_ERRORS = 1;
    protected static final int LEVEL_OFF = 0;

    private static final String DEBUG = "DD";
    private static final String WARNING = "WW";
    private static final String ERROR = "EE";
    private static final String TEXT_LINE_DELIMITER = " - ";

    protected static OnLogChangedCallback callback;
    /**
     * Muestra el dato pero no lo guarda , solo para debug
     */
    public static void debug(Object... data) {
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
    public static  void warn(Object... data) {
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
    public static  void error( Throwable e, Object... data) {

        if(SettingsManager.getDefaultState().debugLevel >= LEVEL_ERRORS) {
            StringBuilder reportData = new StringBuilder();
            reportData.append(getMethodName()).append(TEXT_LINE_DELIMITER);
            for(int i = 0 ; i < data.length ; i++)
            {
                reportData.append(data[i]).append(TEXT_LINE_DELIMITER);
            }
            if (SettingsManager.getDefaultState().debug)
                Log.e(SettingsManager.getDefaultState().debugTAG, reportData.toString(), e);
            if (SettingsManager.getDefaultState().saveLog)
                writeFormatedLog(reportData.toString() + " "
                        + e.getMessage() + " "
                        + e.getCause(),ERROR);
        }
    }

    public static  void error( ProviderException e, Object... data) {

        if(SettingsManager.getDefaultState().debugLevel >= LEVEL_ERRORS) {
            StringBuilder reportData = new StringBuilder();
            reportData.append(getMethodName()).append(TEXT_LINE_DELIMITER);
            for(int i = 0 ; i < data.length ; i++)
            {
                reportData.append(data[i]).append(TEXT_LINE_DELIMITER);
            }
            if (SettingsManager.getDefaultState().debug)
                Log.e(SettingsManager.getDefaultState().debugTAG, reportData.toString(), e);
            if (SettingsManager.getDefaultState().saveLog)
                writeFormatedLog(reportData.toString() + " "
                        + e.getMessage() + " "
                        + e.getCause(),ERROR);
        }
    }

    public static  void error(Object... data) {
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



    private static void writeFormatedLog(final String text ,String type)
    {
        FilesManager.saveTextRoundFile(SettingsManager.getDefaultState().debugFileName,type
                + getTime()
                + text
                + " \n");
        if(SettingsManager.getDefaultState().debugToasts) {
            //Si es llamado desde un thread en bakground(como los WS) no puedo mostrar el Toast y si
            //en el callback se ejecuta código de UI tambien falla,
            //por lo que si no está en el UIthread, lo corro en UIThread

            try {
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
                if(callback!= null)
                {
                    callback.onLogChanged();
                }
            }
            catch (Exception e)
            {
                if(activity != null)
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
                            if(callback!= null)
                            {
                                callback.onLogChanged();
                            }
                        }
                    });
            }
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
        return FilesManager.loadText( SettingsManager.getDefaultState().debugFileName);
    }

    public static void deleteLog()
    {
        FilesManager.deleteFile(SettingsManager.getDefaultState().debugFileName);
    }

    public static void setCallback(OnLogChangedCallback callBack)
    {
        callback = callBack;
    }

    /***
     * Exporta el archivo de LOG a Documents
     */
    public static void exportLogFile()
    {
        FilesManager.copyToExternalFile( SettingsManager.getDefaultState().debugFileName, Environment.getExternalStoragePublicDirectory("logs").getAbsolutePath());

    }
}
