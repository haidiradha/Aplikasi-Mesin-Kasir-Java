package Tampilan;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Setting.*;

public class DBObat extends JFrame {
	private Utama core;
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private JTable tbl;

	public DBObat(Utama core) {
		super("Data Obat");
		this.core = core;
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(DBObat.class.getResource("/Gambar/okboy.png")));
		this.core = core;
		setResizable(false);
		setSize(485, 348);
		setLocation((screenSize.width - getWidth()) / 2,
				(screenSize.height - getHeight()) / 2);
		JTable tbl = new JTable(Syntax.resultSetToTableModel(Syntax
				.getListObat(core.getConnection())));
		Syntax.disableTableEdit(tbl);
		getContentPane().setLayout(null);
		getContentPane().setBackground(Color.WHITE);
		JPanel pan = new JPanel();
		pan.setBackground(Color.WHITE);
		pan.setBounds(0, 0, 480, 320);
		pan.setLayout(new BorderLayout());
		pan.add(tbl, BorderLayout.CENTER);
		pan.add(tbl.getTableHeader(), BorderLayout.NORTH);
		pan.add(new JScrollPane(tbl), BorderLayout.CENTER);
		getContentPane().add(pan);
		
		
	}
}
