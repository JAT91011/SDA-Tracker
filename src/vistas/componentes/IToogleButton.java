package vistas.componentes;

import javax.swing.JButton;

public class IToogleButton extends JButton {

	private static final long	serialVersionUID	= 2464624645556241740L;

	private boolean				selected;
	private String				enableText;
	private String				disableText;

	public IToogleButton(final String enableText, final String disableText) {
		super(enableText);
		this.enableText = enableText;
		this.disableText = disableText;
		this.selected = true;
	}

	public void performChange() {
		if (!selected) {
			selected = true;
			setText(enableText);
		} else {
			selected = false;
			setText(disableText);
		}
	}
}