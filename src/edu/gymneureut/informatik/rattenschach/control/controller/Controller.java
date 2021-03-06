/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Jan Christian Grünhage; Alex Klug
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package edu.gymneureut.informatik.rattenschach.control.controller;

import edu.gymneureut.informatik.rattenschach.model.Field;
import edu.gymneureut.informatik.rattenschach.model.figures.Figure;
import edu.gymneureut.informatik.rattenschach.model.turns.Castling;
import edu.gymneureut.informatik.rattenschach.model.turns.Move;
import edu.gymneureut.informatik.rattenschach.model.turns.Promotion;
import edu.gymneureut.informatik.rattenschach.model.turns.Turn;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * The <tt>Controller</tt> interface.
 *
 * @author Jan Christian Gruenhage, Alex Klug
 * @version 0.1
 */
public interface Controller {

    /**
     * Get's a list of figures out of a list of Turns
     *
     * @param turns the turns
     * @return the figures
     */
    static List<Figure> getFigures(List<Turn> turns) {
        List<Figure> figures = new LinkedList<>();
        for (Turn turn : turns) {
            if (turn instanceof Promotion) {
                Figure figure = ((Promotion) turn).getPawn();
                if (!figures.contains(figure)) {
                    figures.add(figure);
                }
            } else if (turn instanceof Move) {
                Figure figure = ((Move) turn).getFigure();
                if (!figures.contains(figure)) {
                    figures.add(figure);
                }
            } else if (turn instanceof Castling) {
                Figure figure = ((Castling) turn).getKing();
                if (!figures.contains(figure)) {
                    figures.add(figure);
                }
                figure = ((Castling) turn).getRook();
                if (!figures.contains(figure)) {
                    figures.add(figure);
                }
            }
        }
        return figures;
    }

    static List<Turn> getTurnsForFigure(Figure figure, List<Turn> turns) {
        List<Turn> turnsForFigure = new LinkedList<>();
        for (Turn turn : turns) {
            if ((turn instanceof Promotion
                    && ((Promotion) turn).getPawn() == figure)
                    || (turn instanceof Move
                    && ((Move) turn).getFigure() == figure)
                    || (turn instanceof Castling
                    && ((((Castling) turn).getKing() == figure)
                    || ((Castling) turn).getRook() == figure))) {
                turnsForFigure.add(turn);
            }
        }
        return turnsForFigure;
    }

    Turn pickMove(Map<Field, Figure> field, List<Turn> turns);

    void hasWon();

    void hasLost();

    void isStalemate();

    void isDraw();
}
