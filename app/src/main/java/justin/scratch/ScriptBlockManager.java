package justin.scratch;

import android.graphics.Rect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import justin.scratch.variables.NumberVariable;

/**
 * Created by Justin on 8/4/2016.
 */
public class ScriptBlockManager  {
    private static ArrayList<ScriptBlock> scriptBlocks =new ArrayList<>();
    private static List scriptBlockList =Collections.synchronizedList(scriptBlocks);
    private static ArrayList<NumberVariable> variables=new ArrayList<>();
    private static List<NumberVariable> variableList=Collections.synchronizedList(variables);
    private static MainActivity activity;

    public static void setActivity(MainActivity a){
        activity=a;
    }

    public static void removeScriptBlock(ScriptBlock s){
        synchronized(scriptBlockList){
            scriptBlockList.remove(s);
        }
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
        }else{
            variableList.add((NumberVariable)scriptBlock);
        }
    }
    public static List<NumberVariable> getVariables(){
        return variableList;
    }

    public static ArrayList<Rect> getRectArray(){
        ArrayList<Rect> rects=new ArrayList<>();
        synchronized (scriptBlockList) {
            for (Iterator<ScriptBlock> iterator = scriptBlockList.iterator(); iterator.hasNext(); ) {
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
        synchronized(scriptBlockList) {
            for (Iterator<ScriptBlock> iterator = scriptBlockList.iterator(); iterator.hasNext(); ) {
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
