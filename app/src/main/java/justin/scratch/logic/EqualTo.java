package justin.scratch.logic;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.lang.reflect.Array;
import java.util.ArrayList;

import justin.scratch.ScriptBlock;
import justin.scratch.variables.NumberVariable;

/**
 * Created by Justin on 8/7/2016.
 */
public class EqualTo extends ScriptBlock {
    private NumberVariable blockArg1=null;
    private NumberVariable blockArg2=null;
    private long arg1;
    private long arg2;


    public EqualTo(){
        makeDialog();
    }
    @Override
    public void makeDialog(){

    }
    @Override
    public ArrayList<Rect> getRectangles(){
        ArrayList<Rect> rects=new ArrayList<>();
        rects.add(new Rect((int)x,(int)y,(int)x+LENGTH/2,(int)y+WIDTH));
        return rects;
    }
    @Override
    public void draw(Canvas canvas){
        Paint paint=new Paint();
        paint.setColor(Color.RED);
        for (Rect r:getRectangles()){
            canvas.drawRect(r,paint);
        }
        paint.setColor(Color.WHITE);
        paint.setTextSize(TEXT_SIZE);
        canvas.drawText("=",(float)x+WIDTH/4,(float)y+TEXT_SIZE,paint);
        if(blockArg1!=null){
            canvas.drawText(blockArg1.getName(),(float)x+10,(float)y+TEXT_SIZE,paint);
        }else{
            canvas.drawText(Long.toString(arg1),(float)x+10,(float)y+TEXT_SIZE,paint);
        }
        if(blockArg2!=null){
            canvas.drawText(blockArg2.getName(),(float)x+WIDTH/4+10,(float)y+TEXT_SIZE,paint);
        }else{
            canvas.drawText(Long.toString(arg1),(float)x+WIDTH/4+10,(float)y+TEXT_SIZE,paint);
        }
    }
}
