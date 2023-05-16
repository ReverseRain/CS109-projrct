package model;

import view.ChessComponent;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class SoundEffect {
    public void playEffect(ChessComponent component) {
        try {
            String musicLocation=new String();
            switch (component.getName()){
                case "狼":
                    musicLocation="resource//music//Wolf barks 1.wav";
                    break;
                case "鼠":
                    musicLocation="resource//music//Moiuse squeal 2.wav";
                    break;
                case "猫":
                    musicLocation="resource//music//Cat meow 5.wav";
                    break;
                case "狗":
                    musicLocation="resource//music//Dog bark 3.wav";
                    break;
                case "豹":
                    musicLocation="resource//music//Lion atacks, bites 5.wav";
                    break;
                case "虎":
                    musicLocation="resource//music//Tiger attack 1.wav";
                    break;
                case "狮":
                    musicLocation="resource//music//Lion roar 3.wav";
                    break;
                case "象":
                    musicLocation="resource//music//Elephant Trumpeting 1.wav";
                    break;
            }
            File musicPath = new File(musicLocation);

            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();
                clip.loop(0);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
