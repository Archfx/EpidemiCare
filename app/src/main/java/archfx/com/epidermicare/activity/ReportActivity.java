package archfx.com.epidermicare.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import archfx.com.epidermicare.R;
import archfx.com.epidermicare.app.AppConfig;
import archfx.com.epidermicare.app.AppController;
import archfx.com.epidermicare.helper.SQLiteHandler;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ReportActivity extends AppCompatActivity {

    private static final String TAG = RegisterActivity.class.getSimpleName();
    private Button btnReport;
    private EditText pName;
    private EditText dName;
    private EditText area;
    private EditText pNIC;
    private EditText details;
    private EditText date;
    private ProgressDialog pDialog;
    private SQLiteHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_report);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        pName = (EditText) findViewById(R.id.pName);
        pNIC = (EditText) findViewById(R.id.nicPatient);
        dName = (EditText) findViewById(R.id.dName);
        area = (EditText) findViewById(R.id.Area);
        details = (EditText) findViewById(R.id.details_epi);
        date = (EditText) findViewById(R.id.Date);
        btnReport = (Button) findViewById(R.id.btnReport);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Register Button Click event
        btnReport.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String patient = pName.getText().toString().trim();
                String nic = pNIC.getText().toString().trim();
                String disease = dName.getText().toString().trim();
                String district = area.getText().toString().trim();
                String detail = details.getText().toString().trim();
                String dateofincident = date.getText().toString().trim();

                if (!patient.isEmpty() && !disease.isEmpty() && !nic.isEmpty() && !district.isEmpty() && !dateofincident.isEmpty()) {
                    reportDisease(patient, nic, disease, district, detail, dateofincident);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Please enter details!", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });


    }

    private void reportDisease(final String patient, final String nic,
                              final String diseaseName, final String district, final String detail,
                              final String date) {
        // Tag used to cancel the request
        String tag_string_req = "req_report";

        pDialog.setMessage("Reporting ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.GET,
                AppConfig.URL_REPORT, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Report Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {
//                        // User successfully stored in MySQL
//                        // Now store the user in sqlite
//                           String rid = jObj.getString("rid");
//
 //                      JSONObject report = jObj.getJSONObject("report");
   //                    String name = report.getString("name");
     //                   String email = report.getString("email");
       //                 String created_at = report
         //                       .getString("created_at");

                        // Inserting row in report table
                        db.addReport(patient, nic, diseaseName, district, date, detail, "false");

                        Toast.makeText(getApplicationContext(), "Report successfully submitted!", Toast.LENGTH_LONG).show();

                        // Launch main activity
                        Intent intent = new Intent(
                                ReportActivity.this,
                                MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {

                        // Error occurred in registration. Get the error
                        // message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                params.put("patientName", patient);
                params.put("patientNic", nic);
                params.put("diseaseName", diseaseName);
                params.put("reportedDate", date);
                params.put("isConfirmed", detail);
                params.put("district", district);

                return params;
            }

        };

        //Adding request to request queue
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
    public  void onClickback(View view)
    {
        if(view.getId()==R.id.backButton) {
            Intent b = new Intent(this, MainActivity.class);
            startActivity(b);
        }


    }

}
