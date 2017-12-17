package archfx.com.epidermicare.activity;


//import android.annotation.SuppressLint;
//import android.content.Intent;
//import android.support.v7.app.ActionBar;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.os.Handler;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.Window;
//import android.widget.ImageButton;
//import android.widget.TextView;
//
//import archfx.com.epidermicare.R;
//import archfx.com.epidermicare.app.User;
//
//
//public class MainActivity extends AppCompatActivity {
//
//
//    ImageButton confirmbtn;
//    TextView confirmbuttonText;
//    User currentUsr;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.activity_main);
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();
//
//        confirmbtn=(ImageButton)findViewById(R.id.confirmButton);
//        confirmbuttonText=(TextView)findViewById(R.id.confirmText);
//
//        //if user is a doctor set confirmation of epidemi to visible
//        if(currentUsr.isIsdoctor())
//        {
//            confirmbtn.setVisibility(View.VISIBLE);
//            confirmbuttonText.setVisibility(View.VISIBLE);
//        }
//        //if a normal user set this to gone
//        else
//        {
//            confirmbtn.setVisibility(View.GONE);
//            confirmbuttonText.setVisibility(View.GONE);
//        }
//
//
//
//        //setTitle("MainMenu");
//
//    }
//
//    public  void onClickProfile(View view)
//    {
//        if(view.getId()==R.id.ProfileButton) {
//            Intent b = new Intent(this, ProfileActivity.class);
//            startActivity(b);
//        }
//
//    }
//    public void onClickReport(View view)
//    {
//        if(view.getId()==R.id.ReportButton) {
//            Intent a = new Intent(this, ReportActivity.class);
//            startActivity(a);
//        }
//
//    }
//    public void onClickStatus(View view)
//    {
//        if(view.getId()==R.id.StatusButton) {
//            Intent c = new Intent(this, StatusActivity.class);
//            startActivity(c);
//        }
//
//    }
//    public void onClickConfirm(View view)
//    {
//        if(view.getId()==R.id.confirmButton) {
//            Intent d = new Intent(this,ConfirmActivity.class);
//            startActivity(d);
//        }
//
//    }
//}
import info.androidhive.loginandregistration.helper.SQLiteHandler;
import info.androidhive.loginandregistration.helper.SessionManager;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    private TextView txtName;
    private TextView txtEmail;
    private Button btnLogout;

    private SQLiteHandler db;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtName = (TextView) findViewById(R.id.name);
        txtEmail = (TextView) findViewById(R.id.email);
        btnLogout = (Button) findViewById(R.id.btnLogout);

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");
        String email = user.get("email");

        // Displaying the user details on the screen
        txtName.setText(name);
        txtEmail.setText(email);

        // Logout button click event
        btnLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });
    }

    /**
     * Logging out the user. Will set isLoggedIn flag to false in shared
     * preferences Clears the user data from sqlite users table
     * */
    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}