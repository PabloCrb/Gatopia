package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class LoadingScreen extends JFrame {
    public static ImageIcon loading_gif;
    private final LoadingScreen loadingScreenRef;

    static {
        loading_gif = new ImageIcon("GIFS\\PORTADA.gif");
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
        setSize(675, 710);
        setLocationRelativeTo(null);
        setContentPane(new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                loading_gif.paintIcon(this, g, 0, 0);
            }
        });
        setVisible(true);
    }

    public static void main(String[] args) {
        new LoadingScreen();
    }
}
