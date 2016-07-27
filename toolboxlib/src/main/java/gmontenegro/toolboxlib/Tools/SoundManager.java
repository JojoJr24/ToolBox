package gmontenegro.toolboxlib.Tools;

import android.annotation.TargetApi;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

/**
 * Created by gmontenegro on 26/07/2016.
 */
public class SoundManager extends BaseManager{

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static void playSound(final int soundRes)
    {
        activity.runOnUiThread(new Runnable() {

            @TargetApi(Build.VERSION_CODES.FROYO)
            @Override
            public void run() {
                SoundPool soundPool = new SoundPool(1, AudioManager.STREAM_NOTIFICATION, 0);
                final int sound = soundPool.load(context, soundRes, 0);
                soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {

                    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                        soundPool.play(sound, 1f, 1f, 0, 0, 1);

                    }
                });



            }
        });

    }
}
