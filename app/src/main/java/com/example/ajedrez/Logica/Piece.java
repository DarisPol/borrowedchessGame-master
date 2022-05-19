package com.example.ajedrez.Logica;

import android.util.Pair;

import java.util.ArrayList;

public abstract class Piece {
    protected int x;
    protected int y;
    protected Tablero tablero;
    protected int color; // 0 indica blanco y 1 indica negro
    protected int resImagen;
    protected Player player;
    public Piece(int x, int y, Tablero tablero, int color) {
        this.x = x;
        this.y = y;
        this.tablero = tablero;
        this.color = color;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setPosicion(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean movimientoEsJaque(int movX, int movY) {
        Piece[][] posiciones = tablero.getPosiciones();
        Piece auxPiece = posiciones[movX][movY];
        int posXInicial = x;
        int posYInicial = y;
        if(auxPiece != null && auxPiece.player != player)
            auxPiece.player.piezaComida(auxPiece);
        mover(movX, movY);
        boolean result = this.player.jaquePropio();
        mover(posXInicial, posYInicial);
        if(auxPiece != null && auxPiece.player != player)
            auxPiece.player.addPieza(auxPiece);
        posiciones[movX][movY] = auxPiece;
        return result;
    }

    private boolean movimientoEsValido(int movimientoX, int movimientoY) {
        ArrayList<Pair<Integer, Integer>> movimientosPosibles = movimientosPosibles();
        for(Pair<Integer, Integer> movimiento : movimientosPosibles){
            if(movimiento.first == movimientoX && movimiento.second == movimientoY ) {
                return true;
            }
        }
        return false;
    }


    public int getResImagen() {
        return resImagen;
    }
    public abstract ArrayList<Pair<Integer, Integer>> movimientosPosibles();
    //public abstract boolean moverPieza(int movimientoX, int movimientoY);
    public boolean moverPieza(int movimientoX, int movimientoY) {
        if(movimientoEsValido(movimientoX, movimientoY)) {
            Piece[][] posiciones = tablero.getPosiciones();
            posiciones[x][y] = null;
            this.x = movimientoX;
            this.y = movimientoY;
            posiciones[x][y] = this;
            return true;
        }
        return false;
    }

    public void mover(int movimientoX, int movimientoY) {
        Piece[][] posiciones = tablero.getPosiciones();
        posiciones[x][y] = null;
        this.x = movimientoX;
        this.y = movimientoY;
        posiciones[x][y] = this;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getColor() {
        return color;
    }
}
