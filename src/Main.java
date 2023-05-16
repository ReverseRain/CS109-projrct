import controller.GameController;
import model.BackgroundMusic;
import model.Chessboard;
import model.PlayerColor;
import view.BeginFrame;
import view.ChessGameFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BeginFrame beginFrame=new BeginFrame(1100, 810);
            beginFrame.setVisible(true);
            new BackgroundMusic().playMusic("resource//bgm.wav");
        });
    }
}