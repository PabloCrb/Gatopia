package GUI;

public class BoardSizeManager {
    public static int marginTop = 10;
    public static int marginBottom = 50;
    public static int marginRight = 25;
    public static int marginLeft = 10;
    public static int marginInline = 2;
    public static int squareSize = 60;
    public static int nRows = 10;
    public static int nCols = 10;

    public static int totalWidth() {
        return marginLeft + (squareSize + marginInline)*nRows + marginRight;
    }

    public static int totalHeight() {
        return marginTop + (squareSize + marginInline)*nCols + marginBottom;
    }
}
