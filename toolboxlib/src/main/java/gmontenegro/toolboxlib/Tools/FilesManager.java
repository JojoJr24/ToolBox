package gmontenegro.toolboxlib.Tools;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
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

            ret = new HashMap<String,Object>((HashMap<String,Object>)objectInputStream.readObject());
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
               file = file.substring(Math.min(textSize,file.length()));
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

    public static void copyToExternalFile(String inputFile, String outputPath) {

        InputStream in = null;
        OutputStream out = null;
        try {

            //create output directory if it doesn't exist
            File dir = new File (outputPath);
            if (!dir.exists())
            {
                dir.mkdirs();
            }


            in = context.openFileInput(inputFile);
            out = new FileOutputStream(outputPath +"/"+ inputFile);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;

            // write the output file (You have now copied the file)
            out.flush();
            out.close();
            out = null;

        }  catch (FileNotFoundException fnfe1) {
            LogManager.error(fnfe1.getMessage());
        }
        catch (Exception e) {
            LogManager.error( e.getMessage());
        }

    }

    public static void copyExternalFile(String inputPath, String inputFile, String outputPath) {

        InputStream in = null;
        OutputStream out = null;
        try {

            //create output directory if it doesn't exist
            File dir = new File (outputPath);
            if (!dir.exists())
            {
                dir.mkdirs();
            }


            in = new FileInputStream(inputPath + inputFile);
            out = new FileOutputStream(outputPath + inputFile);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;

            // write the output file (You have now copied the file)
            out.flush();
            out.close();
            out = null;

        }  catch (FileNotFoundException fnfe1) {
           LogManager.error(fnfe1.getMessage());
        }
        catch (Exception e) {
            LogManager.error( e.getMessage());
        }

    }


    public static void deleteExternalFile(String inputPath, String inputFile) {
        try {
            // delete the original file
            new File(inputPath + inputFile).delete();
        }
        catch (Exception e) {
            LogManager.error( e.getMessage());
        }
    }

    public static void moveFile(String inputPath, String inputFile, String outputPath) {

        InputStream in = null;
        OutputStream out = null;
        try {

            //create output directory if it doesn't exist
            File dir = new File (outputPath);
            if (!dir.exists())
            {
                dir.mkdirs();
            }


            in = new FileInputStream(inputPath + inputFile);
            out = new FileOutputStream(outputPath + inputFile);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;

            // write the output file
            out.flush();
            out.close();
            out = null;

            // delete the original file
            new File(inputPath + inputFile).delete();


        }

        catch (FileNotFoundException fnfe1) {
            LogManager.error(fnfe1.getMessage());
        }
        catch (Exception e) {
            LogManager.error( e.getMessage());
        }

    }

}
