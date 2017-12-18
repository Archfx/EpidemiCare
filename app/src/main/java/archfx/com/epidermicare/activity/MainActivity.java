package archfx.com.epidermicare.activity;
import java.util.StringTokenizer;
import java.util.HashMap;
import java.util.Map;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.ImageButton;
import android.widget.TextView;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import archfx.com.epidermicare.R;
import archfx.com.epidermicare.app.AppConfig;
import archfx.com.epidermicare.app.AppController;
import archfx.com.epidermicare.app.User;
import archfx.com.epidermicare.helper.SQLiteHandler;
import archfx.com.epidermicare.helper.SessionManager;

public class MainActivity extends AppCompatActivity {

    private TextView txtName;
    private TextView txtEmail;
    private StatusActivity statusActivity;
    ImageButton confirmbtn;
    TextView confirmbuttonText;
    User currentUsr=new User();
    private ProgressDialog pDialog;
    private static final String TAG = MainActivity.class.getSimpleName();

    private SQLiteHandler db;
    private SessionManager session;
    float  diseseaseData[]=new float[10];
    String disName[]=new String[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        confirmbtn = (ImageButton) findViewById(R.id.confirmButton);
        confirmbuttonText = (TextView) findViewById(R.id.confirmText);

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        HashMap<String, String> user = db.getUserDetails();

/*        String isDoctor=user.get("is_doctor");
        if(isDoctor=="true")
        {
            currentUsr.setIsdoctor(true);
        }*/




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



        if (!session.isLoggedIn()) {
            logoutUser();
        }


    }
    public void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        this.finish();
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

            retrive();



        }

    }
    public void onClickConfirm(View view)
    {
        if(view.getId()==R.id.confirmButton) {
            Intent d = new Intent(this,ConfirmActivity.class);
            startActivity(d);
        }

    }

public void retrive()
{

    // Tag used to cancel the request
    String tag_string_req = "req_login";

//    pDialog.setMessage("Logging in ...");
    //showDialog();

    final StringRequest strReq = new StringRequest(Request.Method.POST,
            AppConfig.URL_COUNT, new Response.Listener<String>()

    {

        @Override
        public void onResponse(String response) {
            Log.d(TAG, "Login Response: " + response.toString());
            //hideDialog();
            for(int i=1;i<(response.toString().split(",")).length;i+=2) {
                String co= response.toString().split(",")[i-1];
                String ct= response.toString().split(",")[i];
                String name= co.split(":")[1];
                String count= ct.split(":")[1];
                String finalCount=count.substring(1,2);
                diseseaseData[i]=Float.parseFloat(finalCount);
                disName[i]=name;

            }




            // Launch main activity
                    Intent c = new Intent(MainActivity.this, StatusActivity.class);
                    c.putExtra("diseseaseData",diseseaseData);
                    c.putExtra("disName",disName);
                    startActivity(c);

//                } else {
//                    // Error in login. Get the error message
//                    String errorMsg = jObj.getString("error_msg");
//                    Toast.makeText(getApplicationContext(),
//                            errorMsg, Toast.LENGTH_LONG).show();
//                }

        }
    }, new Response.ErrorListener() {

        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e(TAG, "Login Error: " + error.getMessage());
            Toast.makeText(getApplicationContext(),
                    error.getMessage(), Toast.LENGTH_LONG).show();
            //hideDialog();
        }
    }) {


    };

    // Adding request to request queue
    AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

}


}