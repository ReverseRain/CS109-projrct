package view;

import controller.GameController;
import model.*;
import model.Action;
import org.w3c.dom.DOMStringList;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGTH;

    private final int ONE_CHESS_SIZE;
    private boolean did;

    private ChessboardComponent chessboardComponent;
    private JLabel label1;
    private JLabel label2;
    private JLabel background;
    private List<Action>actions=new ArrayList<>();
    public JLabel getLabel1() {
        return label1;
    }
    public ChessGameFrame(int width, int height,int turn,PlayerColor color) {
        setTitle("2023 CS109 Project Demo"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.ONE_CHESS_SIZE = (HEIGTH * 4 / 5) / 9;

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);

        addChessboard();
        addHelloButton();
        addRestartButton();
        addTurnLabel(turn);
        addPlayerLabel(color);
        addLoadButton();
        addSaveButton();
        addRegretButton();
        addBackground(turn, color);


        chessboardComponent.registerFrame(this);
    }

    public ChessboardComponent getChessboardComponent() {
        return chessboardComponent;
    }



    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {
        chessboardComponent = new ChessboardComponent(ONE_CHESS_SIZE);
        chessboardComponent.setLocation(HEIGTH / 5, HEIGTH / 10);
        add(chessboardComponent);
    }

    public void addTurnLabel(int turn) {
        this.label1 = new JLabel(String.valueOf(turn));
        label1.setLocation(HEIGTH, HEIGTH / 20);
        label1.setSize(200, 60);
        label1.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(label1);
    }

    public JLabel getLabel2() {
        return label2;
    }

    public void addPlayerLabel(PlayerColor color){
        label2=new JLabel(String.valueOf(color));
        label2.setLocation(HEIGTH, HEIGTH / 10);
        label2.setSize(200, 60);
        label2.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(label2);
    }

    /**
     * 在游戏面板中添加标签
     */



    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */

    private void addHelloButton() {          //以此为模板编写一个重新开始的按钮
        JButton button = new JButton("Show Hello Here");
        button.addActionListener((e) -> JOptionPane.showMessageDialog(this, "Hello, world!"));
        button.setLocation(HEIGTH, HEIGTH / 10 + 120);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    private void addRestartButton() {
        JButton button = new JButton("Restart");
        button.setLocation(HEIGTH, HEIGTH / 10 + 240);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click restart");int response=JOptionPane.showConfirmDialog(null,"确定重新开始吗","confirm",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (response==JOptionPane.YES_OPTION){
            SwingUtilities.invokeLater(() -> {
                BeginFrame beginFrame=new BeginFrame(1100, 810);
                beginFrame.setVisible(true);
            });
        }
        });
    }


    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.setLocation(HEIGTH, HEIGTH / 10 + 360);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);


        button.addActionListener(e -> {
            System.out.println("Click load");
            String path = JOptionPane.showInputDialog(this,"Input Path here");
            List<String> lines =GameController.readFileByFileReader(path);
            System.out.println(lines.size());
            if (lines!=null){
            String[][]str=new String[9][];
            for (int i = 0; i < 9; i++) {
                str[i]=lines.get(i).split(",");
            }
            if (checkRight(path,str,lines)){
                List<Action>actions=new ArrayList<>();
                actions=convertToAction(lines);
            ChessGameFrame mainFrame = new ChessGameFrame(1100, 810,1,PlayerColor.BLUE);
            GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard(),1,PlayerColor.BLUE);
            mainFrame.setVisible(true);
            System.out.println(actions.size());
                for (int i = 0; i <actions.size() ; i++) {
                    gameController.doAction(actions.get(i));
                }
            }}});

    }
    protected void addBackground(int turn,PlayerColor color){
        Image img = new ImageIcon("resource/gameBg2.png").getImage();
        img=img.getScaledInstance(WIDTH,HEIGTH,Image.SCALE_DEFAULT);
        ImageIcon imageIcon=new ImageIcon(img);
        this.background = new JLabel(imageIcon);
        background.setSize(WIDTH,HEIGTH);
        background.setLocation(0,0);
        add(background);
    }

    private void addRegretButton() {
        JButton button = new JButton("Regret");
        button.setLocation(HEIGTH, HEIGTH / 10 + 600);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click regret");
            if (chessboardComponent.getGameController().getActions().size()>0){
            chessboardComponent.getGameController().regretAction(chessboardComponent.getGameController().getActions().get(chessboardComponent.getGameController().getActions().size() - 1));}
            else {
                JOptionPane.showMessageDialog(null,"错误","没有前一步",JOptionPane.ERROR_MESSAGE);
            }
        });
    }
    private void addSaveButton() {
        JButton button = new JButton("Save");
        button.setLocation(HEIGTH, HEIGTH / 10 + 480);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click save");
            String path = JOptionPane.showInputDialog("存档名");
            while (path.equals("")){
                JOptionPane.showMessageDialog(null,"名字不能为空");
                path = JOptionPane.showInputDialog("存档名");
            }
            try {
                convertFile(this,path);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private File convertFile(ChessGameFrame frame,String path) throws IOException {
        int[][]ints=new int[9][7];
        for (int i = 0; i <9; i++) {
            for (int j = 0; j < 7; j++) {
                ChessPiece temp =frame.getChessboardComponent().getGameController().getModel().getChessPieceAt(i,j);
                if (temp==null){
                    ints[i][j]=0;
                } else if (temp.getOwner()==PlayerColor.BLUE) {
                    ints[i][j]=temp.getFinalRank();
                } else {ints[i][j]=-temp.getFinalRank();
                }
            }
        }
        String[]strings=new String[11+chessboardComponent.getGameController().getActions().size()];
        for (int i = 0; i < 9; i++) {
            strings[i]="";
            for (int j = 0; j < 6; j++) {
                strings[i]+=ints[i][j]+",";
            }
            strings[i]=strings[i]+ints[i][6];
        }
        strings[9]=frame.getLabel1().getText();
        strings[10]=frame.getLabel2().getText();
        for (int i = 0; i < chessboardComponent.getGameController().getActions().size(); i++) {
            strings[11+i]=actions.get(i).toString();
        }
        File file=new File(path);
//        if (file.exists()){
//            int response =JOptionPane.showConfirmDialog(null,"存档已经存在是，是否进行覆盖","confirm",JOptionPane.YES_NO_OPTION);
//            if (response==JOptionPane.YES_OPTION){
//                file.delete();//将其原本的进行覆盖
//            }
//        }
        FileWriter writer=new FileWriter(file);
        for (int i = 0; i < strings.length; i++) {
            writer.write(strings[i]+"\n");
        }
        writer.close();
        return file;
    }
    public boolean checkRight(String path,String[][]strings,List<String> lines){
        File file=new File(path);
        if (!file.getName().endsWith(".txt")){
            JOptionPane.showMessageDialog(null,"错误","文件的后缀应该是.txt",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else if (lines.size()==11) {
            JOptionPane.showMessageDialog(null,"错误","缺少行棋步骤",JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (strings.length!=9&&strings[0].length!=7){
            JOptionPane.showMessageDialog(null,"错误","棋盘的大小应该是7*9",JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (lines.size()<10) {
            JOptionPane.showMessageDialog(null,"错误","信息缺失",JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (Integer.parseInt(lines.get(9))<=0) {
            JOptionPane.showMessageDialog(null,"错误","轮数不可能小于0",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        else if (!lines.get(10).equals("BLUE")&&!lines.get(10).equals("RED")) {
            JOptionPane.showMessageDialog(null,"错误","玩家仅为红与蓝",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    public List<Action> convertToAction(List<String> lines){
//        ObjectInputStream ois=null;
//        try {
//            ois=new ObjectInputStream(lines.get(0));
//        }
        List<Action>actions=new ArrayList<>();
        if (lines.size()-11>0){
            String[][]strings=new String[lines.size()-11][];
            for (int i = 0; i < strings.length; i++) {
                strings[i]=lines.get(11+i).split("--");
                String[][]strings1=new String[2][];
                strings1[0]=strings[i][4].split(",");
                strings1[1]=strings[i][5].split(",");
                ChessboardPoint from=new ChessboardPoint(Integer.parseInt(strings1[0][0]),Integer.parseInt(strings1[0][1]));
                ChessboardPoint dest=new ChessboardPoint(Integer.parseInt(strings1[1][0]),Integer.parseInt(strings1[1][1]));
                ChessPiece piece1=produceChess(strings[i][1],strings[i][0]);
                if (!strings[i][2].equals("null")){
                    ChessPiece piece2=produceChess(strings[i][3],strings[i][2]);
                    actions.add(new Action(from,dest,piece1,piece2));
                }else actions.add(new Action(from,dest,piece1,null));
            }
        return actions;}else return null;
    }
    public ChessPiece produceChess(String str,String str2){
        if (str.equals("BLUE")){
            ChessPiece chessPiece=new ChessPiece(PlayerColor.BLUE,str2);
            return chessPiece;
        }else {
            ChessPiece chessPiece=new ChessPiece(PlayerColor.RED,str2);
            return chessPiece;
        }
    }
}
