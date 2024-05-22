package GUI;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public class SkinLoader extends JFrame {
    private static Connection connection;
    private final List<ImageIcon> images;
    private JLabel imageLabel;
    private int currentIndex;

    public SkinLoader(LoadingScreen ls) throws HeadlessException {
        this.setSize(675, 710);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("SELECT YOUR SKINS");
        this.setLayout(new BorderLayout());

        images = loadImagesFromFolder("SkinSelectorImages");
        currentIndex = 0;

        addLayout();
        this.setVisible(true);
        ls.dispose();
    }

    private void addLayout() {
        JPanel buttonPanel = new JPanel(new BorderLayout());

        JButton leftButton = new JButton("<");
        leftButton.addActionListener(e -> showPreviousImage());
        buttonPanel.add(leftButton, BorderLayout.WEST);

        JButton rightButton = new JButton(">");
        rightButton.addActionListener(e -> showNextImage());
        buttonPanel.add(rightButton, BorderLayout.EAST);

        JPanel southPanel = new JPanel(new BorderLayout());
        JButton chooseButton = new JButton("Elegir");
        chooseButton.addActionListener(e -> chooseImage());
        southPanel.add(chooseButton, BorderLayout.CENTER);

        JPanel bottomMargin = new JPanel();
        southPanel.add(bottomMargin, BorderLayout.SOUTH);

        this.add(buttonPanel, BorderLayout.SOUTH);
        this.add(southPanel, BorderLayout.SOUTH);

        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        if (!images.isEmpty()) {
            imageLabel.setIcon(images.get(currentIndex));
        }

        this.add(imageLabel, BorderLayout.CENTER);
    }

    private List<ImageIcon> loadImagesFromFolder(String folderPath) {
        List<ImageIcon> imageList = new ArrayList<>();
        try {
            Files.walk(Paths.get(folderPath))
                    .filter(Files::isRegularFile)
                    .forEach(path -> {
                        try {
                            Image image = ImageIO.read(path.toFile());
                            if (image != null) {
                                Image scaledImage = image.getScaledInstance(500, 500, Image.SCALE_SMOOTH);
                                imageList.add(new ImageIcon(scaledImage));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageList;
    }

    private void showPreviousImage() {
        if (!images.isEmpty() && currentIndex > 0) {
            currentIndex--;
            imageLabel.setIcon(images.get(currentIndex));
        }
    }

    private void showNextImage() {
        if (!images.isEmpty() && currentIndex < images.size() - 1) {
            currentIndex++;
            imageLabel.setIcon(images.get(currentIndex));
        }
    }

    private void chooseImage() {
        if (!images.isEmpty()) {
            ImageIcon selectedImage = images.get(currentIndex);
            JOptionPane.showMessageDialog(this, "Has elegido una imagen.");
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
