package justin.scratch;

import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import justin.scratch.Scripting.MainActivity;
import justin.scratch.Scripting.SaveInstance;

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
    public void addProject(String projectName){
        File projects=new File(MainActivity.getActivity().getFilesDir(),"projects.txt");
        if(!projects.exists()){
            try {
                projects.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            fis=new FileInputStream(projects);
            byte[] buffer=new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            fos=new FileOutputStream(projects);
            String contents=new String(buffer,"UTF-8");
            Log.d("Contents",contents);
            contents=contents+projectName+";";
            Log.d("Contents",contents);
            fos.write(contents.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public String[] getProjects(){
        File projects=new File(MainActivity.getActivity().getFilesDir(),"projects.txt");
        if(!projects.exists()){
            try {
                projects.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            fis=new FileInputStream(projects);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<String> s=new ArrayList<>();
        try {
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            String contents=new String(buffer,"UTF-8");
            if(contents.length()>0){
                while(true) {
                    String t = contents.substring(0,contents.indexOf(";"));
                    contents = contents.substring(contents.indexOf(";")+1);
                    s.add(t);
                    Log.d("Running", contents);
                    if (contents.length() == 0) {
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] strings=new String[s.size()];
        int i=0;
        for(String string:s){
            strings[i]=string;
            i++;
        }
        return strings;
    }
    public void save(SaveInstance instance, final String projectName){
        File f=new File(MainActivity.getActivity().getFilesDir()+"/"+projectName+".ser");
        if(!f.exists()){
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try{
            fos = new FileOutputStream(f);
            oos=new ObjectOutputStream(fos);
            oos.writeObject(instance);
            MainActivity.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.getActivity().getBaseContext(),"Saved Project: "+projectName,Toast.LENGTH_LONG).show();
                }
            });
            oos.close();
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

    }
    public SaveInstance load(String projectName){
        File f=new File(MainActivity.getActivity().getFilesDir(),projectName+".ser");
        if(f.exists()){
            try {
                fis = new FileInputStream(f);
                ois=new ObjectInputStream(fis);
                SaveInstance m=(SaveInstance)ois.readObject();
                ois.close();
                return m;
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }catch(ClassNotFoundException e){
                e.printStackTrace();
            }
        }
        return new SaveInstance();
    }
}
