
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class Main extends JFrame {

	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("GIF Widgets 0.1 - Control Panel");

		GUI gui = new GUI(shell, SWT.NONE);
		gui.pack();

		shell.addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent e) {
				int quit = JOptionPane.showConfirmDialog(null, "<html><body><p style='width:150px;'>Close all widgets and quit?</p></body></html>", "GIF Widget 0.1", JOptionPane.YES_NO_OPTION);
				switch(quit) {
					case JOptionPane.YES_OPTION:
					    gui.dispose();
                        System.exit(0);
                        break;
                    case JOptionPane.NO_OPTION:
                        e.doit = false;
                        break;
                    default:
                        break;
				}
			}
		});

		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}

}
