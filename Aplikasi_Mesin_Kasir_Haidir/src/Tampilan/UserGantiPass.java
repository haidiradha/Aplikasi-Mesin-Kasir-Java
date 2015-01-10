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

import java.awt.Font;

public class UserGantiPass extends JFrame {
	private Utama core;
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private JTable tbl;
	private JTextField tfuser, tfpass, tfadmin;
	private Vector<User> User = new Vector<User>();
	public UserGantiPass(Utama core) {
		super("Gantti Password");
		this.core = core;

		getContentPane().setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		setIconImage(Toolkit.getDefaultToolkit().getImage(UserGantiPass.class.getResource("/Gambar/okboy.png")));
		this.core = core;
		setResizable(false);
		setSize(400, 285);
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

		
		JTable tbl = new JTable(Syntax.resultSetToTableModel(Syntax
				.getListUser(core.getConnection())));
		Syntax.disableTableEdit(tbl);
	
		JPanel pan = new JPanel();
		pan.setBackground(Color.WHITE);
		pan.setBounds(5, 5, 385, 180);
		pan.setLayout(new BorderLayout());
		pan.add(tbl, BorderLayout.CENTER);
		pan.add(tbl.getTableHeader(), BorderLayout.NORTH);
		pan.add(new JScrollPane(tbl), BorderLayout.CENTER);
		getContentPane().add(pan);
		
		
		JButton btnTambah = new JButton("Ubah");
		btnTambah.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		btnTambah.setBounds(269, 204, 97, 29);
		getContentPane().add(btnTambah);
		btnTambah.addActionListener(new CustActionListener(core, this,tbl,
				btnTambah, CustActionListener.EDIT_USER));
		
		JLabel lbuser = new JLabel("Username");
		lbuser.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		lbuser.setBounds(15, 199, 65, 19);
		getContentPane().add(lbuser);
		
		JLabel lbpass = new JLabel("Password");
		lbpass.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		lbpass.setBounds(15, 224, 65, 19);
		getContentPane().add(lbpass);
		
		tfadmin = new JTextField();
		tfuser = new JTextField();
		tfuser.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		tfpass = new JTextField();
		tfpass.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		
		tfuser.setBounds(90, 197, 157, 19);
		tfpass.setBounds(90, 224, 157, 19);
		
		container.add(tfuser);
		container.add(tfpass);
		container.add(tfadmin);
		
		JLabel Backg = new JLabel();
		Backg.setBounds(0, 0, 415, 273);
		getContentPane().add(Backg);
		
		ImageIcon back = new ImageIcon(UserGantiPass.class.getResource("/Gambar/back.jpg"));
		Backg.setIcon(back);
		
	}
	
	public void submitToDB() {
		if (Syntax.editUser(getUser(), core.getConnection())) {
			JOptionPane.showMessageDialog(this, "Data telah dirubah!");
		} else {
			JOptionPane.showMessageDialog(this, "Terjadi kesalahan!");
		}
		

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
