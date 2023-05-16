package view;

import model.PlayerColor;

import javax.swing.*;
import java.awt.*;

/**
 * This is the equivalent of the Cell class,
 * but this class only cares how to draw Cells on ChessboardComponent
 */

public class CellComponent extends JPanel {
    private Color background;
    private Color finalBackground;
    private String name;
    private boolean bite=false;
    private boolean occupy=false;
    private PlayerColor Owner;

    public Color getFinalBackground() {
        return finalBackground;
    }

    public CellComponent(Color background, Point location, int size) {
        setLayout(new GridLayout(1,1));
        setLocation(location);
        setSize(size, size);
        this.background = background;
        finalBackground=background;
    }
    public CellComponent(Color background, Point location, int size,String name ) {
        setLayout(new GridLayout(1,1));
        setLocation(location);
        setSize(size, size);
        this.name=name;
        this.background = background;
        finalBackground=background;
    }

    public void setBite(boolean bite) {
        this.bite = bite;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setBackground(Color background) {
        this.background = background;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponents(g);
        g.setColor(background);
        g.fillRect(1, 1, this.getWidth()-1, this.getHeight()-1);
    }

    public void setOwner(PlayerColor owner) {
        Owner = owner;
    }

    public PlayerColor getOwner() {
        return Owner;
    }

    public boolean isBite(){
        return bite;
    }
    public boolean isOccupy(){
        return occupy;
    }

    public void setOccupy(boolean occupy) {
        this.occupy = occupy;
    }
}
