import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Button;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;

import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Combo;

public class GUI extends Composite {
	private Text text;
	private Widget w;
	private String imagePath;
	private Config config;
	private Combo combo;

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
	
		combo = new Combo(this, SWT.NONE);
		loadHistory(combo);

		
		Button btnNewButton_1 = new Button(this, SWT.NONE);
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					if(w != null)
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
		// 	@Override
		// 	public void keyPressed(org.eclipse.swt.events.KeyEvent arg0) {
		// 		if(arg0.keyCode == SWT.CR || arg0.keyCode == SWT.KEYPAD_CR){
		// 			imagePath = text.getText();
		// 		}
		// 	}

		// 	@Override
		// 	public void keyReleased(org.eclipse.swt.events.KeyEvent arg0) {

		// 	}
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
				if(arg0.keyCode == SWT.CR || arg0.keyCode == SWT.KEYPAD_CR){
					String fieldText = combo.getText();
					imagePath = fieldText;
					config.setImagePath(fieldText);
					// if(combo.indexOf(fieldText) == -1){
					// 	combo.add(fieldText);
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
                if(w != null) {

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
				if(w != null)
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
					if(imagePath == null || imagePath.isEmpty()){
						Widget.dialogBox("Path is empty. Please specify a GIF File Path.", "Error", 200, JOptionPane.ERROR_MESSAGE);
					} else if (!new File(imagePath).exists()) {
						Widget.dialogBox("\"" + imagePath + "\" does not exist.", "Error", 170, JOptionPane.ERROR_MESSAGE);
					} else {				
						w = new Widget(imagePath, 100, btnBorderless.getSelection(), btnAlwaysOnTop.getSelection(), btnClickThrough.getSelection());
						Widget.run(w);
						config.setImagePath(imagePath);
						if(combo.indexOf(combo.getText()) == -1)
							combo.add(imagePath);
					}
                } catch (Exception e1) {
                    e1.printStackTrace();
                }   
			}
		});
		btnNewButton.setBounds(10, 152, 190, 111);
		btnNewButton.setText("Start Widget");
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	private void loadHistory(Combo combo) {
		try {
            Scanner readHistory = new Scanner(new File("history.dat"));
            while (readHistory.hasNextLine()) {
                combo.add(readHistory.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("\"history.dat\" not found. Creating new file.");
        }
	}

	public void saveHistory() {
		try {
            FileWriter writeHistory = new FileWriter(new File("history.dat"), false);
            for (int i = 0; i < combo.getItemCount(); i++) {
                writeHistory.write(combo.getItem(i) + "\n");
            }
            writeHistory.close();
        } catch (IOException e) {
            Widget.dialogBox("An IOException has occurred while saving path history to \"history.dat\", please check file permissions.", "Error", 200, JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
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

			readConfig.close();
			
			return new Config(imagePath, imageSize, borderless, alwaysOnTop, clickThrough);

		} catch (FileNotFoundException e) {
			return new Config();
		}
	}

	public void saveConfig() {
		try {
            FileWriter writeConfig = new FileWriter(new File("config.dat"), false);
            writeConfig.write(this.config.toString());
            writeConfig.close();
        } catch (IOException e) {
			Widget.dialogBox("An IOException has occurred while saving path history to \"config.dat\", please check file permissions.", "Error", 200, JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
	}
	
}
