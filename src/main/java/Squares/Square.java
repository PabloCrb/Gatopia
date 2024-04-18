package Squares;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static GUI.BoardSizeManager.nRows;
import static GUI.BoardSizeManager.squareSize;
import static Turns.TurnManager.currentTurn;
import static java.awt.Image.SCALE_SMOOTH;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
import static java.lang.Integer.parseInt;

public class Square extends JPanel {

    public static BufferedImage black_cat;
    public static BufferedImage white_cat;

    public static BufferedImage paloma_negra_quieta;
    public static BufferedImage paloma_negra_movida;
    public static BufferedImage paloma_blanca_quieta;
    public static BufferedImage paloma_blanca_movida;

    public static ImageIcon gif_gn;
    public static ImageIcon gif_gb;

    static {
        try {
            black_cat = scaleImage(ImageIO.read(new File("Images\\black_cat.png")));
            white_cat = scaleImage(ImageIO.read(new File("Images\\white_cat.png")));

            paloma_negra_quieta = scaleImage(ImageIO.read(new File("Images\\black_paloma_default_quieta.png")));
            paloma_blanca_quieta = scaleImage(ImageIO.read(new File("Images\\white_paloma_default_quieta.png")));

            paloma_negra_movida = scaleImage(ImageIO.read(new File("Images\\black_paloma_default_movida.png")));
            paloma_blanca_movida = scaleImage(ImageIO.read(new File("Images\\white_paloma_default_movida.png")));

            gif_gn = new ImageIcon("Images\\black_cat_gif.gif");
            gif_gn.setImage(gif_gn.getImage().getScaledInstance(squareSize, squareSize, Image.SCALE_DEFAULT));

            gif_gb = new ImageIcon("Images\\white_cat_gif.gif");
            gif_gb.setImage(gif_gb.getImage().getScaledInstance(squareSize, squareSize, Image.SCALE_DEFAULT));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public final String id;
    public int squareType;

    //1 -> EmptySquare blue team
    //2 -> EmptySquare red team
    //3 -> Minino negro
    //4 -> Minino blanco
    //5 -> Paloma negra quieta
    //6 -> Paloma blanca quieta
    //7 -> Paloma negra movida
    //8 -> Paloma blanca movida

    //9 -> GIF gato negro
    //10 -> GIF gato blanco

    public Square(String id) {
        this.id = id;
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
        return  ( (currentTurn == 1 && isWhitePalomaQuieta()) ||  (currentTurn == 2 && isBlackPalomaQuieta()) );
    }

    public boolean isFriendlyPaloma() {
        return  ( (currentTurn == 1 && isBlackPalomaQuieta()) ||  (currentTurn == 2 && isWhitePalomaQuieta()) );
    }

    public boolean isBlackTeam() {
        return getRow() <= nRows/2;
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
                g.drawImage(black_cat, 0, 0, this);
                break;
            case 4:
                g.drawImage(white_cat, 0, 0, this);
                break;
            case 5:
                g.drawImage(paloma_negra_quieta, 0, 0, this);
                break;
            case 6:
                g.drawImage(paloma_blanca_quieta, 0, 0, this);
                break;
            case 7:
                g.drawImage(paloma_negra_movida, 0, 0, this);
                break;
            case 8:
                g.drawImage(paloma_blanca_movida, 0, 0, this);
                break;
            case 9:
                gif_gn.paintIcon(this, g, 0, 0);
                break;
            case 10:
                gif_gb.paintIcon(this, g, 0, 0);
                break;
        }
    }

    private static BufferedImage scaleImage(BufferedImage originalImage) {
        Image scaledImage = originalImage.getScaledInstance(squareSize, squareSize, SCALE_SMOOTH);
        BufferedImage bufferedImage = new BufferedImage(squareSize, squareSize, TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(scaledImage, 0, 0, null);
        g2d.dispose();
        return bufferedImage;
    }

    @Override
    public String toString() {
        return "ID: " + id;
    }
}
