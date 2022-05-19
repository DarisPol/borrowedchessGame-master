package com.example.ajedrez;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ajedrez.Logica.Controller;
import com.example.ajedrez.Logica.Pawn;
import com.example.ajedrez.Logica.Piece;
import com.example.ajedrez.Logica.Player;
import com.example.ajedrez.Logica.Tablero;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class OnlineGameActivity extends AppCompatActivity {

    private GridLayout tablero;
    private Tablero tab;
    private Piece[][] posiciones;
    private Pair<Integer, Integer> clickeado;
    private Pair<Integer, Integer> movimiento;
    private Controller controller;
    private ArrayList<Pair<Integer, Integer>> movPosibles;
    private Player turnPlayer;
    private boolean checkMate;
    private Button finishButton;
    private Button resetButton;
    private int unselectedWhiteColor;
    private int unselectedGrayColor;
    private int selectedWhiteColor;
    private int selectedGrayColor;
    private boolean itsMyTurn;
    private FirebaseController fbController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_game);
        fbController = FirebaseController.INSTANCE;
        fbController.turnListener(this);
        tablero = findViewById(R.id.tablero);
        controller = Controller.getInstance();
        inicializarColores();
        inicializarPartida();
    }

    private void setOnClickListeners() {
        for(int i = 0; i < tablero.getChildCount(); i++) {
            final int indexI = i;
            LinearLayout view = (LinearLayout)tablero.getChildAt(i);
            for(int j = 0; j < view.getChildCount(); j++) {
                final int indexJ = j;
                ImageButton boton = (ImageButton) view.getChildAt(j);
                boton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(itsMyTurn){
                        int fila = indexI;
                        int columna = indexJ;

                        if (posiciones[fila][columna] != null) {
                            if (clickeado == null) {
                                if (posiciones[fila][columna].getColor() == turnPlayer.getColor()) {
                                    clickeado = new Pair(fila, columna);
                                    movPosibles = mostrarMovimientosPosibles();
                                    if (movPosibles.isEmpty())
                                        clickeado = null;
                                }
                            } else {
                                if (clickeado.first == fila && clickeado.second == columna) {
                                    clickeado = null;
                                    removerMovimientosPosibles(movPosibles, null);
                                } else if (movimientoEsPosible(fila, columna)) {
                                    movimiento = new Pair(fila, columna);
                                    boolean resMov = realizarMovimiento(clickeado.first, clickeado.second, movimiento.first, movimiento.second);
                                    Pair<Integer, Integer> movHecho = null;
                                    if (!resMov) {
                                        Toast.makeText(OnlineGameActivity.this, R.string.moveNotPossible, Toast.LENGTH_SHORT).show();
                                        movHecho = new Pair<>(movimiento.first, movimiento.second);
                                    } else {
                                        terminarTurno();
                                    }
                                    removerMovimientosPosibles(movPosibles, movHecho);
                                    movPosibles = null;
                                    clickeado = null;
                                    movimiento = null;
                                    checkMate = controller.checkMate();
                                    if (checkMate) {
                                        disableTablero();
                                    }
                                }
                            }
                        } else {
                            if (clickeado != null && movimientoEsPosible(fila, columna)) {
                                movimiento = new Pair(fila, columna);
                                boolean resMov = realizarMovimiento(clickeado.first, clickeado.second, movimiento.first, movimiento.second);
                                Pair<Integer, Integer> movHecho = null;
                                if (!resMov) {
                                    Toast.makeText(OnlineGameActivity.this, R.string.moveNotPossible, Toast.LENGTH_SHORT).show();
                                    movHecho = new Pair<>(movimiento.first, movimiento.second);
                                } else {
                                    terminarTurno();
                                }
                                removerMovimientosPosibles(movPosibles, movHecho);
                                movPosibles = null;
                                clickeado = null;
                                movimiento = null;
                                checkMate = controller.checkMate();
                                if (checkMate) {
                                    disableTablero();
                                }


                            }
                        }
                        setTextTurno();
                    }
                    }
                });
            }

        }
    }

    public void leerMovimiento(Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> movimiento) {
        System.out.println(movimiento);
        boolean ret = controller.moverFicha(movimiento.first.first, movimiento.first.second, movimiento.second.first, movimiento.second.second);
        if(ret)
            mostrarTablero();
        else
            System.out.println("Not Available");
        controller.cambiarTurno();
        turnPlayer = controller.getTurnPlayer();
    }

    private void terminarTurno() {
        fbController.terminarTurno(OnlineGameActivity.this);
        controller.cambiarTurno();
        turnPlayer = controller.getTurnPlayer();
        fbController.leerMovimiento(this);
    }

    public void inicializarColores() {
        Resources res = getResources();
        unselectedGrayColor = res.getColor(R.color.unselectedGray);
        unselectedWhiteColor = res.getColor(R.color.unselectedWhite);
        selectedGrayColor = res.getColor(R.color.selectedGray);
        selectedWhiteColor = res.getColor(R.color.selectedWhite);
    }

    public void checkMate() {
        checkMate = controller.checkMate();
        if(checkMate) {
            disableTablero();
            setTextTurno();
        }
    }

    private boolean realizarMovimiento(int posX, int posY, int movX, int movY) {
        fbController.realizarMovimiento(new Pair(new Pair(posX, posY), new Pair(movX, movY)));
        boolean ret = controller.moverFicha(posX, posY, movX, movY);
        if(ret) {
            if(posiciones[movX][movY] instanceof Pawn) {
                if(movX == 0 || movX == 7)
                    mostrarEleccionesPeon(movX, movY, turnPlayer);

            }
            mostrarTablero();
        }
        return ret;
    }

    private void inicializarPartida() {
        tab = controller.nuevoTablero();
        finishButton = findViewById(R.id.terminarPartida);
        resetButton = findViewById(R.id.reiniciarPartida);
        turnPlayer = tab.getPlayer1();
        posiciones = tab.getPosiciones();
        movPosibles = null;
        itsMyTurn = getIntent().getBooleanExtra("itsMyTurn", false);
        if(!itsMyTurn) {
            fbController.leerMovimiento(this);
        }
        setTextTurno();
        mostrarTablero();
        clickeado = null;
        movimiento = null;
        checkMate = false;
        findViewById(R.id.options).setVisibility(View.VISIBLE);
        findViewById(R.id.pieceChoice).setVisibility(View.GONE);
        setOnClickListeners();

    }

    private boolean movimientoEsPosible(int movX, int movY) {
        for(Pair<Integer, Integer> mov : movPosibles)
            if(movX == mov.first && movY == mov.second)
                return true;
        return false;
    }

    private void mostrarTablero() {
        for(int i = 0; i < tablero.getChildCount(); i++) {
            LinearLayout view = (LinearLayout)tablero.getChildAt(i);
            for(int j = 0; j < view.getChildCount(); j++) {
                ImageButton boton = (ImageButton) view.getChildAt(j);
                boton.setAdjustViewBounds(true);
                if(posiciones[i][j] != null) {
                    boton.setImageResource(posiciones[i][j].getResImagen());
                } else
                    boton.setImageResource(android.R.color.transparent);
                boton.setBackgroundColor(getColorPosicion(i, j));


            }

        }
    }

    public ArrayList<Pair<Integer, Integer>> mostrarMovimientosPosibles() {
        ArrayList<Pair<Integer, Integer>> movPosibles = controller.movimientosPosiblesFicha(posiciones[clickeado.first][clickeado.second]);
        for(Pair<Integer, Integer> mov : movPosibles) {
            getImageButton(mov.first, mov.second).setBackgroundColor(getColorPosicionMarcado(mov.first, mov.second));
        }
        return movPosibles;
    }

    public void disableTablero() {
        for(int i = 0; i < tablero.getChildCount(); i++) {
            LinearLayout view = (LinearLayout)tablero.getChildAt(i);
            for(int j = 0; j < view.getChildCount(); j++) {
                ImageButton boton = (ImageButton) view.getChildAt(j);
                boton.setClickable(false);
            }

        }
    }

    public void enableTablero() {
        for(int i = 0; i < tablero.getChildCount(); i++) {
            LinearLayout view = (LinearLayout)tablero.getChildAt(i);
            for(int j = 0; j < view.getChildCount(); j++) {
                ImageButton boton = (ImageButton) view.getChildAt(j);
                boton.setClickable(true);
            }

        }
    }

    public void removerMovimientosPosibles(ArrayList<Pair<Integer, Integer>> movPosibles, Pair<Integer, Integer> movHecho) {
        for(Pair<Integer, Integer> mov : movPosibles) {
            if(mov != movHecho) {
                getImageButton(mov.first, mov.second).setBackgroundColor(getColorPosicion(mov.first, mov.second));
            }
        }
    }

    public int getColorPosicion(int x, int y) {
        if(x % 2 == 0 && y % 2 == 0 || x % 2 == 1 && y % 2 == 1)
            return unselectedGrayColor;
        else
            return unselectedWhiteColor;
    }

    public int getColorPosicionMarcado(int x, int y) {
        if(x % 2 == 0 && y % 2 == 0 || x % 2 == 1 && y % 2 == 1)
            return selectedGrayColor;
        else
            return selectedWhiteColor;
    }

    public void setTextTurno() {
        TextView turno = findViewById(R.id.turnoJugador);
        if(checkMate && turnPlayer == tab.getPlayer1())
            turno.setText(R.string.blacksWin);
        else if(checkMate && turnPlayer == tab.getPlayer2())
            turno.setText(R.string.whitesWin);
        else if(turnPlayer == tab.getPlayer1())
            turno.setText(R.string.whitesTurn);
        else
            turno.setText(R.string.blacksTurn);
    }


    public ImageButton getImageButton(int x, int y) {
        return ((ImageButton)(((LinearLayout)(tablero.getChildAt(x))).getChildAt(y)));
    }

    public void terminarPartida(View v) {
        onBackPressed();
    }

    public void reiniciarPartida(View v) {
        inicializarPartida();
    }

    public void mostrarEleccionesPeon(final int x, final int y, final Player turnPlayer) {
        disableTablero();
        final LinearLayout options = findViewById(R.id.options);
        options.setVisibility(View.GONE);
        final LinearLayout layout = findViewById(R.id.pieceChoice);
        layout.setVisibility(View.VISIBLE);
        ArrayList<Integer> piecesImages = controller.getImagenesFichasJugador();
        for(int i = 0; i < layout.getChildCount(); i++) {
            final int index = i;
            ImageButton button = (ImageButton)layout.getChildAt(i);
            button.setImageResource(piecesImages.get(i));
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    controller.changePawn((Pawn)posiciones[x][y], index, turnPlayer);
                    layout.setVisibility(View.GONE);
                    options.setVisibility(View.VISIBLE);
                    enableTablero();
                    mostrarTablero();
                    //check if turning the pawn into another piece causes a checkmate
                    checkMate();
                }
            });
        }
    }

    public void setItsMyTurn(boolean itsMyTurn) {
        this.itsMyTurn = itsMyTurn;
    }

    public void endGame(View v) {
        finish();
    }

}
