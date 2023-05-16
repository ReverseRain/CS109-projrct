package view;

import controller.GameController;
import model.Chessboard;
import model.PlayerColor;

import javax.swing.*;
import java.awt.*;

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
