package Tampilan;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Database.Obat;
import Database.User;
import Setting.*;
import Tampilan.Listener.CustActionListener;
import Tampilan.Listener.CustKeyListener;

import java.awt.Font;

public class DataUser extends JFrame {
	private Utama core;
	
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private JTextField tfuser, tfpass, tfadmin;
	private JTable tbl;
	
	private Vector<User> User = new Vector<User>();
	private Vector<String> mnUser = new Vector<String>();
	
	public DataUser(Utama core) {
		super("Data User");
		this.core = core;

		getContentPane().setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		setIconImage(Toolkit.getDefaultToolkit().getImage(DataUser.class.getResource("/Gambar/okboy.png")));
		this.core = core;
		setResizable(false);
		setSize(400, 343);
		setLocation((screenSize.width - getWidth()) / 2,
				(screenSize.height - getHeight()) / 2);
		
		getContentPane().setLayout(null);
		Container container = this.getContentPane();
		container.setBackground(new Color(0, 255, 102));
		
		ResultSet rs = Syntax.getListUser(core.getConnection());
		try {
			while (rs.next()) {
				User.add(new User(rs.getString(1), rs.getString(2), rs.getBoolean(3)));
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}

		
		tbl = new JTable(Syntax.resultSetToTableModel(Syntax
				.getListUser(core.getConnection())));
		Syntax.disableTableEdit(tbl);
	
		JPanel pan = new JPanel();
		pan.setBackground(Color.WHITE);
		pan.setBounds(5, 90, 385, 180);
		pan.setLayout(new BorderLayout());
		pan.add(tbl, BorderLayout.CENTER);
		pan.add(tbl.getTableHeader(), BorderLayout.NORTH);
		pan.add(new JScrollPane(tbl), BorderLayout.CENTER);
		getContentPane().add(pan);
		
		
		JButton btnTambah = new JButton("Tambah");
		btnTambah.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		btnTambah.setBounds(275, 30, 97, 29);
		btnTambah.addActionListener(new CustActionListener(core, this,tbl,
				btnTambah, CustActionListener.TAMBAH_USER));
		
		JButton btnHapus = new JButton("Hapus");
		btnHapus.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		btnHapus.setBounds(108, 277, 180, 30);
		btnHapus.addActionListener(new CustActionListener(core, this,tbl,
				btnHapus, CustActionListener.HAPUS_USER));
		
		JLabel lbuser = new JLabel("Username");
		lbuser.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		lbuser.setBounds(22, 23, 65, 19);
		getContentPane().add(lbuser);
		
		JLabel lbpass = new JLabel("Password");
		lbpass.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		lbpass.setBounds(22, 48, 65, 19);
		getContentPane().add(lbpass);
		
		tfadmin = new JTextField();
		tfuser = new JTextField();
		tfuser.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		tfpass = new JTextField();
		tfpass.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		
		tfuser.setBounds(97, 21, 157, 19);
		tfpass.setBounds(97, 48, 157, 19);
		
		container.add(tfuser);
		container.add(tfpass);
		container.add(tfadmin);
		container.add(btnTambah);
		container.add(btnHapus);
		
		JLabel Backg = new JLabel();
		Backg.setBounds(0, 0, 415, 334);
		getContentPane().add(Backg);
		
		ImageIcon back = new ImageIcon(DataUser.class.getResource("/Gambar/back.jpg"));
		Backg.setIcon(back);
	}
	
	public Vector<User> getListUser() {
		return User;
	}

	public User getSelectedUser() {
		return User.get(tbl.getSelectedRow());
	}
	
	public void submitToDB() {
		if (Syntax.tambahUser(getUser(), core.getConnection())) {
			JOptionPane.showMessageDialog(this, "User telah ditambahkan!");
		} else {
			JOptionPane.showMessageDialog(this, "Maaf terjadi kesalahan!");
		}
		
		((DefaultTableModel)tbl.getModel()).addRow(new Object[]{tfuser.getText(),tfpass.getText(),tfadmin.getText()});

		tfuser.setText("");
		tfpass.setText("");
		tfadmin.setText("");
	}
	
	public void resetForm() {
		tfuser.setText("");
		tfpass.setText("");
		tfadmin.setText("");

		if (tbl.getSelectedRow() >= 0) {
			((DefaultTableModel) tbl.getModel()).removeRow(tbl.getSelectedRow());
		}
	}


	public User getUser() {
		return new User(tfuser.getText(),tfpass.getText(), Boolean.parseBoolean(tfadmin.getText()));
	}

}
