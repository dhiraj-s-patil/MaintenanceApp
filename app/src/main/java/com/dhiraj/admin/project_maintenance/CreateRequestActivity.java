package com.dhiraj.admin.project_maintenance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

//import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CreateRequestActivity extends AppCompatActivity implements View.OnClickListener{

    FirebaseAuth mAuth;
    Button createRequest;
    EditText reqIdTV, renterNameTV,aptNoTV,reqTypeTV,reqDetailsTV,createTimeTV,statusTV;
    TextView statusLabel;
    String tmpReqNum = "R100";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_request);
        mAuth = FirebaseAuth.getInstance();
        reqIdTV = findViewById(R.id.req_id_tv);
        renterNameTV = findViewById(R.id.renter_name_tv);
        aptNoTV = findViewById(R.id.apt_no_tv);
        reqTypeTV = findViewById(R.id.request_type_tv);
        reqDetailsTV = findViewById(R.id.request_details_tv);
        createTimeTV = findViewById(R.id.create_time_tv);
        statusTV = findViewById(R.id.status_tv);
        statusLabel = findViewById(R.id.textView4);
        
        //disable known fields
        reqIdTV.setEnabled(false);
        renterNameTV.setEnabled(false);
        createTimeTV.setEnabled(false);

        //hide status while creating request
        statusLabel.setVisibility(View.INVISIBLE);
        statusTV.setVisibility(View.INVISIBLE);

        //attach onClick listeners to button
        createRequest = findViewById(R.id.request_create_button);
        createRequest.setOnClickListener(this);

        //populate known data
        reqIdTV.setText(tmpReqNum);
        renterNameTV.setText(mAuth.getCurrentUser().getDisplayName());
        Date currentTime = new Date(Calendar.DAY_OF_MONTH);
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy_HH:mm:ss");
        createTimeTV.setText(sdf.format(currentTime));

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.request_create_button:
                createMaintenanceRequest();
                startActivity(new Intent(getApplicationContext(),UserHomeActivity.class));
        }
    }

    private void createMaintenanceRequest(){
        //create request
        //1. get data from text fields
        //2. validate
        //3. assign id and status
        //4. update firebase
    }
}
