package justin.scratch;

import android.app.FragmentManager;
import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import justin.scratch.control.Start;
import justin.scratch.logic.If;
import justin.scratch.variables.NumberVariable;

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
                        for(NumberVariable v:ScriptBlockManager.getVariables()){
                            display.add(v.getName());
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
                }
                if(i>1){//user clicked a variable, open up dialog
                    listView.setAdapter(new ArrayAdapter<>(context, R.layout.drawer_item_layout,
                            context.getResources().getStringArray(R.array.drawer_items)));
                    drawerLayout.closeDrawer(Gravity.START);
                    ScriptBlockManager.getVariables().get(i-2).makeDialog();
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
                        break;
                }
                listView.setAdapter(new ArrayAdapter<>(context, R.layout.drawer_item_layout,
                        context.getResources().getStringArray(R.array.drawer_items)));
                index=-1;
                break;
        }
    }
}
