package vistas.componentes;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.JLabel;

/*
 * @author Jordan Aranda
 */
public class IToogleButton extends JLabel implements MouseListener {

	private boolean	selected;
	private Icon	iconON;
	private Icon	iconOFF;

	private static final long serialVersionUID = 5728251792417526726L;

	public IToogleButton(final Icon iconON, final Icon iconOFF) {
		super();
		setOpaque(false);
		this.iconON = iconON;
		this.iconOFF = iconOFF;
		this.selected = false;
		this.setIcon(iconOFF);
		super.addMouseListener(this);
	}

	/**
	 * 
	 * @return If the component is selected
	 */
	public boolean isSelected() {
		return this.selected;
	}

	/**
	 * @param iconON
	 *            The icon to be displayed when component is selected
	 */
	public void setIconON(Icon iconON) {
		this.iconON = iconON;
	}

	/**
	 * @param iconOFF
	 *            The icon to be displayed when component is not selected
	 */
	public void setIconOFF(Icon iconOFF) {
		this.iconOFF = iconOFF;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (this.selected) {
			this.selected = false;
			this.setIcon(iconOFF);
		} else {
			this.selected = true;
			this.setIcon(iconON);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}
}