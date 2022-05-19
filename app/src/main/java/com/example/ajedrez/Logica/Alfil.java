package com.example.ajedrez.Logica;

import android.util.Pair;

import com.example.ajedrez.R;

import java.util.ArrayList;

public class Alfil extends Piece {
    public Alfil(int x, int y, Tablero tablero, int color) {
        super(x, y, tablero, color);
        if(color == 0)
            resImagen = R.drawable.whitealfil;
        else
            resImagen = R.drawable.blackalfil;
    }

    @Override
    public ArrayList<Pair<Integer, Integer>> movimientosPosibles() {
        Piece[][] posiciones = tablero.getPosiciones();
        ArrayList<Pair<Integer, Integer>> movimientosPosibles = new ArrayList<>();
        if(y < 7 && x < 7) {
            //diagonal para abajo derecha
            for (int i = 1; i < Math.min(8 - x, 8 - y); i++) {
                if (posiciones[x + i][y + i] != null && posiciones[x + i][y + i].color != color) {
                    Pair<Integer, Integer> pair = new Pair<>(x + i, y + i);
                    movimientosPosibles.add(pair);
                    break;
                } else if (posiciones[x + i][y + i] == null) {
                    Pair<Integer, Integer> pair = new Pair<>(x + i, y + i);
                    movimientosPosibles.add(pair);
                }
                // si es el mismo color no puede avanzar mas
                else if (posiciones[x + i][y + i].color == color) {
                    break;
                }
            }
        }
            if(y < 7 && x > 0) {
                //diagonal para arriba derecha
                for (int i = 1; i < Math.min(x+1, 8 - y); i++) {
                    if (posiciones[x - i][y + i] != null && posiciones[x - i][y + i].color != color) {
                        Pair<Integer, Integer> pair = new Pair<>(x - i, y + i);
                        movimientosPosibles.add(pair);
                        break;
                    } else if (posiciones[x - i][y + i] == null) {
                        Pair<Integer, Integer> pair = new Pair<>(x - i, y + i);
                        movimientosPosibles.add(pair);
                    }
                    // si es el mismo color no puede avanzar mas
                    else if (posiciones[x - i][y + i].color == color) {
                        break;
                    }
                }
            }
            if(y > 0 && x > 0) {
                //diagonal arriba izquierda
                for (int i = 1; i < Math.min(x+1, y+1); i++) {
                    if (posiciones[x - i][y - i] != null && posiciones[x - i][y - i].color != color) {
                        Pair<Integer, Integer> pair = new Pair<>(x - i, y - i);
                        movimientosPosibles.add(pair);
                        break;
                    } else if (posiciones[x - i][y - i] == null) {
                        Pair<Integer, Integer> pair = new Pair<>(x - i, y - i);
                        movimientosPosibles.add(pair);
                    }
                    // si es el mismo color no puede avanzar mas
                    else if (posiciones[x - i][y - i].color == color) {
                        break;
                    }
                }
            }
            if(y > 0 && x < 7) {
                //diagonal abajo izquierda
                for (int i = 1; i < Math.min(8-x, y+1); i++) {
                    if (posiciones[x + i][y - i] != null && posiciones[x + i][y - i].color != color) {
                        Pair<Integer, Integer> pair = new Pair<>(x + i, y - i);
                        movimientosPosibles.add(pair);
                        break;
                    } else if (posiciones[x + i][y - i] == null) {
                        Pair<Integer, Integer> pair = new Pair<>(x + i, y - i);
                        movimientosPosibles.add(pair);
                    }
                    // si es el mismo color no puede avanzar mas
                    else if (posiciones[x + i][y - i].color == color) {
                        break;
                    }
                }
            }



            return movimientosPosibles;
    }

}
