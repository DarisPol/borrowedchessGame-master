package com.example.ajedrez.Logica;

import android.util.Pair;

import com.example.ajedrez.R;

import java.util.ArrayList;

public class King extends Piece {
    private boolean seMovio;
    public King(int x, int y, Tablero tablero, int color) {
        super(x, y, tablero, color);
        seMovio = false;
        if(color == 0)
            resImagen = R.drawable.whiteking;
        else
            resImagen = R.drawable.blackking;
    }

    @Override
    public ArrayList<Pair<Integer, Integer>> movimientosPosibles() {
        Piece[][] posiciones = tablero.getPosiciones();
        ArrayList<Pair<Integer, Integer>> movimientosPosibles = new ArrayList<>();
        if(x>0) {
            if (posiciones[x - 1][y] != null && posiciones[x - 1][y].color != color) {
                Pair<Integer, Integer> pair = new Pair<>(x - 1, y);
                movimientosPosibles.add(pair);
            } else if (posiciones[x - 1][y] == null) {
                Pair<Integer, Integer> pair = new Pair<>(x - 1, y);
                movimientosPosibles.add(pair);
            }
        }
        if(x<7) {
            if (posiciones[x + 1][y] != null && posiciones[x + 1][y].color != color) {
                Pair<Integer, Integer> pair = new Pair<>(x + 1, y);
                movimientosPosibles.add(pair);
            } else if (posiciones[x + 1][y] == null) {
                Pair<Integer, Integer> pair = new Pair<>(x + 1, y);
                movimientosPosibles.add(pair);
            }
        }
        if(y < 7){
            if (posiciones[x][y+1] != null && posiciones[x][y+1].color != color) {
                Pair<Integer, Integer> pair = new Pair<>(x, y+1);
                movimientosPosibles.add(pair);
            } else if (posiciones[x][y+1] == null) {
                Pair<Integer, Integer> pair = new Pair<>(x, y+1);
                movimientosPosibles.add(pair);
            }
        }
        if(y > 0){
            if (posiciones[x][y-1] != null && posiciones[x][y-1].color != color) {
                Pair<Integer, Integer> pair = new Pair<>(x, y-1);
                movimientosPosibles.add(pair);
            } else if (posiciones[x][y-1] == null) {
                Pair<Integer, Integer> pair = new Pair<>(x, y-1);
                movimientosPosibles.add(pair);
            }
        }
        if(x > 0 && y > 0) {
            if (posiciones[x-1][y-1] != null && posiciones[x-1][y-1].color != color) {
                Pair<Integer, Integer> pair = new Pair<>(x-1, y-1);
                movimientosPosibles.add(pair);
            } else if (posiciones[x-1][y-1] == null) {
                Pair<Integer, Integer> pair = new Pair<>(x-1, y-1);
                movimientosPosibles.add(pair);
            }
        }
        if(x > 0 && y < 7) {
            if (posiciones[x-1][y+1] != null && posiciones[x-1][y+1].color != color) {
                Pair<Integer, Integer> pair = new Pair<>(x-1, y+1);
                movimientosPosibles.add(pair);
            } else if (posiciones[x-1][y+1] == null) {
                Pair<Integer, Integer> pair = new Pair<>(x-1, y+1);
                movimientosPosibles.add(pair);
            }
        }
        if(x < 7 && y > 0) {
            if (posiciones[x+1][y-1] != null && posiciones[x+1][y-1].color != color) {
                Pair<Integer, Integer> pair = new Pair<>(x+1, y-1);
                movimientosPosibles.add(pair);
            } else if (posiciones[x+1][y-1] == null) {
                Pair<Integer, Integer> pair = new Pair<>(x+1, y-1);
                movimientosPosibles.add(pair);
            }
        }
        if(x < 7 && y < 7) {
            if (posiciones[x+1][y+1] != null && posiciones[x+1][y+1].color != color) {
                Pair<Integer, Integer> pair = new Pair<>(x+1, y+1);
                movimientosPosibles.add(pair);
            } else if (posiciones[x+1][y+1] == null) {
                Pair<Integer, Integer> pair = new Pair<>(x+1, y+1);
                movimientosPosibles.add(pair);
            }
        }

        return movimientosPosibles;
    }



    public boolean seMovio() {
        return seMovio;
    }

    public void setSeMovio(boolean seMovio) {
        this.seMovio = seMovio;
    }

    @Override
    public boolean moverPieza(int movimientoX, int movimientoY) {
        boolean movioPieza = super.moverPieza(movimientoX, movimientoY);
        if(movioPieza)
            seMovio = true;
        return movioPieza;
    }
}
