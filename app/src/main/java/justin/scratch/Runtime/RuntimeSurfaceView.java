package justin.scratch.Runtime;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Justin on 8/13/2016.
 */
public class RuntimeSurfaceView extends SurfaceView implements SurfaceHolder.Callback{
    private MyThread thread;
    private boolean surfaceCreated;

    public RuntimeSurfaceView(Context context) {
        super(context);
        getHolder().addCallback(this);

    }

    public RuntimeSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);

    }

    public RuntimeSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getHolder().addCallback(this);

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

    private class MyThread extends Thread{
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
            try {
                while (running) {
                    mCanvas = holder.lockCanvas();
                    mCanvas.drawColor(Color.WHITE);
                    RuntimeData.draw(mCanvas);
                    holder.unlockCanvasAndPost(mCanvas);
                }
            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }

    }
}
