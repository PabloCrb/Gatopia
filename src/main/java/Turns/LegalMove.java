package Turns;

import Squares.Square;

import static GUI.MainFrame.blackCatSquare;
import static GUI.MainFrame.whiteCatSquare;
import static Turns.AlertManager.alert;
import static Turns.TurnManager.currentTurn;

import static java.lang.Math.abs;

public class LegalMove {

    public static boolean isLegal(Square square) {
        return switch (currentTurn) {
            case -1 -> {
                if (square.getRow() > (blackCatSquare.getRow() + 1)) {
                    alert(-3);
                    yield false;
                }
                if (square.squareType > 2) {
                    alert(-4);
                    yield false;
                }
                yield true;
            }
            case -2 -> {
                if (square.getRow() < (whiteCatSquare.getRow() - 1)) {
                    alert(-3);
                    yield false;
                }
                if (square.squareType > 2) {
                    alert(-4);
                    yield false;
                }
                yield true;
            }
            default -> true;
        };
    }

    public static boolean legalMoveGato(Square origin, Square destiny) {
        if (abs(origin.getRow() - destiny.getRow()) <= 2 && abs(origin.getCol() - destiny.getCol()) <= 2) {
            if (destiny.isFriendlyPaloma()) {alert(3); return false;}
            if (destiny.isEmptySquare()) return true;
            if (destiny.isEnemyPaloma()) {System.out.println("Me la como altoke"); return true;}
        }
        alert(0);
        return false;
    }

    public static boolean legalMovePaloma(Square origin, Square destiny) {
        if (abs(origin.getRow() - destiny.getRow()) <= 2 && abs(origin.getCol() - destiny.getCol()) <= 2 //Maximo movimiento
        ) {
            return true;
        }
        alert(0);
        return false;
    }
}
