package com.arielu.shopper.demo.Map;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.arielu.shopper.demo.externalHardware.Beacon;

import java.util.ArrayList;
import java.util.LinkedList;

import Gui.DepartmentBlockDrawer;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class LineView extends View {

    private Paint paintLines = new Paint();
    private Paint paintPoints = new Paint();
    private Paint paintBeacons = new Paint();

    private ArrayList<Line> lines  = new ArrayList<>() ;

    public LineView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paintLines.setColor(Color.BLUE);
        paintPoints.setColor(Color.RED);
        paintLines.setStrokeWidth(7);
        paintPoints.setStrokeWidth(15);
        paintBeacons.setColor(Color.GREEN);


        paintBeacons.setStrokeWidth(20);
        float density = getContext().getResources().getDisplayMetrics().density;

        //draw lines
        for (int i = 1; i < lines.size() ; i++) {
            PointF pointA = new PointF();
            PointF pointB = new PointF();

            pointA.x = lines.get(i).pointA.x ;
            pointA.y = lines.get(i).pointA.y ;
            pointB.x = lines.get(i).pointB.x ;
            pointB.y = lines.get(i).pointB.y ;

            canvas.drawLine(pointA.x, pointA.y, pointB.x, pointB.y, paintLines);
            canvas.drawPoint(pointA.x, pointA.y, paintLines);
            canvas.drawPoint(pointB.x, pointB.y, paintPoints);
        }
        BeaconSetter beaconSetter = new BeaconSetter();
        beaconSetter.initBeacons();
        // draw beacons
        PointF beacon_draw;
        for(Beacon beacon : beaconSetter.beacons)
        {
            beacon_draw = new PointF();
            beacon_draw.x = beacon.getX() ;//* (420/160) - 160;
            beacon_draw.y = beacon.getY() ;//* (420/160) + 320;
            canvas.drawPoint(density/2 * beacon_draw.x + 95,density * beacon_draw.y - 60 ,paintBeacons);
        }
        //draw points

        //draw blocks
        DepartmentBlockDrawer departmentBlockDrawer = new DepartmentBlockDrawer();
        LinkedList<PointF> blocks = departmentBlockDrawer.drawAllBlocks();
        if (blocks.size() > 0) {
            Log.d(TAG, "onDraw: starting to draw all corners of blocks to check if they are placed currently.");
            for (PointF pointF : blocks) {
                canvas.drawPoint(pointF.x, pointF.y, paintPoints);
            }
        }
        else
        {
            Toast.makeText(getContext(),"failed to draw blocks",Toast.LENGTH_LONG).show();
        }

        super.onDraw(canvas);
    }

    public void addNewLine(Line line) // Add new line to the array list
    {
        lines.add(line) ;
    }

    public void draw()
    {
        invalidate();
        requestLayout();
    }
}

