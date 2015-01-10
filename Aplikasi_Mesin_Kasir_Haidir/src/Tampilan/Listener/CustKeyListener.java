package Tampilan.Listener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JTextField;

import Setting.Utama;
import Setting.Syntax;
import Tampilan.FormTransaksi;
import Tampilan.FormUtama;

public class CustKeyListener implements KeyListener {

	public static final int NUMBER_ONLY = 0, ON_STOCK = 1;
	private int mode;
	private JTextField jf;
	private FormTransaksi frmFormTrans;
	private FormUtama frmUtama;
	private JButton btn;
	private Utama core;

	public CustKeyListener(FormTransaksi frmFormTrans, JTextField jf,
			int mode) {
		this.frmFormTrans = frmFormTrans;
		this.jf = jf;
		this.mode = mode;
	}

	public CustKeyListener(Utama core, FormUtama frm, JTextField jf,
			int mode) {
		this.core = core;
		this.frmUtama = frm;
		this.jf = jf;
		this.mode = mode;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {

	}

	@Override
	public void keyReleased(KeyEvent ev) {
		switch (mode) {
		case ON_STOCK:
			final int LIMIT = frmFormTrans.getSelectedObat().getStok();
			if (jf.getText().equalsIgnoreCase("")) {

			} else if (Integer.parseInt(jf.getText()) > LIMIT) {
				jf.setText("" + LIMIT);
			}
			break;
		case NUMBER_ONLY:
			if (jf.getText().equalsIgnoreCase("")) {
				jf.setText("1");
			}
			break;
		}

	}

	@Override
	public void keyTyped(KeyEvent ev) {
		switch (mode) {
		case NUMBER_ONLY:
			if (ev.getKeyChar() < '0' || ev.getKeyChar() > '9') {
				ev.consume();
			}
			break;
		case ON_STOCK:
			final int LIMIT = frmFormTrans.getSelectedObat().getStok();
			if (jf.getText().equalsIgnoreCase("")) {

			} else if (Integer.parseInt(jf.getText()) > LIMIT) {
				ev.consume();
			}
			break;
		}

	}
}
