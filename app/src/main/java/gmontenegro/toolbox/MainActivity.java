package gmontenegro.toolbox;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.springframework.http.HttpMethod;

import Elements.BaseActivity;
import gmontenegro.toolboxlib.Tools.LogManager;
import gmontenegro.toolboxlib.Tools.OnLogChangedCallback;
import gmontenegro.toolboxlib.Tools.OnWebServiceResponseCallback;
import gmontenegro.toolboxlib.Tools.PermissionManager;
import gmontenegro.toolboxlib.Tools.SoapWSManager;


public class MainActivity extends BaseActivity implements OnWebServiceResponseCallback {

    private TextView logView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PermissionManager.RequestReadWrite(R.string.rw_permission);
        setContentView(R.layout.activity_main);
        logView = (TextView) findViewById(R.id.logView);

        LogManager.deleteLog();
        LogManager.debug("El secreto de todo es " + LocalSettingManager.getLocalState().otro);
        LogManager.warn("Warning");

        LogManager.setCallback(new OnLogChangedCallback() {
            @Override
            public void onLogChanged() {
                logView.setText(LogManager.getLog());
            }
        });

    }

    public void callWSREST(View v) {
        new RestWSMock(this, HttpMethod.GET, "http://192.168.1127.111:8080/user/"
                , "user", "175"
                , "deviceToken", "Token")
                .initHeaders("Accept", "application/json",
                        "Content-Type", "application/json")
                .initBody("Color" , "Verde",
                        "Largo" , "1")
                .execute(true);

    }

    public void callWSSoap(View v) {
        new SoapWSMock(this,
                "http://192.168.127.56:8088/mockSampleServiceSoapBinding",
                "login",
                SoapWSManager.createParameter("username", "Login", "password", "Login123")).execute(true);

    }

    public void exportLog(View v) {
        LogManager.exportLogFile();
    }

    @Override
    public void onWebServiceResponse(Object response) {
        LogManager.debug("Respondió " + response.toString());
        Toast.makeText(this, "Respondió " + response.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onWebServiceFail(String e) {
        LogManager.error("Falló " + e);
    }
}
