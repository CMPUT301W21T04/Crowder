package com.example.crowderapp;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.crowderapp.controllers.ExperimentHandler;
import com.example.crowderapp.controllers.callbackInterfaces.getExperimentCallBack;
import com.example.crowderapp.controllers.callbackInterfaces.getExperimentStatsCallBack;
import com.example.crowderapp.models.Experiment;
import com.example.crowderapp.models.ExperimentStats;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


//https://medium.com/@karthikganiga007/create-barchart-in-android-studio-14943339a211
//https://www.tutorialspoint.com/how-to-use-bar-chart-graph-in-android
public class HistogramActivity  extends AppCompatActivity {

    ExperimentHandler handler = new ExperimentHandler();
    Experiment experiment;
    BarChart barView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histogram);

        experiment = (Experiment) getIntent().getSerializableExtra("Experiment");

        barView = (BarChart) findViewById(R.id.barchart);

        handler.refreshExperimentTrials(experiment, new getExperimentCallBack() {
            @Override
            public void callBackResult(Experiment exp) {
                experiment = exp;
                handler.getStatistics(experiment, new getExperimentStatsCallBack() {
                    @Override
                    public void callBackResult(ExperimentStats experimentStats) {

                        List<ExperimentStats.Bar> bars = experimentStats.getHistPoints();
                        List<BarEntry> data = new ArrayList<BarEntry>();
                        List<String> labels = new ArrayList<>();




                        for(int i = 0; i < bars.size(); i++) {
                            data.add(new BarEntry(i, (float)bars.get(i).getY()));
                            labels.add(bars.get(i).getX());
                        }


                        BarDataSet set = new BarDataSet(data, experiment.getName() + " Bar Graph");
                        BarData barData = new BarData(set);

                        https://stackoverflow.com/questions/38857038/mpandroidchart-adding-labels-to-bar-chart
                        barView.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
                        barView.getXAxis().setGranularity(1f);
                        barView.getXAxis().setGranularityEnabled(true);

                        barView.getXAxis().setTextColor(Color.WHITE);
                        barView.getXAxis().setTextSize(20f);

                        barView.getAxisLeft().setTextColor(Color.WHITE);
                        barView.getAxisLeft().setTextSize(12f);
                        barView.getAxisRight().setTextColor(Color.WHITE);
                        barView.getAxisRight().setTextSize(12f);

                        set.setColors(ColorTemplate.JOYFUL_COLORS);
                        set.setValueTextColor(Color.WHITE);
                        set.setValueTextSize(15f);

                        barView.setExtraOffsets(10, 10, 10, 10);

                        barView.setData(barData);
                        barView.invalidate();
                        barView.refreshDrawableState();
                    }
                });
            }
        });
    }
}
