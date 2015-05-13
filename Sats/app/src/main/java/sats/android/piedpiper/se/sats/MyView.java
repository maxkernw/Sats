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

    public MyView(Context context) {
        super(context);
        init();
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        paint.setColor(getResources().getColor(R.color.orange));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(11);
        canvas.drawCircle(110, 65, 30, paint);

        paint.setStrokeWidth(4);
        paint.setTextSize(32);
        paint.setColor(Color.BLACK);
        canvas.drawText("+7", 92, 75, paint);

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
}