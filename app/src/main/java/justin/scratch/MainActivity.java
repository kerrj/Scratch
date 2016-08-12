package justin.scratch;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextSwitcher;
import android.widget.TextView;

import java.util.Iterator;
import java.util.ListIterator;

import bsh.EvalError;
import bsh.Interpreter;
import bsh.TargetError;
import justin.scratch.logic.If;
import justin.scratch.variables.NumberVariable;

public class MainActivity extends ActionBarActivity {
    private FileManager fileManager=new FileManager();
    private EditText scriptEditor;
    private ListView listView;
    private DrawerLayout drawerLayout;
    private DrawerItemClickListener clickListener;
    private MySurfaceView surfaceView;
    private static MainActivity activity;

    public static MainActivity getActivity(){
        return activity;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity=this;
        surfaceView=(MySurfaceView)findViewById(R.id.surface_view);
        listView=(ListView)findViewById(R.id.left_drawer);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        clickListener=new DrawerItemClickListener(listView,this.getBaseContext(),drawerLayout);
        listView.setAdapter(new ArrayAdapter<>(this, R.layout.drawer_item_layout, getResources().getStringArray(R.array.drawer_items)));
        listView.setOnItemClickListener(clickListener);
        fileManager.init();
    }

    public void run(View view){
        String input="";
        synchronized (ScriptBlockManager.getVariables()) {
            for (Iterator<NumberVariable> iterator = ScriptBlockManager.getVariables().iterator(); iterator.hasNext(); ) {
                NumberVariable n = iterator.next();
                input += n.parse();
            }
        }
        synchronized(ScriptBlockManager.getScriptBlocks()) {
            for (Iterator<ScriptBlock> iterator = ScriptBlockManager.getScriptBlocks().iterator(); iterator.hasNext(); ) {
                ScriptBlock s = iterator.next();
                if(s.getType().equalsIgnoreCase("Start")) {
                    input += s.parse();
                }
            }
        }
        fileManager.setScript(input);
        Intent intent=new Intent(MainActivity.this,RunActivity.class);
        startActivity(intent);
    }
    public void save(View view){
    }
}
