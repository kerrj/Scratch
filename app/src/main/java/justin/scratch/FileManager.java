package justin.scratch;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import justin.scratch.variables.NumberVariable;

/**
 * Created by Justin on 8/3/2016.
 */
public class FileManager {
    private FileOutputStream fos;
    private FileInputStream fis;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
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
    public void save(List<ScriptBlock> blocks,List<NumberVariable> variables,String projectName){
        synchronized (variables) {
            int i=0;
            for (Iterator<NumberVariable> iterator = variables.iterator(); iterator.hasNext(); ) {
                try {
                    NumberVariable n = iterator.next();
                    File file = new File(MainActivity.getActivity().getFilesDir()+"/"+projectName+"/Variables", Integer.toString(i)+".ser");
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    fos = new FileOutputStream(file);
                    oos=new ObjectOutputStream(fos);
                    oos.writeObject(n);
                    oos.close();
                    fos.close();
                    i++;
                }catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            synchronized (blocks){
                int k=0;
                for (Iterator<ScriptBlock> iterator = blocks.iterator(); iterator.hasNext(); ) {
                    try {
                        ScriptBlock s = iterator.next();
                        File file = new File(MainActivity.getActivity().getFilesDir()+"/"+projectName+"/ScriptBlocks", Integer.toString(k)+".ser");
                        if (!file.exists()) {
                            file.createNewFile();
                        }
                        fos = new FileOutputStream(file);
                        oos=new ObjectOutputStream(fos);
                        oos.writeObject(s);
                        oos.close();
                        fos.close();
                        k++;
                    }catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
