package gmontenegro.toolboxlib.Tools;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by gmontenegro on 26/07/2016.
 */
public class PermissionManager  extends BaseManager{

    final static String CAMERA = Manifest.permission.CAMERA;
    final static String CONTACTS = Manifest.permission.READ_CONTACTS;
    final static String LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;//android.Manifest.permission.ACCESS_COARSE_LOCATION
    final static String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    final static String READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;

    public static boolean RequestPermission(final String permission , String text)
    {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (activity.shouldShowRequestPermissionRationale(permission)) {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
            alertBuilder.setCancelable(true);
            alertBuilder.setTitle("Permission necessary");
            alertBuilder.setMessage("External storage permission is necessary");
            alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                public void onClick(DialogInterface dialog, int which) {
                    activity.requestPermissions(new String[]{permission}, 0);}});

            AlertDialog alert = alertBuilder.create();
            alert.show();
        } else {
            activity.requestPermissions(new String[]{permission}, 0);
        }
        return false;
    }

    public static boolean RequestMultiplePermission(final List<String> permissions, CharSequence text)
    {

        final ArrayList<String> permissionsNeeded = new ArrayList<String>();

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        for (String permission: permissions) {
            if (context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
                permissionsNeeded.add(permission);

            }

        }
        if(permissionsNeeded.isEmpty())
            return true;


        boolean permissionRationaleNeeded = false;
        for (String permission: permissionsNeeded) {
            if (activity.shouldShowRequestPermissionRationale(permission))
                permissionRationaleNeeded = true;
        }
        if (permissionRationaleNeeded) {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
            alertBuilder.setCancelable(true);
            alertBuilder.setTitle("Permission necessary");
            alertBuilder.setMessage("External storage permission is necessary");
            alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                public void onClick(DialogInterface dialog, int which) {
                    activity.requestPermissions((String[]) permissionsNeeded.toArray(), 0);}});

            AlertDialog alert = alertBuilder.create();
            alert.show();
        } else {
            activity.requestPermissions((String[]) permissionsNeeded.toArray(), 0);
        }
        return false;
    }

    public static boolean RequestCamera( int StringRes) {
        return RequestPermission(CAMERA, context.getResources().getString(StringRes));
    }

    public static boolean RequestContacts( int StringRes) {
        return RequestPermission(CONTACTS , context.getResources().getString(StringRes));
    }

    public static boolean RequestLocation( int StringRes) {
        return RequestPermission(LOCATION, context.getResources().getString(StringRes));
    }

    public static boolean RequestReadWrite( int StringRes) {
        return RequestMultiplePermission(Arrays.asList(WRITE_EXTERNAL_STORAGE,READ_EXTERNAL_STORAGE)
                , context.getResources().getString(StringRes));
    }




////////////////////////////////////////////////////////////////////////////
    /// Este metodo va en el activity que lo llamó
    /**
     * Callback received when a permissions request has been completed.
     */
    /*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            }
        }
    }*/
    ///////////////////////////////////////////////////////////////////////////////////
}
