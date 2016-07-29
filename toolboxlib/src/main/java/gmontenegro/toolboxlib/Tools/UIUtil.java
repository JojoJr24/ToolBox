package gmontenegro.toolboxlib.Tools;

import android.content.res.Resources;
import android.util.TypedValue;

/**
 * Created by gmontenegro on 26/07/2016.
 */
public class UIUtil extends BaseManager {


    static public int DpToPixels(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }

    static public int SpToPixels(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, Resources.getSystem().getDisplayMetrics());
    }
}
