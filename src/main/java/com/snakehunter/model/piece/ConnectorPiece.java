package com.snakehunter.model.piece;

/**
 * @author WeiYi Yu
 * @date 2019-09-06
 */
public abstract class ConnectorPiece
        extends Piece {

    private int connectedPosition;

    public ConnectorPiece(int position, int connectedPosition) {
        super(position);
        this.connectedPosition = connectedPosition;
    }

    public int getConnectedPosition() {
        return connectedPosition;
    }

    public void setConnectedPosition(int connectedPosition) {
        this.connectedPosition = connectedPosition;
    }

    public int getLength() {
        return Math.abs(connectedPosition - getPosition());
    }

}
