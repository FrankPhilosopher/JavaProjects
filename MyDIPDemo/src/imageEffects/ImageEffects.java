package imageEffects;

import org.eclipse.swt.*;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

/**
 * Represents a tab of a CTabFolder. Handles layout, resizing, and painting of images.
 */
class EffectPanel {
	CTabItem tab;
	Group parametersGroup; // group of image effect settings
	Canvas canvas; // for image
	public static ImageData originalImageData;
	ImageData lastOriginalImageData; // used to track when originalImageData changes
	ImageData filteredImageData;

	public EffectPanel(final Display display, final Shell shell, CTabFolder folder, String title) {
		tab = new CTabItem(folder, SWT.NONE);
		tab.setText(title);
		Composite tabControlComposite = new Composite(folder, SWT.BORDER);
		tabControlComposite.setLayout(new GridLayout());
		tab.setControl(tabControlComposite);
		parametersGroup = new Group(tabControlComposite, SWT.NONE);
		parametersGroup.setText("Parameters");
		parametersGroup.setLayout(new GridLayout(2, false));
		canvas = new Canvas(tabControlComposite, SWT.DOUBLE_BUFFERED);
		canvas.setLayoutData(new GridData(GridData.FILL_BOTH));
		canvas.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				if (originalImageData == null)
					return;
				if (filteredImageData == null || lastOriginalImageData != originalImageData) {
					filteredImageData = lastOriginalImageData = originalImageData;
				}
				String text = tab.getText();
				if (text.equals("Glow") || text.equals("Drop Shadow"))
					shell.setSize(Math.max(500, filteredImageData.width + 50),
							Math.max(500, filteredImageData.height + 325));
				else
					shell.setSize(Math.max(500, filteredImageData.width + 50),
							Math.max(500, filteredImageData.height + 125));
				Image image = new Image(display, filteredImageData);
				e.gc.drawImage(image, 0, 0);
				image.dispose();
			}
		});
	}
}

/**
 * Creates UI for testing image effects
 */
