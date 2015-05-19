package sats.android.piedpiper.se.sats;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
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

    static int y0Position = 411;
    static int y1Position = 373;
    static int y2Position = 298;
    static int y3Position = 226;
    static int y4Position = 150;
    static int y5Position = 73;

    static int[] yPos = new int[]{411,373,298,226,150,73};





    int leftCell = 0, rightCell = 0;

    public MyView(Context context, final boolean filled, final int position, final int leftCell, final int rightCell)
    {
        super(context);
        this.filled = filled;
        this.position = position;
        this.leftCell = leftCell;
        this.rightCell = rightCell;
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
                writeText(101, 80, "5", canvas);
                paintLine(position, leftCell, rightCell, canvas);
                break;
            case 4:
                canvas.drawCircle(xPosition, yPos[position], radius, paint);
                writeText(101, 160, "4", canvas);
                paintLine(position, leftCell, rightCell, canvas);
                break;
            case 3:
                canvas.drawCircle(xPosition, yPos[position], radius, paint);
                writeText(101, 236, "3", canvas);
                paintLine(position, leftCell, rightCell, canvas);
                break;
            case 2:
                canvas.drawCircle(xPosition, yPos[position], radius, paint);
                writeText(101, 308, "2", canvas);
                paintLine(position, leftCell, rightCell, canvas);
                break;
            case 1:
                canvas.drawCircle(xPosition, yPos[position], radius, paint);
                writeText(101, 383, "1", canvas);
                paintLine(position, leftCell, rightCell, canvas);
                break;
            case 0:
                canvas.drawCircle(xPosition, yPos[position], radius, paint);
                writeText(101, 421, "0", canvas);
                paintLine(position, leftCell, rightCell, canvas);
                break;
            default:
                canvas.drawCircle(xPosition, yPos[5], radius, paint);
                writeText(92, 80, "+5", canvas);
                paintLine(5, leftCell, rightCell, canvas);
                break;
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

    public void paintLine(int currentCell, int leftCell, int rightCell, Canvas canvas)
    {
        paint.setStrokeWidth(15);
        paint.setColor(getResources().getColor(R.color.orange));


        if(currentCell == rightCell)
        {
            canvas.drawLine(143, yPos[currentCell]+2, 220, yPos[rightCell]+2, paint);
        }else if(currentCell < rightCell && currentCell == 0)
        {
            canvas.drawLine(143, yPos[currentCell]+2, 220, yPos[rightCell]+22, paint);
        }else if(currentCell > rightCell && currentCell == 1)
        {
            canvas.drawLine(143, yPos[currentCell]+2, 220, yPos[rightCell]-14, paint);
        }


        else if(currentCell < rightCell)// && currentCell == 1)
        {
            canvas.drawLine(143, yPos[currentCell]+2, 220, yPos[rightCell]+42, paint);
        }else if(currentCell > rightCell)// && currentCell == 2)
        {
            canvas.drawLine(143, yPos[currentCell]+2, 220, yPos[rightCell]-34, paint);
        }

        //////////////////////////////////////////////

        if(currentCell == leftCell)
        {
            canvas.drawLine(77, yPos[currentCell]+2, 0, yPos[leftCell]+2, paint);
        }else if(currentCell > leftCell && currentCell == 1)
        {
            canvas.drawLine(77, yPos[currentCell]+2, -10, yPos[leftCell]-12, paint);
        }else if(currentCell < leftCell && currentCell == 0)
        {
            canvas.drawLine(77, yPos[currentCell]+2, -10, yPos[leftCell]+21, paint);
        }

        else if(currentCell > leftCell) //&& currentCell == 2)
        {
            canvas.drawLine(77, yPos[currentCell]+2, -10, yPos[leftCell]-28, paint);
        }else if(currentCell < leftCell) //&& currentCell == 1)
        {
            canvas.drawLine(77, yPos[currentCell]+2, -10, yPos[leftCell]+32, paint);
        }


    }
}