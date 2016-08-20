package justin.scratch.Scripting.control;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;

import justin.scratch.Scripting.ScriptBlock;

/**
 * Created by Justin on 8/6/2016.
 */
@SuppressLint("ParcelCreator")
public class Start extends ScriptBlock {

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        Paint paint=new Paint();
        paint.setColor(Color.GREEN);
        for(Rect r:getRectangles()){
            canvas.drawRect(r,paint);
        }
        paint.setColor(Color.BLACK);
        paint.setTextSize(TEXT_SIZE);
        canvas.drawText("Start",(float)x,(float)y+TEXT_SIZE,paint);
    }

    @Override
    public double[] getChildNode(){
        double[] node={x,y+getWidth()};
        return node;
    }
    @Override
    public String getType(){
        return "Start";
    }

    @Override
    public int getWidth(){
        return WIDTH;
    }

    @Override
    public String parse(){
        if(getChild()!=null){
            return getChild().parse();
        }else {
            return "";
        }

    }

    @Override
    public ArrayList<Rect> getRectangles(){
        ArrayList<Rect> rects=new ArrayList<>();
        rects.add(new Rect((int) x, (int) y, (int) x + LENGTH, (int) y + getWidth()));
        return rects;
    }
}
