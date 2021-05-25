package com.example.medcarapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements Adapter.ItemClickListener {
    RecyclerView rvAvailableCars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        carSelection();
        connectButton();
        loadImage();
    }

    public void carSelection(){
        String[] s1, s2;
        rvAvailableCars = findViewById(R.id.rvAvaliableCars);

        s1 = getResources().getStringArray(R.array.carNames);
        s2 = getResources().getStringArray(R.array.description);

        Adapter adapter = new Adapter(this, s1, s2);
        adapter.addItemClickListener(this);
        rvAvailableCars.setAdapter(adapter);
        rvAvailableCars.setLayoutManager(new LinearLayoutManager(this));
    }

    public void connectButton(){
        Button button = findViewById(R.id.btnConnect);
        button.setActivated(false);
        buttonStatus(getApplicationContext(),R.color.disabled_background,R.color.disabled_text);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!button.isActivated()) {
                    String connectButtonDisabledMessage = getString(R.string.connectButtonDisabledMessage);
                    disabledFeedbackMessage(connectButtonDisabledMessage);
                    return;
                } else {
                    Intent intent = new Intent(MainActivity.this, ManualControl.class);
                    boolean shouldSwitch = getIntent().getExtras().getBoolean(getApplicationContext().getString(R.string.switchServer));
                    intent.putExtra(getApplicationContext().getString(R.string.switchServer), shouldSwitch);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Button button = findViewById(R.id.btnConnect);
        button.setActivated(true);
        buttonStatus(getApplicationContext(),R.color.teal_200,R.color.white);
    }

    public void buttonStatus(Context context, int backgroundColor, int textColor) {
        Button button = findViewById(R.id.btnConnect);
        button.setBackgroundColor(ContextCompat.getColor(context, backgroundColor));
        button.setTextColor(ContextCompat.getColor(context, textColor));
    }

    public void disabledFeedbackMessage(String message){
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP,0,0);
        toast.show();
    }

    public void loadImage(){
        ImageView mainActivityLogo;
        mainActivityLogo = (ImageView) findViewById(R.id.logo);
        mainActivityLogo.setTag(R.drawable.logo7);
    }

    @Override
    public void onBackPressed() {
        boolean allowBack;
        allowBack = getIntent().getExtras().getBoolean(getApplicationContext().getString(R.string.restrictBack));
        if (!allowBack) {
            super.onBackPressed();
        } else {
            disabledFeedbackMessage(getApplicationContext().getString(R.string.restrictBackButton));
        }
    }
}