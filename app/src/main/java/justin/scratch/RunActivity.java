package justin.scratch;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;

import bsh.EvalError;
import bsh.Interpreter;
import bsh.TargetError;

/**
 * Created by Justin on 8/3/2016.
 */
public class RunActivity extends ActionBarActivity {
    private TextView textDisplay;
    private FileManager fileManager=new FileManager();
    private Interpreter i=new Interpreter();
    @Override
    protected void onCreate(Bundle savedInstanceStance){
        super.onCreate(savedInstanceStance);
        setContentView(R.layout.activity_run);
        textDisplay=(TextView)findViewById(R.id.text);
        initInterpreter(i);
        run();
    }

    public void run(){
        try {
            i.eval(fileManager.getScript());
        } catch ( TargetError e ) {
            // The script threw an exception
            Throwable t = e.getTarget();
            Log.e("TargetError","----------------------------------------------");
            Log.e("TargetError","Message: "+t.toString());
            textDisplay.setText("TargetError: " + t.toString());
        } catch ( bsh.ParseException e ) {
            // Parsing error
            Log.e("ParseException","----------------------------------------------");
            Log.e("ParseException","Message: "+e.getMessage());
            textDisplay.setText("ParseException: " + e.getMessage());
        }catch (EvalError e) {
            //general error
            Log.e("EvalError", "------------------------------------------");
            Log.e("EvalError","Message: "+e.getMessage());
            textDisplay.setText("EvalError: " + e.getMessage());
        }
    }

    public void initInterpreter(Interpreter i){
        try {
            i.set("textView", textDisplay);                   //pass the textview to the intepreter for printing
            i.set("activity",this);                           //pass the activity to the interpreter for context
            i.eval("import android.os.Handler;" +             //initialize print() command
                    "public void print(String message){" +
                    "Handler handler=new Handler();"+
                    "handler.post(new Runnable(){" +
                    "public void run(){" +
                    "textView.setText(message);" +
                    "}"+
                    "});"+
                    "}");
        }catch (EvalError e){
            Log.e("error", e.getMessage());
        }
    }
}
