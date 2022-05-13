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
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
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
        initPieChart();
    }

    private void setControl() {
        pieChart = findViewById(R.id.pieChart);
        colorTemplate = new int[]{R.color.colorLightRed, R.color.colorSkyBlue, R.color.green_color,
                R.color.teal_200, R.color.teal_700, R.color.purple_200,
                R.color.purple_500, R.color.purple_700, R.color.line_color};
    }

    private void initPieChart() {
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
}