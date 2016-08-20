package justin.scratch.Scripting;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.Toast;

import java.util.Iterator;

import justin.scratch.FileManager;
import justin.scratch.R;
import justin.scratch.Runtime.RunActivity;
import justin.scratch.Scripting.Dialogs.NewProjectDialog;
import justin.scratch.Scripting.Dialogs.NewSpriteDialog;
import justin.scratch.Scripting.sprites.Sprite;
import justin.scratch.Scripting.variables.NumberVariable;

@SuppressLint("ParcelCreator")
public class MainActivity extends ActionBarActivity implements NewProjectDialog.NewProjectListener,Parcelable,NewSpriteDialog.NewSpriteListener {
    private FileManager fileManager=new FileManager();
    private ListView listView;
    private DrawerLayout drawerLayout;
    private DrawerItemClickListener clickListener;
    private ScriptBlockSurfaceView surfaceView;
    private static MainActivity activity;
    private String currentProject;
    private String currentSprite="";

    public static MainActivity getActivity(){
        return activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity=this;
        setContentView(R.layout.project_menu);
        TableLayout layout=(TableLayout)findViewById(R.id.project_menu_TableLayout);
        fileManager.init();
        String[] projects=fileManager.getProjects();
        LayoutInflater inflater = (LayoutInflater)getBaseContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for(String s:projects){
            Button button=new Button(this);
            button.setOnClickListener(projectListener);
            button.setText(s);
            layout.addView(button);
        }
    }
    View.OnClickListener projectListener =new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Button b=(Button)view;
            currentProject=(String)b.getText();
            Log.d("Click",currentProject);
            setContentView(R.layout.activity_main);
            setTitle("Scratch: "+currentProject);
            surfaceView=(ScriptBlockSurfaceView)findViewById(R.id.surface_view);
            listView=(ListView)findViewById(R.id.left_drawer);
            drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
            clickListener=new DrawerItemClickListener(listView,getBaseContext(),drawerLayout);
            listView.setAdapter(new ArrayAdapter<>(MainActivity.getActivity().getBaseContext(), R.layout.drawer_item_layout, getResources().getStringArray(R.array.drawer_items)));
            listView.setOnItemClickListener(clickListener);
            ScriptingManager.loadFromSave(fileManager.load(currentProject));
            ScriptingManager.setCurrentSprite(ScriptingManager.getSprites().get(0).getName());
            populateSprites();
        }
    };
    View.OnClickListener spriteListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Button button=(Button)view;
            currentSprite=(String)button.getText();
            ScriptingManager.setCurrentSprite(currentSprite);
            populateSprites();
        }
    };
    @Override
    public void onPause(){
        super.onPause();
        if(currentProject!=null) {
            fileManager.save(ScriptingManager.getSaveInstance(), currentProject);
        }
    }

    public void projects(View view){
        setContentView(R.layout.project_menu);
        setTitle("Scratch");
        fileManager.save(ScriptingManager.getSaveInstance(),currentProject);
        TableLayout layout=(TableLayout)findViewById(R.id.project_menu_TableLayout);
        String[] projects=fileManager.getProjects();
        LayoutInflater inflater = (LayoutInflater)getBaseContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for(String s:projects){
            Button button=new Button(this);
            button.setOnClickListener(projectListener);
            button.setText(s);
            layout.addView(button);
        }
        currentProject=null;
        currentSprite=null;
    }

    private int id=1;
    public void newSprite(View view){
        NewSpriteDialog dialog=new NewSpriteDialog();
        Bundle s=new Bundle();
        s.putParcelable("Activity",this);
        dialog.setArguments(s);
        dialog.show(getFragmentManager(),"newsprite");
    }
    @Override
    public void onPositiveSpriteClick(DialogFragment f,Bundle s){
        String name=s.getString("spriteName");
        if(name.length()==0){
            Toast.makeText(getBaseContext(),"Sprite must have a name",Toast.LENGTH_SHORT).show();
            return;
        }
        for(Iterator<Sprite> iterator =ScriptingManager.getSprites().iterator();iterator.hasNext();){
            if(iterator.next().getName().equalsIgnoreCase(name)){
                Toast.makeText(getBaseContext(),"Sprite name must be unique",Toast.LENGTH_SHORT).show();
                return;
            }
        }
        ScriptingManager.addSprite(new Sprite(name));
        ScriptingManager.setCurrentSprite(name);
        populateSprites();
//        RelativeLayout layout=(RelativeLayout)findViewById(R.id.activity_main_spriteLayout);
//        Button button=new Button(getBaseContext());
//        button.setId(id);
//        button.setOnClickListener(spriteListener);
//        id++;
//        button.setText(name);
//        button.setTextSize(8);
//        button.setTextColor(getResources().getColor(android.R.color.black));
//        button.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));
//        layout.addView(button);
//        RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(button.getLayoutParams());
//        if(layout.getChildCount()>1) {
//            params.addRule(RelativeLayout.RIGHT_OF, layout.getChildAt(layout.getChildCount() - 2).getId());
//        }
//        button.setLayoutParams(params);
    }
    @Override
    public void onNegativeSpriteClick(DialogFragment d){

    }

    public void run(View view){
//        String input="";
//        synchronized (ScriptingManager.getVariables()) {
//            for (Iterator<NumberVariable> iterator = ScriptingManager.getVariables().iterator(); iterator.hasNext(); ) {
//                NumberVariable n = iterator.next();
//                input += n.parse();
//            }
//        }
//        synchronized(ScriptingManager.getScriptBlocks()) {
//            for (Iterator<ScriptBlock> iterator = ScriptingManager.getScriptBlocks().iterator(); iterator.hasNext(); ) {
//                ScriptBlock s = iterator.next();
//                if(s.getType().equalsIgnoreCase("Start")) {
//                    input += s.parse();
//                }
//            }
//        }
//        fileManager.setScript(input);
        Intent intent=new Intent(MainActivity.this,RunActivity.class);
        startActivity(intent);
    }
    public void save(View view){
        fileManager.save(ScriptingManager.getSaveInstance(),currentProject);
    }
    public void populateSprites(){
        Sprite s;
        RelativeLayout layout=(RelativeLayout)findViewById(R.id.activity_main_spriteLayout);
        layout.removeAllViews();
        for(Iterator<Sprite> iterator=ScriptingManager.getSprites().iterator();iterator.hasNext();){
            s=iterator.next();
            Button button=new Button(getBaseContext());
            button.setId(id);
            button.setOnClickListener(spriteListener);
            id++;
            button.setText(s.getName());
            button.setTextSize(8);
            button.setTextColor(getResources().getColor(android.R.color.black));
            if(s.getName().equals(ScriptingManager.getCurrentSprite().getName())) {
                button.setBackgroundColor(getResources().getColor(R.color.colorPrimaryMedium));
            }else {
                button.setBackgroundColor(getResources().getColor(R.color.colorPrimaryLight));
            }
            layout.addView(button);
            RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(button.getLayoutParams());
            if(layout.getChildCount()>1) {
                params.addRule(RelativeLayout.RIGHT_OF, layout.getChildAt(layout.getChildCount() - 2).getId());
            }
            button.setLayoutParams(params);
        }
    }
    public void newProject(View view){
        NewProjectDialog dialog=new NewProjectDialog();
        Bundle s=new Bundle();
        s.putParcelable("Activity",this);
        dialog.setArguments(s);
        dialog.show(this.getFragmentManager(),"newproject");
    }

    @Override
    public void onPositiveClick(DialogFragment d,Bundle s){
        if(s.getString("projectName").length()==0){
            Toast.makeText(getBaseContext(),"Input project name",Toast.LENGTH_LONG).show();
            return;
        }
        for(String t:fileManager.getProjects()){
            if(s.getString("projectName").equalsIgnoreCase(t)){
                Toast.makeText(getBaseContext(),"Name is not unique",Toast.LENGTH_LONG).show();
                return;
            }
        }
        if(s.getString("projectName").contains(";")){
            Toast.makeText(getBaseContext(),"Please don't use semicolons",Toast.LENGTH_LONG).show();
            return;
        }
        fileManager.addProject(s.getString("projectName"));
        currentProject=s.getString("projectName");
        setTitle("Scatch: "+currentProject);
        setContentView(R.layout.activity_main);
        surfaceView=(ScriptBlockSurfaceView)findViewById(R.id.surface_view);
        listView=(ListView)findViewById(R.id.left_drawer);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        clickListener=new DrawerItemClickListener(listView,getBaseContext(),drawerLayout);
        listView.setAdapter(new ArrayAdapter<>(MainActivity.getActivity().getBaseContext(), R.layout.drawer_item_layout, getResources().getStringArray(R.array.drawer_items)));
        listView.setOnItemClickListener(clickListener);
        ScriptingManager.loadFromSave(fileManager.load(currentProject));
        ScriptingManager.addSprite(new Sprite("Sprite"));
        ScriptingManager.setCurrentSprite(ScriptingManager.getSprites().get(0).getName());
        fileManager.save(ScriptingManager.getSaveInstance(),currentProject);
        populateSprites();
    }
    @Override
    public void onNegativeClick(DialogFragment d){

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel p, int i){
        p.writeValue(this);
    }
}
