package justin.scratch.Runtime;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;

import bsh.EvalError;
import bsh.Interpreter;
import bsh.TargetError;
import justin.scratch.FileManager;
import justin.scratch.R;
import justin.scratch.Scripting.ScriptingManager;
import justin.scratch.Scripting.sprites.Sprite;

/**
 * Created by Justin on 8/3/2016.
 */
public class RunActivity extends ActionBarActivity {
    private RuntimeSurfaceView surfaceView;
    private FileManager fileManager=new FileManager();
    private ArrayList<MyThread> threads=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceStance){
        super.onCreate(savedInstanceStance);
        setContentView(R.layout.activity_run);
        surfaceView=(RuntimeSurfaceView)findViewById(R.id.activity_run_surfaceView);
        RuntimeData.init();
        synchronized(ScriptingManager.getSprites()) {
            for(Iterator<Sprite> iterator = ScriptingManager.getSprites().iterator(); iterator.hasNext();) {
                Sprite s=iterator.next();
                threads.add(new MyThread(s.parse()));
                threads.get(threads.size()-1).start();
            }
        }
    }
    @Override
    public void onPause(){
        super.onPause();
        for(MyThread thread:threads){
            thread.slaughter();
        }
        Log.d("Pause","pause");
    }
    public class MyThread extends Thread {
        Interpreter i=new Interpreter();
        String script;
        public MyThread(String s){
            script=s;
        }
        public void slaughter(){
            try {
                i.eval("running=false;");
            } catch (EvalError evalError) {
                evalError.printStackTrace();
            }
        }
        @Override
        public void run(){
            try {
                i.eval("boolean running=true;");
                i.eval("import justin.scratch.Runtime.RuntimeData;public void print(String message){RuntimeData.addPrintLine(message);}");
                i.eval(script);
            } catch ( TargetError e ) {
                // The script threw an exception
                Throwable t = e.getTarget();
                Log.e("TargetError","----------------------------------------------");
                Log.e("TargetError","Message: "+t.toString());
            } catch ( bsh.ParseException e ) {
                // Parsing error
                Log.e("ParseException","----------------------------------------------");
                Log.e("ParseException","Message: "+e.getMessage());
            }catch (EvalError e) {
                //general error
                Log.e("EvalError", "------------------------------------------");
                Log.e("EvalError", "Message: " + e.getMessage());
            }
        }
    }
}
