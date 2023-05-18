package controller;


import listener.GameListener;
import model.*;
import model.Action;
import view.*;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;


/**
 * Controller is the connection between model and view,
 * when a Controller receive a request from a view, the Controller
 * analyzes and then hands over to the model for processing
 * [in this demo the request methods are onPlayerClickCell() and onPlayerClickChessPiece()]
 *
*/
public class GameController implements GameListener {


    private Chessboard model;
    private ChessboardComponent view;
    private PlayerColor currentPlayer;

    private ChessboardPoint selectedPoint;
    private boolean isAI=false;
    private boolean isMove=false;

    public boolean isAI() {
        return isAI;
    }

    public boolean isMove() {
        return isMove;
    }

    public void setMove(boolean move) {
        isMove = move;
    }

    private int turn;
    private ChessGameFrame gameframe;
    private List<Action> actions=new ArrayList<>();

    public Chessboard getModel() {
        return model;
    }

    public List<Action> getActions() {
        return actions;
    }

    public GameController(ChessboardComponent view, Chessboard model, int turn, PlayerColor color) {
        this.view = view;
        this.model = model;
        this.currentPlayer = color;
        this.turn=turn;
        view.registerController(this);
        initialize();
        view.initiateChessComponent(model);
        view.repaint();

        model.registerController(this);
    }

