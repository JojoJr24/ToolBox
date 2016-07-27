package Elements;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import gmontenegro.toolboxlib.Tools.BaseManager;

/**
 * Created by gmontenegro on 27/07/2016.
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseManager.initManagers(this);
    }
}
