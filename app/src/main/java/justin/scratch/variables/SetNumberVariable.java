package justin.scratch.variables;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

import justin.scratch.MainActivity;
import justin.scratch.OneArgDialog;
import justin.scratch.ScriptBlock;
import justin.scratch.ScriptBlockManager;

/**
 * Created by Justin on 8/10/2016.
 */
@SuppressLint("ParcelCreator")
public class SetNumberVariable extends ScriptBlock implements Parcelable,OneArgDialog.OneArgListener{
    NumberVariable variable=null;
    ScriptBlock bodyChild=null;

    public SetNumberVariable(){
        makeDialog();
    }
    @Override
    public int getWidth(){
        return WIDTH;
    }
    @Override
    public double[] getChildNode(){
        double[] d={x,y+WIDTH};
        return d;
    }
    @Override
    public double[] getBodyNode(){
        double[] d={x+getLength(),y};
        return d;
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
    public String getType(){
        return "SetNumberVariable";
    }
    public int getLength(){
        try {
            return 10 + "set to ".length() * TEXT_SIZE / 2 + variable.getName().length() * TEXT_SIZE / 2;
        }catch (NullPointerException n){
            return 10 + "set to ".length() * TEXT_SIZE / 2;
        }
    }
    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        Paint paint=new Paint();
        paint.setColor(Color.YELLOW);
        paint.setTextSize(TEXT_SIZE);
        for(Rect r:getRectangles()){
            canvas.drawRect(r,paint);
        }
        paint.setColor(Color.BLACK);
        if(variable!=null){
            canvas.drawText("Set "+variable.getName()+" to",(float)x+10,(float)y+TEXT_SIZE,paint);
        }else{
            canvas.drawText("Set ",(float)x+10,(float)y+TEXT_SIZE,paint);

        }
    }
    @Override
    public ArrayList<Rect> getRectangles(){
        ArrayList<Rect> rects=new ArrayList<>();
        rects.add(new Rect((int)x,(int)y,(int)x+getLength(),(int)y+getWidth()));
        return rects;
    }
    @Override
    public void makeDialog(){
        OneArgDialog dialog=new OneArgDialog();
        Bundle s=new Bundle();
        s.putParcelable("ScriptBlock",this);
        dialog.setArguments(s);
        dialog.show(MainActivity.getActivity().getFragmentManager(),null);
    }
    @Override
    public void writeToParcel(Parcel p, int i){
        p.writeValue(this);
    }
    @Override
    public void onPositiveClick(DialogFragment dialog, Bundle s) {
        if(s.containsKey("variable")){
            for(NumberVariable v:ScriptBlockManager.getVariables()){
                if(v.getName().equals(s.getString("variable"))) {
                    variable = v;
                }
            }
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
        ScriptBlockManager.removeScriptBlock(this);
        this.removeBodyChild();
        this.removeBodyParent();
        this.removeConditionalParent();
        this.removeConditionalChild();
        this.removeChild();
        this.removeParent();
        ScriptBlockManager.removeScriptBlock(this);
    }
    @Override
    public String parse(){
        String script="";
        if(variable!=null) {
            script =variable.getName()+"="+bodyChild.parse()+";";
        }else{
            script= "";
        }
        if(getChild()!=null){
            return script+getChild().parse();
        }else{
            return script;
        }
    }
}
