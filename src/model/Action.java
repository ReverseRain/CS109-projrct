package model;

import view.CellComponent;
import view.ChessComponent;
import view.ChessboardComponent;

import java.io.Serializable;

public class Action implements Comparable<Action> {
    private ChessboardPoint from;
    private ChessboardPoint dest;
    private int value;

    public ChessPiece getPieceSecond() {
        return pieceSecond;
    }
    private ChessPiece pieceSecond;

    public ChessPiece getPieceFirst() {
        return pieceFirst;
    }

    private ChessPiece pieceFirst;
    public Action(ChessboardPoint from, ChessboardPoint dest, ChessPiece pieceFirst, ChessPiece pieceSecond) {
        this.from = from;
        this.dest = dest;
        this.pieceFirst=pieceFirst;
        this.pieceSecond=pieceSecond;
    }


    public ChessboardPoint getDest() {
        return dest;
    }

    public ChessboardPoint getFrom() {
        return from;
    }

    public int compareTo(Action a) {
        return a.getValue() - this.getValue();
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = this.value + value;
    }

    public String toString(){
        if (pieceSecond!=null){
            return String.format(pieceFirst+"--"+pieceSecond+"--"+from+"--"+dest);
        }else return String.format(pieceFirst+"--"+null+"--"+null+"--"+from+"--"+dest);
}
}
