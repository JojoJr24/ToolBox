package gmontenegro.toolboxlib.Tools;

import android.content.Intent;
import android.widget.Toast;

/**
 * Created by gmontenegro on 29/07/2016.
 */
public class OtherAppsConnectionManager extends BaseManager {

    public static void sendMail(String subject, String body, String... to) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, to);
        i.putExtra(Intent.EXTRA_SUBJECT, subject);
        i.putExtra(Intent.EXTRA_TEXT, body);
        try {
            mContext.startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(mContext, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
