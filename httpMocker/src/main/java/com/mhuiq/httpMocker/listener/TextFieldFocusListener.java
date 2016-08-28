package com.mhuiq.httpMocker.listener;

import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

import com.mhuiq.httpMocker.httpClient.ConstDef;


public class TextFieldFocusListener implements FocusListener {

	@Override
	public void focusGained(FocusEvent e) {
		Component comp = e.getComponent();
		String compName = comp.getName();
		JTextField field = (JTextField) comp;
		switch (compName) {
		case ConstDef.hostField:
			if (ConstDef.HOST_TIP.equals(field.getText())) {
				field.setText("");
			}
			break;
		case ConstDef.portField:
			if (ConstDef.PORT_TIP.equals(field.getText())) {
				field.setText("");
			}
			break;
		case ConstDef.urlField:
			if (ConstDef.URL_TIP.equals(field.getText())) {
				field.setText("");
			}
			break;
		case ConstDef.respTmoField:
			if (ConstDef.RESPTMO_TIP.equals(field.getText())) {
				field.setText("");
			}
			break;
		case ConstDef.connTmoField:
			if (ConstDef.CONNTMO_TIP.equals(field.getText())) {
				field.setText("");
			}
			break;
		case ConstDef.keyStorePwdField:
			if (ConstDef.KEYSTORE_PWD_TIP.equals(field.getText())) {
				field.setText("");
			}
			break;
		case ConstDef.trustKeyStorePwdField:
			if (ConstDef.TRUSTKEYSTORE_PWD_TIP.equals(field.getText())) {
				field.setText("");
			}
			break;
		default:
			break;
		}
		
	}

	@Override
	public void focusLost(FocusEvent e) {
		Component comp = e.getComponent();
		String compName = comp.getName();
		JTextField field = (JTextField) comp;
		switch (compName) {
		case ConstDef.hostField:
			if ("".equals(field.getText())) {
				field.setText(ConstDef.HOST_TIP);
			}
			break;
		case ConstDef.portField:
			if ("".equals(field.getText())) {
				field.setText(ConstDef.PORT_TIP);
			}
			break;
		case ConstDef.urlField:
			if ("".equals(field.getText())) {
				field.setText(ConstDef.URL_TIP);
			}
			break;
		case ConstDef.respTmoField:
			if ("".equals(field.getText())) {
				field.setText(ConstDef.RESPTMO_TIP);
			}
			break;
		case ConstDef.connTmoField:
			if ("".equals(field.getText())) {
				field.setText(ConstDef.CONNTMO_TIP);
			}
			break;
		case ConstDef.keyStorePwdField:
			if ("".equals(field.getText())) {
				field.setText(ConstDef.KEYSTORE_PWD_TIP);
			}
			break;
		case ConstDef.trustKeyStorePwdField:
			if ("".equals(field.getText())) {
				field.setText(ConstDef.TRUSTKEYSTORE_PWD_TIP);
			}
			break;
		default:
			break;
		}
	}

}
