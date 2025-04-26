package com.example.ejemplo02;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {
    private TextView tvPlayerInfo;
    private Button[][] buttons = new Button[3][3];
    private String playerName;
    private String playerSymbol;
    private String computerSymbol;
    private boolean playerTurn;
    private int turnCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // Obtener datos de la actividad anterior
        playerName = getIntent().getStringExtra("PLAYER_NAME");
        playerSymbol = getIntent().getStringExtra("PLAYER_SYMBOL");
        computerSymbol = playerSymbol.equals("X") ? "O" : "X";

        tvPlayerInfo = findViewById(R.id.tvPlayerInfo);
        tvPlayerInfo.setText(playerName + " (" + playerSymbol + ") vs Maquina (" + computerSymbol + ")");

        // Inicializar el tablero
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "btn_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(new CellClickListener(i, j));
            }
        }

        Button btnReset = findViewById(R.id.btnReset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });

        Button btnBackToMain = findViewById(R.id.btnBackToMain);
        btnBackToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameActivity.this, MainActivity.class);
                startActivity(intent);
                finish();  // Finaliza la actividad actual para que el usuario no pueda volver a ella con el botón "Atrás"
            }
        });

        playerTurn = true;// X siempre comienza
        turnCount = 0;
    }

    private class CellClickListener implements View.OnClickListener {
        private int row;
        private int col;

        public CellClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void onClick(View v) {
            if (!buttons[row][col].getText().toString().isEmpty()) {
                return; // Casilla ya ocupada
            }

            if (playerTurn) {
                buttons[row][col].setText(playerSymbol);
                turnCount++;

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
    }

    private void computerMove() {
        // Lógica simple de la computadora (puedes mejorarla)
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().toString().isEmpty()) {
                    buttons[i][j].setText(computerSymbol);
                    turnCount++;

                    if (checkWin(computerSymbol)) {
                        gameOver("Computadora gana!");
                        return;  // Si la computadora gana, termina la función
                    } else if (turnCount == 9) {
                        gameOver("Empate!");
                        return;  // Si es un empate, termina la función
                    } else {
                        playerTurn = true;  // Después de que la computadora haga su jugada, el turno vuelve al jugador
                    }
                    return; // Sale del ciclo y pasa al siguiente turno
                }
            }
        }
    }

    private boolean checkWin(String symbol) {
        // Verificar filas
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().toString().equals(symbol) &&
                    buttons[i][1].getText().toString().equals(symbol) &&
                    buttons[i][2].getText().toString().equals(symbol)) {
                return true;
            }
        }

        // Verificar columnas
        for (int j = 0; j < 3; j++) {
            if (buttons[0][j].getText().toString().equals(symbol) &&
                    buttons[1][j].getText().toString().equals(symbol) &&
                    buttons[2][j].getText().toString().equals(symbol)) {
                return true;
            }
        }

        // Verificar diagonales
        if (buttons[0][0].getText().toString().equals(symbol) &&
                buttons[1][1].getText().toString().equals(symbol) &&
                buttons[2][2].getText().toString().equals(symbol)) {
            return true;
        }

        if (buttons[0][2].getText().toString().equals(symbol) &&
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

        playerTurn = playerSymbol.equals("X");
        turnCount = 0;
    }





}