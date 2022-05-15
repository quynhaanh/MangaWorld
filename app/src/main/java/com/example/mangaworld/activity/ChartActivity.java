package com.example.mangaworld.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mangaworld.R;
import com.example.mangaworld.controller.IVolleyCallback;
import com.example.mangaworld.controller.NovelController;
import com.example.mangaworld.model.ChapterModel;
import com.example.mangaworld.model.NovelModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ChartActivity extends AppCompatActivity {
    PieChart pieChart;
    BarChart barChart;
    RadioGroup radioGroup;
    TextView tvBarChartTitle;

    String pieChartType = "count";
    String barChartType = "sum";
    int[] colorTemplate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        setControl();
        setEvent();
    }

    private void setEvent() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int id = radioGroup.getCheckedRadioButtonId();

                if(id == R.id.radioButtonPie)
                {
                    initPieChart();
                }
                else if(id == R.id.radioButtonBar)
                {
                    initBarChart();
                }
            }
        });

        initPieChart();
    }

    private void setControl() {
        pieChart = findViewById(R.id.pieChart);
        barChart = findViewById(R.id.barChart);
        tvBarChartTitle = findViewById(R.id.tvBarChartTitle);

        radioGroup = findViewById(R.id.radioGroupChart);

        colorTemplate = new int[]{R.color.colorLightRed, R.color.colorSkyBlue, R.color.green_color,
                R.color.teal_200, R.color.teal_700, R.color.purple_200,
                R.color.purple_500, R.color.purple_700, R.color.line_color};
    }

    private void initPieChart() {
        pieChart.setVisibility(View.VISIBLE);
        barChart.setVisibility(View.GONE);
        tvBarChartTitle.setVisibility(View.GONE);

        String urlAPI = LoadActivity.url + "/api/truyenchu/api_chart.php";

        StringRequest request = new StringRequest(Request.Method.POST, urlAPI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("test", response);
                handlePieChartData(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Type", pieChartType);

                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

    private void handlePieChartData(String result) {
        ArrayList<PieEntry> dataEntry = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("statistics");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);

                int value = object.getInt("Count");
                String genreName = object.getString("Genre_Name");

                dataEntry.add(new PieEntry(value, genreName));
            }

        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        PieDataSet dataSet = new PieDataSet(dataEntry, "");
        dataSet.setColors(colorTemplate,getApplicationContext());
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(15f);

        PieData pieData = new PieData(dataSet);
        pieData.setValueFormatter(new PercentFormatter(pieChart));

        pieChart.setEntryLabelTextSize(10f);
        pieChart.setData(pieData);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.getLegend().setEnabled(false);
        pieChart.setCenterTextSize(15f);
        pieChart.setCenterText("Tỉ lệ số lượng truyện theo từng thể loại");
        pieChart.animateXY(1000, 1000);
    }

    private void initBarChart()
    {
        pieChart.setVisibility(View.GONE);
        barChart.setVisibility(View.VISIBLE);
        tvBarChartTitle.setVisibility(View.VISIBLE);

        String urlAPI = LoadActivity.url + "/api/truyenchu/api_chart.php";

        StringRequest request = new StringRequest(Request.Method.POST, urlAPI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                handleBarChart(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Type", barChartType);

                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

    private void handleBarChart(String result)
    {
        ArrayList<BarEntry> dataEntry = new ArrayList<>();
        String[] label;
        XAxis xAxis = barChart.getXAxis();

        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("statistics");
            label = new String[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);

                int value = object.getInt("Count");
                String genreName = object.getString("Genre_Name");

                dataEntry.add(new BarEntry(i, value));
                label[i] = genreName;
            }

            xAxis.setLabelRotationAngle(-30f);
            xAxis.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    return label[(int)value].replaceAll(" ", "\n");
                }
            });

        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        BarDataSet dataSet = new BarDataSet(dataEntry,"");
        dataSet.setColors(colorTemplate, getApplicationContext());
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(10f);

        BarData barData = new BarData(dataSet);

        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        barChart.setFitBars(true);
        barChart.getLegend().setEnabled(false);
        barChart.setData(barData);
        barChart.getDescription().setEnabled(false);
        barChart.animateY(2000);
    }

    public void closeActivity(View view)
    {
        finish();
    }
}