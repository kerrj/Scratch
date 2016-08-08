package justin.scratch;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Justin on 8/4/2016.
 */
public class ScriptBlock {
    public double x=300;
    public double y=500;
    public static final int WIDTH=100;
    public static final int LENGTH=400;
    public boolean nested=false;
    public ScriptBlock child=null;
    public ScriptBlock parent=null;
    public ScriptBlock bodyParent=null;
    public ScriptBlock conditionalparent=null;
    public final int TEXT_SIZE=70;

    //mandatory methods to extend for all: getWidth,draw, getChildNode, parse, getRectangles
    //mandatory methods to extend for blocks with a body: getBody, getBodyNode,setPosition, incPosition,setBodyChild,removeBodyChild
    //methods that must call super:draw,incPosition,setPosition
    public void setBodyChild(ScriptBlock s){

    }

    public void removeBodyChild(){

    }
    public ArrayList<ScriptBlock> getBody(boolean allChildren){
        return null;
    }
    public ScriptBlock getBodyChild(){
        return null;
    }
    public ScriptBlock getBodyParent(){
        return bodyParent;
    }
    public void setBodyParent(ScriptBlock s){
        bodyParent=s;
    }
    public void removeBodyParent(){
        bodyParent=null;
    }
    public void addChild(ScriptBlock s){
        child=s;
    }

    public void addParent(ScriptBlock s){
        parent=s;
    }

    public void removeChild(){
        child=null;
    }

    public void removeParent(){
        parent=null;
    }

    public ScriptBlock getChild(){
        return child;
    }

    public ScriptBlock getLastChild(){
        ScriptBlock c = child;
        if(c!=null) {
            if (c.getChild() != null) {
                c = c.getLastChild();
            }
        }else{
            c=this;
        }
        return c;
    }

    public ScriptBlock getParent(){
        return parent;
    }

    public double[] getChildNode(){
        return null;
    }

    public double[] getConditionalChildNode(){
        return null;
    }

    public ScriptBlock getConditionalChild(){
        return null;
    }

    public ScriptBlock getConditionalParent(){
        return conditionalparent;
    }
    public void setConditionalChild(ScriptBlock s){

    }
    public void setConditionalParent(ScriptBlock s){
        conditionalparent=s;
    }
    public void removeConditionalParent(){
        conditionalparent=null;
    }
    public void removeConditionalChild(){
    }
    public void draw(Canvas canvas){
        if(getParent()!=null){
            x=getParent().getChildNode()[0];
            y=getParent().getChildNode()[1];
        }else if(getBodyParent()!=null){
            x=getBodyParent().getBodyNode()[0];
            y=getBodyParent().getBodyNode()[1];
        }else if(getConditionalParent()!=null){
            x=getConditionalParent().getConditionalChildNode()[0];
            y=getConditionalParent().getConditionalChildNode()[1];
        }
    }
    public void makeDialog(){

    }

    public double[] getBodyNode(){
        return null;
    }

    public int getWidth(){
        return WIDTH;
    }

    public void incPosition(double xstep,double ystep,boolean surfaceFocused){
        if(!surfaceFocused) {
            x += xstep;
            y += ystep;
//            if (child != null) {
//                child.incPosition(xstep, ystep,surfaceFocused);
//            }
        }else{
            x += xstep;
            y += ystep;
        }
    }

    public void setPosition(double xnew,double ynew){
        double dx=xnew-x;
        double dy=ynew-y;
        this.incPosition(dx,dy,false);
    }

    public double[] getPosition(){
        double[] d={x,y};
        return d;
    }

    public void setNested(boolean b){
        nested=b;
    }

    public boolean isNested(){
        return nested;
    }

    public String parse(){
        return "";
    }

    public ArrayList<Rect> getRectangles(){
        return null;
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }
}