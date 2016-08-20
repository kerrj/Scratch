package justin.scratch.Scripting;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import justin.scratch.Scripting.sprites.Sprite;
import justin.scratch.Scripting.variables.NumberVariable;

/**
 * Created by Justin on 8/12/2016.
 */
public class SaveInstance implements Serializable {
    private ArrayList<ScriptBlock> scriptBlocks =new ArrayList<>();
    private ArrayList<NumberVariable> variables=new ArrayList<>();
    private ArrayList<Sprite> sprites=new ArrayList<>();

    public SaveInstance(){

    }
    public void init(){
        synchronized (ScriptingManager.getVariables()){
            for(Iterator<NumberVariable> iterator = ScriptingManager.getVariables().iterator(); iterator.hasNext();){
                variables.add(iterator.next());
            }
        }
        synchronized (ScriptingManager.getScriptBlocks()){
            for(Iterator<ScriptBlock> iterator = ScriptingManager.getScriptBlocks().iterator(); iterator.hasNext();){
                scriptBlocks.add(iterator.next());
            }
        }
        synchronized (ScriptingManager.getSprites()){
            for(Iterator<Sprite> iterator = ScriptingManager.getSprites().iterator(); iterator.hasNext();){
                sprites.add(iterator.next());
            }
        }
    }

    public ArrayList<ScriptBlock> getScriptBlocks(){
        return scriptBlocks;
    }
    public ArrayList<NumberVariable> getVariables(){
        return variables;
    }
    public ArrayList<Sprite> getSprites(){ return sprites;}
}