public class ImageEffects {
	public static void main(String args[]) {
		final Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setText("Image Effects");
		shell.setLayout(new GridLayout());
		final CTabFolder folder = new CTabFolder(shell, SWT.NONE);
		folder.setLayoutData(new GridData(GridData.FILL_BOTH));
		folder.setLayout(new FillLayout());
//		folder.setSimple(false);//simple显示选项卡时是弧线形的
		final EffectPanel emboss = new EffectPanel(display, shell, folder, "Emboss");
		final EffectPanel blur = new EffectPanel(display, shell, folder, "Blur");
		final EffectPanel glow = new EffectPanel(display, shell, folder, "Glow");
		final EffectPanel shadow = new EffectPanel(display, shell, folder, "Drop Shadow");

		// Emboss Panel
		new Label(emboss.parametersGroup, SWT.NONE).setText("Gray level: ");
		Spinner embossGrayLevel = new Spinner(emboss.parametersGroup, SWT.NONE);
		embossGrayLevel.setMaximum(255);
		embossGrayLevel.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				ImageData imageData = EffectPanel.originalImageData;
				if (imageData == null)
					return;
				emboss.filteredImageData = Emboss.emboss(imageData, ((Spinner) event.widget).getSelection());
				emboss.canvas.redraw();
			}
		});

		// Blur Panel
		new Label(blur.parametersGroup, SWT.NONE).setText("Blur radius: ");
		Spinner blurRadius = new Spinner(blur.parametersGroup, SWT.NONE);
		blurRadius.setMaximum(9999);
		blurRadius.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				ImageData imageData = EffectPanel.originalImageData;
				if (imageData == null)
					return;
				blur.filteredImageData = Blur.blur(imageData, ((Spinner) event.widget).getSelection());
				blur.canvas.redraw();
			}
		});

		// Glow Panel
		new Label(glow.parametersGroup, SWT.NONE).setText("Glow radius: ");
		final Spinner glowRadius = new Spinner(glow.parametersGroup, SWT.NONE);
		glowRadius.setMaximum(9999);
		new Label(glow.parametersGroup, SWT.NONE).setText("Highlight radius: ");
		final Spinner glowHighlightRadius = new Spinner(glow.parametersGroup, SWT.NONE);
		new Label(glow.parametersGroup, SWT.NONE).setText("Opacity: ");
		final Spinner glowOpacity = new Spinner(glow.parametersGroup, SWT.NONE);
		glowOpacity.setMaximum(255);
		glowOpacity.setSelection(255);
		new Label(glow.parametersGroup, SWT.NONE).setText("Glow color: ");
		final Canvas glowColor = new Canvas(glow.parametersGroup, SWT.NONE);
		glowColor.setBackground(display.getSystemColor(SWT.COLOR_BLUE));
		glowRadius.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				doGlow(glow, glowColor.getBackground(), glowRadius, glowHighlightRadius, glowOpacity);
			}
		});
		glowHighlightRadius.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				doGlow(glow, glowColor.getBackground(), glowRadius, glowHighlightRadius, glowOpacity);
			}
		});
		glowOpacity.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				doGlow(glow, glowColor.getBackground(), glowRadius, glowHighlightRadius, glowOpacity);
			}
		});
		glowColor.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent e) {
				RGB rgb = new ColorDialog(shell).open();
				if (rgb == null)
					return;
				glowColor.setBackground(new Color(display, rgb));
				doGlow(glow, glowColor.getBackground(), glowRadius, glowHighlightRadius, glowOpacity);
			}
		});

		// Drop Shadow Panel
		new Label(shadow.parametersGroup, SWT.NONE).setText("Shadow radius: ");
		final Spinner shadowRadius = new Spinner(shadow.parametersGroup, SWT.NONE);
		shadowRadius.setMaximum(100);
		new Label(shadow.parametersGroup, SWT.NONE).setText("Highlight radius: ");
		final Spinner shadowHighlightRadius = new Spinner(shadow.parametersGroup, SWT.NONE);
		new Label(shadow.parametersGroup, SWT.NONE).setText("Opacity: ");
		final Spinner shadowOpacity = new Spinner(shadow.parametersGroup, SWT.NONE);
		shadowOpacity.setMaximum(255);
		shadowOpacity.setSelection(255);
		new Label(shadow.parametersGroup, SWT.NONE).setText("Shadow color: ");
		final Canvas shadowColor = new Canvas(shadow.parametersGroup, SWT.NONE);
		shadowColor.setBackground(display.getSystemColor(SWT.COLOR_BLACK));
		shadowRadius.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				doDropShadow(shadow, shadowColor.getBackground(), shadowRadius, shadowHighlightRadius, shadowOpacity);
			}
		});
		shadowHighlightRadius.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				doDropShadow(shadow, shadowColor.getBackground(), shadowRadius, shadowHighlightRadius, shadowOpacity);
			}
		});
		shadowOpacity.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				doDropShadow(shadow, shadowColor.getBackground(), shadowRadius, shadowHighlightRadius, shadowOpacity);
			}
		});
		shadowColor.addMouseListener(new MouseAdapter() {
			public void mouseUp(MouseEvent e) {
				RGB rgb = new ColorDialog(shell).open();
				if (rgb == null)
					return;
				shadowColor.setBackground(new Color(display, rgb));
				doDropShadow(shadow, shadowColor.getBackground(), shadowRadius, shadowHighlightRadius, shadowOpacity);
			}
		});

		// Shared file selection button
		// This is always visible because it is part of the Shell, not the CTabFolder
		Button button = new Button(shell, SWT.PUSH);
		button.setText("Select Image File");
		button.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				FileDialog dialog = new FileDialog(shell);
				dialog.setFilterExtensions(new String[] { "*.jpg;*.bmp;*.gif;*.png" });
				String fileName = dialog.open();
				if (fileName != null) {
					try {
						EffectPanel.originalImageData = new ImageData(fileName);
						(((Composite) folder.getSelection().getControl()).getChildren())[1].redraw(); // force canvas to
																										// be redrawn
					} catch (RuntimeException e) {
						System.err.println("Error while opening file: " + fileName);
					}
				}
			}
		});

		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	private static void doGlow(EffectPanel panel, Color color, Spinner radius, Spinner highlightRadius, Spinner opacity) {
		ImageData imageData = EffectPanel.originalImageData;
		if (imageData == null)
			return;
		panel.filteredImageData = Glow.glow(imageData, color, radius.getSelection(), highlightRadius.getSelection(),
				opacity.getSelection());
		panel.canvas.redraw();
	}

	private static void doDropShadow(EffectPanel panel, Color color, Spinner radius, Spinner highlightRadius,
			Spinner opacity) {
		ImageData imageData = EffectPanel.originalImageData;
		if (imageData == null)
			return;
		panel.filteredImageData = DropShadow.dropShadow(imageData, color, radius.getSelection(),
				highlightRadius.getSelection(), opacity.getSelection());
		panel.canvas.redraw();
	}
}
