import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Button;

import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;

import org.eclipse.swt.widgets.Text;

public class GUI extends Composite {
	private Text text;
	private Widget w;
	private String imagePath;
	private Config config;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public GUI(Composite parent, int style) {
		super(parent, style);
		setLayout(null);
		config = loadConfig();
		imagePath = config.getImagePath();
		
		Button btnNewButton = new Button(this, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
                try {
					if(imagePath == null){
						Widget.dialogBox("Please specify GIF File Path first.", "Error", 170, JOptionPane.ERROR_MESSAGE);
					} else if (!new File(imagePath).exists()) 
						Widget.dialogBox("\"" + imagePath + "\" does not exist.", "Error", 170, JOptionPane.ERROR_MESSAGE);
					else {				
						w = new Widget(imagePath);
						Widget.run(w);
					}
                } catch (Exception e1) {
                    e1.printStackTrace();
                }   
			}
		});
		btnNewButton.setBounds(10, 152, 190, 111);
		btnNewButton.setText("Start Widget");
		
		Button btnNewButton_1 = new Button(this, SWT.NONE);
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					w.stop();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		btnNewButton_1.setText("Close Widgets");
		btnNewButton_1.setBounds(222, 152, 190, 111);
		
		Label lblGifFilePath = new Label(this, SWT.NONE);
		lblGifFilePath.setBounds(10, 10, 94, 19);
		lblGifFilePath.setText("GIF File Path:");
		
		text = new Text(this, SWT.BORDER);
		text.setBounds(10, 40, 402, 25);
		text.addKeyListener(new KeyAdapter() {
			public void keyReleased (KeyEvent ke) {
				if(ke.getKeyCode() == SWT.CR) {
					imagePath = text.getText();
				}
			}
		});

		if (imagePath != null) {
			text.setText(imagePath);
		} else {
			text.setText("");
		}
		
		Button btnBrowse = new Button(this, SWT.NONE);
		btnBrowse.addSelectionListener(new SelectionAdapter() {
			@Override
            public void widgetSelected(SelectionEvent e) {
                FileDialog dialog = new FileDialog(new Frame(), "Locate an image...", FileDialog.LOAD);
				dialog.setVisible(true);
				imagePath = dialog.getDirectory() + dialog.getFile();
				text.setText(imagePath);
            }
		});
		btnBrowse.setBounds(10, 71, 85, 29);
		btnBrowse.setText("Browse...");
		
		Button btnBorderless = new Button(this, SWT.CHECK);
		btnBorderless.setSelection(true);
		btnBorderless.setEnabled(false);
		btnBorderless.setBounds(20, 109, 104, 19);
		btnBorderless.setText("Borderless");
		
		Button btnAlwaysOnTop = new Button(this, SWT.CHECK);
		btnAlwaysOnTop.setText("Always On Top");
		btnAlwaysOnTop.setSelection(config.isAlwaysOnTop());
		btnAlwaysOnTop.setBounds(141, 109, 122, 19);
		
		Button btnClickThrough = new Button(this, SWT.CHECK);
		btnClickThrough.setGrayed(true);
		btnClickThrough.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnClickThrough.setText("Click Through");
		btnClickThrough.setBounds(289, 109, 122, 19);
		
		Spinner spinner = new Spinner(this, SWT.BORDER);
		spinner.setSelection(100);
		spinner.setBounds(241, 73, 50, 25);
		
		Label lblImageSize = new Label(this, SWT.NONE);
		lblImageSize.setBounds(141, 76, 94, 19);
		lblImageSize.setText("Image Size (%)");

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	private Config loadConfig() {
		try {
			Scanner readConfig = new Scanner(new File("config.dat"));
			String[] tokens = readConfig.nextLine().split("\\|");
			String imagePath = tokens[0];
			int imageSize = Integer.parseInt(tokens[1]);
			boolean borderless = Boolean.parseBoolean(tokens[2]);
			boolean alwaysOnTop = Boolean.parseBoolean(tokens[3]);
			boolean clickThrough = Boolean.parseBoolean(tokens[4]);
			
			return new Config(imagePath, imageSize, borderless, alwaysOnTop, clickThrough);

		} catch (FileNotFoundException e) {
			return new Config();
		}
	}
}
