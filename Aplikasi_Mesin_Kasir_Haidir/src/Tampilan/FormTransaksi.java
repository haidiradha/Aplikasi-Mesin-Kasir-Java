package Tampilan;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import Database.Obat;
import Database.DetilTransaksi;
import Database.Transaksi;
import Database.User;
import Setting.*;
import Tampilan.Listener.CustActionListener;
import Tampilan.Listener.CustKeyListener;

import java.awt.Font;

public class FormTransaksi extends JFrame {

	final int TGL = 0, KASIR = 1, OBAT = 2, HARGA = 3, JUMLAH = 4;

	private Utama core;

	private User user;
	private Transaksi t;

	private JPanel panAtas, panTable, panGrand;
	private JTextField tfTgl, tfKasir, tfHarga, tfJumlah;
	private JLabel l[] = new JLabel[5];
	private JComboBox cb;
	private JButton btnTambahObat, btnTambahTransaksi,btnReset;
	private JTable tbl;

	private DefaultTableModel model;

	private Container container;
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private Vector<String> nmObat = new Vector<String>();
	private Vector<Obat> obat = new Vector<Obat>();

	public FormTransaksi(Utama core) {
		super("Formulir Transaksi - " + core.getDateAsString());
		this.core = core;
		this.user = core.getLoggedInUser();
		setIconImage(Toolkit.getDefaultToolkit().getImage(FormTransaksi.class.getResource("/Gambar/okboy.png")));

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setSize(720, 400);
		setLocation((screenSize.width - getWidth()) / 2,
				(screenSize.height - getHeight()) / 2);
		getContentPane().setLayout(null);
		container = this.getContentPane();
		container.setBackground(Color.WHITE);
		model = new DefaultTableModel();
		model.addColumn("Nama Item");
		model.addColumn("Jumlah Item");
		model.addColumn("Total Harga");
		tbl = new JTable(model);
		Syntax.disableTableEdit(tbl);
		ResultSet rs = Syntax.getListObat(core.getConnection());
		nmObat.removeAllElements();
		obat.removeAllElements();
		try {
			while (rs.next()) {
				obat.add(new Obat(rs.getString(1), rs.getString(2), rs
						.getString(3), rs.getInt(4), rs.getInt(5)));
				if (obat.lastElement().getStok() > 0)
					nmObat.add(obat.lastElement().getNama());
				else
					obat.removeElement(obat.lastElement());
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}
		cb = new JComboBox(nmObat);
		tfTgl = new JTextField(core.getDateAsString());
		tfKasir = new JTextField(user.getUsername());
		tfJumlah = new JTextField();
		tfHarga = new JTextField();
		fillFormByIndex(cb.getSelectedIndex());

		panTable = new JPanel(new BorderLayout());
		panTable.setBounds(10, 101, 694, 205);
		panTable.setBackground(Color.WHITE);
		panGrand = new JPanel(null);
		panGrand.setBounds(0, 91, 715, 259);
		panGrand.setBackground(new Color(0, 255, 102));
		panAtas = new JPanel(null);
		panAtas.setBackground(Color.GREEN);
		panAtas.setBounds(0, 0, 715, 100);

		JMenuBar menu = new JMenuBar();
		this.setJMenuBar(menu);
		
		
		JMenu menuUser = new JMenu(
				core.getLoggedInUser().admin() ? " " : "Kasir");
		JMenuItem miLogOut = new JMenuItem("Log Out");
		miLogOut.addActionListener(new CustActionListener(core, this, miLogOut,
				CustActionListener.LOGOUT));
		
		
		JMenu menuObat = new JMenu("Obat");
		JMenuItem miDataObat = new JMenuItem("Data Obat");
		miDataObat.addActionListener(new CustActionListener(core, this,
				miDataObat, CustActionListener.SHOW_DATA_OBAT));
		
		menu.add(menuUser);
		menuUser.add(miLogOut);
		
		menu.add(menuObat);
		menuObat.add(miDataObat);

		l[TGL] = new JLabel("Tanggal");
		l[KASIR] = new JLabel("Username");
		l[OBAT] = new JLabel("Nama Obat ");
		l[HARGA] = new JLabel("Harga Rp.");
		l[JUMLAH] = new JLabel("Jumlah Item");

		tfTgl.setEnabled(false);
		tfKasir.setEnabled(false);
		tfHarga.setEnabled(false);
		tfTgl.setBounds(130, 11, 170, 20);
		tfKasir.setBounds(130, 36, 170, 20);
		cb.setBounds(448, 11, 170, 20);
		tfHarga.setBounds(448, 36, 170, 20);
		tfJumlah.setBounds(215, 69, 140, 20);

		l[TGL].setBounds(30, 10, 80, 20);
		l[KASIR].setBounds(30, 36, 80, 20);
		l[OBAT].setBounds(350, 10, 80, 20);
		l[HARGA].setBounds(350, 36, 80, 20);
		l[JUMLAH].setBounds(130, 69, 80, 20);

		btnTambahObat = new JButton("Tambah Obat");
		btnTambahObat.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
		btnTambahObat.setBounds(371, 64, 130, 30);
		btnTambahObat.addActionListener(new CustActionListener(this,
				btnTambahObat));
		
		btnTambahTransaksi = new JButton("Selesai & Print");
		btnTambahTransaksi.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		btnTambahTransaksi.setBounds(195, 222, 285, 30);
		btnTambahTransaksi.addActionListener(new CustActionListener(core, this,
				btnTambahTransaksi));
		
		btnReset = new JButton("Reset");
		btnReset.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		btnReset.setBounds(628, 222, 77, 30);
		
		btnReset.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				resetForm();
			}
		});
		
		tfJumlah.addKeyListener(new CustKeyListener(this, tfJumlah,
				CustKeyListener.NUMBER_ONLY));
		tfJumlah.addKeyListener(new CustKeyListener(this, tfJumlah,
				CustKeyListener.ON_STOCK));
		cb.addActionListener(new CustActionListener(this, cb));
		panAtas.add(cb);
		panAtas.add(tfTgl);
		panAtas.add(tfKasir);
		panAtas.add(tfHarga);
		panAtas.add(tfJumlah);
		for (int i = 0; i < l.length; i++) {
			l[i].setHorizontalAlignment(JLabel.RIGHT);
			panAtas.add(l[i]);
		}
		panTable.add((JTableHeader) tbl.getTableHeader(), BorderLayout.NORTH);
		panTable.add(new JScrollPane(tbl), BorderLayout.CENTER);
		panGrand.add(btnTambahTransaksi);
		panGrand.add(btnReset);
		panAtas.add(btnTambahObat);

		container.add(panAtas);
		container.add(panTable);
		container.add(panGrand);
		
		ImageIcon back = new ImageIcon(FormTransaksi.class.getResource("/Gambar/back.jpg"));
		JLabel BackAtas = new JLabel();
		BackAtas.setBounds(0, 0, 727, 272);
		JLabel BackG = new JLabel();
		BackG.setBounds(0, 0, 727, 272);
		panGrand.add(BackAtas);
		panAtas.add(BackG);
		BackAtas.setIcon(back);
		BackG.setIcon(back);

		resetForm();
		
		
	}

	public void fillFormByIndex(int index) {
		tfJumlah.setText("1");
		tfHarga.setText(obat.get(index).getHarga()
				* Integer.parseInt(tfJumlah.getText()) + "");
	}

	public void resetForm() {
		int row = tbl.getRowCount() - 1;
		for (int i = row; i >= 0; i--)
			((DefaultTableModel) tbl.getModel()).removeRow(i);
		t = new Transaksi(core.getDate(), user);
	}

	public void addObatToTable(DetilTransaksi dt) {
		for (int i = 0; i < tbl.getRowCount(); i++) {
			// test apakah sudah ada
		}
		model.addRow(new String[] { dt.getObat().getNama(),
				dt.getKuantitas() + "",
				dt.getObat().getHarga() * dt.getKuantitas() + "" });
		t.addDetilTransaksi(dt);
	}

	public Vector<Obat> getListObat() {
		return obat;
	}

	public Obat getSelectedObat() {
		return obat.get(cb.getSelectedIndex());
	}

	public int getQtyObat() {
		return Integer.parseInt(tfJumlah.getText());
	}

	public Transaksi getTransaksi() {
		return t;
	}

	public Vector<DetilTransaksi> getDetilTransaksi() {
		return t.getDetilTransaksi();
	}

	public void submitToDB() {
		if (Syntax.tambahTransaksi(getTransaksi(), core.getConnection())) {
			JOptionPane.showMessageDialog(this, "Data telah ditambahkan!");
		} else {
			JOptionPane.showMessageDialog(this, "Maaf terjadi kesalahan!");
		}
		if (JOptionPane.showConfirmDialog(this, "Ingin Cetak Transaksi?", "",
				JOptionPane.YES_NO_OPTION) == 0) {
			core.printReport(t);
		}
		resetForm();
	}
}
