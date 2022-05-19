package com.example.ajedrez.Logica;

import android.util.Pair;

import com.example.ajedrez.R;

import java.util.ArrayList;

public class Pawn extends Piece {
    private boolean seMovio;
    public Pawn(int x, int y, Tablero tablero, int color) {
        super(x, y, tablero, color);
        seMovio = false;
        if(color == 0)
            resImagen = R.drawable.whitepawn;
        else
            resImagen = R.drawable.blackpawn;
    }

    @Override
    public boolean moverPieza(int movimientoX, int movimientoY) {
        boolean movioPieza = super.moverPieza(movimientoX, movimientoY);
        if(movioPieza)
            seMovio = true;
        return movioPieza;
    }

    @Override
    public ArrayList<Pair<Integer, Integer>> movimientosPosibles() {
        Piece[][] posiciones = tablero.getPosiciones();
        ArrayList<Pair<Integer, Integer>> movimientosPosibles = new ArrayList<>();
        if(color == 0) {
            if(x < 7) {
                if (posiciones[x + 1][y] == null) {
                    Pair<Integer, Integer> pair = new Pair<>(x + 1, y);
                    movimientosPosibles.add(pair);
                }
                if (posiciones[x + 1][y] == null && !seMovio && posiciones[x + 2][y] == null) {
                    Pair<Integer, Integer> pair = new Pair<>(x + 2, y);
                    movimientosPosibles.add(pair);
                }
                if (y < 7 && posiciones[x + 1][y + 1] != null && posiciones[x + 1][y + 1].color != color) {
                    Pair<Integer, Integer> pair = new Pair<>(x + 1, y + 1);
                    movimientosPosibles.add(pair);
                }
                if (y > 0 && posiciones[x + 1][y - 1] != null && posiciones[x + 1][y - 1].color != color) {
                    Pair<Integer, Integer> pair = new Pair<>(x + 1, y - 1);
                    movimientosPosibles.add(pair);
                }
            }
        }else{
            if(x > 0) {
                if (posiciones[x - 1][y] == null) {
                    Pair<Integer, Integer> pair = new Pair<>(x - 1, y);
                    movimientosPosibles.add(pair);
                }
                if (posiciones[x - 1][y] == null && !seMovio && posiciones[x - 2][y] == null) {
                    Pair<Integer, Integer> pair = new Pair<>(x - 2, y);
                    movimientosPosibles.add(pair);
                }
                if (y < 7 && posiciones[x - 1][y + 1] != null && posiciones[x - 1][y + 1].color != color) {
                    Pair<Integer, Integer> pair = new Pair<>(x - 1, y + 1);
                    movimientosPosibles.add(pair);
                }
                if (y > 0 && posiciones[x - 1][y - 1] != null && posiciones[x - 1][y - 1].color != color) {
                    Pair<Integer, Integer> pair = new Pair<>(x - 1, y - 1);
                    movimientosPosibles.add(pair);
                }
            }
        }





        return movimientosPosibles;
    }
}
