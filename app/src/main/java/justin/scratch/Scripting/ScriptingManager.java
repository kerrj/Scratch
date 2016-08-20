package justin.scratch.Scripting;

import android.graphics.Rect;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import justin.scratch.Scripting.sprites.Sprite;
import justin.scratch.Scripting.variables.NumberVariable;

/**
 * Created by Justin on 8/4/2016.
 */
public class ScriptingManager implements Serializable{
    private static ArrayList<ScriptBlock> scriptBlocks =new ArrayList<>();
    private static List<ScriptBlock> scriptBlockList =Collections.synchronizedList(scriptBlocks);
    private static ArrayList<NumberVariable> variables=new ArrayList<>();
    private static List<NumberVariable> variableList=Collections.synchronizedList(variables);
    private static ArrayList<Sprite> sprites=new ArrayList<>();
    private static List<Sprite> spriteList=Collections.synchronizedList(sprites);

    private static Sprite currentSprite;


    public static SaveInstance getSaveInstance(){
        SaveInstance i=new SaveInstance();
        i.init();
        return i;
    }
    public static void setCurrentSprite(String s){
        for(Iterator<Sprite> i=spriteList.iterator();i.hasNext();){
            Sprite p=i.next();
            if(s.equals(p.getName())){
                currentSprite=p;
            }
        }
    }
    public static List<ScriptBlock> getCurrentScriptBlocks(){
        return currentSprite.getScriptBlocks();
    }
    public static Sprite getCurrentSprite(){
        return currentSprite;
    }
    public static void loadFromSave(SaveInstance saveInstance){
        scriptBlocks=saveInstance.getScriptBlocks();
        variables=saveInstance.getVariables();
        sprites=saveInstance.getSprites();
        scriptBlockList=Collections.synchronizedList(scriptBlocks);
        variableList=Collections.synchronizedList(variables);
        spriteList=Collections.synchronizedList(sprites);
    }

    public static List<Sprite> getSprites(){
        return spriteList;
    }
    public static void addSprite(Sprite s){
        synchronized(spriteList){
            spriteList.add(s);
        }
    }
    public static void removeSprite(Sprite s){
        synchronized(spriteList){
            spriteList.remove(s);
        }
    }
    public static void removeScriptBlock(ScriptBlock s){
        synchronized(scriptBlockList){
            scriptBlockList.remove(s);
        }
        currentSprite.removeScriptBlock(s);
    }
    public static void removeVariable(NumberVariable n) {
        synchronized (variableList){
        variableList.remove(n);
        }
    }
    public static void addScriptBlock(ScriptBlock scriptBlock){
        if(scriptBlock.getRectangles()!=null) {
            synchronized (scriptBlockList) {
                scriptBlockList.add(scriptBlock);
            }
            currentSprite.addScriptBlock(scriptBlock);
        }else{
            variableList.add((NumberVariable)scriptBlock);
        }
    }
    public static List<NumberVariable> getVariables(){
        return variableList;
    }

    public static ArrayList<Rect> getRectArray(){
        ArrayList<Rect> rects=new ArrayList<>();
        synchronized (currentSprite.getScriptBlocks()) {
            for (Iterator<ScriptBlock> iterator = currentSprite.getScriptBlocks().iterator(); iterator.hasNext(); ) {
                ScriptBlock s = iterator.next();
                for (Rect r : s.getRectangles()) {
                    rects.add(r);
                }
            }
        }
        return rects;
    }

    public static List<ScriptBlock> getScriptBlocks(){
        return scriptBlockList;
    }

    public static ScriptBlock getTouchFocus(double x,double y){
        ScriptBlock focus=null;
        synchronized(currentSprite.getScriptBlocks()) {
            for (Iterator<ScriptBlock> iterator = currentSprite.getScriptBlocks().iterator(); iterator.hasNext(); ) {
                ScriptBlock s = iterator.next();
                for (Rect r : s.getRectangles()) {
                    if (r.contains((int) x, (int) y)) {
                        focus = s;
                        break;
                    }
                }
                if (focus != null) {
                    break;
                }
            }
        }
        return focus;
    }
}
