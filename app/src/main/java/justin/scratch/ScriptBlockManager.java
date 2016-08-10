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
    private static List list=Collections.synchronizedList(scriptBlocks);
    private static ArrayList<NumberVariable> variables=new ArrayList<>();
    private static MainActivity activity;

    public static void setActivity(MainActivity a){
        activity=a;
    }

    public static void removeScriptBlock(ScriptBlock s){
        synchronized(list){
            list.remove(s);
        }
    }
    public static void removeVariable(NumberVariable n){
        variables.remove(n);
    }
    public static void addScriptBlock(ScriptBlock scriptBlock){
        if(scriptBlock.getRectangles()!=null) {
            synchronized (list) {
                list.add(scriptBlock);
            }
        }else{
            variables.add((NumberVariable)scriptBlock);
        }
    }
    public static ArrayList<NumberVariable> getVariables(){
        return variables;
    }

    public static ArrayList<Rect> getRectArray(){
        ArrayList<Rect> rects=new ArrayList<>();
        synchronized (list) {
            for (Iterator<ScriptBlock> iterator = list.iterator(); iterator.hasNext(); ) {
                ScriptBlock s = iterator.next();
                for (Rect r : s.getRectangles()) {
                    rects.add(r);
                }
            }
        }
        return rects;
    }

    public static List getScriptBlocks(){
        return list;
    }

    public static ScriptBlock getTouchFocus(double x,double y){
        ScriptBlock focus=null;
        synchronized(list) {
            for (Iterator<ScriptBlock> iterator = list.iterator(); iterator.hasNext(); ) {
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
