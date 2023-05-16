package view;


import model.ChessPiece;
import model.PlayerColor;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

/**
 * This is the equivalent of the ChessPiece class,
 * but this class only cares how to draw Chess on ChessboardComponent
 */
public class ChessComponent extends JComponent implements Serializable {
    private PlayerColor owner;

    private boolean selected;
    private String name;
    private ChessPiece chessPiece;
    private int size;

    public ChessPiece getChessPiece() {
        return chessPiece;
    }

//    public ChessComponent(PlayerColor owner, int size, String name, ChessPiece chessPiece) {
//        this.owner = owner;
//        this.selected = false;
//        this.name = name;
//        this.chessPiece=chessPiece;
//        this.size=size;
//        setSize(size / 2, size / 2);
//        setLocation(0, 0);
//        setVisible(true);
//    }
    public ChessComponent(int size, ChessPiece chessPiece) {
        this.owner = chessPiece.getOwner();
        this.selected = false;
        this.name = chessPiece.getName();
        this.chessPiece=chessPiece;
        this.size=size;
        setSize(size / 2, size / 2);
        setLocation(0, 0);
        setVisible(true);
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }


    public PlayerColor getOwner() {
        return owner;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Font font = new Font("楷体", Font.PLAIN, getWidth() / 2);
        g2.setFont(font);
        g2.setColor(owner.getColor());
//        g.drawString(name,getWidth() / 4, getHeight() * 5 / 8);
        g.fillArc(0,0,getWidth(),getHeight(),0,360);
        Image img = new ImageIcon("resource/Image/"+name+".png").getImage();
        img=img.getScaledInstance(getWidth(),getHeight(),Image.SCALE_DEFAULT);
        ImageIcon imageIcon=new ImageIcon(img);
        JLabel label=new JLabel(imageIcon);
        label.setSize(size,size);
        add(label);
//        g2.drawString("象", getWidth() / 4, getHeight() * 5 / 8); // FIXME: Use library to find the correct offset.
        if (isSelected()) {     // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth(), getHeight());
        }
    }

    @Override
    public String getName() {
        return name;
    }
    public String toString(){
        return String.format(getName()+"--"+getOwner());
    }
}