    private void initialize() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {

            }
        }
    }

    // after a valid move swap the player
    private void swapColor() {
        if (win()){
            System.out.println(currentPlayer);
            JOptionPane.showConfirmDialog(null,"Winner is "+currentPlayer+"\n恭喜！","战斗结束",JOptionPane.YES_NO_OPTION);
        }
        currentPlayer = currentPlayer == PlayerColor.BLUE ? PlayerColor.RED : PlayerColor.BLUE;
        if (currentPlayer==PlayerColor.BLUE){turn++;
        view.getGameFrame().getLabel1().setText(String.valueOf(turn));
        setMove(false);
        view.getGameFrame().getLabel1().repaint();}else setMove(true);   //添加标签的步骤与所需要的组件！！
        view.getGameFrame().getLabel2().setText(String.valueOf(currentPlayer));
        view.getGameFrame().getLabel2().repaint();
    }
    private void swapColor2() {
        currentPlayer = currentPlayer == PlayerColor.BLUE ? PlayerColor.RED : PlayerColor.BLUE;
        if (currentPlayer==PlayerColor.RED){turn--;
            view.getGameFrame().getLabel1().setText(String.valueOf(turn));
            setMove(true);
            view.getGameFrame().getLabel1().repaint();} setMove(false);   //添加标签的步骤与所需要的组件！！
        view.getGameFrame().getLabel2().setText(String.valueOf(currentPlayer));
        view.getGameFrame().getLabel2().repaint();
    }
    public boolean win() {
        int redNumber=0,blueNumber=0;
        for (int i = 0; i < view.getGridComponents().length; i++) {
            for (int j = 0; j < view.getGridComponents()[0].length; j++) {
                if (model.getGrid()[i][j].getPiece()!=null){
                    switch (model.getGrid()[i][j].getPiece().getOwner()){
                        case RED -> redNumber++;
                        case BLUE -> blueNumber++;
                    }
                }
            }
        }
        if (redNumber==0||blueNumber==0)
            return true;
        if (view.getCellComponentAt(0,3).isOccupy()||view.getCellComponentAt(8,3).isOccupy())
            return true;// TODO: Check the board if there is a winner
        return false;
    }


    // click an empty cell
    @Override
    public void onPlayerClickCell(ChessboardPoint point, CellComponent component) {
        if (selectedPoint != null && model.isValidMove(selectedPoint, point)) {
            actions.add(new Action(selectedPoint,point,model.getChessPieceAt(selectedPoint),null));
            model.moveChessPiece(selectedPoint, point);
            view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
            if (view.getCellComponentAt(selectedPoint).getName()!=null){
                view.getCellComponentAt(selectedPoint).setBite(false);
                model.getChessPieceAt(point).setRank(model.getChessPieceAt(point).getFinalRank());
            }
            if (component.getName()!=null){
                component.setBite(true);
                if (!model.getChessPieceAt(point).getOwner().equals(component.getOwner())){
                    component.setOccupy(true);
                }
                model.getChessPieceAt(point).setRank(0);
            }

            selectedPoint = null;
            swapColor();
            view.clean();
            view.repaint();
            // TODO: if the chess enter Dens or Traps and so on
        }
    }

    // click a cell with a chess
    @Override
    public void onPlayerClickChessPiece(ChessboardPoint point, ChessComponent component) {
        new SoundEffect().playEffect(component);
        if (selectedPoint == null) {
            if (model.getChessPieceOwner(point).equals(currentPlayer)) {
                selectedPoint = point;
                view.showValidStep(point,model);
                component.setSelected(true);
                component.repaint();
            }
        } else if (selectedPoint.equals(point)) {
            selectedPoint = null;
            view.clean();
            component.setSelected(false);
            component.repaint();
        }
        else if (model.getChessPieceAt(selectedPoint).canCapture(component.getChessPiece())&& model.isValidCapture(selectedPoint,point)){
            actions.add(new Action(selectedPoint,point,model.getChessPieceAt(selectedPoint),model.getChessPieceAt(point)));
            model.captureChessPiece(selectedPoint, point);
            view.removeChessComponentAtGrid(point);
            view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
            if (view.getCellComponentAt(point.getRow(),point.getCol()).isBite()){
                model.getChessPieceAt(point).setRank(0);}
            selectedPoint = null;
            swapColor();
            view.clean();
            view.repaint();
        }

        // TODO: Implement capture function
    }
    public static java.util.List<String> readFileByFileReader(String path) {
        try {
            FileReader fileReader = new FileReader(path);
            BufferedReader reader = new BufferedReader(fileReader);
            String line;
            List<String> readLines = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                readLines.add(line);
            }
            reader.close();
            fileReader.close();
            return readLines;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null,"找不到文件");
            e.printStackTrace();
        }
        return null;
    }
    public void doAction(Action action)  {
//        try {
//            Thread.sleep(300);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        System.out.println(action.getValue());
//        System.out.println(action);
        if (model.getChessPieceAt(action.getFrom())==null){
            JOptionPane.showMessageDialog(null,"错误","行棋步骤",JOptionPane.ERROR_MESSAGE);
        }
        ChessComponent component1=(ChessComponent) view.getCellComponentAt(action.getFrom()).getComponents()[0];
        if (model.getChessPieceAt(action.getDest())==null){
            if (!getModel().isValidMove(action.getFrom(),action.getDest())||!model.getChessPieceAt(action.getFrom()).equals(action.getPieceFirst())){
                JOptionPane.showMessageDialog(null,"错误","行棋步骤",JOptionPane.ERROR_MESSAGE);
            }
            if (!model.isValidMove(action.getFrom(),action.getDest())) {
                JOptionPane.showMessageDialog(null, "错误", "行棋步骤", JOptionPane.ERROR_MESSAGE);
            }
                onPlayerClickChessPiece(action.getFrom(),component1);
            onPlayerClickCell(action.getDest(),view.getCellComponentAt(action.getDest()));

        }else {
            ChessComponent component2=(ChessComponent) view.getCellComponentAt(action.getDest()).getComponents()[0];
            if (!getModel().isValidCapture(action.getFrom(),action.getDest())||!model.getChessPieceAt(action.getFrom()).equals(action.getPieceFirst())||!model.getChessPieceAt(action.getDest()).equals(action.getPieceSecond())){
                JOptionPane.showMessageDialog(null,"错误","行棋步骤错误",JOptionPane.ERROR_MESSAGE);
            }
            if (!model.isValidCapture(action.getFrom(),action.getDest())){
                JOptionPane.showMessageDialog(null,"错误","行棋步骤错误",JOptionPane.ERROR_MESSAGE);
            }
            onPlayerClickChessPiece(action.getFrom(),component1);
            onPlayerClickChessPiece(action.getDest(),component2);
        }
        view.paintImmediately(-view.getGameFrame().getHeight()/5,-view.getGameFrame().getHeight()/10,view.getGameFrame().getWidth(),view.getGameFrame().getHeight());
    }
    public void regretAction(Action action){
        System.out.println(model.getChessPieceAt(action.getDest()));
        model.moveChessPiece(action.getDest(),action.getFrom());
        view.setChessComponentAtGrid(action.getFrom(), view.removeChessComponentAtGrid(action.getDest()));
        if (view.getCellComponentAt(action.getDest()).getName()!=null){
            view.getCellComponentAt(action.getDest()).setBite(false);
            model.getChessPieceAt(action.getFrom()).setRank(model.getChessPieceAt(action.getFrom()).getFinalRank());
        }
        if (view.getCellComponentAt(action.getFrom()).getName()!=null){
            view.getCellComponentAt(action.getFrom()).setBite(true);
            model.getChessPieceAt(action.getFrom()).setRank(0);
        }
        if (action.getPieceSecond()!=null) {
            model.setChessPiece(action.getDest(), action.getPieceSecond());
            view.setChessComponentAtGrid(action.getDest(),new ChessComponent((810*4/5)/9,action.getPieceSecond()));
            if (view.getCellComponentAt(action.getDest()).getName()!=null){
            view.getCellComponentAt(action.getDest()).setBite(true);
            model.getChessPieceAt(action.getDest()).setRank(0);}}
        if (action.getDest().equals(new ChessboardPoint(0,3))||action.getDest().equals(new ChessboardPoint(8,3))){
            view.getCellComponentAt(action.getDest()).setOccupy(false);
        }
        actions.remove(actions.get(actions.size()-1));
        swapColor2();
        view.repaint();
    }
    public List<Action> getActionWithValue(PlayerColor color){
        List<Action> validActions=getValidAction(color);
        List<Action> validActions2=getValidAction(PlayerColor.BLUE);
        List<ChessboardPoint> dest2=new ArrayList<>();
        List<Integer>rank2=new ArrayList<>();
        for (int i = 0; i < validActions2.size(); i++) {
            dest2.add(validActions2.get(i).getDest());
            rank2.add(validActions2.get(i).getPieceFirst().getFinalRank());
        }
        for (int i = 0; i <validActions.size(); i++) {
            if (model.calculateDistance(validActions.get(i).getDest(),view.getReserveDenCell(color))-model.calculateDistance(validActions.get(i).getFrom(),view.getReserveDenCell(color))<0){
            validActions.get(i).setValue(20-model.calculateDistance(validActions.get(i).getDest(),view.getReserveDenCell(color)));}
            if ((model.getChessPieceAt(7,0)!=null&&model.getChessPieceAt(7,6)!=null)||turn<=3){
                if (model.getChessPieceAt(validActions.get(i).getFrom()).getRank()==1||model.getChessPieceAt(validActions.get(i).getFrom()).getRank()==3||model.getChessPieceAt(validActions.get(i).getFrom()).getRank()==2){
                    validActions.get(i).setValue(20);
                }
            }
            if (model.getChessPieceAt(validActions.get(i).getFrom()).getFinalRank()==7||model.getChessPieceAt(validActions.get(i).getFrom()).getFinalRank()==6){
                if (turn<10||turn>=23){validActions.get(i).setValue(3*model.getChessPieceAt(validActions.get(i).getFrom()).getFinalRank());}
            }
            if (validActions.get(i).getDest().equals(view.getReserveDenCell(color))){
                validActions.get(i).setValue(10000);
            }
            if (model.getChessPieceAt(validActions.get(i).getDest())!=null){
                validActions.get(i).setValue(20*model.getChessPieceAt(validActions.get(i).getDest()).getFinalRank());
            }
            for (int j = 0; j < dest2.size(); j++) {
                if (dest2.get(j).equals(validActions.get(i).getDest())){
                    if (rank2.get(j)>=validActions.get(i).getPieceFirst().getRank()){
                        validActions.get(i).setValue(-10*model.getChessPieceAt(validActions.get(i).getFrom()).getFinalRank());
                    }
                    if (rank2.get(j)==1&&validActions.get(i).getPieceFirst().getRank()==8){
                        validActions.get(i).setValue(-10*model.getChessPieceAt(validActions.get(i).getFrom()).getFinalRank());
                    }
                    break;
                }
            }
            if (dest2.contains(validActions.get(i).getFrom())){
                validActions.get(i).setValue(20*model.getChessPieceAt(validActions.get(i).getFrom()).getFinalRank());
            }
        }
        return validActions;
    }
    public List<Action> getValidAction(PlayerColor color){
        List<Action> validActions=new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 7; j++) {
                if (model.getChessPieceAt(i,j)!=null&&model.getChessPieceAt(i,j).getOwner()==color){
                    List<ChessboardPoint> chessboardPoints=view.getValidDest(new ChessboardPoint(i,j),model);
                    for (int k = 0; k < chessboardPoints.size(); k++) {
                        if (model.getChessPieceAt(chessboardPoints.get(k).getRow(),chessboardPoints.get(k).getCol())!=null){
                        validActions.add(new Action(new ChessboardPoint(i,j),chessboardPoints.get(k),model.getChessPieceAt(i,j),model.getChessPieceAt(chessboardPoints.get(k))));
                    }
                    else validActions.add(new Action(new ChessboardPoint(i,j),chessboardPoints.get(k),model.getChessPieceAt(i,j),model.getChessPieceAt(chessboardPoints.get(k))));
                    }
                }
            }
        }return validActions;
    }

    public void setAI(boolean AI) {
        isAI = AI;
    }

    public Action playerAI(){
        List<Action>actions=getActionWithValue(PlayerColor.RED);
        Collections.sort(actions);
        if (turn<=6){
        return actions.get(0);}
        else if (actions.get(0).getValue()<20&&actions.size()>10){
            int random=(int) Math.random()*10;
            return actions.get(random);
        }
        return actions.get(0);}
}
