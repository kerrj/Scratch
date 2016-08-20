package justin.scratch.Runtime;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * Created by Justin on 8/13/2016.
 */
public class RuntimeData {
    private static final int TEXT_SIZE=40;
    private static final int LINES_DISPLAYED=10;

    private static ArrayList<String> print=new ArrayList<>();
    private static List printList= Collections.synchronizedList(print);

    public static void init(){
        print=new ArrayList<>();
        printList= Collections.synchronizedList(print);
    }
    public static List getPrintList(){
        return printList;
    }
    public static void addPrintLine(String line){
        synchronized(printList){
            if(printList.size()>LINES_DISPLAYED-1) {
                printList.remove(0);
            }
            printList.add(line);
        }
    }
    public static void draw(Canvas canvas){
        Paint paint=new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(TEXT_SIZE);
        int i=0;
        synchronized (printList) {
            for (Iterator<String> iterator = printList.iterator(); iterator.hasNext(); ) {
                canvas.drawText(iterator.next(), 0,  i * TEXT_SIZE+TEXT_SIZE, paint);
                i++;
            }
        }
    }
}
