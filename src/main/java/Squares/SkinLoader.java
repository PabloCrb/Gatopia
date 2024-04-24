package Squares;

import GUI.LoadingScreen;
import GUI.MainFrame;
import Turns.TurnManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SkinLoader extends JFrame {
    private static Connection connection;
    public static String fontFilePath = "Fonts/TacOne-Regular.ttf";
    private final SkinLoader skinLoaderREF;
    public static Font customFont;
    private static boolean exitNext = false;
    private static final List<BufferedImage> PalomaSkins = new ArrayList<>();
    public BufferedImage currentImage;

    static {
        try {
            SkinLoader.connection = DriverManager.getConnection("jdbc:sqlite:db/skin_db.db");
            loadPalomaSkins("black");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void loadPalomaSkins(String team) {
        try {
            String s = "Paloma_" + team + "%";
            PreparedStatement selectStatement = connection.prepareStatement("SELECT Imagen FROM Skins WHERE Nombre LIKE '" + s + "'");
            ResultSet resultSet = selectStatement.executeQuery();

            while (resultSet.next()) {
                byte[] imageBytes = resultSet.getBytes("Imagen");
                ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
                BufferedImage image = ImageIO.read(inputStream);
                PalomaSkins.add(image);
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }


    private final String catSkin1Name = "Black_cat";
    private final String catSkin2Name = "White_cat";

    public SkinLoader(LoadingScreen loadingScreenRef) throws HeadlessException {
        this.skinLoaderREF = this;

        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File(fontFilePath)).deriveFont(50f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
        } catch (IOException | FontFormatException e) {
            throw new RuntimeException(e);
        }

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(675, 710);
        setLocationRelativeTo(null);
        addLayout();
        setVisible(true);
        loadingScreenRef.dispose();
    }

    public int index = 0;

    private void addLayout() {
        currentImage = PalomaSkins.get(index);
        Image scaledImage = currentImage.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel imageLabel = new JLabel(scaledIcon);
        JPanel panel = new JPanel(new BorderLayout());

        JButton prevButton = new JButton("<");
        JButton nextButton = new JButton(">");

        prevButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (index == 0) index = PalomaSkins.size() - 1;
                else index--;
                updateImage(panel, imageLabel);
            }
        });

        nextButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (index == PalomaSkins.size() - 1) index = 0;
                else index++;
                updateImage(panel, imageLabel);
            }
        });


        JButton chooseButton = getChooseButton();

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        chooseButton.setFont(customFont.deriveFont(30f));
        chooseButton.setPreferredSize(new Dimension(200, 100));
        buttonPanel.add(chooseButton);
        panel.add(prevButton, BorderLayout.WEST);
        panel.add(imageLabel, BorderLayout.CENTER);
        panel.add(nextButton, BorderLayout.EAST);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        getContentPane().add(panel);
    }

    private JButton getChooseButton()  {
        JButton chooseButton = new JButton("ELEGIR");
        chooseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (exitNext) {
                    Square.setPalomaSkinJ2(currentImage);
                    try {
                        getSkin(1, catSkin1Name);
                        getSkin(2, catSkin2Name);
                        new MainFrame(new TurnManager(), skinLoaderREF);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                else {
                    Square.setPalomaSkinJ1(currentImage);
                    exitNext = true;
                    PalomaSkins.clear();
                    index = 0;
                    loadPalomaSkins("white");
                    getContentPane().removeAll();
                    addLayout();
                    setVisible(true);
                }
            }
        });
        return chooseButton;
    }

    private void updateImage(JPanel panel, JLabel imageLabel) {
        currentImage = PalomaSkins.get(index);
        Image scaledImage = currentImage.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        imageLabel.setIcon(scaledIcon);
        panel.revalidate();
        panel.repaint();
    }

    public void getSkin(int player, String skinName) throws IOException {

        try {
            PreparedStatement selectStatement = connection.prepareStatement("SELECT Imagen FROM Skins WHERE Nombre=?");
            selectStatement.setString(1, skinName);
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next()) {
                byte[] imageBytes = resultSet.getBytes("Imagen");
                ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
                if (player == 1) Square.setCatSkinJ1(ImageIO.read(inputStream));
                else Square.setCatSkinJ2(ImageIO.read(inputStream));

            } else {
                System.out.println("No se encontró ninguna imagen");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
