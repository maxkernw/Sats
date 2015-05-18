package sats.android.piedpiper.se.sats;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Steffe on 15-05-11.
 */
public class MyView extends View {

    Paint paint;
    final boolean filled;
    final int position;
    public static final int notFilledRadius = 30;
    public static final int filledRadius = 35;
    int radius;

    public MyView(Context context, final boolean filled, final int position) {
        super(context);
        this.filled = filled;
        this.position = position;
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        paint.setColor(getResources().getColor(R.color.orange));
        paint.setStrokeWidth(11);

        if(filled == true){
            paint.setStyle(Paint.Style.FILL);
            radius = filledRadius;
        }else{
            paint.setStyle(Paint.Style.STROKE);
            radius = notFilledRadius;
        }

        switch(position){
            case 5: canvas.drawCircle(110, 73, radius, paint); writeText(102 ,80 , "5", canvas); break;
            case 4: canvas.drawCircle(110, 150, radius, paint); writeText(102 ,160 , "4", canvas); break;
            case 3: canvas.drawCircle(110, 226, radius, paint); writeText(102 ,236 , "3", canvas); break;
            case 2: canvas.drawCircle(110, 298, radius, paint); writeText(102 ,308 , "2", canvas); break;
            case 1: canvas.drawCircle(110, 373, radius, paint); writeText(102 ,383 , "1", canvas); break;
            default: canvas.drawCircle(110, 73, radius, paint); writeText(92 ,80 , "+5", canvas); break;
        }

        super.onDraw(canvas);

        /*paint.setColor(getResources().getColor(R.color.orange));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(11);
        canvas.drawCircle(110, 65, 30, paint);

        paint.setStrokeWidth(4);
        paint.setTextSize(32);
        paint.setColor(Color.BLACK);
        canvas.drawText("+7", 92, 75, paint);*/

        //

//        paint.setTextSize(80);
//        paint.setStrokeWidth(200);
//        paint.setColor(getResources().getColor(R.color.orange));
//        paint.setStyle(Paint.Style.FILL);
//        canvas.drawCircle(300, 300, 80, paint);
//
//        paint.setColor(Color.WHITE);
//        canvas.drawText("3", 275, 330, paint);
    }

    public void writeText(int x, int y, String text, Canvas canvas){
        paint.setStrokeWidth(4);
        paint.setTextSize(32);

    if(filled){
            paint.setColor(Color.WHITE);
        }else{
            paint.setColor(Color.BLACK);
        }

        canvas.drawText(text, x, y, paint);
    }
}