package justin.scratch.Scripting.logic;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import justin.scratch.Scripting.Dialogs.RepeatDialog;
import justin.scratch.Scripting.MainActivity;
import justin.scratch.Scripting.ScriptBlock;
import justin.scratch.Scripting.ScriptingManager;

/**
 * Created by Justin on 8/9/2016.
 */
@SuppressLint("ParcelCreator")
public class Repeat extends ScriptBlock implements RepeatDialog.RepeatDialogListener,Parcelable{
    private ScriptBlock bodyChild;
    private int repeatFor=1;
    private static final int BODY_GAP=10;

    public Repeat(){
        makeDialog();
    }
    @Override
    public void makeDialog(){
        RepeatDialog dialog=new RepeatDialog();
        Bundle s=new Bundle();
        s.putParcelable("ScriptBlock",this);
        dialog.setArguments(s);
        dialog.show(MainActivity.getActivity().getFragmentManager(),null);
    }
    @Override
    public ScriptBlock getBodyChild(){
        return bodyChild;
    }
    @Override
    public String getType(){
        return "Repeat";
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
    public int getWidth(){
        int bodyWidth=0;
        for(ScriptBlock s:getBody(false)){
            bodyWidth+=s.getWidth();
        }
        return WIDTH+WIDTH+BODY_GAP+bodyWidth;
    }
    public int getLength(){
        return LENGTH+Integer.toString(repeatFor).length()*TEXT_SIZE/2-20;
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
        canvas.drawText("Repeat",(float)x+BODY_GAP,(float)y+TEXT_SIZE,paint);
        canvas.drawText(Integer.toString(repeatFor),(float)x+6*BODY_GAP+6*TEXT_SIZE/2,(float)y+TEXT_SIZE,paint);
    }

    @Override
    public double[] getBodyNode(){
        double[] node={x+WIDTH,y+WIDTH};
        return node;
    }

    @Override
    public void incPosition(double xstep,double ystep,boolean surfaceFocused){
        super.incPosition(xstep,ystep,surfaceFocused);
    }

    @Override
    public void setPosition(double xstep,double ystep){
        super.setPosition(xstep, ystep);
        double dx=xstep-x;
        double dy=ystep-y;
        for(ScriptBlock s:getBody(true)){
            s.x+=dx;
            s.y+=dy;
        }
    }

    @Override
    public String parse(){
        String script= "for(int i=0;i<"+Integer.toString(repeatFor)+";i++){";
        try {
            script += getBodyChild().parse();
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
        rects.add(new Rect((int)x,(int)y,(int)x+getLength(),(int)y+WIDTH));
        rects.add(new Rect((int)x,(int)y,(int)x+WIDTH,(int)y+WIDTH+bodyWidth+BODY_GAP));
        rects.add(new Rect((int)x,(int)y+WIDTH+bodyWidth+BODY_GAP,(int)x+LENGTH,(int)y+2*WIDTH+bodyWidth+BODY_GAP));

        return rects;
    }

    @Override
    public void onPositiveClick(DialogFragment f,Bundle s){
        try {
            repeatFor = Integer.decode(s.getString("repeatFor"));
        }catch(NumberFormatException n){

        }
    }
    @Override
    public void onNegativeClick(DialogFragment f){
        if(this.getBodyChild()!=null){
            this.getBodyChild().removeBodyParent();
        }
        if(this.getChild()!=null){
            this.getChild().removeParent();
        }
        if(this.getConditionalChild()!=null){
            this.getConditionalChild().removeConditionalParent();
        }
        if(this.getParent()!=null){
            this.getParent().removeChild();
        }
        if(this.getBodyParent()!=null){
            this.getBodyParent().removeBodyChild();
        }
        if(this.getConditionalParent()!=null){
            this.getConditionalParent().removeConditionalChild();
        }
        ScriptingManager.removeScriptBlock(this);
        this.removeBodyChild();
        this.removeBodyParent();
        this.removeConditionalParent();
        this.removeConditionalChild();
        this.removeChild();
        this.removeParent();
        ScriptingManager.removeScriptBlock(this);
    }
    @Override
    public void writeToParcel(Parcel p,int i){
        p.writeValue(this);
    }
}
