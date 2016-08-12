package justin.scratch.logic;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import java.util.ArrayList;

import justin.scratch.ScriptBlock;

/**
 * Created by Justin on 8/4/2016.
 */
@SuppressLint("ParcelCreator")
public class If extends ScriptBlock {
    private ScriptBlock conditionalChild;
    private ScriptBlock bodyChild;
    private static final int BODY_GAP=10;

    @Override
    public ScriptBlock getBodyChild(){
        return bodyChild;
    }
    @Override
    public String getType(){
        return "If";
    }
    @Override
    public ArrayList<ScriptBlock> getBody(boolean allChildren){
        ArrayList<ScriptBlock> body=new ArrayList<>();
        if(bodyChild!=null) {
            ScriptBlock s = bodyChild;
            while (true) {
                body.add(s);
                if(s.getBodyChild()!=null){
                    if(allChildren) {
                        for (ScriptBlock sb : s.getBody(true)) {
                            body.add(sb);
                        }
                    }
                }
                if(s.getChild()!=null) {
                    s = s.getChild();
                }else{
                    break;
                }
            }
        }
        return body;
    }

    @Override
    public void setBodyChild(ScriptBlock s){
        bodyChild=s;
    }

    @Override
    public void removeBodyChild(){
        bodyChild=null;
    }

    @Override
    public double[] getChildNode(){
        double[] node={x,y+getWidth()+1};
        return node;
    }

    @Override
    public double[] getConditionalChildNode(){
        double[] d={x+WIDTH+50,y};
        return d;
    }
    @Override
    public void removeConditionalChild(){
        conditionalChild=null;
    }

    @Override
    public int getWidth(){
        int bodyWidth=0;
        for(ScriptBlock s:getBody(false)){
            bodyWidth+=s.getWidth();
        }
        return WIDTH+WIDTH+BODY_GAP+bodyWidth;
    }

    @Override
    public void setConditionalChild(ScriptBlock s){
        conditionalChild=s;
    }

    public void draw(Canvas canvas){
        super.draw(canvas);
        Paint paint=new Paint();
        paint.setColor(Color.BLUE);
        for(Rect r:getRectangles()){
            canvas.drawRect(r,paint);
        }
        paint.setColor(Color.WHITE);
        paint.setTextSize(TEXT_SIZE);
        canvas.drawText("If",(float)x+BODY_GAP,(float)y+TEXT_SIZE,paint);
    }

    @Override
    public double[] getBodyNode(){
        double[] node={x+WIDTH,y+WIDTH};
        return node;
    }

    @Override
    public void incPosition(double xstep,double ystep,boolean surfaceFocused){
        super.incPosition(xstep,ystep,surfaceFocused);
//        if(!surfaceFocused) {
//            for (ScriptBlock s : getBody(true)) {
//                s.x += xstep;
//                s.y += ystep;
//            }
//        }
    }

    @Override
    public void setPosition(double xstep,double ystep){
        super.setPosition(xstep,ystep);
        double dx=xstep-x;
        double dy=ystep-y;
        for(ScriptBlock s:getBody(true)){
            s.x+=dx;
            s.y+=dy;
        }
    }

    @Override
    public String parse(){
        String script="";
        try {
            script = "if(" + conditionalChild.parse() + "){";
            script += bodyChild.parse();
        }catch(NullPointerException n){
            try {
                return "" + getChild().parse();
            }catch(NullPointerException e){
                return "";
            }
        }
        script+="}";
        try {
            return script+getChild().parse();
        }catch (NullPointerException n){
            return script;
        }
    }

    @Override
    public ArrayList<Rect> getRectangles(){
        int bodyWidth=0;
        for(ScriptBlock s:getBody(false)){
            bodyWidth+=s.getWidth();
        }
        ArrayList<Rect> rects=new ArrayList<>();
        rects.add(new Rect((int)x,(int)y,(int)x+WIDTH+50,(int)y+WIDTH));
        rects.add(new Rect((int)x,(int)y,(int)x+WIDTH,(int)y+WIDTH+bodyWidth+BODY_GAP));
        rects.add(new Rect((int)x,(int)y+WIDTH+bodyWidth+BODY_GAP,(int)x+LENGTH,(int)y+2*WIDTH+bodyWidth+BODY_GAP));

        return rects;
    }

}
