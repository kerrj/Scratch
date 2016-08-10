package justin.scratch.logic;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;

import justin.scratch.ScriptBlock;

/**
 * Created by Justin on 8/9/2016.
 */
@SuppressLint("ParcelCreator")
public class Or extends ScriptBlock {
    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        Paint paint=new Paint();
        paint.setTextSize(TEXT_SIZE);
        paint.setColor(Color.RED);
        for(Rect r:getRectangles()){
            canvas.drawRect(r,paint);
        }
        paint.setColor(Color.BLACK);
        canvas.drawText("Or",(float)x+10,(float)y+TEXT_SIZE,paint);
    }
    @Override
    public int getWidth(){
        return WIDTH;
    }
    @Override
    public double[] getChildNode(){
        double[] d={x+100,y};
        return d;
    }
    @Override
    public ArrayList<Rect> getRectangles(){
        ArrayList<Rect> rects=new ArrayList<>();
        rects.add(new Rect((int)x,(int)y,(int)x+100,(int)y+WIDTH));
        return rects;
    }
    @Override
    public String parse(){
        return "";
    }
}
