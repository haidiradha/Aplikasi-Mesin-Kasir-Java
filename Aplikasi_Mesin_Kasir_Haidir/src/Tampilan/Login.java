package Tampilan;

import java.awt.*;

import javax.swing.*;

import java.awt.Dialog.ModalExclusionType;

import javax.swing.GroupLayout.Alignment;

import Setting.*;
import Tampilan.Listener.CustActionListener;

public class Login extends JFrame {

	final private Utama core;

	private JButton btnLogin;
	private JTextField txUsr;
	private JPasswordField txPsw;
	private JLabel lblUsr, lblPsw;

	private Container container;
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	public Login(Utama core) {
		super("Login");
		getContentPane().setForeground(Color.WHITE);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/Gambar/okboy.png")));
		setForeground(Color.GREEN);
		this.core = core;

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(460, 400);
		setLocation((screenSize.width - getWidth()) / 2,
				(screenSize.height - getHeight()) / 2);
		setResizable(false);
		JLabel labelHeader = new JLabel();
		labelHeader.setBounds(63, 11, 350, 222);
		labelHeader.setForeground(Color.BLACK);
		labelHeader.setFont(new Font("Comic Sans MS", Font.PLAIN, 25));
		
		container = this.getContentPane();
		container.setBackground(Color.GREEN);
		btnLogin = new JButton();
		btnLogin.setForeground(Color.GREEN);
		btnLogin.setBounds(262, 246, 164, 115);
		txUsr = new JTextField(15);
		txUsr.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		txUsr.setBounds(32, 246, 215, 35);
		txPsw = new JPasswordField(15);
		txPsw.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		txPsw.setBounds(32, 325, 215, 35);
		lblUsr = new JLabel("Username :");
		lblUsr.setFont(new Font("Comic Sans MS", Font.BOLD, 21));
		lblUsr.setBounds(21, 212, 126, 31);
		lblPsw = new JLabel("Password :");
		lblPsw.setFont(new Font("Comic Sans MS", Font.BOLD, 21));
		lblPsw.setBounds(21, 292, 114, 31);

		lblUsr.setHorizontalAlignment(JLabel.RIGHT);
		lblPsw.setHorizontalAlignment(JLabel.RIGHT);

		btnLogin.addActionListener(new CustActionListener(core, this, btnLogin));
		getContentPane().setLayout(null);
		getContentPane().add(labelHeader);
		getContentPane().add(lblUsr);
		getContentPane().add(txUsr);
		getContentPane().add(lblPsw);
		getContentPane().add(txPsw);
		getContentPane().add(btnLogin);
		
		JLabel Backg = new JLabel();
		Backg.setBounds(0, 0, 643, 385);
		getContentPane().add(Backg);
		
		ImageIcon back = new ImageIcon(Login.class.getResource("/Gambar/back.jpg"));
		ImageIcon backI = new ImageIcon(Login.class.getResource("/Gambar/iconDoc.png"));
		ImageIcon logo = new ImageIcon(Login.class.getResource("/Gambar/okboy.png"));
		ImageIcon lgn = new ImageIcon(Login.class.getResource("/Gambar/btnLgn.png"));
		
		labelHeader.setIcon(logo);
		Backg.setIcon(back);
		btnLogin.setIcon(lgn);
	}

	public String getUser() {
		return txUsr.getText();
	}

	public String getPass() {
		return txPsw.getText();
	}
}
