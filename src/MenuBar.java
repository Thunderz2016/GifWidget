import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

public class MenuBar extends Shell {
	private FileManager fileManager;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	// public static void main(String args[]) {
	// try {
	// Display display = Display.getDefault();
	// MenuBar shell = new MenuBar(display);
	// shell.open();
	// shell.layout();
	// while (!shell.isDisposed()) {
	// if (!display.readAndDispatch()) {
	// display.sleep();
	// }
	// }
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }

	/**
	 * Create the shell.
	 * 
	 * @param display
	 */
	public MenuBar(Display display, Shell shell, GUI gui) {
		super(display, SWT.NONE);
		fileManager = new FileManager();

		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);

		MenuItem mntmFile = new MenuItem(menu, SWT.CASCADE);
		mntmFile.setText("File");

		Menu menu_1 = new Menu(mntmFile);
		mntmFile.setMenu(menu_1);

		MenuItem mntmImportConfig = new MenuItem(menu_1, SWT.NONE);
		mntmImportConfig.setText("Import Config...");
		mntmImportConfig.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog dialog = new FileDialog(shell);
				dialog.setText("Select a backup config.dat file...");
				dialog.setFilterExtensions(new String[] { "*.dat", "*.*" });
				dialog.setFilterNames(new String[] { "DAT file", "All files" });
				String path = dialog.open();
				Config importedConfig;
				if (path != null) {
					importedConfig = fileManager.loadConfig(path);
					if (importedConfig != null) {
						gui.setConfig(importedConfig);
					}
					Widget.dialogBox("Import complete. Please restart GIF Widget to apply new config.", "Import Config",
							300, JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		MenuItem mntmExportConfig = new MenuItem(menu_1, SWT.NONE);
		mntmExportConfig.setText("Export Config...");
		mntmExportConfig.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog dialog = new FileDialog(shell, SWT.SAVE);
				dialog.setText("Select a location to save config file...");
				dialog.setFilterExtensions(new String[] { "*.dat", "*.*" });
				dialog.setFilterNames(new String[] { "DAT files", "All files" });
				String path = dialog.open();
				if (path != null) {
					fileManager.saveConfig(gui.getConfig(), path);
				}
			}
		});

		new MenuItem(menu_1, SWT.SEPARATOR);

		MenuItem mntmRemoveLastFile = new MenuItem(menu_1, SWT.NONE);
		mntmRemoveLastFile.setText("Edit File Path History...");
		mntmRemoveLastFile.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					gui.saveHistory();
					Widget.dialogBox(
							"You are about to open \"history.dat,\" if your OS prompts you to select a program to open it with, please select a text editor.",
							"Edit File Path History", 300, JOptionPane.INFORMATION_MESSAGE);
					Desktop.getDesktop().open(new File("history.dat"));
				} catch (IOException e1) {
					Widget.dialogBox("\"history.dat\" is not accessible.", "Error", 200, JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}

			}
		});
		;

		MenuItem mntmReloadFilePath = new MenuItem(menu_1, SWT.NONE);
		mntmReloadFilePath.setText("Reload File Path History");
		mntmReloadFilePath.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				gui.loadHistory();
			}
		});

		MenuItem mntmClearFilePath = new MenuItem(menu_1, SWT.NONE);
		mntmClearFilePath.setText("Clear File Path History");
		mntmClearFilePath.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				JFrame jf = new JFrame();
				int quit = JOptionPane.showConfirmDialog(jf,
						"<html><body><p style='width:150px;'>Clear all file path history?</p></body></html>",
						"GIF Widget 0.1", JOptionPane.YES_NO_OPTION);

				switch (quit) {
					case JOptionPane.YES_OPTION:
						gui.clearHistory();
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

		new MenuItem(menu_1, SWT.SEPARATOR);

		MenuItem mntmExit = new MenuItem(menu_1, SWT.NONE);
		mntmExit.setText("Exit");
		mntmExit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});

		MenuItem mntmHelp = new MenuItem(menu, SWT.CASCADE);
		mntmHelp.setText("Help");

		Menu menu_2 = new Menu(mntmHelp);
		mntmHelp.setMenu(menu_2);

		MenuItem mntmGithub = new MenuItem(menu_2, SWT.NONE);
		mntmGithub.setText("GitHub Repository");

		MenuItem mntmAbout = new MenuItem(menu_2, SWT.NONE);
		mntmAbout.setText("About");

		// createContents();

	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("SWT Application");
		setSize(450, 300);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
