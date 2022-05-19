package com.example.ajedrez.Logica;

import android.util.Pair;

import com.example.ajedrez.R;

import java.util.ArrayList;

public class Queen extends Piece {
    public Queen(int x, int y, Tablero tablero, int color) {
        super(x, y, tablero, color);
        if(color == 0)
            resImagen = R.drawable.whitequeen;
        else
            resImagen = R.drawable.blackqueen;
    }

    @Override
    public ArrayList<Pair<Integer, Integer>> movimientosPosibles() {
        Piece[][] posiciones = tablero.getPosiciones();
        ArrayList<Pair<Integer, Integer>> movimientosPosibles = new ArrayList<>();
        //reviso la columna
        for (int i = x + 1; i < posiciones.length; i++) {
            // es una ficha contraria, no puede atravezarla
            if (posiciones[i][y] != null && posiciones[i][y].color != color) {
                Pair<Integer, Integer> pair = new Pair<>(i, y);
                movimientosPosibles.add(pair);
                break;
            } else if (posiciones[i][y] == null) {
                Pair<Integer, Integer> pair = new Pair<>(i, y);
                movimientosPosibles.add(pair);
            }
            // si es el mismo color no puede avanzar mas
            else if (posiciones[i][y].color == color) {
                break;
            }
        }
        for (int i = x - 1; i >= 0; i--) {
            // es una ficha contraria, no puede atravezarla
            if (posiciones[i][y] != null && posiciones[i][y].color != color) {
                Pair<Integer, Integer> pair = new Pair<>(i, y);
                movimientosPosibles.add(pair);
                break;
            } else if (posiciones[i][y] == null) {
                Pair<Integer, Integer> pair = new Pair<>(i, y);
                movimientosPosibles.add(pair);
            }
            // si es el mismo color no puede avanzar mas
            else if (posiciones[i][y].color == color) {
                break;
            }
        }

        //reviso por fila
        for (int j = y + 1; j < posiciones[0].length; j++) {
            // es una ficha contraria, no puede atravezarla
            if (posiciones[x][j] != null && posiciones[x][j].color != color) {
                Pair<Integer, Integer> pair = new Pair<>(x, j);
                movimientosPosibles.add(pair);
                break;
            } else if (posiciones[x][j] == null) {
                Pair<Integer, Integer> pair = new Pair<>(x, j);
                movimientosPosibles.add(pair);
            }
            // si es el mismo color no puede avanzar mas
            else if (posiciones[x][j].color == color) {
                break;
            }
        }
        for (int j = y - 1; j >= 0; j--) {
            // es una ficha contraria, no puede atravezarla
            if (posiciones[x][j] != null && posiciones[x][j].color != color) {
                Pair<Integer, Integer> pair = new Pair<>(x, j);
                movimientosPosibles.add(pair);
                break;
            } else if (posiciones[x][j] == null) {
                Pair<Integer, Integer> pair = new Pair<>(x, j);
                movimientosPosibles.add(pair);
            }
            // si es el mismo color no puede avanzar mas
            else if (posiciones[x][j].color == color) {
                break;
            }
        }

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
