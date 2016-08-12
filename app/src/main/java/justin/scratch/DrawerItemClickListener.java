package justin.scratch;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Environment;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.apache.http.cookie.SM;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import justin.scratch.Math.Add;
import justin.scratch.Math.Divide;
import justin.scratch.Math.Multiply;
import justin.scratch.Math.Subtract;
import justin.scratch.control.Start;
import justin.scratch.logic.And;
import justin.scratch.logic.EqualTo;
import justin.scratch.logic.GreaterThan;
import justin.scratch.logic.If;
import justin.scratch.logic.LessThan;
import justin.scratch.logic.Or;
import justin.scratch.logic.Repeat;
import justin.scratch.logic.While;
import justin.scratch.variables.NumberVariable;
import justin.scratch.variables.SetNumberVariable;

/**
 * Created by Justin on 8/3/2016.
 */
public class DrawerItemClickListener implements ListView.OnItemClickListener {
    private ListView listView;
    private int index=-1;
    private Context context;
    private DrawerLayout drawerLayout;

    public DrawerItemClickListener(ListView list,Context c,DrawerLayout d){
        listView=list;
        context=c;
        drawerLayout=d;
    }
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d("View",view.toString());
        Log.d("i",Integer.toString(i));
        switch (index){
            case -1:
                //starting from main menu
                switch(i) {
                    case 0://control
                        listView.setAdapter(new ArrayAdapter<>(context, R.layout.drawer_item_layout,
                                context.getResources().getStringArray(R.array.control_items)));
                        break;
                    case 1://logic
                        listView.setAdapter(new ArrayAdapter<>(context, R.layout.drawer_item_layout,
                                context.getResources().getStringArray(R.array.logic_items)));
                        break;
                    case 2://variables
                        ArrayList<String> display=new ArrayList<>();
                        for(String s:context.getResources().getStringArray(R.array.variables_items)){
                            display.add(s);
                        }
                        synchronized (ScriptBlockManager.getVariables()) {
                            for (Iterator<NumberVariable> iterator = ScriptBlockManager.getVariables().iterator(); iterator.hasNext(); ) {
                                NumberVariable n = iterator.next();
                                display.add(n.getName());
                            }
                        }
                        listView.setAdapter(new ArrayAdapter<>(context, R.layout.drawer_item_layout, display));
                        break;
                    case 3://sprites
                        listView.setAdapter(new ArrayAdapter<>(context, R.layout.drawer_item_layout,
                                context.getResources().getStringArray(R.array.sprites_items)));
                        break;
                    case 4://math
                        listView.setAdapter(new ArrayAdapter<>(context, R.layout.drawer_item_layout,
                                context.getResources().getStringArray(R.array.math_items)));
                        break;

                }
                index = i;
                break;
            case 0://control menu
                switch(i){
                    case 1://start
                        drawerLayout.closeDrawer(Gravity.START);
                        ScriptBlockManager.addScriptBlock(new Start());
                        break;
                }
                listView.setAdapter(new ArrayAdapter<>(context, R.layout.drawer_item_layout,
                        context.getResources().getStringArray(R.array.drawer_items)));
                index=-1;
                break;
            case 1://logic menu
                switch(i){
                    case 1://if
                        drawerLayout.closeDrawer(Gravity.START);
                        ScriptBlockManager.addScriptBlock(new If());
                        break;
                    case 2://while
                        drawerLayout.closeDrawer(Gravity.START);
                        ScriptBlockManager.addScriptBlock(new While());
                        break;
                    case 3://repeat
                        drawerLayout.closeDrawer(Gravity.START);
                        ScriptBlockManager.addScriptBlock(new Repeat());
                        break;
                    case 4://equal to
                        drawerLayout.closeDrawer(Gravity.START);
                        ScriptBlockManager.addScriptBlock(new EqualTo());
                        break;
                    case 5://less than
                        drawerLayout.closeDrawer(Gravity.START);
                        ScriptBlockManager.addScriptBlock(new LessThan());
                        break;
                    case 6://greater than
                        drawerLayout.closeDrawer(Gravity.START);
                        ScriptBlockManager.addScriptBlock(new GreaterThan());
                        break;
                    case 7://and
                        drawerLayout.closeDrawer(Gravity.START);
                        ScriptBlockManager.addScriptBlock(new And());
                        break;
                    case 8://or
                        drawerLayout.closeDrawer(Gravity.START);
                        ScriptBlockManager.addScriptBlock(new Or());
                        break;
                }
                listView.setAdapter(new ArrayAdapter<>(context, R.layout.drawer_item_layout,
                        context.getResources().getStringArray(R.array.drawer_items)));
                index=-1;
                break;
            case 2://variables menu
                switch(i){
                    case 0://back
                        listView.setAdapter(new ArrayAdapter<>(context, R.layout.drawer_item_layout,
                                context.getResources().getStringArray(R.array.drawer_items)));
                        break;
                    case 1://number
                        drawerLayout.closeDrawer(Gravity.START);
                        ScriptBlockManager.addScriptBlock(new NumberVariable());
                        listView.setAdapter(new ArrayAdapter<>(context, R.layout.drawer_item_layout,
                                context.getResources().getStringArray(R.array.drawer_items)));
                        break;
                    case 2://set variable
                        drawerLayout.closeDrawer(Gravity.START);
                        ScriptBlockManager.addScriptBlock(new SetNumberVariable());
                        listView.setAdapter(new ArrayAdapter<>(context, R.layout.drawer_item_layout,
                                context.getResources().getStringArray(R.array.drawer_items)));
                        break;

                }
                if(i>2){//user clicked a variable, open up dialog
                    listView.setAdapter(new ArrayAdapter<>(context, R.layout.drawer_item_layout,
                            context.getResources().getStringArray(R.array.drawer_items)));
                    drawerLayout.closeDrawer(Gravity.START);
                    ScriptBlockManager.getVariables().get(i-3).makeDialog();
                }
                index=-1;
                break;
            case 3://sprites menu
                switch(i){
                    case 1://sprite
                        drawerLayout.closeDrawer(Gravity.START);
                        break;
                }
                listView.setAdapter(new ArrayAdapter<>(context, R.layout.drawer_item_layout,
                        context.getResources().getStringArray(R.array.drawer_items)));
                index=-1;
                break;
            case 4://math menu
                switch(i){
                    case 1://add
                        drawerLayout.closeDrawer(Gravity.START);
                        ScriptBlockManager.addScriptBlock(new Add());
                        break;
                    case 2://subtract
                        drawerLayout.closeDrawer(Gravity.START);
                        ScriptBlockManager.addScriptBlock(new Subtract());
                        break;
                    case 3://multiply
                        drawerLayout.closeDrawer(Gravity.START);
                        ScriptBlockManager.addScriptBlock(new Multiply());
                        break;
                    case 4://divide
                        drawerLayout.closeDrawer(Gravity.START);
                        ScriptBlockManager.addScriptBlock(new Divide());
                        break;
                }
                listView.setAdapter(new ArrayAdapter<>(context, R.layout.drawer_item_layout,
                        context.getResources().getStringArray(R.array.drawer_items)));
                index=-1;
                break;
        }
    }
}
