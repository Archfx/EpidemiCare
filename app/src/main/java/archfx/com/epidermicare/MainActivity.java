package archfx.com.epidermicare;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
    ImageButton confirmbtn;
    TextView confirmbuttonText;
    User currentUsr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        confirmbtn=(ImageButton)findViewById(R.id.confirmButton);
        confirmbuttonText=(TextView)findViewById(R.id.confirmText);

        //if user is a doctor set confirmation of epidemi to visible
        if(currentUsr.isIsdoctor())
        {
            confirmbtn.setVisibility(View.VISIBLE);
            confirmbuttonText.setVisibility(View.VISIBLE);
        }
        //if a normal user set this to gone
        else
        {
            confirmbtn.setVisibility(View.GONE);
            confirmbuttonText.setVisibility(View.GONE);
        }



        //setTitle("MainMenu");

    }

    public  void onClickProfile(View view)
    {
        if(view.getId()==R.id.ProfileButton) {
            Intent b = new Intent(this, ProfileActivity.class);
            startActivity(b);
        }

    }
    public void onClickReport(View view)
    {
        if(view.getId()==R.id.ReportButton) {
            Intent a = new Intent(this, ReportActivity.class);
            startActivity(a);
        }

    }
    public void onClickStatus(View view)
    {
        if(view.getId()==R.id.StatusButton) {
            Intent c = new Intent(this, StatusActivity.class);
            startActivity(c);
        }

    }
    public void onClickConfirm(View view)
    {
        if(view.getId()==R.id.confirmButton) {
            Intent d = new Intent(this,ConfirmActivity.class);
            startActivity(d);
        }

    }
}
