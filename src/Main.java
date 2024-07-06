
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Main extends org.eclipse.swt.widgets.Shell  {
	public Main() {
	}

	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(SWT.CLOSE | SWT.TITLE | SWT.MIN);
		shell.setText(String.format("GIF Widget %s", Widget.VERSION));

		GUI gui = new GUI(shell, SWT.NONE);
		new MenuBar(display, shell, gui);

		// gui.createMenuBar(shell);
		gui.pack();

		shell.addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent e) {
				JFrame jf = new JFrame();
				jf.setAlwaysOnTop(true);
				int quit = JOptionPane.showConfirmDialog(jf,
						"<html><body><p style='width:150px;'>Close all widgets and quit?</p></body></html>",
						String.format("GIF Widget %s", Widget.VERSION), JOptionPane.YES_NO_OPTION);

				switch (quit) {
					case JOptionPane.YES_OPTION:
						gui.saveConfig();
						gui.saveHistory();
						gui.dispose();
						System.exit(0);
						break;
					case JOptionPane.NO_OPTION:
						e.doit = false;
						break;
					case JOptionPane.CLOSED_OPTION:
						e.doit = false;
						break;
					default:
						break;
				}
			}
		});

		shell.setSize(420, 335);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}

}
