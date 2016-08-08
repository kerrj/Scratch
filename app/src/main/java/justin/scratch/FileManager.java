package justin.scratch;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Justin on 8/3/2016.
 */
public class FileManager {
    private FileOutputStream fos;
    private FileInputStream fis;
    private File script=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)+"/Scratch/Script.txt");

    public void init(){
        File scratchDir=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)+"/Scratch");
        if(!scratchDir.exists()){
            scratchDir.mkdirs();
        }
        if(!script.exists()){
            try {
                script.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getScript(){
        String string="";
        try {
            fis=new FileInputStream(script);
            byte[] buffer=new byte[fis.available()];
            fis.read(buffer);
            string=new String(buffer,"UTF-8");
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (java.io.IOException e){
            e.printStackTrace();
        }finally{
            return string;
        }
    }

    public void setScript(String message){
        try {
            fos=new FileOutputStream(script);
            fos.write(message.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch(java.io.IOException e){
            e.printStackTrace();
        }
    }
}
