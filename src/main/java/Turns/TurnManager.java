package Turns;

import Squares.Square;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import static GUI.BoardSizeManager.nRows;
import static Turns.AlertManager.*;
import static Turns.LegalMove.*;
import static java.lang.Integer.parseInt;

public class TurnManager {
    public List<Square> squares;
    public Square origin;

    public static int currentTurn = -1;

    public int nPalomas = 5;
    public int nPalomasColocadasJ1 = 0;
    public int nPalomasColocadasJ2 = 0;
    public List<Square> palomasMovidasEsteTurnoJ1;
    public List<Square> palomasMovidasEsteTurnoJ2;

    public TurnManager() {
        squares = new ArrayList<>();
        palomasMovidasEsteTurnoJ1 = new ArrayList<>();
        palomasMovidasEsteTurnoJ2 = new ArrayList<>();
    }


    public void addSquare(Square square) {
        squares.add(square);
    }

    public void changeSquareType(String id, int squareType) {
        int index  = getIndex(id);
        squares.get(index).setSquareType(squareType);
    }

    public int getIndex(String id) {
        int row = parseInt(id.substring(0, 2)) - 1;
        int col = parseInt(id.substring(2, 4)) - 1;
        return (row)* nRows + col;
    }

    public void addClickListener(Square square) {
        square.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                switch (currentTurn) {
                    //Colocar palomas negras
                    case -1:
                        if (isLegal(square)) {
                            changeSquareType(square.id, 5);
                            square.repaint();
                            nPalomasColocadasJ1++;
                            System.out.println(nPalomasColocadasJ1);
                            if (nPalomasColocadasJ1 == nPalomas) {
                                setCurrentTurn(-2);
                                AlertManager.alert();
                            }
                        }
                        break;
                    //Colocar palomas blancas
                    case -2:
                        if (isLegal(square)) {
                            changeSquareType(square.id, 6);
                            square.repaint();
                            nPalomasColocadasJ2++;
                            if (nPalomasColocadasJ2 == nPalomas) {
                                setCurrentTurn(1);
                                AlertManager.alert();
                            }
                        }
                        break;
                    //Mover gato negro
                    case 1:
                        if (origin == null && square.isBlackCat()) {
                            changeBorder(square);
                            origin = square;

                        } else if (origin != null && !square.isBlackCat()) {
                            if (legalMoveGato(origin, square)) {
                                if (origin.isBlackTeam()) {origin.setSquareType(1);}
                                else origin.setSquareType(2);
                                origin.setBorder(null);
                                origin.repaint();

                                square.setSquareType(3);
                                square.repaint();

                                origin = null;
                                currentTurn += 2;
                            }
                        }
                        break;
                    //Mover gato blanco
                    case 2:
                        if (origin == null && square.isWhiteCat()) {
                            changeBorder(square);
                            origin = square;

                        } else if (origin != null && !square.isWhiteCat()) {
                            if (legalMoveGato(origin, square)) {
                                if (origin.isBlackTeam()) {origin.setSquareType(1);}
                                else origin.setSquareType(2);
                                origin.setBorder(null);
                                origin.repaint();

                                square.setSquareType(4);
                                square.repaint();

                                origin = null;
                                currentTurn += 2;
                            }
                        }
                        break;
                    //Mover palomas negras
                    case 3:
                        if (origin == null && square.isBlackPalomaQuieta()) {
                            changeBorder(square);
                            origin = square;
                        } else if (origin != null && square.squareType <= 2) {
                            if (legalMovePaloma(origin, square)) {
                                square.setSquareType(origin.squareType+2);
                                if (origin.isBlackTeam()) origin.setSquareType(1);
                                else origin.setSquareType(2);
                                origin.setBorder(null);
                                origin.repaint();
                                origin = null;
                                square.repaint();
                                palomasMovidasEsteTurnoJ1.add(square);
                                if (palomasMovidasEsteTurnoJ1.size() == 5) {currentTurn = 2; alert(); resetPalomas(1);}
                            }
                        }
                        break;
                    //Mover palomas blancas
                    case 4:
                        if (origin == null && square.isWhitePalomaQuieta()) {
                            changeBorder(square);
                            origin = square;
                        } else if (origin != null && square.squareType <= 2) {
                            if (legalMovePaloma(origin, square)) {
                                square.setSquareType(origin.squareType+2);
                                if (origin.isBlackTeam()) origin.setSquareType(1);
                                else origin.setSquareType(2);
                                origin.setBorder(null);
                                origin.repaint();
                                origin = null;
                                square.repaint();
                                palomasMovidasEsteTurnoJ2.add(square);
                                if (palomasMovidasEsteTurnoJ2.size() == 5) {currentTurn = 1; alert(); resetPalomas(2);}
                            }
                        }
                        break;
                }
            }
        });
    }

    private void resetPalomas(int player) {
        List<Square> palomasMovidasEsteTurno;
        if (player == 1) palomasMovidasEsteTurno = palomasMovidasEsteTurnoJ1; else palomasMovidasEsteTurno = palomasMovidasEsteTurnoJ2;
        for (Square s : palomasMovidasEsteTurno) {
            s.setSquareType(s.squareType - 2);
            s.repaint();
        }
        palomasMovidasEsteTurno.clear();
    }

    private void changeBorder(Square square) {
        if (origin != null) {
            origin.setBorder(null);
        }
        if (square.isCat()) square.setSquareType(square.squareType + 6);
        square.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.YELLOW));
    }
}