package justin.scratch.Scripting.Math;

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

import justin.scratch.Scripting.MainActivity;
import justin.scratch.Scripting.ScriptBlock;
import justin.scratch.Scripting.ScriptingManager;
import justin.scratch.Scripting.Dialogs.TwoArgDialog;
import justin.scratch.Scripting.variables.NumberVariable;

/**
 * Created by Justin on 8/10/2016.
 */
@SuppressLint("ParcelCreator")
public class Divide extends ScriptBlock implements Parcelable,TwoArgDialog.TwoArgListener{
    private NumberVariable variableArg1 =null;
    private NumberVariable variableArg2 =null;
    private double valueArg1=0;
    private double valueArg2=0;


    public Divide(){
        makeDialog();
    }
    @Override
    public void makeDialog(){
        TwoArgDialog dialog=new TwoArgDialog();
        Bundle s=new Bundle();
        s.putParcelable("ScriptBlock",this);
        dialog.setArguments(s);
        dialog.show(MainActivity.getActivity().getFragmentManager(), null);
    }
    @Override
    public ArrayList<Rect> getRectangles(){
        ArrayList<Rect> rects=new ArrayList<>();
        rects.add(new Rect((int) x, (int) y, (int) x + getLength(), (int) y + WIDTH));
        return rects;
    }
    @Override
    public String getType(){
        return "Divide";
    }
    @Override
    public int getWidth(){
        return WIDTH;
    }
    public int getLength(){
        String arg1="";
        String arg2="";
        if(variableArg1 !=null){
            if(variableArg1.getName()!=null){
                arg1= variableArg1.getName();
            }else{
                arg1=Double.toString(valueArg1);
            }
        }else{
            arg1=Double.toString(valueArg1);
        }
        if(variableArg2 !=null){
            if(variableArg2.getName()!=null){
                arg2= variableArg2.getName();
            }else{
                arg2=Double.toString(valueArg2);
            }
        }else{
            arg2=Double.toString(valueArg2);
        }
        return arg1.length()*TEXT_SIZE/2+TEXT_SIZE/2+arg2.length()*TEXT_SIZE/2;
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        Paint paint=new Paint();
        paint.setColor(Color.CYAN);
        for (Rect r:getRectangles()){
            canvas.drawRect(r,paint);
        }
        paint.setColor(Color.BLACK);
        paint.setTextSize(TEXT_SIZE);
        String arg1="";
        String arg2="";
        if(variableArg1 !=null){
            if(variableArg1.getName()!=null){
                arg1= variableArg1.getName();
            }else{
                arg1=Double.toString(valueArg1);
            }
        }else{
            arg1=Double.toString(valueArg1);
        }
        if(variableArg2 !=null){
            if(variableArg2.getName()!=null){
                arg2= variableArg2.getName();
            }else{
                arg2=Double.toString(valueArg2);
            }
        }else{
            arg2=Double.toString(valueArg2);
        }
        canvas.drawText(arg1,(float)x,(float)y+TEXT_SIZE,paint);
        canvas.drawText("/",(float)x+arg1.length()*TEXT_SIZE/2,(float)y+TEXT_SIZE,paint);
        canvas.drawText(arg2,(float)x+TEXT_SIZE/2+arg1.length()*TEXT_SIZE/2,(float)y+TEXT_SIZE,paint);

    }
    @Override public void onPositiveClick(DialogFragment d, Bundle s){
        if(s.containsKey("valueArg1")){
            valueArg1=s.getDouble("valueArg1");
            variableArg1=null;
        }else if(s.containsKey("variableArg1")){
            valueArg1=0;
            for(NumberVariable n: ScriptingManager.getVariables()){
                if(n.getName().equals(s.get("variableArg1"))){
                    variableArg1=n;
                }
            }
        }
        if(s.containsKey("valueArg2")){
            valueArg2=s.getDouble("valueArg2");
            variableArg2=null;
        }else if(s.containsKey("variableArg2")){
            valueArg2=0;
            for(NumberVariable n: ScriptingManager.getVariables()){
                if(n.getName().equals(s.get("variableArg2"))){
                    variableArg2=n;
                }
            }
        }

    }
    @Override public void onNegativeClick(DialogFragment d){
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
    }
    @Override public void writeToParcel(Parcel parcel, int i){
        parcel.writeValue(this);
    }
    @Override
    public String parse(){
        String arg1="";
        String arg2="";
        if(variableArg1 !=null){
            if(variableArg1.getName()!=null){
                arg1= variableArg1.getName();
            }else{
                arg1=Double.toString(valueArg1);
            }
        }else{
            arg1=Double.toString(valueArg1);
        }
        if(variableArg2 !=null){
            if(variableArg2.getName()!=null){
                arg2= variableArg2.getName();
            }else{
                arg2=Double.toString(valueArg2);
            }
        }else{
            arg2=Double.toString(valueArg2);
        }
        try {
            return arg1 + "/" + arg2+getChild().parse();
        }catch(NullPointerException n){
            return arg1+"/"+arg2;
        }
    }
}
