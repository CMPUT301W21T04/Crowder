package com.example.crowderapp;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.crowderapp.controllers.ExperimentHandler;
import com.example.crowderapp.controllers.callbackInterfaces.getExperimentCallBack;
import com.example.crowderapp.controllers.callbackInterfaces.getExperimentStatsCallBack;
import com.example.crowderapp.models.Experiment;
import com.example.crowderapp.models.ExperimentStats;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PlotActivity extends AppCompatActivity  {

    ExperimentHandler handler = new ExperimentHandler();
    Experiment experiment;

    LineChart lineView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plots);

        experiment = (Experiment) getIntent().getSerializableExtra("Experiment");

        lineView = (LineChart) findViewById(R.id.linechart);

        handler.refreshExperimentTrials(experiment, new getExperimentCallBack() {
            @Override
            public void callBackResult(Experiment experiment) {
                handler.getStatistics(experiment, new getExperimentStatsCallBack() {
                    @Override
                    public void callBackResult(ExperimentStats experimentStats) {

                        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

                        ExperimentStats.Graph graph = experimentStats.getPlotPoints();
                        List<ExperimentStats.Point> points = graph.getPoints();
                        List<Entry> data = new ArrayList<Entry>();
                        List<String> labels = new ArrayList<>();

                        for(int i = 0; i < points.size(); i++) {
                            data.add(new Entry(i, (float)points.get(i).getY()));
                            labels.add(df.format(points.get(i).getX()));
                        }

                        LineDataSet set = new LineDataSet(data, graph.getName());
                        LineData lineData = new LineData(set);

                        lineView.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
                        lineView.getXAxis().setGranularity(1f);
                        lineView.getXAxis().setGranularityEnabled(true);


                        lineView.getXAxis().setTextColor(Color.WHITE);
                        lineView.getXAxis().setTextSize(15f);
                        lineView.getXAxis().setLabelRotationAngle(-45);


                        lineView.getAxisLeft().setTextColor(Color.WHITE);
                        lineView.getAxisLeft().setTextSize(12f);
                        lineView.getAxisRight().setTextColor(Color.WHITE);
                        lineView.getAxisRight().setTextSize(12f);

                        set.setColors(ColorTemplate.JOYFUL_COLORS);
                        set.setValueTextColor(Color.WHITE);
                        set.setValueTextSize(15f);

                        lineView.setExtraOffsets(10, 10, 10, 10);

                        lineView.setData(lineData);
                        lineView.invalidate();
                        lineView.refreshDrawableState();
                    }
                });
            }
        });
    }
}
