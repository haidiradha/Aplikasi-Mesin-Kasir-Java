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

import Database.*;
import Setting.*;
import Tampilan.Listener.CustActionListener;
import Tampilan.Listener.CustKeyListener;

import java.awt.Font;

public class DataSupp extends JFrame {
	private Utama core;
	
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private JTextField tfidsupp, tfnama;
	private JTable tbl;
	
	private Vector<Supplier> Supplier = new Vector<Supplier>();
	private Vector<String> mnSupplier = new Vector<String>();
	
	public DataSupp(Utama core) {
		super("Data Supplier");
		this.core = core;

		getContentPane().setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		setIconImage(Toolkit.getDefaultToolkit().getImage(DataSupp.class.getResource("/Gambar/okboy.png")));
		this.core = core;
		setResizable(false);
		setSize(422, 343);
		setLocation((screenSize.width - getWidth()) / 2,
				(screenSize.height - getHeight()) / 2);
		
		getContentPane().setLayout(null);
		Container container = this.getContentPane();
		container.setBackground(new Color(0, 255, 102));
		
		ResultSet rs = Syntax.getListSupp(core.getConnection());
		try {
			while (rs.next()) {
				Supplier.add(new Supplier(rs.getString(1), rs.getString(2)));
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}

		
		tbl = new JTable(Syntax.resultSetToTableModel(Syntax
				.getListSupp(core.getConnection())));
		Syntax.disableTableEdit(tbl);
	
		JPanel pan = new JPanel();
		pan.setBackground(Color.WHITE);
		pan.setBounds(5, 90, 405, 180);
		pan.setLayout(new BorderLayout());
		pan.add(tbl, BorderLayout.CENTER);
		pan.add(tbl.getTableHeader(), BorderLayout.NORTH);
		pan.add(new JScrollPane(tbl), BorderLayout.CENTER);
		getContentPane().add(pan);
		
		
		JButton btnTambah = new JButton("Tambah");
		btnTambah.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		btnTambah.setBounds(307, 28, 97, 29);
		btnTambah.addActionListener(new CustActionListener(core, this,tbl,
				btnTambah, CustActionListener.TAMBAH_SUPP));
		
		JButton btnHapus = new JButton("Hapus");
		btnHapus.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		btnHapus.setBounds(108, 277, 180, 30);
		btnHapus.addActionListener(new CustActionListener(core, this,tbl,
				btnHapus, CustActionListener.HAPUS_SUPP));
		
		JLabel lbsup = new JLabel("ID SUPPLIER");
		lbsup.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		lbsup.setBounds(23, 21, 90, 19);
		getContentPane().add(lbsup);
		
		JLabel lbpass = new JLabel("NAMA SUPPLIER");
		lbpass.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		lbpass.setBounds(23, 46, 121, 19);
		getContentPane().add(lbpass);
		
		tfidsupp = new JTextField();
		tfidsupp.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		tfnama = new JTextField();
		tfnama.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		
		tfidsupp.setBounds(154, 19, 140, 19);
		tfnama.setBounds(154, 46, 140, 19);
		
		container.add(tfidsupp);
		container.add(tfnama);
		container.add(btnTambah);
		container.add(btnHapus);
		
		JLabel Backg = new JLabel();
		Backg.setBounds(0, 0, 415, 334);
		getContentPane().add(Backg);
		
		ImageIcon back = new ImageIcon(DataSupp.class.getResource("/Gambar/back.jpg"));
		Backg.setIcon(back);
	}
	
	public Vector<Supplier> getListSupp() {
		return Supplier;
	}

	public Supplier getSelectedSupplier() {
		return Supplier.get(tbl.getSelectedRow());
	}
	
	public void submitToDB() {
		if (Syntax.tambahSupp(getSupp(), core.getConnection())) {
			JOptionPane.showMessageDialog(this, "Supplier telah ditambahkan!");
		} else {
			JOptionPane.showMessageDialog(this, "Maaf terjadi kesalahan!");
		}
		
		((DefaultTableModel)tbl.getModel()).addRow(new Object[]{tfidsupp.getText(),tfnama.getText()});

		tfidsupp.setText("");
		tfnama.setText("");
	}
	
	public void resetForm() {
		tfidsupp.setText("");
		tfnama.setText("");

		if (tbl.getSelectedRow() >= 0) {
			((DefaultTableModel) tbl.getModel()).removeRow(tbl.getSelectedRow());
		}
	}


	public Supplier getSupp() {
		return new Supplier(tfidsupp.getText(),tfnama.getText());
	}

}
