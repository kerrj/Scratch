package justin.scratch.Scripting.sprites;

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
import java.util.Iterator;

import justin.scratch.Scripting.Dialogs.PrintDialog;
import justin.scratch.Scripting.MainActivity;
import justin.scratch.Scripting.ScriptBlock;
import justin.scratch.Scripting.ScriptingManager;
import justin.scratch.Scripting.variables.NumberVariable;

/**
 * Created by Justin on 8/12/2016.
 */
@SuppressLint("ParcelCreator")
public class Print extends ScriptBlock implements Parcelable, PrintDialog.PrintListener{
    private String text="";
    private NumberVariable variable=null;

    public Print(){
        makeDialog();
    }
    @Override
    public int getWidth(){
        return WIDTH;
    }

    public int getLength(){
        try {
            return "print ".length() * TEXT_SIZE / 2 +variable.getName().length()*TEXT_SIZE/2;
        }catch (NullPointerException n){
            return "print ".length() * TEXT_SIZE / 2+text.length()*TEXT_SIZE/2;
        }
    }
    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);

        Paint paint=new Paint();
        paint.setTextSize(TEXT_SIZE);
        paint.setColor(Color.MAGENTA);
        for(Rect r:getRectangles()){
            canvas.drawRect(r,paint);
        }
        paint.setColor(Color.BLACK);
        try {
            canvas.drawText("Print " +variable.getName(),(float)x,(float)y+TEXT_SIZE,paint);
        }catch (NullPointerException n){
            canvas.drawText("Print " +text,(float)x,(float)y+TEXT_SIZE,paint);
        }
    }
    @Override
    public ArrayList<Rect> getRectangles(){
        ArrayList<Rect> rects= new ArrayList<>();
        rects.add(new Rect((int)x,(int)y,(int)x+getLength(),(int)y+getWidth()));
        return rects;
    }
    @Override
    public double[] getChildNode(){
        double[] d={x,y+getWidth()};
        return d;
    }
    @Override
    public String parse(){
        if(getChild()!=null) {
            try {
                return "print(Double.toString(" + variable.getName() + "));"+getChild().parse();
            } catch (NullPointerException n) {
                return "print(\"" + text + "\");"+getChild().parse();
            }
        }else{
            try {
                return "print(Double.toString(" + variable.getName() + "));";
            } catch (NullPointerException n) {
                return "print(\"" + text + "\");";
            }
        }
    }
    @Override
    public String getType(){
        return "Print";
    }
    @Override
    public void makeDialog(){
        PrintDialog dialog=new PrintDialog();
        Bundle s=new Bundle();
        s.putParcelable("ScriptBlock",this);
        dialog.setArguments(s);
        dialog.show(MainActivity.getActivity().getFragmentManager(),null);
    }

    @Override
    public void onPositiveClick(DialogFragment dialog, Bundle s) {
        if(s.containsKey("variable")){
            for(Iterator<NumberVariable> iterator = ScriptingManager.getVariables().iterator(); iterator.hasNext();){
                NumberVariable n=iterator.next();
                if(n.getName().equals(s.getString("variable"))){
                    variable=n;
                }
            }
        }else if(s.getString("text").length()>0){
            text=s.getString("text");
            variable=null;
        }
    }
    @Override
    public void onNegativeClick(DialogFragment d){
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
    @Override
    public void writeToParcel(Parcel p, int i){
        p.writeValue(this);
    }
}
