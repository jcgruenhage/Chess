package edu.gymneureut.informatik.rattenschach;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public abstract class Figure implements Cloneable {
    public static final Figure EMPTY = new Figure() {
        @Override
        public Figure clone() {
            return Figure.EMPTY;
        }

        @Override
        public List<Move> getPossibleMoves() {
            return null;
        }
    };
    protected Player owner;
    protected Field position;
    protected Map<Field, Figure> field;
    protected boolean captured = false;
    protected boolean hasMoved = false;

    public Figure() {

    }


    public Figure(Player owner, Field position, Map<Field, Figure> field) {
        this.owner = owner;
        this.position = position;
        this.field = field;
    }

    public abstract List<Move> getPossibleMoves();

//    public abstract Figure copyFigure();

    public abstract Figure clone();

    protected Figure cloneTo(Figure figure) {
        return figure.setAll(owner, position, field, captured, hasMoved);
    }

    private Figure setAll(Player owner, Field position, Map<Field, Figure> field, boolean captured, boolean hasMoved) {
        this.owner = owner;
        this.position = position;
        this.field = field;
        this.captured = captured;
        this.hasMoved = hasMoved;
        return this;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public void setCaptured() {
        captured = true;
    }

    public void setNotCaptured() {
        captured = false;
    }

    protected LinkedList<Move> getMoves(int lineChange, int rowChange, Field position, int maxDistance) {
        int walkedDistance = 0;
        Field tempPosition = new Field(position.getLine(), position.getRow());
        boolean directionUnfinished = true;
        LinkedList<Move> moves = new LinkedList<>();
        while (directionUnfinished) {
            if (tempPosition.getLine() + lineChange > 8
                    || tempPosition.getRow() + rowChange > 8
                    || tempPosition.getLine() + lineChange < 1
                    || tempPosition.getRow() + rowChange < 1
                    || (maxDistance != -1 && walkedDistance >= maxDistance)) {
                directionUnfinished = false;
            } else {
                if (maxDistance != -1) {
                    walkedDistance += 1;
                }
                tempPosition = new Field(tempPosition.getLine() + lineChange,
                        tempPosition.getRow() + rowChange);
                Figure resultFigure = field.get(tempPosition);
                if (resultFigure == Figure.EMPTY) {
                    moves.add(new Move(this, position, tempPosition, false, resultFigure));
                } else if (resultFigure.getOwner() == this.getOwner().getOpponent()) {
                    moves.add(new Move(this, position, tempPosition, true, resultFigure));
                    directionUnfinished = false;
                } else if (resultFigure.getOwner() == this.getOwner()) {
                    directionUnfinished = false;
                }
            }
        }
        return moves;
    }

    public void move(Field destinationPosition) {
        if (!hasMoved) {
            hasMoved = true;
        }
        position = destinationPosition;
    }

    public Field getPosition() {
        return position;
    }

    public void setPosition(Field position) {
        this.position = position;
    }

    public void setField(Map<Field, Figure> field) {
        this.field = field;
    }

    public String getDetails() {
        return null;
    }
}
