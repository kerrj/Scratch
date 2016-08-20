package justin.scratch.Scripting;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Iterator;

import justin.scratch.Scripting.Math.Add;
import justin.scratch.Scripting.Math.Divide;
import justin.scratch.Scripting.Math.Multiply;
import justin.scratch.Scripting.Math.Subtract;
import justin.scratch.R;
import justin.scratch.Scripting.control.Start;
import justin.scratch.Scripting.logic.And;
import justin.scratch.Scripting.logic.EqualTo;
import justin.scratch.Scripting.logic.GreaterThan;
import justin.scratch.Scripting.logic.If;
import justin.scratch.Scripting.logic.LessThan;
import justin.scratch.Scripting.logic.Or;
import justin.scratch.Scripting.logic.Repeat;
import justin.scratch.Scripting.logic.While;
import justin.scratch.Scripting.sprites.Print;
import justin.scratch.Scripting.variables.NumberVariable;
import justin.scratch.Scripting.variables.SetNumberVariable;

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
                        synchronized (ScriptingManager.getVariables()) {
                            for (Iterator<NumberVariable> iterator = ScriptingManager.getVariables().iterator(); iterator.hasNext(); ) {
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
                        ScriptingManager.addScriptBlock(new Start());
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
                        ScriptingManager.addScriptBlock(new If());
                        break;
                    case 2://while
                        drawerLayout.closeDrawer(Gravity.START);
                        ScriptingManager.addScriptBlock(new While());
                        break;
                    case 3://repeat
                        drawerLayout.closeDrawer(Gravity.START);
                        ScriptingManager.addScriptBlock(new Repeat());
                        break;
                    case 4://equal to
                        drawerLayout.closeDrawer(Gravity.START);
                        ScriptingManager.addScriptBlock(new EqualTo());
                        break;
                    case 5://less than
                        drawerLayout.closeDrawer(Gravity.START);
                        ScriptingManager.addScriptBlock(new LessThan());
                        break;
                    case 6://greater than
                        drawerLayout.closeDrawer(Gravity.START);
                        ScriptingManager.addScriptBlock(new GreaterThan());
                        break;
                    case 7://and
                        drawerLayout.closeDrawer(Gravity.START);
                        ScriptingManager.addScriptBlock(new And());
                        break;
                    case 8://or
                        drawerLayout.closeDrawer(Gravity.START);
                        ScriptingManager.addScriptBlock(new Or());
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
                        ScriptingManager.addScriptBlock(new NumberVariable());
                        listView.setAdapter(new ArrayAdapter<>(context, R.layout.drawer_item_layout,
                                context.getResources().getStringArray(R.array.drawer_items)));
                        break;
                    case 2://set variable
                        drawerLayout.closeDrawer(Gravity.START);
                        ScriptingManager.addScriptBlock(new SetNumberVariable());
                        listView.setAdapter(new ArrayAdapter<>(context, R.layout.drawer_item_layout,
                                context.getResources().getStringArray(R.array.drawer_items)));
                        break;

                }
                if(i>2){//user clicked a variable, open up dialog
                    listView.setAdapter(new ArrayAdapter<>(context, R.layout.drawer_item_layout,
                            context.getResources().getStringArray(R.array.drawer_items)));
                    drawerLayout.closeDrawer(Gravity.START);
                    ScriptingManager.getVariables().get(i-3).makeDialog();
                }
                index=-1;
                break;
            case 3://sprites menu
                switch(i){
                    case 1://sprite
                        drawerLayout.closeDrawer(Gravity.START);
                        break;
                    case 2://print
                        drawerLayout.closeDrawer(Gravity.START);
                        ScriptingManager.addScriptBlock(new Print());
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
                        ScriptingManager.addScriptBlock(new Add());
                        break;
                    case 2://subtract
                        drawerLayout.closeDrawer(Gravity.START);
                        ScriptingManager.addScriptBlock(new Subtract());
                        break;
                    case 3://multiply
                        drawerLayout.closeDrawer(Gravity.START);
                        ScriptingManager.addScriptBlock(new Multiply());
                        break;
                    case 4://divide
                        drawerLayout.closeDrawer(Gravity.START);
                        ScriptingManager.addScriptBlock(new Divide());
                        break;
                }
                listView.setAdapter(new ArrayAdapter<>(context, R.layout.drawer_item_layout,
                        context.getResources().getStringArray(R.array.drawer_items)));
                index=-1;
                break;
        }
    }
}
