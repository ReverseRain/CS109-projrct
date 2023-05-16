package view;


import controller.GameController;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

import static model.Constant.CHESSBOARD_COL_SIZE;
import static model.Constant.CHESSBOARD_ROW_SIZE;

/**
 * This class represents the checkerboard component object on the panel
 */
public class ChessboardComponent extends JComponent {
    private final CellComponent[][] gridComponents = new CellComponent[CHESSBOARD_ROW_SIZE.getNum()][CHESSBOARD_COL_SIZE.getNum()];
    private final int CHESS_SIZE;
    private final Set<ChessboardPoint> riverCell = new HashSet<>();

    private final Set<ChessboardPoint> trapCell_Blue = new HashSet<>();
    private final Set<ChessboardPoint> trapCell_Red = new HashSet<>();
    private final List<ChessboardPoint> shedCell_Red = new ArrayList<>();
    private final List<ChessboardPoint> shedCell_Blue = new ArrayList<>();
    private GameController gameController;
    private ChessGameFrame gameFrame;
    private final int[][] field={
            {-1,0},{0,-1},{1,0},{0,1},{0,-3},{0,3},{4,0},{-4,0}
    };
    private List<ChessboardPoint> pointList=new ArrayList<>();
    public int getCHESS_SIZE() {
        return CHESS_SIZE;
    }




