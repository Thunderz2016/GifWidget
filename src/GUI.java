import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;

import org.eclipse.swt.widgets.Text;

public class GUI extends Composite {
	private Text text;
	private Widget w;
	private String imagePath;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public GUI(Composite parent, int style) {
		super(parent, style);
		setLayout(null);

		
		Button btnNewButton = new Button(this, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
                try {
					w = new Widget("default.gif");
					Widget.run(w);
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
		
		Button btnBrowse = new Button(this, SWT.NONE);
		btnBrowse.setBounds(10, 71, 85, 29);
		btnBrowse.setText("Browse...");
		
		Button btnBorderless = new Button(this, SWT.CHECK);
		btnBorderless.setBounds(20, 109, 104, 19);
		btnBorderless.setText("Borderless");
		
		Button btnAlwaysOnTop = new Button(this, SWT.CHECK);
		btnAlwaysOnTop.setText("Always On Top");
		btnAlwaysOnTop.setBounds(141, 109, 122, 19);
		
		Button btnClickThrough = new Button(this, SWT.CHECK);
		btnClickThrough.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnClickThrough.setText("Click Through");
		btnClickThrough.setBounds(289, 109, 122, 19);
		
		Spinner spinner = new Spinner(this, SWT.BORDER);
		spinner.setBounds(241, 73, 50, 25);
		
		Label lblImageSize = new Label(this, SWT.NONE);
		lblImageSize.setBounds(141, 76, 94, 19);
		lblImageSize.setText("Image Size (%)");

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
