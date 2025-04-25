package com.example.ejemplo02;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText etPlayerName;
    private RadioGroup rgSymbol;
    private Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etPlayerName = findViewById(R.id.etPlayerName);
        rgSymbol = findViewById(R.id.rgSymbol);
        btnStart = findViewById(R.id.btnStart);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String playerName = etPlayerName.getText().toString().trim();
                if (playerName.isEmpty()) {
                    playerName = "Extraño";
                }

                String playerSymbol = "X"; // Default to X
                int selectedId = rgSymbol.getCheckedRadioButtonId();

                if (selectedId == R.id.rbCircle) {
                    playerSymbol = "O"; // Change to O if Circle is selected
                }
                // If nothing is selected, it will keep the default X

                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                intent.putExtra("PLAYER_NAME", playerName);
                intent.putExtra("PLAYER_SYMBOL", playerSymbol);
                startActivity(intent);
            }
        });
    }
}