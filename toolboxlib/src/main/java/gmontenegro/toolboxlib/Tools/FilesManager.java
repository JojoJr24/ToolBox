package gmontenegro.toolboxlib.Tools;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

/**
 * Created by gmontenegro on 26/07/2016.
 */
public class FilesManager extends BaseManager{

    public static void saveHashmap(String filename , HashMap map)
    {
        try
        {
            FileOutputStream fileOutputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream= new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(map);
            objectOutputStream.close();


        } catch (IOException e) {
            LogManager.error(e ,"Filemanager");
        }

    }

    public static HashMap loadHashmap(String filename)
    {
        HashMap ret;
        try
        {
            FileInputStream fileInputStream  = context.openFileInput(filename);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

            ret = new HashMap<String,Object>((HashMap)objectInputStream.readObject());
            objectInputStream.close();
            return ret;
        }
        catch(Exception e) {
            LogManager.error(e,"Filemanager");
            return null;
        }
    }


    public static  void saveText(String filename , String text , boolean append)
    {
        try
        {
            FileOutputStream fileOutputStream = context.openFileOutput(filename
                    , append?Context.MODE_APPEND : Context.MODE_PRIVATE);
            fileOutputStream.write(text.getBytes());
            fileOutputStream.close();


        } catch (IOException e) {
            LogManager.error(e,"Filemanager");
        }

    }

    public static  void saveTextRoundFile(String filename , String text )
    {
        try
        {
            String file = loadText(filename);
            int maxSize = SettingsManager.getDefaultState().debugFileSize;
            int textSize = text.length();
            //Si el archivo con el nuevo texto supera el tamaño maximo
            //borra una porsion del principio equivalente al nuevo texto
            if(file.length()+ textSize> maxSize)
            {
               file = file.substring(textSize);
            }
            //Agrega el nuevo texto
            file = file + text;
            FileOutputStream fileOutputStream = context.openFileOutput(filename
                    , Context.MODE_PRIVATE);
            fileOutputStream.write(file.getBytes());
            fileOutputStream.close();


        } catch (IOException e) {
            LogManager.error(e,"Filemanager");
        }

    }

    public static String loadText(String filename)
    {
        String ret="";
        try
        {
            FileInputStream fileInputStream  = context.openFileInput(filename);

            int i = 0;
            char c;
            // read till the end of the file
            while((i=fileInputStream.read())!=-1)
            {
                // converts integer to character
                c=(char)i;

                ret = ret + c;
            }

            return ret;
        }
        catch(Exception e) {
            //LogManager.error(e, "Filemanager");
            return ret;
        }
    }

    public static  void saveTextEncrypted(String filename , String text , boolean append)
    {
            saveText(filename, EncryptionManager.encrypt(text), append);
    }



    public static String loadTextDecrypted(String filename)
    {

        return  EncryptionManager.decrypt(loadText(filename)) ;
    }


    public static void deleteFile(String filename)
    {
        context.deleteFile(filename);
    }

}
