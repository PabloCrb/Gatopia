package Squares;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static GUI.BoardSizeManager.nRows;
import static GUI.BoardSizeManager.squareSize;
import static Turns.TurnManager.currentTurn;
import static java.awt.Image.SCALE_SMOOTH;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
import static java.lang.Integer.parseInt;

public class Square extends JPanel {

    private static BufferedImage catSkinJ1;
    private static BufferedImage catSkinJ2;
    private static BufferedImage palomaSkinJ1;
    private static BufferedImage palomaSkinJ2;
    private static BufferedImage pajareraSkinJ1;
    private static BufferedImage pajareraSkinJ2;

    public final String id;
    public int squareType;

    public Square(String id) {
        this.id = id;
    }

    public static void setCatSkinJ1(BufferedImage s) {
        catSkinJ1 = scaleImage(s);
    }

    public static void setCatSkinJ2(BufferedImage s) {
        catSkinJ2 = scaleImage(s);
    }

    public static void setPalomaSkinJ1(BufferedImage s) {
        palomaSkinJ1 = scaleImage(s);
    }

    public static void setPalomaSkinJ2(BufferedImage s) {
        palomaSkinJ2 = scaleImage(s);
    }

    public static void setPajareraSkinJ1(BufferedImage s) {
        pajareraSkinJ1 = scaleImage(s);
    }

    public static void setPajareraSkinJ2(BufferedImage s) {
        pajareraSkinJ2 = scaleImage(s);
    }

    public void setSquareType(int squareType) {
        this.squareType = squareType;
    }

    public int getRow() {
        return parseInt(id.substring(0, 2));
    }

    public int getCol() {
        return parseInt(id.substring(2, 4));
    }

    public boolean isBlackCat() {
        return squareType == 3 || squareType == 9;
    }

    public boolean isWhiteCat() {
        return squareType == 4 || squareType == 10;
    }

    public boolean isEmptySquare() {
        return squareType == 1 || squareType == 2;
    }

    public boolean isCat() {
        return isBlackCat() || isWhiteCat();
    }

    public boolean isPaloma() {
        return squareType == 5 || squareType == 6;
    }

    public boolean isEnemyPaloma() {
        return ((currentTurn == 1 && isWhitePalomaQuieta()) || (currentTurn == 2 && isBlackPalomaQuieta()));
    }

    public boolean isFriendlyPaloma() {
        return ((currentTurn == 1 && isBlackPalomaQuieta()) || (currentTurn == 2 && isWhitePalomaQuieta()));
    }

    public boolean isBlackTeam() {
        return getRow() <= nRows / 2;
    }

    public boolean isBlackPalomaQuieta() {
        return squareType == 5;
    }

    public boolean isWhitePalomaQuieta() {
        return squareType == 6;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        float alpha;
        switch (squareType) {
            case 1:
                alpha = 0.7f;
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
                g2d.setColor(Color.GRAY);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                break;
            case 2:
                alpha = 0.7f;
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
                g2d.setColor(Color.pink);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                break;
            case 3:
                g.drawImage(catSkinJ1, 0, 0, this);
                break;

            case 4:
                g.drawImage(catSkinJ2, 0, 0, this);
                break;
            case 5:
                g.drawImage(palomaSkinJ1, 0, 0, this);
                break;
            case 6:
                g.drawImage(palomaSkinJ2, 0, 0, this);
                break;
            case 7:
                g.drawImage(pajareraSkinJ1, 0, 0, this);
                break;
            case 8:
                g.drawImage(pajareraSkinJ2, 0, 0, this);
                break;
        }
    }

        private static BufferedImage scaleImage (BufferedImage originalImage){
            Image scaledImage = originalImage.getScaledInstance(squareSize, squareSize, SCALE_SMOOTH);
            BufferedImage bufferedImage = new BufferedImage(squareSize, squareSize, TYPE_INT_ARGB);
            Graphics2D g2d = bufferedImage.createGraphics();
            g2d.drawImage(scaledImage, 0, 0, null);
            g2d.dispose();
            return bufferedImage;
        }

        @Override
        public String toString () {
            return "ID: " + id;
        }
    }