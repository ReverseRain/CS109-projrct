package view;

import controller.GameController;
import model.Chessboard;
import model.PlayerColor;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class BeginFrame extends JFrame {
    private final int WIDTH;
    private final int HEIGTH;
    private JLabel background;
    private final int ONE_CHESS_SIZE;
    public  BeginFrame(int width,int height){
        setTitle("欢迎来玩斗兽棋");
        this.WIDTH = width;
        this.HEIGTH = height;
        this.ONE_CHESS_SIZE = (HEIGTH * 4 / 5) / 9;

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);

        addBeginButton();
        addAIButton();
        addNetButton();
        addEasyAIButton();
        addBackground();
    }
    private void addBeginButton() {          //以此为模板编写一个重新开始的按钮
        JButton button = new JButton("Begin");
        button.addActionListener(e -> {
            System.out.println("Click begin");
            ChessGameFrame mainFrame = new ChessGameFrame(1100, 810,1, PlayerColor.BLUE);
            GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard(),1,PlayerColor.BLUE);
            mainFrame.setVisible(true);});
        button.setLocation(WIDTH/3+20,HEIGTH/3);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }
    private void addAIButton() {          //以此为模板编写一个重新开始的按钮
        JButton button = new JButton("AI");
        button.addActionListener(e -> {
            System.out.println("Click AI");
            ChessGameFrame mainFrame = new ChessGameFrame(1100, 810,1, PlayerColor.BLUE);
            GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard(),1,PlayerColor.BLUE);
            gameController.setAI(true);
            mainFrame.setVisible(true);});
        button.setLocation(WIDTH/3+20,HEIGTH/3+120);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }
    private void addEasyAIButton() {          //以此为模板编写一个重新开始的按钮
        JButton button = new JButton("easyAI");
        button.addActionListener(e -> {
            System.out.println("Click easyAI");
            ChessGameFrame mainFrame = new ChessGameFrame(1100, 810,1, PlayerColor.BLUE);
            GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard(),1,PlayerColor.BLUE);
            gameController.setEasyAI(true);
            mainFrame.setVisible(true);});
        button.setLocation(WIDTH/3+20,HEIGTH/3+240);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }
    private void addNetButton() {          //以此为模板编写一个重新开始的按钮
        JButton button = new JButton("Net");
        button.addActionListener(e -> {
            System.out.println("Click Net");
            Object[]colors={"BLUE","RED"};
            int op = JOptionPane.showOptionDialog(null, "选择颜色", "“标题”",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.QUESTION_MESSAGE,null, colors, colors[0]);
            String yoursIP = JOptionPane.showInputDialog(this,"Input IP(yours) here");
            int yoursPort = Integer.parseInt(JOptionPane.showInputDialog(this,"Input port(yours) here"));
            String IP = JOptionPane.showInputDialog(this,"Input IP(other) here");
            int port = Integer.parseInt(JOptionPane.showInputDialog(this,"Input port(other) here"));
            ChessGameFrame mainFrame = new ChessGameFrame(1100, 810,1, PlayerColor.BLUE);
            GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard(),1,PlayerColor.BLUE);
            gameController.setYourIP(yoursIP);gameController.setIP(IP);gameController.setPort(port);gameController.setYourPort(yoursPort);
            mainFrame.setVisible(true);
            gameController.getView().paintImmediately(-gameController.getView().getGameFrame().getHeight()/5,-gameController.getView().getGameFrame().getHeight()/10,gameController.getView().getGameFrame().getWidth(),gameController.getView().getGameFrame().getHeight());
            if (op==0){
                try {
                    gameController.setServer(new ServerSocket(yoursPort));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }else {
                try {
                    gameController.setYourSocket(new Socket(IP,port));
                    Socket socket = gameController.getYourSocket();
                    InputStream inputStream=socket.getInputStream();
                    byte[] bytes = new byte[1024];
                    int len;
                    StringBuilder sb = new StringBuilder();
                    while ((len = inputStream.read(bytes)) != -1) {
                        sb.append(new String(bytes, 0, len, "UTF-8"));
                    }
                    gameController.doAction(gameController.translateAction(sb));
                    inputStream.close();
                    gameController.setYourSocket(new Socket(IP,port));
                    gameController.setMove(false);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        button.setLocation(WIDTH/3+20,HEIGTH/3+360);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    private void addBackground(){
        Image img = new ImageIcon("resource/gameBg4.png").getImage();
        img=img.getScaledInstance(WIDTH,HEIGTH,Image.SCALE_DEFAULT);
        ImageIcon imageIcon=new ImageIcon(img);
        this.background = new JLabel(imageIcon);
        background.setSize(WIDTH,HEIGTH);
        background.setLocation(0,0);
        add(background);
    }
}
