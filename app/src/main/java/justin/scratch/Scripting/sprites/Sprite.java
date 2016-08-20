package justin.scratch.Scripting.sprites;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import justin.scratch.Scripting.ScriptBlock;
import justin.scratch.Scripting.ScriptingManager;
import justin.scratch.Scripting.variables.NumberVariable;

/**
 * Created by Justin on 8/14/2016.
 */
public class Sprite implements Serializable {
    private double x=100;
    private double y=200;
    private String name;
    private Bitmap bitmap;
    private ArrayList<ScriptBlock> scriptBlocks =new ArrayList<>();
    private List<ScriptBlock> scriptBlockList = Collections.synchronizedList(scriptBlocks);

    public List<ScriptBlock> getScriptBlocks(){
        return scriptBlockList;
    }
    public void addScriptBlock(ScriptBlock s){
        synchronized (scriptBlockList){
            scriptBlockList.add(s);
        }
    }
    public void removeScriptBlock(ScriptBlock s){
        scriptBlockList.remove(s);
    }
    public Sprite(String n){
        name=n;
    }
    public String getName() {
        return this.name;
    }
    public String parse(){
        String input="";
        synchronized (ScriptingManager.getVariables()) {
            for (Iterator<NumberVariable> iterator = ScriptingManager.getVariables().iterator(); iterator.hasNext(); ) {
                NumberVariable n = iterator.next();
                input += n.parse();
            }
        }
        synchronized(scriptBlockList) {
            for (Iterator<ScriptBlock> iterator = scriptBlockList.iterator(); iterator.hasNext(); ) {
                ScriptBlock s = iterator.next();
                if(s.getType().equalsIgnoreCase("Start")) {
                    input += s.parse();
                }
            }
        }
        return input;
    }

}
