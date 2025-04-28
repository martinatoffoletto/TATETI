package com.example.ejemplo02;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class GameActivity extends AppCompatActivity {
    private TextView tvPlayerInfo;  //text view
    private Button[][] buttons = new Button[3][3]; //grilla de tateti
    private String playerName; //nombre jugador
    private String playerSymbol; //simbolo juagdor
    private String computerSymbol; //simbolo computador
    private boolean playerTurn; // es turno del jugador?
    private int turnCount; // numero de ronda/turno

    @Override
    protected void onCreate(Bundle savedInstanceState) { //lo q se muestra cuando arranca la pantalla
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // obtengo info de la MainActivity
        playerName = getIntent().getStringExtra("PLAYER_NAME");
        playerSymbol = getIntent().getStringExtra("PLAYER_SYMBOL");

        if ("X".equals(playerSymbol)) { //si es x computador tiene O, sino X
            computerSymbol = "O";
        } else {
            computerSymbol = "X";}

        tvPlayerInfo = findViewById(R.id.tvPlayerInfo);  // text view de el nombre del usuario su eleccion y el de la maquina
        tvPlayerInfo.setText(playerName + " (" + playerSymbol + ") vs Maquina (" + computerSymbol + ")");

        // inicializar el grilla
        buttons = new Button[][] {
                { findViewById(R.id.btn_00), findViewById(R.id.btn_01), findViewById(R.id.btn_02) },
                { findViewById(R.id.btn_10), findViewById(R.id.btn_11), findViewById(R.id.btn_12) },
                { findViewById(R.id.btn_20), findViewById(R.id.btn_21), findViewById(R.id.btn_22) }};

        //listener de grilla
        for (int i = 0; i < 3; i++) { //fila
            for (int j = 0; j < 3; j++) { //col
                final int row = i;
                final int col = j;
                buttons[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handleCellClick(row, col);
                    }
                });
            }
        }

        //listener boton de reinicio
        Button btnReset = findViewById(R.id.btnReset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });

        //listener boton volver al menu
        Button btnBackToMain = findViewById(R.id.btnBackToMain);
        btnBackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        playerTurn = true; // siempre comienza jugador
        turnCount = 0;
    }

    private void handleCellClick(int row, int col) { //para cuando clickeas celda
        if (!buttons[row][col].getText().toString().isEmpty()) {
            return; // ya ocupada
        }
        if (playerTurn) {
            buttons[row][col].setText(playerSymbol);
            turnCount++; //pone simbolo en casilla seleccionada y suma turno

            if (checkWin(playerSymbol)) {
                gameOver(playerName + " gana!");
            } else if (turnCount == 9) {
                gameOver("Empate!");
            } else {
                playerTurn = false;
                computerMove();
            }
        }
    }


    private void computerMove() {
        //pone en un lugar random disponible de la grilla
        Random random = new Random();

        while (true) {
            int row = random.nextInt(3);
            int col = random.nextInt(3);

            if (buttons[row][col].getText().toString().isEmpty()) {
                buttons[row][col].setText(computerSymbol);
                turnCount++;

                if (checkWin(computerSymbol)) {
                    gameOver("Computadora gana!");
                } else if (turnCount == 9) {
                    gameOver("Empate!");
                } else {
                    playerTurn = true;
                }
                return;
            }
        }
    }

    private boolean checkWin(String symbol) {

        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().toString().equals(symbol) && //verifica filas
                    buttons[i][1].getText().toString().equals(symbol) &&
                    buttons[i][2].getText().toString().equals(symbol)) {
                return true;
            }
            if (buttons[0][i].getText().toString().equals(symbol) && // verificar columnas
                    buttons[1][i].getText().toString().equals(symbol) &&
                    buttons[2][i].getText().toString().equals(symbol)) {
                return true;
            }
        }

        if (buttons[0][0].getText().toString().equals(symbol) && // verifica diagonal de izq a der
                buttons[1][1].getText().toString().equals(symbol) &&
                buttons[2][2].getText().toString().equals(symbol)) {
            return true;
        }

        if (buttons[0][2].getText().toString().equals(symbol) && // verifica diagonal de der a izq
                buttons[1][1].getText().toString().equals(symbol) &&
                buttons[2][0].getText().toString().equals(symbol)) {
            return true;
        }

        return false;
    }


    private void gameOver(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        disableAllButtons();
    }

    private void disableAllButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setEnabled(false);
            }
        }
    }

    private void resetGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
            }
        }

        turnCount = 0;
        playerTurn = true;
    }





}