package ooad.piece;

import ooad.board.Tile;
import ooad.game.IChessGame;

import java.util.ArrayList;

public abstract class Piece {
    protected Boolean isWhite;
    protected String type;
    protected Tile tile = null;
    protected Boolean firstMove = true;
    protected Boolean isTaken = false;
    protected String fileName;

    public Piece(String type, Boolean isWhite){
        this.type = type;
        this.isWhite = isWhite;
        String color;
        if (this.getIsWhite()){
            color = "white";
        }
        else{
            color = "black";
        }
        fileName = color +  this.type + ".png";
    }

    public Boolean getIsWhite() {
        return this.isWhite;
    }

    public String getType() {
        return this.type;
    }

    public Tile getTile() {
        return this.tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public Boolean getFirstMove(){
        return this.firstMove;
    }

    public void setFirstMove(Boolean firstMove){
        this.firstMove = firstMove;
    }

    public boolean getIsTaken() {
        return this.isTaken;
    }

    public void take(){
        this.isTaken = true;
    }

    public void placeOnBoard() {
        this.isTaken = false;
    }

    public String getFileName(){
        return this.fileName;
    }

    public ArrayList<Tile> getValidMoves(IChessGame game){
        ArrayList<Tile> validMoves = new ArrayList<>();
        validMoves.addAll(this.getThreateningMoves(game));
        validMoves.addAll(this.getNonThreateningMoves(game));
        validMoves.removeIf(move -> game.pieceMoveResultsInCheck(this, move, this.isWhite));
        return validMoves;
    }
    abstract public ArrayList<Tile> getThreateningMoves(IChessGame game);
    public ArrayList<Tile> getNonThreateningMoves(IChessGame game){
        return new ArrayList<>();
    }

    public String toString() {
        if (this.getIsWhite()){
            return "white " + this.type;
        }
        else{
            return "black " + this.type;
        }
    }
}
