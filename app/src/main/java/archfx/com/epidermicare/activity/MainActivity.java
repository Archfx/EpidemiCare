package archfx.com.epidermicare.activity;

import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.ImageButton;
import android.widget.TextView;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;


import archfx.com.epidermicare.R;
import archfx.com.epidermicare.app.User;
import archfx.com.epidermicare.helper.SQLiteHandler;
import archfx.com.epidermicare.helper.SessionManager;

public class MainActivity extends AppCompatActivity {

    private TextView txtName;
    private TextView txtEmail;

    ImageButton confirmbtn;
    TextView confirmbuttonText;
    User currentUsr=new User();

    private SQLiteHandler db;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //txtName = (TextView) findViewById(R.id.name);
        //txtEmail = (TextView) findViewById(R.id.email);

        confirmbtn = (ImageButton) findViewById(R.id.confirmButton);
        confirmbuttonText = (TextView) findViewById(R.id.confirmText);





        //if user is a doctor set confirmation of epidemi to visible

        if (currentUsr.isIsdoctor()) {
            confirmbtn.setVisibility(View.VISIBLE);
            confirmbuttonText.setVisibility(View.VISIBLE);
        }
        //if a normal user set this to gone
        else {
            confirmbtn.setVisibility(View.GONE);
            confirmbuttonText.setVisibility(View.GONE);
        }


        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());


    }
    public void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        //Intent intent = new Intent(this, LoginActivity.class);
        //startActivity(intent);
        finish();
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