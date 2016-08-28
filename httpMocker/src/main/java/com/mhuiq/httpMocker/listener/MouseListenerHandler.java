package com.mhuiq.httpMocker.listener;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTextField;

import com.mhuiq.httpMocker.ConstDef;

public class MouseListenerHandler implements MouseListener {
	
	@Override
	public void mouseClicked(MouseEvent e) {
		Component component = e.getComponent();
		String comName = component.getName();
		if (e.getButton() == MouseEvent.BUTTON1) {
			switch (comName) {
			case "hostField":
				Component[] coms = e.getComponent().getParent().getComponents();
				for (int i=0; i<coms.length; ++i) {
					System.out.println(coms[i].getName());
				}
				JTextField hostField = (JTextField) component;
				if (ConstDef.HOST_TIP.equals(hostField.getText())) {
					hostField.setText("");
				}
				break;

			default:
				
				break;
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

}
