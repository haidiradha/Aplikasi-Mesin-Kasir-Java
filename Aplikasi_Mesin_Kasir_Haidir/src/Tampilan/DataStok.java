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

public class DataStok extends JFrame {
	private Utama core;
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private JTable tbl;
	private JTextField tfid, tfTstok;
	private Vector<Obat> User = new Vector<Obat>();

	public DataStok(Utama core) {
		super("Data Stok");
		this.core = core;
		
		getContentPane().setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		setIconImage(Toolkit.getDefaultToolkit().getImage(DataStok.class.getResource("/Gambar/okboy.png")));
		this.core = core;
		setResizable(false);
		setSize(708, 189);
		setLocation((screenSize.width - getWidth()) / 2,
				(screenSize.height - getHeight()) / 2);
		JTable tbl = new JTable(Syntax.resultSetToTableModel(Syntax
				.getListStok(core.getConnection())));
		Syntax.disableTableEdit(tbl);
		
		getContentPane().setLayout(null);
		Container container = this.getContentPane();
		container.setBackground(new Color(0, 255, 102));
		
		JPanel pan = new JPanel();
		pan.setBackground(Color.WHITE);
		pan.setBounds(245, 5, 450, 150);
		pan.setLayout(new BorderLayout());
		pan.add(tbl, BorderLayout.CENTER);
		pan.add(tbl.getTableHeader(), BorderLayout.NORTH);
		pan.add(new JScrollPane(tbl), BorderLayout.CENTER);
		getContentPane().add(pan);
		
		JLabel lbid = new JLabel("ID Obat");
		lbid.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		lbid.setBounds(10, 25, 87, 21);
		getContentPane().add(lbid);
		
		JLabel lbstok = new JLabel("Jumlah Stok");
		lbstok.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		lbstok.setBounds(10, 65, 87, 21);
		getContentPane().add(lbstok);
		
		tfid = new JTextField();
		tfid.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		tfid.setBounds(99, 21, 135, 30);
		getContentPane().add(tfid);
		tfid.setColumns(10);
		
		tfTstok = new JTextField();
		tfTstok.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		tfTstok.setBounds(99, 61, 135, 30);
		getContentPane().add(tfTstok);
		tfTstok.setColumns(10);
		
		JButton btntambah = new JButton("Tambah");
		btntambah.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		btntambah.setBounds(50, 112, 140, 37);
		getContentPane().add(btntambah);
		btntambah.addActionListener(new CustActionListener(core, this,tbl,
				btntambah, CustActionListener.TAMBAH_STOK));
		
		JLabel Backg = new JLabel();
		Backg.setBounds(0, 0, 713, 175);
		getContentPane().add(Backg);
		
		ImageIcon back = new ImageIcon(DataStok.class.getResource("/Gambar/back.jpg"));
		Backg.setIcon(back);
	}
	
	public Obat getObat() {
		return new Obat(tfid.getText(),Integer.parseInt( tfTstok.getText()));
	}
	
	public void submitToDB() {
		if (Syntax.tambahStok(getObat(), core.getConnection())) {
			JOptionPane.showMessageDialog(this, "Stok telah ditambahkan!");
		} else {
			JOptionPane.showMessageDialog(this, "Maaf terjadi kesalahan!");
		}

		tfid.setText("");
		tfTstok.setText("");
	}

	public void resetForm() {
		tfid.setText("");
		tfTstok.setText("");

		if (tbl.getSelectedRow() >= 0) {
			((DefaultTableModel) tbl.getModel()).removeRow(tbl.getSelectedRow());
		}
	}

}
