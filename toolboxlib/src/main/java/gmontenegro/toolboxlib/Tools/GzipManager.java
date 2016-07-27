package gmontenegro.toolboxlib.Tools;

import android.util.Base64;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by GMontenegro on 25/02/16.
 */
public class GzipManager extends BaseManager{

    public static byte[] zipToByteArray(String bytes) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(bytes.length());
        GZIPOutputStream gzip = new GZIPOutputStream(bos);
        gzip.write(bytes.getBytes());
        gzip.close();
        byte[] compressed = bos.toByteArray();
        bos.close();
        return compressed;

    }


    public static byte[] zipToByteArray64(String bytes) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(bytes.length());
        GZIPOutputStream gzip = new GZIPOutputStream(bos);
        gzip.write(bytes.getBytes());
        gzip.close();
        byte[] compressed = bos.toByteArray();
        bos.close();
        return  Base64.encode(compressed, Base64.DEFAULT);

    }

    public static byte[] zipToByteArray(byte[] bytes) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(bytes.length);
        GZIPOutputStream gzip = new GZIPOutputStream(bos);
        gzip.write(bytes);
        gzip.close();
        byte[] compressed = bos.toByteArray();
        bos.close();
        return compressed;

    }

    public static String unzipToString(byte[] compressed) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(compressed);
        GZIPInputStream gis = new GZIPInputStream(bis);
        BufferedReader br = new BufferedReader(new InputStreamReader(gis, "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line;
        while((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        gis.close();
        bis.close();
        return sb.toString();
    }
}
