import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.*;

import static java.awt.GraphicsDevice.WindowTranslucency.*;

public class Widget extends JFrame {
    public String imagePath;
    private int xDrag;
    private int yDrag;
    private int xPress;
    private int yPress;

    public Widget(String imagePath, int imageSize, boolean borderless, boolean alwaysOnTop, boolean clickThrough) {
        super("Transparent GIF Widget Instance");

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
        setAlwaysOnTop(alwaysOnTop);

        // setContentPane(panel);
        add(imageLabel, gbc);

        addMouseMotionListener((MouseMotionListener) new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                xDrag = e.getX();
                yDrag = e.getY();

                JFrame sFrame = (JFrame) e.getSource();
                sFrame.setLocation(sFrame.getLocation().x + xDrag - xPress,
                        sFrame.getLocation().y + yDrag - yPress);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                xPress = e.getX();
                yPress = e.getY();
            }

        });
    }

    public static void dialogBox(String message, String titleBar, int width, int type) {
        JFrame jf = new JFrame();
        jf.setAlwaysOnTop(true);
        JOptionPane.showMessageDialog(jf,
                "<html><body><p style='width: " + width + "px;'>" + message + "</p></body></html>", titleBar,
                type);
    }

    public static void run(Widget w) {
        // Determine what the GraphicsDevice can support.
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        final boolean isTranslucencySupported = gd.isWindowTranslucencySupported(TRANSLUCENT);

        // dialogBox("To move the GIF, select \"Move\" from the Alt + Space menu, then use the arrow keys and mouse.",
        //         "GIF Widget 0.1", 420, JOptionPane.INFORMATION_MESSAGE);

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
                w.setVisible(true);
            }
        });
    }

    public void stop() {
        this.dispose();
    }

}