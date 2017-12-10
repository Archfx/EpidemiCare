package archfx.com.epidermicare;

import android.annotation.SuppressLint;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class StatusActivity extends AppCompatActivity {
    float diseseaseData[]={98,844,46,451,151,11,151,151,151,5151,5151};
    String disName[]={"sadads","sadads","sadads","sadads","sadads","sadads","sadads","sadads","sadads","sadads","sadads"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setTitle("status of the Country");
        setContentView(R.layout.activity_status);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setupPieChart();


    }

    private void setupPieChart()
    {
        List<PieEntry> pieEntries=new ArrayList<>();
        for(int i=0;i<diseseaseData.length;i++)
        {
            pieEntries.add(new PieEntry(diseseaseData[i],disName[i]));
        }
        PieDataSet dataSet=new PieDataSet(pieEntries,"Epidermic diseases");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData data=new PieData(dataSet);

        PieChart chart=(PieChart)findViewById(R.id.Piechart);
        chart.setData(data);
        chart.animateX(1000);
        chart.invalidate();
    }

}
