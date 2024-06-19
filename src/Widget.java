import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.Ellipse2D;
import static java.awt.GraphicsDevice.WindowTranslucency.*;

public class Widget extends JFrame {
    public String imagePath;

    public Widget(String imagePath) {
        super("Borderless GIF Widget Instance");

        ImageIcon gif = new ImageIcon(imagePath);
        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(gif);
        imageLabel.setBounds(0, 0, gif.getIconHeight(), gif.getIconWidth());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;

        setSize(new Dimension(gif.getIconWidth(), gif.getIconHeight()));
        setLayout(new GridBagLayout());
        setUndecorated(true);
        setSize(gif.getIconWidth(), gif.getIconHeight());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setBackground(new Color(0, 0, 0, 0));
        setAlwaysOnTop(true);

        // setContentPane(panel);
        add(imageLabel, gbc);

    }

    public static void dialogBox(String message, String titleBar, int width, int type) {
        JOptionPane.showMessageDialog(null,
                "<html><body><p style='width: " + width + "px;'>" + message + "</p></body></html>", titleBar,
                type);
    }

    public static void run(Widget w) {
        // Determine what the GraphicsDevice can support.
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        final boolean isTranslucencySupported = gd.isWindowTranslucencySupported(TRANSLUCENT);

        dialogBox("To move the GIF, select \"Move\" from the Alt + Space menu, then use the arrow keys and mouse.",
                "GIF Widget 0.1", 420, JOptionPane.INFORMATION_MESSAGE);

        // If translucent windows aren't supported,
        // create an opaque window.
        if (!isTranslucencySupported) {
            dialogBox("Translucency is not supported, creating an opaque window", "GIF Widget 0.1", 300,
                    JOptionPane.INFORMATION_MESSAGE);
        }

        // Create the GUI on the event-dispatching thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Widget sw = new Widget(imagePath);

                // Set the window to 70% translucency, if supported.
                if (isTranslucencySupported) {
                    // sw.setOpacity(0.7f);
                }

                // Display the window.
                w.setVisible(true);
            }
        });
    }

    public void stop() {
        this.dispose();
    }

}