package com.example.crowderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.crowderapp.controllers.ExperimentHandler;
import com.example.crowderapp.controllers.callbackInterfaces.getExperimentCallBack;
import com.example.crowderapp.controllers.callbackInterfaces.getExperimentStatsCallBack;
import com.example.crowderapp.models.Experiment;
import com.example.crowderapp.models.ExperimentStats;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class StatsActivity extends AppCompatActivity {

    ExperimentHandler handler = new ExperimentHandler();
    Experiment experiment;
    private static DecimalFormat df = new DecimalFormat("0.00");

    TextView q1TextView;
    TextView q2TextView;
    TextView q3TextView;
    TextView meanTextView;
    TextView medianTextView;
    TextView stdDevTextView;
    LineChart lineView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        experiment = (Experiment) getIntent().getSerializableExtra("Experiment");

        q1TextView = findViewById(R.id.q1_value_TextView);
        q2TextView = findViewById(R.id.q2_value_TextView);
        q3TextView = findViewById(R.id.q3_value_TextView);
        meanTextView = findViewById(R.id.mean_value_TextView);
        medianTextView = findViewById(R.id.median_value_TextView);
        stdDevTextView = findViewById(R.id.std_dev_value_TextView);
        lineView = (LineChart) findViewById(R.id.linechart);

        handler.refreshExperimentTrials(experiment, new getExperimentCallBack() {
            @Override
            public void callBackResult(Experiment experiment) {
                handler.getStatistics(experiment, new getExperimentStatsCallBack() {
                    @Override
                    public void callBackResult(ExperimentStats experimentStats) {
                        List<Double> qs = experimentStats.getQuartiles();
                        q1TextView.setText(df.format(qs.get(0)));
                        q2TextView.setText(df.format(qs.get(1)));
                        q3TextView.setText(df.format(qs.get(2)));
                        meanTextView.setText(df.format(experimentStats.getMean()));
                        medianTextView.setText(df.format(experimentStats.getMedian()));
                        stdDevTextView.setText(df.format(experimentStats.getStdev()));
                        DateFormat df = new SimpleDateFormat("MMdd");
                        List<ExperimentStats.Graph> graphs = experimentStats.getPlotPoints();
                        List<ExperimentStats.Point> points = graphs.get(0).getPoints();
                        List<Entry> data = new ArrayList<Entry>();
                        String date;
                        for(ExperimentStats.Point point : points) {
                            date = df.format(point.getX());
                            //Float.valueOf(date);
                            data.add(new Entry(Float.valueOf(date), (float)point.getY()));
                        }

                        LineDataSet set1 = new LineDataSet(data, "Points");

                        set1.setFillAlpha(110);

                        List<ILineDataSet> dataSets = new ArrayList<>();
                        dataSets.add(set1);
                        LineData lineData = new LineData(dataSets);

                        lineView.setData(lineData);
                    }
                });
            }
        });


    }
}