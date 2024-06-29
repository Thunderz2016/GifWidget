import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Button;

import java.io.File;
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
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.FileDialog;

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
		fileManager = new FileManager();

		config = fileManager.loadConfig();
		imagePath = config.getImagePath();
		setLayout(new GridLayout(2, false));
		// setSize(410, 325);

		Group grpImageProperties = new Group(this, SWT.NONE);
		GridData gd_grpImageProperties = new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1);
		gd_grpImageProperties.heightHint = 96;
		grpImageProperties.setLayoutData(gd_grpImageProperties);
		grpImageProperties.setText("Image Properties");

		Label lblGifFilePath = new Label(grpImageProperties, SWT.NONE);
		lblGifFilePath.setBounds(10, 19, 222, 19);
		lblGifFilePath.setText("GIF File Path: (Press Enter to apply)");
		combo = new Combo(grpImageProperties, SWT.NONE);
		combo.setBounds(10, 46, 382, 27);
		fileManager.loadHistory(combo);

		combo.setText(config.getImagePath());

		Button btnBrowse = new Button(grpImageProperties, SWT.NONE);
		btnBrowse.setBounds(10, 79, 85, 29);
		btnBrowse.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog dialog = new FileDialog(parent.getShell());
				dialog.setText("Select an image...");
				dialog.setFilterExtensions(new String[] { "*.gif", "*.jpg", "*.png", "*.webp", "*.*" });
				dialog.setFilterNames(new String[] { "Grahics Interchange Format", "JPEG Image",
						"Portable Network Graphics", "WebP Image", "All files" });
				String path = dialog.open();
				if (path != null) {
					imagePath = path;
					combo.setText(imagePath);
				}
			}
		});
		btnBrowse.setText("Browse...");

		Label lblImageSize = new Label(grpImageProperties, SWT.NONE);
		lblImageSize.setBounds(140, 82, 94, 19);
		lblImageSize.setText("Image Size (%)");

		Spinner spinner = new Spinner(grpImageProperties, SWT.BORDER);
		spinner.setBounds(240, 79, 50, 25);
		spinner.setEnabled(false);
		spinner.setSelection(100);
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

		Group grpWidgetProperties = new Group(this, SWT.SHADOW_ETCHED_IN);
		grpWidgetProperties.setLayout(new GridLayout(3, false));
		GridData gd_grpWidgetProperties = new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1);
		gd_grpWidgetProperties.heightHint = 33;
		grpWidgetProperties.setLayoutData(gd_grpWidgetProperties);
		grpWidgetProperties.setText("Widget Properties");

		Button btnBorderless = new Button(grpWidgetProperties, SWT.CHECK);
		GridData gd_btnBorderless = new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1);
		gd_btnBorderless.widthHint = 100;
		btnBorderless.setLayoutData(gd_btnBorderless);
		btnBorderless.setSelection(config.isBorderless());
		btnBorderless.setEnabled(false);
		btnBorderless.setText("Borderless");

		Button btnAlwaysOnTop = new Button(grpWidgetProperties, SWT.CHECK);
		GridData gd_btnAlwaysOnTop = new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1);
		gd_btnAlwaysOnTop.widthHint = 137;
		btnAlwaysOnTop.setLayoutData(gd_btnAlwaysOnTop);
		btnAlwaysOnTop.setText("Always On Top");
		btnAlwaysOnTop.setSelection(config.isAlwaysOnTop());

		Button btnClickThrough = new Button(grpWidgetProperties, SWT.CHECK);
		GridData gd_btnClickThrough = new GridData(SWT.CENTER, SWT.CENTER, true, false, 1, 1);
		gd_btnClickThrough.widthHint = 130;
		btnClickThrough.setLayoutData(gd_btnClickThrough);
		btnClickThrough.setEnabled(false);
		btnClickThrough.setSelection(config.isClickThrough());
		btnClickThrough.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				config.setClickThrough(btnClickThrough.getSelection());
			}
		});
		btnClickThrough.setText("Click Through");
		btnAlwaysOnTop.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				config.setAlwaysOnTop(btnAlwaysOnTop.getSelection());
				if (w != null)
					w.setAlwaysOnTop(btnAlwaysOnTop.getSelection());
			}
		});
		btnBorderless.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				config.setBorderless(btnBorderless.getSelection());
				if (w != null) {

				}

			}
		});

		Button btnStartWidget = new Button(this, SWT.NONE);
		// gd_btnStartWidget.heightHint = 106;
		// gd_btnStartWidget.widthHint = 193;
		btnStartWidget.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		btnStartWidget.setText("Start Widget");
		Button btnCloseWidget = new Button(this, SWT.NONE);
		// gd_btnCloseWidget.heightHint = 106;
		// gd_btnCloseWidget.widthHint = 197;
		GridData gd_btnCloseWidget = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_btnCloseWidget.heightHint = 90;
		btnCloseWidget.setLayoutData(gd_btnCloseWidget);
		btnCloseWidget.setText("Close Widget");
		btnCloseWidget.setEnabled(false);

		btnStartWidget.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					if (imagePath == null || imagePath.isEmpty()) {
						Widget.dialogBox("Path is empty. Please specify a GIF File Path.", "Error", 200,
								JOptionPane.ERROR_MESSAGE);
					} else if (!new File(imagePath).exists() || new File(imagePath).isDirectory()) {
						Widget.dialogBox("\"" + imagePath + "\" is not a valid file.", "Error", 160,
								JOptionPane.ERROR_MESSAGE);
					} else {
						if (w == null) {
							w = new Widget(imagePath, 100, btnBorderless.getSelection(), btnAlwaysOnTop.getSelection(),
									btnClickThrough.getSelection());
							Widget.run(w);
							config.setImagePath(imagePath);
							if (combo.indexOf(combo.getText()) == -1)
								combo.add(imagePath);
							btnStartWidget.setEnabled(false); // disable "Start Widget" button
							btnCloseWidget.setEnabled(true); // enable "Close Widget" button
						}
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		btnCloseWidget.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (w != null) {
					w.stop();
					w = null;
					btnStartWidget.setEnabled(true); // enable "Start Widget" button
					btnCloseWidget.setEnabled(false); // disable "Close Widget" button
				}
			}
		});

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

	public void setConfig(Config config) {
		this.config = config;
	}

	public Config getConfig() {
		return config;
	}

	public void loadHistory() {
		combo.removeAll();
		fileManager.loadHistory(combo);
	}

	public void saveConfig() {
		fileManager.saveConfig(config);
	}

	public void saveHistory() {
		fileManager.saveHistory(combo);
	}

	public void clearHistory() {
		config.setImagePath("");
		combo.removeAll();
		fileManager.saveHistory(combo);
	}
}