    public ChessboardComponent(int chessSize) {
        CHESS_SIZE = chessSize;
        int width = CHESS_SIZE * 7;
        int height = CHESS_SIZE * 9;
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);// Allow mouse events to occur
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        System.out.printf("chessboard width, height = [%d : %d], chess size = %d\n", width, height, CHESS_SIZE);
        initiateGridComponents();
    }

    public GameController getGameController() {
        return gameController;
    }

    /**
     * This method represents how to initiate ChessComponent
     * according to Chessboard information
     */
    public void initiateChessComponent(Chessboard chessboard) {      //就是在这个方法内绘制并初始化一个棋盘
        Cell[][] grid = chessboard.getGrid();
        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                // TODO: Implement the initialization checkerboard

                if (grid[i][j].getPiece() != null) {
                    ChessPiece chessPiece = grid[i][j].getPiece();
                    System.out.println(chessPiece.getOwner());
                    gridComponents[i][j].add(
                                    new ChessComponent(
                                            CHESS_SIZE,chessPiece));
                }
            }
        }

    }

    public void initiateGridComponents() {

        riverCell.add(new ChessboardPoint(3,1));
        riverCell.add(new ChessboardPoint(3,2));
        riverCell.add(new ChessboardPoint(4,1));
        riverCell.add(new ChessboardPoint(4,2));
        riverCell.add(new ChessboardPoint(5,1));
        riverCell.add(new ChessboardPoint(5,2));

        riverCell.add(new ChessboardPoint(3,4));
        riverCell.add(new ChessboardPoint(3,5));
        riverCell.add(new ChessboardPoint(4,4));
        riverCell.add(new ChessboardPoint(4,5));
        riverCell.add(new ChessboardPoint(5,4));
        riverCell.add(new ChessboardPoint(5,5));

        trapCell_Blue.add(new ChessboardPoint(0,2));
        trapCell_Blue.add(new ChessboardPoint(0,4));
        trapCell_Blue.add(new ChessboardPoint(1,3));

        trapCell_Red.add(new ChessboardPoint(8,2));
        trapCell_Red.add(new ChessboardPoint(8,4));
        trapCell_Red.add(new ChessboardPoint(7,3));

        shedCell_Blue.add(new ChessboardPoint(0,3));
        shedCell_Red.add(new ChessboardPoint(8,3));
        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                ChessboardPoint temp = new ChessboardPoint(i, j);
                CellComponent cell;
                if (riverCell.contains(temp)) {                     //怎么分辨河流Cell的！！  将兽穴和陷阱都要改成Cell类型的组件
                    cell = new CellComponent(Color.CYAN, calculatePoint(i, j), CHESS_SIZE);
                    this.add(cell);
                }
                else if (trapCell_Blue.contains(temp)) {
                    cell=new CellComponent(Color.GREEN, calculatePoint(i, j), CHESS_SIZE,"Blue Trap");
                    this.add(cell);
                }
                else if (trapCell_Red.contains(temp)) {
                    cell=new CellComponent(Color.GREEN, calculatePoint(i, j), CHESS_SIZE,"Red Trap");
                    this.add(cell);
                } else if (shedCell_Blue.contains(temp)) {
                    cell=new CellComponent(Color.ORANGE, calculatePoint(i, j), CHESS_SIZE,"Shed");
                    this.add(cell);
                } else if (shedCell_Red.contains(temp)) {
                    cell=new CellComponent(Color.ORANGE, calculatePoint(i, j), CHESS_SIZE,"Shed");
                    this.add(cell);
                } else {
                    cell = new CellComponent(Color.LIGHT_GRAY, calculatePoint(i, j), CHESS_SIZE);
                    this.add(cell);
                }
                gridComponents[i][j] = cell;
            }
        }
    }

    public void registerController(GameController gameController) {
        this.gameController = gameController;
    }
    public void registerFrame(ChessGameFrame frame) {
        this.gameFrame=frame;
    }
    public void setChessComponentAtGrid(ChessboardPoint point, ChessComponent chess) {
        getGridComponentAt(point).add(chess);
    }


    public ChessComponent removeChessComponentAtGrid(ChessboardPoint point) {
        // Note re-validation is required after remove / removeAll.
        ChessComponent chess = (ChessComponent) getGridComponentAt(point).getComponents()[0];
        getGridComponentAt(point).removeAll();
        getGridComponentAt(point).revalidate();
        chess.setSelected(false);
        return chess;
    }

    private CellComponent getGridComponentAt(ChessboardPoint point) {
        return gridComponents[point.getRow()][point.getCol()];
    }

    private ChessboardPoint getChessboardPoint(Point point) {
        System.out.println("[" + point.y/CHESS_SIZE +  ", " +point.x/CHESS_SIZE + "] Clicked");
        return new ChessboardPoint(point.y/CHESS_SIZE, point.x/CHESS_SIZE);
    }
    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    @Override
    protected void processMouseEvent(MouseEvent e) {
        if (e.getID() == MouseEvent.MOUSE_PRESSED) {
            JComponent clickedComponent = (JComponent) getComponentAt(e.getX(), e.getY());
            if (clickedComponent.getComponentCount() == 0) {
                System.out.print("None chess here and ");
                gameController.onPlayerClickCell(getChessboardPoint(e.getPoint()), (CellComponent) clickedComponent);
            } else {
                System.out.print("One chess here and ");
                gameController.onPlayerClickChessPiece(getChessboardPoint(e.getPoint()), (ChessComponent) clickedComponent.getComponents()[0]);
            }
            System.out.println(gameController.isMove());
            if (gameController.isMove()&& gameController.isAI()&&!gameController.win()){
                gameController.doAction(gameController.playerAI());
                gameController.setMove(false);
            }
        }
    }
    public ChessGameFrame getGameFrame() {
        return gameFrame;
    }

    public CellComponent[][] getGridComponents() {
        return gridComponents;
    }

    public CellComponent getCellComponentAt(ChessboardPoint point){
        CellComponent cell=gridComponents[point.getRow()][point.getCol()];
        return cell;
    }
    public CellComponent getCellComponentAt(int x,int y){
        CellComponent cell=gridComponents[x][y];
        return cell;
    }
    public void showValidStep(ChessboardPoint point,Chessboard model){
        for (int i = 0; i < 8; i++) {
            int newRow=point.getRow()+field[i][0];
            int newCol=point.getCol()+field[i][1];
            if (newRow>=0&&newRow<9){
                if (newCol>=0&&newCol<7){
                    pointList.add(new ChessboardPoint(newRow,newCol));
                }
            }
        }
        for (int i = 0; i < pointList.size(); i++) {
            if (model.getChessPieceAt(pointList.get(i))!=null){
                if (model.isValidCapture(point,pointList.get(i))){
                    getCellComponentAt(pointList.get(i)).setBackground(Color.PINK);
                    getCellComponentAt(pointList.get(i)).repaint();
                }
            }
            else {
                if (model.isValidMove(point,pointList.get(i))){
                        getCellComponentAt(pointList.get(i)).setBackground(Color.PINK);
                        getCellComponentAt(pointList.get(i)).repaint();
                }
            }
        }
    }

    public ChessboardPoint getReserveDenCell(PlayerColor color) {
        if (color.equals(PlayerColor.BLUE)){
            return shedCell_Red.get(0);
        }else return shedCell_Blue.get(0);
    }

    public List<ChessboardPoint> getValidDest(ChessboardPoint point, Chessboard model){
        List<ChessboardPoint>points=new ArrayList<>();
        List<ChessboardPoint>destPoint=new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            int newRow=point.getRow()+field[i][0];
            int newCol=point.getCol()+field[i][1];
            if (newRow>=0&&newRow<9){
                if (newCol>=0&&newCol<7){
                    points.add(new ChessboardPoint(newRow,newCol));
                }
            }
        }
        for (int i = 0; i < points.size(); i++) {
            if (model.getChessPieceAt(points.get(i))!=null){
                if (model.isValidCapture(point,points.get(i))){
                    destPoint.add(points.get(i));
                }
            }
            else {
                if (model.isValidMove(point,points.get(i))){
                    destPoint.add(points.get(i));
                }
            }
        }return destPoint;
    }
    public void clean(){
        for (int i = 0; i < pointList.size(); i++) {
            getCellComponentAt(pointList.get(i)).setBackground(getCellComponentAt(pointList.get(i)).getFinalBackground());
            getCellComponentAt(pointList.get(i)).repaint();
        }
        pointList.removeAll(pointList);
    }
}
