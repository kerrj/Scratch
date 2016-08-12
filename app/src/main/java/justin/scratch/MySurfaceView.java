package justin.scratch;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Iterator;

/**
 * Created by Justin on 6/26/2016.
 */
public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback{
    public boolean surfaceCreated=false;
    private MyThread thread;
    private float[] actionDownCoordinates ={0,0};
    private float[] lastActionCoordinates={0,0};
    private ScriptBlock focus=new ScriptBlock();
    private MainActivity activity;

    public MySurfaceView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    public MySurfaceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        getHolder().addCallback(this);
    }

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
    }
    public void setActivity(MainActivity a){
        activity=a;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        surfaceCreated=true;
        thread=new MyThread(getHolder());
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        thread.setStop();
    }

    public class MyThread extends Thread{
        private boolean running=true;
        private SurfaceHolder holder;
        public Canvas mCanvas;

        public MyThread(SurfaceHolder surfaceHolder){
            holder=surfaceHolder;
        }

        public void setStop(){
            running=false;
        }
        @Override
        public void run(){
            while(running){
                try {
                    mCanvas = holder.lockCanvas();
                    mCanvas.drawColor(Color.WHITE);
                    synchronized(ScriptBlockManager.getScriptBlocks()) {
                        for (Iterator<ScriptBlock> iterator = ScriptBlockManager.getScriptBlocks().iterator(); iterator.hasNext(); ) {
                            ScriptBlock s = iterator.next();
                            s.draw(mCanvas);
                        }
                    }
//                    for(ScriptBlock s:ScriptBlockManager.getScriptBlocks()){
//                        s.draw(mCanvas);
//                    }
                    holder.unlockCanvasAndPost(mCanvas);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent e){
        switch (e.getActionMasked()){
            case MotionEvent.ACTION_MOVE:
                if(focus==null){//user touched the surface, not a script block, so move every scriptblock
                    for(Iterator<ScriptBlock> iterator=ScriptBlockManager.getScriptBlocks().iterator(); iterator.hasNext();){
                        ScriptBlock s=iterator.next();
                        s.incPosition(e.getX()-lastActionCoordinates[0],e.getY()-lastActionCoordinates[1],true);
                    }
                }else{//user touched a scriptblock, so move it
                    focus.incPosition(e.getX()-lastActionCoordinates[0],e.getY()-lastActionCoordinates[1],false);
                }
                lastActionCoordinates[0]=e.getX();
                lastActionCoordinates[1]=e.getY();
                break;
            case MotionEvent.ACTION_DOWN:
                actionDownCoordinates[0]=e.getX();
                actionDownCoordinates[1]=e.getY();
                lastActionCoordinates[0]=e.getX();
                lastActionCoordinates[1]=e.getY();
                focus=ScriptBlockManager.getTouchFocus(actionDownCoordinates[0], actionDownCoordinates[1]);
                if(focus!=null){
                    if(focus.getParent()!=null){
                        focus.getParent().removeChild();
                        focus.removeParent();
                    }
                    if(focus.getBodyParent()!=null){
                        focus.getBodyParent().removeBodyChild();
                        focus.removeBodyParent();
                    }
                    if(focus.getConditionalParent()!=null){
                        focus.getConditionalParent().removeConditionalChild();
                        focus.removeConditionalParent();
                    }
                    focus.setNested(false);

                }
                break;
            case MotionEvent.ACTION_UP:
                if(focus!=null) {
                    boolean found = false;
                    for (Iterator<ScriptBlock> iterator = ScriptBlockManager.getScriptBlocks().iterator(); iterator.hasNext()
                            && !found; ) {
                        ScriptBlock s = iterator.next();
                        if (s != focus) {
                            double distance;
                            if(s.getChildNode()!=null) {
                                distance = Math.sqrt(Math.pow(s.getChildNode()[0] - focus.getX(), 2) +
                                        Math.pow(s.getChildNode()[1] - focus.getY(), 2));
                                if (distance < 50) {
                                    if (s.getChild() == null) {//parent doesnt have a child(inserting at end of block)
                                        focus.addParent(s);
                                        s.addChild(focus);
                                        if (s.isNested()) {
                                            focus.setNested(true);
                                        }
                                        found = true;
                                    } else {//parent has child(inserting between 2 blocks)
                                        s.getChild().addParent(focus.getLastChild());
                                        focus.getLastChild().addChild(s.getChild());
                                        s.addChild(focus);
                                        focus.addParent(s);
                                        if (s.isNested()) {
                                            focus.setNested(true);
                                        }
                                        found = true;
                                    }
                                }
                            }
                            if (s.getBodyNode() != null) {
                                distance = Math.sqrt(Math.pow(s.getBodyNode()[0] - focus.getX(), 2) +
                                        Math.pow(s.getBodyNode()[1] - focus.getY(), 2));
                                if (distance < 50) {
                                    if (s.getBodyChild() == null) {
                                        focus.setNested(true);
                                        s.setBodyChild(focus);
                                        focus.setBodyParent(s);
                                        found = true;
                                    } else {
                                        focus.setNested(true);
                                        s.getBodyChild().addParent(focus.getLastChild());
                                        focus.getLastChild().addChild(s.getBodyChild());
                                        s.getBodyChild().removeBodyParent();
                                        s.setBodyChild(focus);
                                        focus.setBodyParent(s);
                                    }
                                }
                            }
                            if (s.getConditionalChildNode() != null) {
                                distance = Math.sqrt(Math.pow(s.getConditionalChildNode()[0] - focus.getX(), 2) +
                                        Math.pow(s.getConditionalChildNode()[1] - focus.getY(), 2));
                                if (distance < 50) {
                                    if (s.getConditionalChild() == null) {
                                        focus.setNested(true);
                                        s.setConditionalChild(focus);
                                        focus.setConditionalParent(s);
                                        found = true;
                                    } else {
                                        focus.setNested(true);
                                    }
                                }
                            }
                        }
                    }
                    double distance = Math.sqrt(Math.pow(e.getX() - actionDownCoordinates[0], 2) +
                            Math.pow(e.getY() - actionDownCoordinates[1], 2));
                    if(distance<5){//user tapped focus
                        focus.makeDialog();
                    }
                }

                break;

        }
        return true;
    }
}
