package archfx.com.epidermicare.activity;

import java.util.HashMap;
import java.util.Map;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.ImageButton;
import android.widget.TextView;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


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
    private static final String TAG = RegisterActivity.class.getSimpleName();

    private SQLiteHandler db;
    private SessionManager session;

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
            statusSet();

        }

    }
    public void onClickConfirm(View view)
    {
        if(view.getId()==R.id.confirmButton) {
            Intent d = new Intent(this,ConfirmActivity.class);
            startActivity(d);
        }

    }

    private void statusSet() {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pDialog.setMessage("Generating report ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_COUNT, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // user successfully logged in
                        // Create login session


                        // Now store the user in SQLite
                        String uid = jObj.getString("uid");

                        JSONObject user = jObj.getJSONObject("user");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String created_at = user
                                .getString("created_at");

                        // Inserting row in users table


                        // Launch main activity
                        float diseseaseData[]={};
                        String disName[]={};
                        Intent c = new Intent(MainActivity.this, StatusActivity.class);
                        c.putExtra("diseseaseData",diseseaseData);
                        c.putExtra("disName",disName);
                        startActivity(c);
                        finish();
                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}