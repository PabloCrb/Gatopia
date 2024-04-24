package GUI;

import Squares.SkinLoader;
import Squares.Square;
import Turns.TurnManager;
import Turns.AlertManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.Random;

import static GUI.BoardSizeManager.*;
import static Turns.AlertManager.alert;
import static Turns.TurnManager.*;
import static java.lang.String.format;

public class MainFrame extends JFrame {
    public TurnManager turnManager;
    public static Square blackCatSquare;
    public static Square whiteCatSquare;

    public MainFrame(TurnManager turnManager, SkinLoader sl) throws HeadlessException {

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);

                Random random = new Random();
                if (e.getKeyChar() == 'r' && currentTurn == -1) {
                    for (int i=0; i < 5; i++) {
                        int random_index = random.nextInt(3*nRows);
                        while (!turnManager.squares.get(random_index).isEmptySquare()) random_index = random.nextInt(2*nRows);
                        Square s = turnManager.squares.get(random_index);
                        s.setSquareType(5);
                        s.repaint();
                        turnManager.nPalomasColocadasJ1++;
                        if (turnManager.nPalomasColocadasJ1 == 5) break;
                    }
                    currentTurn = -2;
                    alert();
                } else if (e.getKeyChar() == 'r' && currentTurn == -2) {
                    for (int i=0; i< 5; i++) {
                        int random_index = 80 + random.nextInt(20);
                        while (!turnManager.squares.get(random_index).isEmptySquare()) random_index = random.nextInt(70 + random.nextInt(30));
                        Square s = turnManager.squares.get(random_index);
                        s.setSquareType(6);
                        s.repaint();
                        turnManager.nPalomasColocadasJ2++;
                        if (turnManager.nPalomasColocadasJ2 == 5) break;
                    }
                    currentTurn = 1;
                    alert();
                }
            }
        });

        this.turnManager = turnManager;


        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Gatos y palomas");
        setSize(totalWidth(), totalHeight());
        setLocationRelativeTo(null);
        setLayout(null);
        addSquares();
        setVisible(true);

        try {
            SkinLoader.getConnection().close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        sl.dispose();
        alert();
    }

    private void addSquares() {
        for (int i=0; i<nRows; i++) {
            for (int j=0; j<nCols; j++) {
                String row = format("%02d", i + 1);
                String col = format("%02d", j + 1);
                Square square = new Square(row + col);

                if (i<nRows/2) square.setSquareType(1);
                else square.setSquareType(2);
                int x = j * (squareSize + marginInline) + marginLeft;
                int y = i * (squareSize + marginInline) + marginTop;
                square.setBounds(x, y, squareSize, squareSize);
                add(square);
                turnManager.addSquare(square);
                turnManager.addClickListener(square);
            }
        }
        addCatSquares();
    }

    private void addCatSquares() {
        String id_bc = "0205";
        String id_wc = "0905";

        turnManager.changeSquareType(id_bc, 3);
        turnManager.changeSquareType(id_wc, 4);
        blackCatSquare = turnManager.squares.get(turnManager.getIndex(id_bc));
        whiteCatSquare = turnManager.squares.get(turnManager.getIndex(id_wc));
    }

}
