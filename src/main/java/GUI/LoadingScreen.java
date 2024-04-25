package GUI;
import Squares.SkinLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LoadingScreen extends JFrame {
    public static ImageIcon loading_gif;
    private final LoadingScreen loadingScreenRef;

    static {
        loading_gif = new ImageIcon("Images\\caza_de_palomas.gif");
        loading_gif.setImage(loading_gif.getImage().getScaledInstance(655, 680, Image.SCALE_DEFAULT));
    }

    public LoadingScreen() throws HeadlessException {
        loadingScreenRef = this;
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
                if (e.getKeyChar() == '\n' || e.getKeyChar() == ' ') {
                    new SkinLoader(loadingScreenRef);
                }
            }
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("GATOS Y PALOMAS");
        setContentPane(new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                // Pintar el rectángulo blanco
                g.setColor(Color.WHITE);
                g.fillRect(0, 0, getWidth(), getHeight());

                // Pintar el GIF
                loading_gif.paintIcon(this, g, 0, 0);
            }
        });
        setSize(675, 710);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new LoadingScreen();
    }
}
