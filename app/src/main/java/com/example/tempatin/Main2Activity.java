package com.example.tempatin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    Button register1;
    TextView login1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        register1 = findViewById(R.id.register);
        login1 = findViewById(R.id.btnLogin);

        login1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent masuk = new Intent(Main2Activity.this, MainActivity.class);
                startActivity(masuk);
            }
        });

    }


}
