package GUI;

import javax.swing.*;
import java.awt.*;

public class SkinSelector extends JFrame {
    public LoadingScreen loadingScreen;

    public SkinSelector(LoadingScreen loadingScreenRef) throws HeadlessException {
        setSize(675, 710);
        setLocationRelativeTo(null);
        setVisible(true);
        loadingScreenRef.dispose();
    }
}
