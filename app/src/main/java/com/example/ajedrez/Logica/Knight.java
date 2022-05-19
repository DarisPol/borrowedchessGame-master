package com.example.ajedrez.Logica;

import android.util.Pair;

import com.example.ajedrez.R;

import java.util.ArrayList;

public class Knight extends Piece {
    public Knight(int x, int y, Tablero tablero, int color) {
        super(x, y, tablero, color);
        if(color == 0)
            resImagen = R.drawable.whiteknight;
        else
            resImagen = R.drawable.blackknight;
    }

    @Override
    public ArrayList<Pair<Integer, Integer>> movimientosPosibles() {
        Piece[][] posiciones = tablero.getPosiciones();
        ArrayList<Pair<Integer, Integer>> movimientosPosibles = new ArrayList<>();
        // L para abajo derecha
        if (x < 7 && y < 6) {
            if(posiciones[x + 1][y + 2] == null || posiciones[x + 1][y + 2].color != color) {
                Pair<Integer, Integer> pair = new Pair<>(x + 1, y + 2);
                movimientosPosibles.add(pair);
            }
        }
        // L para abajo izquierda
        if (x < 7 && y > 1) {
            if(posiciones[x + 1][y - 2] == null || posiciones[x + 1][y - 2].color != color) {
                Pair<Integer, Integer> pair = new Pair<>(x + 1, y - 2);
                movimientosPosibles.add(pair);
            }
        }
        // L para arriba derecha
        if (x > 0 && y < 6) {
            if(posiciones[x - 1][y + 2] == null || posiciones[x - 1][y + 2].color != color) {
                Pair<Integer, Integer> pair = new Pair<>(x - 1, y + 2);
                movimientosPosibles.add(pair);
            }
        }
        // L para arriba izquierda
        if (x > 0 && y > 1) {
            if(posiciones[x - 1][y - 2] == null || posiciones[x - 1][y - 2].color != color) {
                Pair<Integer, Integer> pair = new Pair<>(x - 1, y - 2);
                movimientosPosibles.add(pair);
            }
        }

        //////
        // L para abajo derecha
        if (x < 6 && y < 7) {
            if(posiciones[x + 2][y + 1] == null || posiciones[x + 2][y + 1].color != color) {
                Pair<Integer, Integer> pair = new Pair<>(x + 2, y + 1);
                movimientosPosibles.add(pair);
            }
        }
        // L para abajo izquierda
        if (x < 6 && y > 0) {
            if(posiciones[x + 2][y - 1] == null || posiciones[x + 2][y - 1].color != color) {
                Pair<Integer, Integer> pair = new Pair<>(x + 2, y - 1);
                movimientosPosibles.add(pair);
            }
        }
        // L para arriba derecha
        if (x > 1 && y < 7) {
            if(posiciones[x - 2][y + 1] == null || posiciones[x - 2][y + 1].color != color) {
                Pair<Integer, Integer> pair = new Pair<>(x - 2, y + 1);
                movimientosPosibles.add(pair);
            }
        }
        // L para arriba izquierda
        if (x > 1 && y > 0) {
            if(posiciones[x - 2][y - 1] == null || posiciones[x - 2][y - 1].color != color) {
                Pair<Integer, Integer> pair = new Pair<>(x - 2, y - 1);
                movimientosPosibles.add(pair);
            }
        }

        return movimientosPosibles;
    }


}
