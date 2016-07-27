package gmontenegro.toolbox;

import android.os.Bundle;
import android.widget.TextView;
import gmontenegro.toolboxlib.Tools.BaseManager;
import gmontenegro.toolboxlib.Tools.LogManager;
import gmontenegro.toolboxlib.Tools.PermissionManager;


public class MainActivity extends BaseActivity {

    private TextView logView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PermissionManager.RequestReadWrite(R.string.rw_permission);
        setContentView(R.layout.activity_main);
        logView = (TextView)findViewById(R.id.logView);
        logView.setText(LogManager.getLog());
    }
}
