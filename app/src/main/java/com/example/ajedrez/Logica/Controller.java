package com.example.ajedrez.Logica;

import android.util.Pair;

import com.example.ajedrez.R;

import java.util.ArrayList;

public class Controller {
    private static final int ROOK = 0;
    private static final int KNIGHT = 1;
    private static final int BISHOP = 2;
    private static final int QUEEN = 3;
    private static final int PAWN = 4;
    private static final int KING = 5;
    private static final Controller ourInstance = new Controller();
    private Tablero tablero;
    private Player turnPlayer;

    public static Controller getInstance() {
        return ourInstance;
    }

    private Controller() {
    }

    public Tablero nuevoTablero() {
        tablero = new Tablero();
        turnPlayer = tablero.getPlayer1();
        return tablero;
    }


    public boolean moverFicha(int posX, int posY, int movX, int movY) {
        Piece[][] posiciones = tablero.getPosiciones();
        if (posiciones[posX][posY] == null || posiciones[posX][posY].color != turnPlayer.getColor() || posiciones[movX][movY] == null || posiciones[movX][movY].color == turnPlayer.getColor()) {
            return turnPlayer.moverPieza(posiciones[posX][posY], movX, movY);
        } else {
            Piece piezaMov = posiciones[movX][movY];
            boolean moverPieza = turnPlayer.moverPieza(posiciones[posX][posY], movX, movY);
            if (moverPieza && piezaMov != null && piezaMov.player != turnPlayer)
                oponente(turnPlayer).piezaComida(piezaMov);
            return moverPieza;
        }

    }


    public boolean checkMate() {
        return turnPlayer.checkMate();
    }


    public ArrayList<Pair<Integer, Integer>> movimientosPosiblesFicha(Piece piece) {
        ArrayList<Pair<Integer, Integer>> movPosibles = piece.movimientosPosibles();
        ArrayList<Pair<Integer, Integer>> movSinJaque = new ArrayList<>();
        for (Pair<Integer, Integer> mov : movPosibles) {
            if (!piece.movimientoEsJaque(mov.first, mov.second))
                movSinJaque.add(mov);
        }
        return movSinJaque;
    }


    public void cambiarTurno() {
        if (turnPlayer == tablero.getPlayer1())
            turnPlayer = tablero.getPlayer2();
        else
            turnPlayer = tablero.getPlayer1();
    }

    public Player oponente(Player player) {
        return player == tablero.getPlayer1() ? tablero.getPlayer2() : tablero.getPlayer1();
    }

    public Player getTurnPlayer() {
        return turnPlayer;
    }

    public ArrayList<Integer> getImagenesFichasJugador() {
        ArrayList<Integer> imagenes = new ArrayList<>();
        if (turnPlayer.getColor() == 0) {
            imagenes.add(new Integer(R.drawable.whiterook));
            imagenes.add(new Integer(R.drawable.whiteknight));
            imagenes.add(new Integer(R.drawable.whitealfil));
            imagenes.add(new Integer(R.drawable.whitequeen));
            imagenes.add(new Integer(R.drawable.whitepawn));
        } else {
            imagenes.add(new Integer(R.drawable.blackrook));
            imagenes.add(new Integer(R.drawable.blackknight));
            imagenes.add(new Integer(R.drawable.blackalfil));
            imagenes.add(new Integer(R.drawable.blackqueen));
            imagenes.add(new Integer(R.drawable.blackpawn));
        }
        return imagenes;
    }

    /*pieceType:
        0 = rook
        1 = knight
        2 = alfil
        3 = queen
        4 = pawn
    */
    public void changePawn(Pawn pawn, int pieceType, Player player) {
        Piece[][] posiciones = tablero.getPosiciones();
        Piece piece;
        if (pieceType == 0)
            piece = new Rook(pawn.getX(), pawn.getY(), tablero, pawn.getColor());
        else if (pieceType == 1)
            piece = new Knight(pawn.getX(), pawn.getY(), tablero, pawn.getColor());
        else if (pieceType == 2)
            piece = new Alfil(pawn.getX(), pawn.getY(), tablero, pawn.getColor());
        else if (pieceType == 3)
            piece = new Queen(pawn.getX(), pawn.getY(), tablero, pawn.getColor());
        else
            piece = new Pawn(pawn.getX(), pawn.getY(), tablero, pawn.getColor());
        piece.setPlayer(player);
        player.piezaComida(pawn);
        posiciones[piece.getX()][piece.getY()] = piece;
        player.addPieza(piece);
    }
    
    public void transformPiece(Player player, Piece oldPiece, Piece newPiece) {
        Piece[][] posiciones = tablero.getPosiciones();
        player.piezaComida(oldPiece);
        newPiece.setPlayer(player);
        posiciones[newPiece.getX()][newPiece.getY()] = newPiece;
        player.addPieza(newPiece);
    }
}

