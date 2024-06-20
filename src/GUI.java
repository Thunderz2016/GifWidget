import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Button;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.io.FilenameFilter;
import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.custom.CLabel;

public class GUI extends Composite {
	private Widget w;
	private String imagePath;
	private Config config;
	private Combo combo;
	private FileManager fileManager;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public GUI(Composite parent, int style) {
		super(parent, style);
		combo = new Combo(this, SWT.NONE);
		fileManager = new FileManager();

		config = fileManager.loadConfig();
		imagePath = config.getImagePath();
		fileManager.loadHistory(combo);

		Button btnNewButton_1 = new Button(this, SWT.NONE);
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					if (w != null)
						w.stop();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		btnNewButton_1.setText("Close Widgets");
		btnNewButton_1.setBounds(222, 152, 190, 111);

		Label lblGifFilePath = new Label(this, SWT.NONE);
		lblGifFilePath.setBounds(10, 10, 222, 19);
		lblGifFilePath.setText("GIF File Path: (Press Enter to apply)");

		// text = new Text(this, SWT.BORDER);
		// text.setBounds(10, 40, 402, 25);
		// text.setFocus();
		// text.addKeyListener(new KeyListener() {
		// @Override
		// public void keyPressed(org.eclipse.swt.events.KeyEvent arg0) {
		// if(arg0.keyCode == SWT.CR || arg0.keyCode == SWT.KEYPAD_CR){
		// imagePath = text.getText();
		// }
		// }

		// @Override
		// public void keyReleased(org.eclipse.swt.events.KeyEvent arg0) {

		// }
		// });

		combo.setText(config.getImagePath());
		combo.setBounds(10, 38, 394, 27);
		combo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				imagePath = combo.getText();
			}
		});
		combo.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(org.eclipse.swt.events.KeyEvent arg0) {
				if (arg0.keyCode == SWT.CR || arg0.keyCode == SWT.KEYPAD_CR) {
					String fieldText = combo.getText();
					imagePath = fieldText;
					config.setImagePath(fieldText);
					// if(combo.indexOf(fieldText) == -1){
					// combo.add(fieldText);
					// }
				}
			}

			@Override
			public void keyReleased(org.eclipse.swt.events.KeyEvent arg0) {

			}
		});

		Button btnBrowse = new Button(this, SWT.NONE);
		btnBrowse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog dialog = new FileDialog(new Frame(), "Select an image...", FileDialog.LOAD);
				dialog.setFilenameFilter(new FilenameFilter() {
					public boolean accept(File dir, String name) {
						return name.endsWith(".gif");
					}
				});
				dialog.setVisible(true);
				imagePath = dialog.getDirectory() + dialog.getFile();
				combo.setText(imagePath);
			}
		});
		btnBrowse.setBounds(10, 71, 85, 29);
		btnBrowse.setText("Browse...");

		Button btnBorderless = new Button(this, SWT.CHECK);
		btnBorderless.setSelection(config.isBorderless());
		btnBorderless.setBounds(20, 109, 104, 19);
		btnBorderless.setText("Borderless");
		btnBorderless.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				config.setBorderless(btnBorderless.getSelection());
				if (w != null) {

				}

			}
		});

		Button btnAlwaysOnTop = new Button(this, SWT.CHECK);
		btnAlwaysOnTop.setText("Always On Top");
		btnAlwaysOnTop.setSelection(config.isAlwaysOnTop());
		btnAlwaysOnTop.setBounds(141, 109, 122, 19);
		btnAlwaysOnTop.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				config.setAlwaysOnTop(btnAlwaysOnTop.getSelection());
				if (w != null)
					w.setAlwaysOnTop(btnAlwaysOnTop.getSelection());
			}
		});

		Button btnClickThrough = new Button(this, SWT.CHECK);
		btnClickThrough.setEnabled(false);
		btnClickThrough.setSelection(config.isClickThrough());
		btnClickThrough.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				config.setClickThrough(btnClickThrough.getSelection());
			}
		});
		btnClickThrough.setText("Click Through");
		btnClickThrough.setBounds(289, 109, 122, 19);

		Spinner spinner = new Spinner(this, SWT.BORDER);
		spinner.setEnabled(false);
		spinner.setSelection(100);
		spinner.setBounds(241, 73, 50, 25);

		Label lblImageSize = new Label(this, SWT.NONE);
		lblImageSize.setBounds(141, 76, 94, 19);
		lblImageSize.setText("Image Size (%)");

		Button btnNewButton = new Button(this, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					if (imagePath == null || imagePath.isEmpty()) {
						Widget.dialogBox("Path is empty. Please specify a GIF File Path.", "Error", 200,
								JOptionPane.ERROR_MESSAGE);
					} else if (!new File(imagePath).exists()) {
						Widget.dialogBox("\"" + imagePath + "\" does not exist.", "Error", 170,
								JOptionPane.ERROR_MESSAGE);
					} else {
						w = new Widget(imagePath, 100, btnBorderless.getSelection(), btnAlwaysOnTop.getSelection(),
								btnClickThrough.getSelection());
						Widget.run(w);
						config.setImagePath(imagePath);
						if (combo.indexOf(combo.getText()) == -1)
							combo.add(imagePath);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(10, 152, 190, 111);
		btnNewButton.setText("Start Widget");

		CLabel lblNewLabel = new CLabel(this, SWT.NONE);
		lblNewLabel.setBounds(267, 7, 69, 25);
		lblNewLabel.setText("New Label");

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public void createMenuBar(Shell shell) {
		Menu menuBar = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menuBar);

		MenuItem fileMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		fileMenuHeader.setText("&File");

		Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
		fileMenuHeader.setMenu(fileMenu);

		MenuItem fileExitItem = new MenuItem(fileMenu, SWT.PUSH);
		fileExitItem.setText("&Exit");
		fileExitItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});

		MenuItem helpMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		helpMenuHeader.setText("&Help");

		Menu helpMenu = new Menu(shell, SWT.DROP_DOWN);
		helpMenuHeader.setMenu(helpMenu);

		MenuItem helpAboutItem = new MenuItem(helpMenu, SWT.PUSH);
		helpAboutItem.setText("&About");
	}

	public void saveConfig() {
        fileManager.saveConfig(config);
	}

	public void saveHistory() {
		fileManager.saveHistory(combo);
	}

	
}
