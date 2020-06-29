package com.example.scram.farming;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import static com.example.scram.farming.topics.HUM;
import static com.example.scram.farming.topics.LDR;
import static com.example.scram.farming.topics.MOIS;
import static com.example.scram.farming.topics.PH;
import static com.example.scram.farming.topics.TEMP;

public class MainActivity extends AppCompatActivity {
Switch sw;
 static String TOPIC="iot/xyz/pump";

    TextView ph,mois,hum,temp,ldr,loc;

    MqttAndroidClient c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MemoryPersistence mPer = new MemoryPersistence();
        String clientId = MqttClient.generateClientId();
        String brokerUrl = "tcp://broker.mqtt-dashboard.com:1883";
        loc=findViewById(R.id.loc);
        Intent intent=getIntent();
        loc.setText(intent.getStringExtra("loc"));
        c= new MqttAndroidClient(getApplicationContext(),brokerUrl, clientId);
        mois=findViewById(R.id.moisture);
        temp=findViewById(R.id.temp);
        hum=findViewById(R.id.hum);
       ldr=findViewById(R.id.ldr);
        ph=findViewById(R.id.ph);
        try {

            c.connect();

        } catch (MqttException e) {
            Toast.makeText(this, "not connected", Toast.LENGTH_SHORT).show();

            e.printStackTrace();
        }

        c.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
                try {
                    c.subscribe(TEMP,0);
                    c.subscribe(HUM,0);
                    c.subscribe(MOIS,0);
                    c.subscribe(LDR,0);
                    c.subscribe(PH,0);
                } catch (MqttException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(), "connected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                Toast.makeText(MainActivity.this,new String(topic.toString()), Toast.LENGTH_SHORT).show();

                if(topic.compareTo(MOIS)==0){
                    mois.setText(new String(message.getPayload()));

                }
                else if(topic.compareTo(TEMP)==0){
                    temp.setText(new String(message.getPayload())+"°C");

                }
                else if(topic.compareTo(HUM)==0){
                    hum.setText(new String(message.getPayload()));

                }
                else if(topic.compareTo(LDR)==0){
                  ldr.setText(new String(message.getPayload()));

                }
                else if(topic.compareTo(PH)==0){
                    ph.setText(new String(message.getPayload()));

                }


            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });






//
//
//        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked){
//                // do something, the isChecked will be
//
//                String msg = "on";
//                MqttMessage m = new MqttMessage();
//                m.setPayload(msg.getBytes());
//                    try {
//                        c.publish(TOPIC, m);
//                    } catch (MqttException e) {
//                        e.printStackTrace();
//                    }
//                }
//                else {
//
//                    String msg = "off";
//                    MqttMessage m = new MqttMessage();
//                    m.setPayload(msg.getBytes());
//                    try {
//                        c.publish(TOPIC, m);
//                    } catch (MqttException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });








    }
}
