package sats.android.piedpiper.se.sats;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class MyView extends View
{
    Paint paint;
    final boolean filled;
    final int position;
    public static final int notFilledRadius = 30;
    public static final int filledRadius = 35;
    int radius;
    static int xPosition = 110;
    static int[] yPos = new int[]{411,373,298,226,150,73};
    int leftCell = 0, rightCell = 0;

    public MyView(Context context, final boolean filled, final int position, final int leftCell, final int rightCell)
    {
        super(context);
        this.filled = filled;
        
        if(position < 6){
            this.position = position;
        }else{
            this.position = 0;
        }
        if(leftCell < 6){
            this.leftCell = leftCell;
        }else{
            this.leftCell = 0;
        }
        if(rightCell < 6){
            this.rightCell = rightCell;
        }else{
            this.rightCell = 0;
        }

        //this.position = position;
        //this.leftCell = leftCell;
        //this.rightCell = rightCell;
        paint = new Paint();
    }

    public MyView(Context context, final boolean filled, final int position)
    {
        super(context);
        this.filled = filled;
        this.position = position;
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {

        paint.setColor(getResources().getColor(R.color.orange));
        paint.setStrokeWidth(11);

        if (filled == true)
        {
            paint.setStyle(Paint.Style.FILL);
            radius = filledRadius;
        } else
        {
            paint.setStyle(Paint.Style.STROKE);
            radius = notFilledRadius;
        }

        switch (position)
        {
            case 5:
                canvas.drawCircle(xPosition, yPos[position], radius, paint);
                if(filled)
                    paintLine(position, leftCell, rightCell, canvas);
                writeText(101, 80, "5", canvas);
                break;
            case 4:
                canvas.drawCircle(xPosition, yPos[position], radius, paint);
                if(filled)
                    paintLine(position, leftCell, rightCell, canvas);
                writeText(101, 160, "4", canvas);
                break;
            case 3:
                canvas.drawCircle(xPosition, yPos[position], radius, paint);
                if(filled)
                    paintLine(position, leftCell, rightCell, canvas);
                writeText(101, 236, "3", canvas);
                break;
            case 2:
                canvas.drawCircle(xPosition, yPos[position], radius, paint);
                if(filled)
                    paintLine(position, leftCell, rightCell, canvas);
                writeText(101, 308, "2", canvas);
                break;
            case 1:
                canvas.drawCircle(xPosition, yPos[position], radius, paint);
                if(filled)
                    paintLine(position, leftCell, rightCell, canvas);
                writeText(101, 383, "1", canvas);
                break;
            case 0:
                canvas.drawCircle(xPosition, yPos[position], radius, paint);
                if(filled)
                    paintLine(position, leftCell, rightCell, canvas);
                writeText(101, 421, "0", canvas);
                break;
            default:
                canvas.drawCircle(xPosition, yPos[5], radius, paint);
                if(filled)
                    paintLine(5, leftCell, rightCell, canvas);
                writeText(92, 80, "+5", canvas);
                break;
        }
        super.onDraw(canvas);
    }

    public void writeText(int x, int y, String text, Canvas canvas)
    {
        paint.setStrokeWidth(4);
        paint.setTextSize(32);

        if (filled)
        {
            paint.setColor(Color.WHITE);
        }
        else
        {
            paint.setColor(Color.BLACK);
        }

        canvas.drawText(text, x, y, paint);
    }

    public void paintLine(int currentCell, int leftCell, int rightCell, Canvas canvas) {
        paint.setStrokeWidth(15);
        paint.setColor(getResources().getColor(R.color.orange));

        if(rightCell != -1){
            canvas.drawLine(110, yPos[currentCell]+2, 340, yPos[rightCell], paint);
        }
        canvas.drawLine(110, yPos[currentCell]+2, -90, yPos[leftCell]+2, paint);
    }
}