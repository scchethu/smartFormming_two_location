package com.example.scram.farming;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectActivity extends AppCompatActivity {
Button a,b;
Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        a=findViewById(R.id.a);
        b=findViewById(R.id.b);
        intent=new Intent(this,MainActivity.class);
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              topics.LDR="iot/xyz/a";
                topics.TEMP="iot/xyz/b";
                topics.HUM="iot/xyz/c";
                topics.MOIS="iot/xyz/d";
                topics.PH="iot/xyz/e";
                intent.putExtra("loc","Location A");
                startActivity(intent);
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                topics.LDR="iot/xyz/f";
                topics.TEMP="iot/xyz/g";
                topics.HUM="iot/xyz/h";
                topics.MOIS="iot/xyz/i";
                topics.PH="iot/xyz/j";
                intent.putExtra("loc","Location B");
                startActivity(intent);
            }
        });
    }
}
