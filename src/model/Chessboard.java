package model;

import controller.GameController;

import javax.management.StringValueExp;
import javax.swing.*;
import java.util.HashSet;
import java.util.Set;

/**
 * This class store the real chess information.
 * The Chessboard has 9*7 cells, and each cell has a position for chess
 */
public class Chessboard {
    private Cell[][] grid;
    private GameController controller;
    private ChessboardPoint BlueDen=new ChessboardPoint(0,3);
    private ChessboardPoint RedDen=new ChessboardPoint(8,3);
    private final int[][] cells={{0,2},{0,4},{1,3},{8,2},{8,4},{7,3}
    };
    private final Set<ChessboardPoint> riverCell = new HashSet<>();
    private final Set<ChessboardPoint> jumpCell = new HashSet<>();
    public Chessboard() {
        this.grid =
                new Cell[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE.getNum()];//19X19

        initGrid();
        initPieces();
        initPoint();
    }
    public Chessboard(String[][]path) {
        this.grid =
                new Cell[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE.getNum()];//19X19

        initGrid();
        initPieces(path);
        initPoint();
    }

    private void initGrid() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                grid[i][j] = new Cell();
            }
        }

    }

    private void initPieces() {                           //在这个方法里头标定好最初棋子的位置
        grid[2][6].setPiece(new ChessPiece(PlayerColor.BLUE, "象"));
        grid[6][0].setPiece(new ChessPiece(PlayerColor.RED, "象"));

        grid[0][0].setPiece(new ChessPiece(PlayerColor.BLUE, "狮"));
        grid[8][6].setPiece(new ChessPiece(PlayerColor.RED, "狮"));

        grid[0][6].setPiece(new ChessPiece(PlayerColor.BLUE, "虎"));
        grid[8][0].setPiece(new ChessPiece(PlayerColor.RED, "虎"));

        grid[2][2].setPiece(new ChessPiece(PlayerColor.BLUE, "豹"));
        grid[6][4].setPiece(new ChessPiece(PlayerColor.RED, "豹"));

        grid[2][4].setPiece(new ChessPiece(PlayerColor.BLUE, "狼"));
        grid[6][2].setPiece(new ChessPiece(PlayerColor.RED, "狼"));

        grid[1][1].setPiece(new ChessPiece(PlayerColor.BLUE, "狗"));
        grid[7][5].setPiece(new ChessPiece(PlayerColor.RED, "狗"));

        grid[1][5].setPiece(new ChessPiece(PlayerColor.BLUE, "猫"));
        grid[7][1].setPiece(new ChessPiece(PlayerColor.RED, "猫"));

        grid[2][0].setPiece(new ChessPiece(PlayerColor.BLUE, "鼠"));
        grid[6][6].setPiece(new ChessPiece(PlayerColor.RED, "鼠"));
    }
    private void initPieces(String[][]path) {    //在这个方法里头标定好最初棋子的位置
        int [][]pathInt=new int[path.length][path[0].length];
        for (int i = 0; i < path.length; i++) {
            for (int j = 0; j < path[0].length; j++) {
                pathInt[i][j]=Integer.parseInt(path[i][j]);
                switch (pathInt[i][j]){
                    case 0:
                        break;
                    case -8:
                        grid[i][j].setPiece(new ChessPiece(PlayerColor.RED, "象"));
                        break;
                    case -7:
                        grid[i][j].setPiece(new ChessPiece(PlayerColor.RED, "狮"));
                        break;
                    case -6:
                        grid[i][j].setPiece(new ChessPiece(PlayerColor.RED, "虎"));
                        break;
                    case -5:
                        grid[i][j].setPiece(new ChessPiece(PlayerColor.RED, "豹"));
                        break;
                    case -4:
                        grid[i][j].setPiece(new ChessPiece(PlayerColor.RED, "狼"));
                        break;
                    case -3:
                        grid[i][j].setPiece(new ChessPiece(PlayerColor.RED, "狗"));
                        break;
                    case -2:
                        grid[i][j].setPiece(new ChessPiece(PlayerColor.RED, "猫"));
                        break;
                    case -1:
                        grid[i][j].setPiece(new ChessPiece(PlayerColor.RED, "鼠"));
                        break;
                    case 8:
                        grid[i][j].setPiece(new ChessPiece(PlayerColor.BLUE, "象"));
                        break;
                    case 7:
                        grid[i][j].setPiece(new ChessPiece(PlayerColor.BLUE, "狮"));
                        break;
                    case 6:
                        grid[i][j].setPiece(new ChessPiece(PlayerColor.BLUE, "虎"));
                        break;
                    case 5:
                        grid[i][j].setPiece(new ChessPiece(PlayerColor.BLUE, "豹"));
                        break;
                    case 4:
                        grid[i][j].setPiece(new ChessPiece(PlayerColor.BLUE, "狼"));
                        break;
                    case 3:
                        grid[i][j].setPiece(new ChessPiece(PlayerColor.BLUE, "狗"));
                        break;
                    case 2:
                        grid[i][j].setPiece(new ChessPiece(PlayerColor.BLUE, "猫"));
                        break;
                    case 1:
                        grid[i][j].setPiece(new ChessPiece(PlayerColor.BLUE, "鼠"));
                        break;
                }
                if (pathInt[i][j]!=0&&getChessPieceAt(i,j)==null){
                    JOptionPane.showMessageDialog(null,"错误","缺少行棋步骤",JOptionPane.ERROR_MESSAGE);
                }

            }
        }
        for (int i = 0; i < cells.length; i++) {
            if (grid[cells[i][0]][cells[i][1]].getPiece()!=null){
                grid[cells[i][0]][cells[i][1]].getPiece().setRank(0);
            }
        }

    }
    public ChessPiece getChessPieceAt(ChessboardPoint point) {
        return getGridAt(point).getPiece();
    }
    public ChessPiece getChessPieceAt(int x,int y) {
        return getGridAt(x,y).getPiece();
    }
    private Cell getGridAt(ChessboardPoint point) {
        return grid[point.getRow()][point.getCol()];
    }
    private Cell getGridAt(int x,int y) {
        return grid[x][y];
    }
    public int calculateDistance(ChessboardPoint src, ChessboardPoint dest) {
        return Math.abs(src.getRow() - dest.getRow()) + Math.abs(src.getCol() - dest.getCol());
    }

    private ChessPiece removeChessPiece(ChessboardPoint point) {
        ChessPiece chessPiece = getChessPieceAt(point);
        getGridAt(point).removePiece();
        return chessPiece;
    }
    public void setChessPiece(ChessboardPoint point, ChessPiece chessPiece) {
        getGridAt(point).setPiece(chessPiece);
    }

    public void moveChessPiece(ChessboardPoint src, ChessboardPoint dest) {
        if (!isValidMove(src, dest)) {
            throw new IllegalArgumentException("Illegal chess move!");
        }
        setChessPiece(dest, removeChessPiece(src));
    }

    public void captureChessPiece(ChessboardPoint src, ChessboardPoint dest) {
        if (!isValidCapture(src, dest)) {
            throw new IllegalArgumentException("Illegal chess capture!");
        }
        setChessPiece(dest, removeChessPiece(src));// TODO: Finish the method.
    }

    public Cell[][] getGrid() {
        return grid;
    }
    public PlayerColor getChessPieceOwner(ChessboardPoint point) {
        return getGridAt(point).getPiece().getOwner();
    }

    public boolean isValidMove(ChessboardPoint src, ChessboardPoint dest) {
        if (getChessPieceAt(src) == null || getChessPieceAt(dest) != null) {    //这个地方getChessPieceAt(dest) != null应该改为canCapture的方法
            return false;
        } else if (riverCell.contains(dest)) {
            if (getChessPieceAt(src).getRank()==1){
                return calculateDistance(src,dest)==1;
            }
            else return false;
        } else if (jumpCell.contains(src)&&jumpCell.contains(dest)) {
            if (getChessPieceAt(src).getRank()==7||getChessPieceAt(src).getRank()==6){
                if (isValidJump(src,dest)){
                    return true;
                }
            }
        } else if (getChessPieceAt(src).getOwner().equals(PlayerColor.BLUE)&&BlueDen.equals(dest)) {
            return false;
        }else if (getChessPieceAt(src).getOwner().equals(PlayerColor.RED)&&RedDen.equals(dest)) {
            return false;
        }
        return calculateDistance(src, dest) == 1;
    }


    public boolean isValidCapture(ChessboardPoint src, ChessboardPoint dest) {
        if (!riverCell.contains(dest)){
            if (jumpCell.contains(src)&&jumpCell.contains(dest)){
                if (getChessPieceAt(src).getRank()==7||getChessPieceAt(src).getRank()==6){
                if (isValidJump(src,dest)){
                    if (getChessPieceAt(src).canCapture(getChessPieceAt(dest))){
                    return true;}
                }
            }}
            if (getChessPieceAt(src).canCapture(getChessPieceAt(dest))){
                return calculateDistance(src, dest)==1;  // TODO:Fix this method
                }
            }
        if (riverCell.contains(dest)&&getChessPieceAt(src).getRank()==1){
            return calculateDistance(src,dest)==1;
        }
        return false;
    }
    private void initPoint(){
        for (int i = 3; i <6 ; i++) {
            for (int j = 1; j <3 ; j++) {
                riverCell.add(new ChessboardPoint(i,3-j));
                riverCell.add(new ChessboardPoint(i,3+j));
            }
        }
        for (int i = 3; i <6 ; i++) {
            jumpCell.add(new ChessboardPoint(i, 0));
            jumpCell.add(new ChessboardPoint(i, 3));
            jumpCell.add(new ChessboardPoint(i, 6));
        }
        for (int i =1; i <3; i++) {
            jumpCell.add(new ChessboardPoint(2,3-i));
            jumpCell.add(new ChessboardPoint(6,3+i));
            jumpCell.add(new ChessboardPoint(2,3+i));
            jumpCell.add(new ChessboardPoint(6,3-i));
        }
    }
    public boolean isValidJump(ChessboardPoint src, ChessboardPoint dest){
        if (src.getCol()==dest.getCol()){
            if (src.getRow()-dest.getRow()==4){
            if (riverCell.contains(new ChessboardPoint(src.getRow()-1,src.getCol()))){
                if (getGrid()[src.getRow()-1][src.getCol()].getPiece()==null&&getGrid()[src.getRow()-2][src.getCol()].getPiece()==null&&getGrid()[src.getRow()-3][src.getCol()].getPiece()==null){
                    return true;
                }
            }
        }
            if (src.getRow()-dest.getRow()==-4){
                System.out.println("h");
            if (riverCell.contains(new ChessboardPoint(src.getRow()+1,src.getCol()))){
                if (getGrid()[src.getRow()+1][src.getCol()].getPiece()==null&&getGrid()[src.getRow()+2][src.getCol()].getPiece()==null&&getGrid()[src.getRow()+3][src.getCol()].getPiece()==null){
                    return true;
                }
            }
        }}
        if (src.getRow()==dest.getRow()){
            if (src.getCol()-dest.getCol()==3){
                if (riverCell.contains(new ChessboardPoint(src.getRow(),src.getCol()-1))){
                    if (getGrid()[src.getRow()][src.getCol()-1].getPiece()==null&&getGrid()[src.getRow()][src.getCol()-2].getPiece()==null){
                        return true;
                    }
                }
            }
            if (src.getCol()-dest.getCol()==-3){
                if (riverCell.contains(new ChessboardPoint(src.getRow(),src.getCol()+1))){
                    if (getGrid()[src.getRow()][src.getCol()+1].getPiece()==null&&getGrid()[src.getRow()][src.getCol()+2].getPiece()==null){
                        return true;
                    }
                }
            }
        }
    return false;
    }
    public GameController registerController(GameController controller){
        this.controller=controller;
        return controller;
    }

    public GameController getController() {
        return controller;
    }
}
