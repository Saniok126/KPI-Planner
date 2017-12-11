package com.example.alex.kpi_planner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class activity_group extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group);
    }

    public void onClick(View view) {
        Intent intent = new Intent(activity_group.this,BaseActivity.class);
        EditText getUserGroup = (EditText)findViewById(R.id.group);
        intent.putExtra("userGroup", getUserGroup.getText().toString());
        startActivity(intent);

    }
}
