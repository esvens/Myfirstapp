package com.example.myfirstapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Random;
import java.util.logging.LogRecord;

public class RealtimeUpdates extends Fragment {
    private final Handler handler = new Handler();
    private Runnable timer;
    private LineGraphSeries<DataPoint> series;
    private double graphLastXValue = 5d;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_main, container, false);

        GraphView graph = (GraphView) rootView.findViewById(R.id.graph);
        series = new LineGraphSeries<>(generateData());
        graph.addSeries(series);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        timer = new Runnable() {
            @Override
            public void run() {
                graphLastXValue += 1d;
                series.appendData(new DataPoint(graphLastXValue, getRandom()), true, 40);
                handler.postDelayed(this, 200);
            }
        };
        handler.postDelayed(timer, 300);

    }

    private DataPoint[] generateData() {
        int count = 30;
        DataPoint[] values = new DataPoint[count];
        for (int i=0; i<count; i++) {
            double x = i;
            double f = mRand.nextDouble()*0.15+0.3;
            double y = Math.sin(i*f+2) + mRand.nextDouble()*0.3;
            DataPoint v = new DataPoint(x, y);
            values[i] = v;
        }
        return values;
    }

    double mLastRandom = 2;
    Random mRand = new Random();
    private double getRandom() {
        return mLastRandom += mRand.nextDouble()*0.5 - 0.25;
    }

}
