package model;


import java.io.Serializable;

public class ChessPiece implements Serializable {
    // the owner of the chess
    private PlayerColor owner;

    // Elephant? Cat? Dog? ...
    private String name;
    private int rank;
    private final int finalRank;

    public int getFinalRank() {
        return finalRank;
    }

    public ChessPiece(PlayerColor owner, String name) {
        this.owner = owner;
        this.name = name;
        switch (name){
            case "鼠":
                this.rank=1;
                break;
            case "猫":
                this.rank=2;
                break;
            case "狗":
                this.rank=3;
                break;
            case "狼":
                this.rank=4;
                break;
            case "豹":
                this.rank=5;
                break;
            case "虎":
                this.rank=6;
                break;
            case "狮":
                this.rank=7;
                break;
            case "象":
                this.rank=8;
                break;
        }
        this.finalRank=rank;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public boolean canCapture(ChessPiece target) {
        if (target.getOwner()!=owner){// TODO: Finish this method!
            if (target.getRank()==8&&rank==1) {
                return true;
            } else if (target.getRank()==1&&rank==8) {
                return false;
            } else if (target.getRank()<=rank){
            return true;}}
        return false;
    }

    public String getName() {
        return name;
    }

    public PlayerColor getOwner() {
        return owner;
    }
    public String toString(){
        return String.format(getName()+"--"+getOwner());
    }
    public Boolean equals(ChessPiece p){
      if (p.getName().equals(name)&&p.getOwner().equals(owner)) {
          return true;
      }else return false;
    }
}
