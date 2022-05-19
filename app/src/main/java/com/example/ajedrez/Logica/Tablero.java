package com.example.ajedrez.Logica;

import java.util.ArrayList;

public class Tablero {
    // array de posiciones, 0 si es blanco, 1 si es negro y -1 si es posicion vacia
    private Piece[][] posiciones;

    private Player player1;
    private Player player2;

    public Tablero() {
        posiciones = new Piece[8][8];
        ArrayList<Piece> fichasJugador = crearFichas(0);
        King king = (King)fichasJugador.get(4);
        this.player1 = new Player(fichasJugador, 0, king, this);
        fichasJugador = crearFichas(1);
        king = (King)fichasJugador.get(4);
        this.player2 = new Player(fichasJugador, 1, king, this);
    }

    public Piece[][] getPosiciones() {
        return posiciones;
    }

    public ArrayList<Piece> crearFichas(int color) {
        ArrayList<Piece> pieces = new ArrayList<>();
        if(color == 0) {
            for(int j = 0; j < 8; j++) {
                    Piece piece;
                    if(j == 0 || j == 7)
                        piece = new Rook(0, j, this, color);
                    else if(j == 1 || j == 6)
                        piece = new Knight(0, j, this, color);
                    else if(j == 2 || j == 5)
                        piece = new Alfil(0, j, this, color);
                    else if(j == 4)
                        piece = new King(0, j, this, color);
                    else
                        piece = new Queen(0, j, this, color);
                    posiciones[0][j] = piece;
                    pieces.add(piece);
            }
            for(int i = 0; i<8; i++) {
                Piece piece = new Pawn(1, i, this, color);
                posiciones[1][i] = piece;
                pieces.add(piece);
            }
        } else {
            for(int j = 0; j < 8; j++) {
                    Piece piece;
                    if(j == 0 || j == 7)
                        piece = new Rook(7, j, this, color);
                    else if(j == 1 || j == 6)
                        piece = new Knight(7, j, this, color);
                    else if(j == 2 || j == 5)
                        piece = new Alfil(7, j, this, color);
                    else if(j == 4)
                        piece = new King(7, j, this, color);
                    else
                        piece = new Queen(7, j, this, color);
                    posiciones[7][j] = piece;
                    pieces.add(piece);
            }

            for(int i = 0; i<8; i++) {
                Piece piece = new Pawn(6, i, this, color);
                posiciones[6][i] = piece;
                pieces.add(piece);
            }
        }
        return pieces;
    }

    public boolean terminoJuego() {
        return false;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }
}
