package com.example.myfirstapp;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import java.lang.Math;


public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    private double dataNumber = 0;
    private LineGraphSeries<DataPoint> series;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);        //skapar en toolbar högst upp i skärmen
        setSupportActionBar(myToolbar);

        GraphView graph = (GraphView) findViewById(R.id.graph);
        series = new LineGraphSeries<>(new DataPoint[]{      //skapar en första mätserie
                new DataPoint(0, 0)
        });
        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Context context = getApplicationContext();
                Toast.makeText(context, "Value "+dataPoint, Toast.LENGTH_SHORT).show();
            }
        });
        graph.addSeries(series);        //lägger till serien med mätvärden till grafen.
        graph.getViewport().setScrollable(true);        //scrollable in horizontal (x-axis)
        graph.getViewport().setScrollableY(true);
        graph.getViewport().setScalable(true);      //för zoomning
        graph.getViewport().setScalableY(true);
        graph.getViewport().setXAxisBoundsManual(true);     //set Viewport window size
        graph.getViewport().setYAxisBoundsManual(true);
        //graph.getViewport().setMaxXAxisSize(1);
        //graph.getViewport().setMinY(-1.5);      //begränsar y-axeln med värden
        //graph.getViewport().setMaxY(1.5);
        graph.getGridLabelRenderer().setHorizontalAxisTitle("Time (s)");
        graph.getGridLabelRenderer().setVerticalAxisTitle("Heartrate (beats/min)");
        graph.setTitle("Heartrate");



        /*for(double i = 0; i < 2*3.14; i += 0.01) {        //skriver ut värden automatiskt
            double y = Math.sin(i);
            DataPoint data = new DataPoint(i,y);
            series.appendData(data, true, 1000);        //uppdaterar mätserien som visas
        }*/



    }

    // called when user taps send button
    public void sendMessage(View view) {
        // do something in response to button
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void addData(View view) {        //knapp för att lägga till ett värde till serien
        double y = Math.sin(dataNumber);
        DataPoint data = new DataPoint(dataNumber,y);
        series.appendData(data, true,100);
        dataNumber += 0.5;
    }

    public void makeGraph() {       //testfunktion för grafen. Anropet behöver ligga i funktionen onCreate för att köras.
        GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0, 1),
                new DataPoint(1, 3),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)

        });
        graph.addSeries(series);
    }
    public void realTimeGraph() {
        GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(0, 1)
        });
    }
}
