package Tampilan;

import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;

import Database.Obat;
import Setting.*;
import Tampilan.Listener.CustActionListener;
import Tampilan.Listener.CustKeyListener;

import java.awt.Font;

public class FormUtama extends JFrame {
	private Utama core;

	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	private JTextField tfNama, tfstok, tfidSupp, tfHarga,tfid;
	private JTable tbl;
	private JLabel lbid, lbNama, lbidSupp, lbharga, lbstok;

	private Vector<Obat> obat = new Vector<Obat>();
	private Vector<String> Obats = new Vector<String>();

	public FormUtama(final Utama core) {
		super("APOTIK OKBOY");
		getContentPane().setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		this.core = core;
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(FormUtama.class.getResource("/Gambar/okboy.png")));

		setSize(545, 467);
		setLocation((screenSize.width - getWidth()) / 2,
				(screenSize.height - getHeight()) / 2);
		getContentPane().setLayout(null);
		Container container = this.getContentPane();
		container.setBackground(Color.GREEN);
		JMenuBar menu = new JMenuBar();
		this.setJMenuBar(menu);

		JMenu menuUser = new JMenu(
				core.getLoggedInUser().admin() ? "Admin " : "Kasir "
						+ core.getLoggedInUser().getUsername());
		JMenuItem miLogOut = new JMenuItem("Log Out");
		miLogOut.addActionListener(new CustActionListener(core, this, miLogOut,
				CustActionListener.LOGOUT));

		JMenu menuTrans = new JMenu("Transaksi");
		JMenuItem miTransData = new JMenuItem("Data Transaksi");
		miTransData.addActionListener(new CustActionListener(core, this,
				miTransData, CustActionListener.SHOW_DATA_TRANSAKSI));
		
		JMenu menuObat = new JMenu("Obat");
		
		JMenuItem miDataStok = new JMenuItem("Stok Obat");
		miDataStok.addActionListener(new CustActionListener(core, this,
				miDataStok, CustActionListener.SHOW_DATA_STOK));
		
		JMenu menuUsers = new JMenu("User");
		JMenuItem miUserDataPass = new JMenuItem("Ganti Password");
		miUserDataPass.addActionListener(new CustActionListener(core, this,
				miUserDataPass, CustActionListener.USERS_GANTI_PASS));
		
		JMenuItem miUserData = new JMenuItem("Data User");
		miUserData.addActionListener(new CustActionListener(core, this,
				miUserData, CustActionListener.SHOW_DATA_USER));
		
		JMenu menuSupp = new JMenu("Supplier");
		JMenuItem miSupp = new JMenuItem("Data Supplier");
		miSupp.addActionListener(new CustActionListener(core, this,
				miSupp, CustActionListener.SHOW_DATA_SUPP));
		
		menu.add(menuUser);
		menuUser.add(miLogOut);

		menu.add(menuTrans);
		menuTrans.add(miTransData);
		menu.add(menuObat);
		menuObat.add(miDataStok);
		
		menu.add(menuUsers);
		menuUsers.add(miUserData);
		menuUsers.add(miUserDataPass);
		
		menu.add(menuSupp);
		menuSupp.add(miSupp);
		
		ResultSet rs = Syntax.getListObat(core.getConnection());
		try {
			while (rs.next()) {
				obat.add(new Obat(rs.getString(1), rs.getString(2), rs
						.getString(3), rs.getInt(4), rs.getInt(5)));
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(this, e.getMessage());
		}

		tbl = new JTable(Syntax.resultSetToTableModel(Syntax
				.getListObat(core.getConnection())));
		Syntax.disableTableEdit(tbl);
		
		JPanel panTbl = new JPanel();
		panTbl.setLayout(new BorderLayout());
		panTbl.setBackground(Color.WHITE);
		panTbl.add(new JScrollPane(tbl), BorderLayout.CENTER);

		tfid = new JTextField();
		tfid.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		tfNama = new JTextField();
		tfNama.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		tfstok = new JTextField();
		tfstok.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		tfidSupp = new JTextField();
		tfidSupp.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		tfHarga = new JTextField();
		tfHarga.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		
		tfid.setBounds(217, 23, 170, 20);
		tfNama.setBounds(217, 48, 170, 20);
		tfidSupp.setBounds(217, 71, 170, 20);
		tfHarga.setBounds(217, 98, 170, 20);
		tfHarga.addKeyListener(new CustKeyListener(core, this, tfHarga,
				CustKeyListener.NUMBER_ONLY));
		tfstok.setBounds(217, 123, 170, 20);
		tfstok.addKeyListener(new CustKeyListener(core, this, tfstok,
				CustKeyListener.NUMBER_ONLY));

		panTbl.setBounds(20, 165, 500, 200);

		lbid = new JLabel("ID Obat");
		lbid.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		lbNama = new JLabel("Nama Obat");
		lbNama.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		lbidSupp = new JLabel("ID Supplier");
		lbidSupp.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		lbharga = new JLabel("Harga Obat");
		lbharga.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		lbstok = new JLabel("Stok Obat");
		lbstok.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));

		lbid.setBounds(112, 23, 100, 20);
		lbid.setHorizontalAlignment(JLabel.RIGHT);
		lbNama.setBounds(112, 48, 100, 20);
		lbNama.setHorizontalAlignment(JLabel.RIGHT);
		lbidSupp.setBounds(112, 71, 100, 20);
		lbidSupp.setHorizontalAlignment(JLabel.RIGHT);
		lbharga.setBounds(112, 98, 100, 20);
		lbharga.setHorizontalAlignment(JLabel.RIGHT);
		lbstok.setBounds(112, 123, 100, 20);
		lbstok.setHorizontalAlignment(JLabel.RIGHT);

		JButton buttonTambah = new JButton("Tambah");
		buttonTambah.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		JButton buttonDelete = new JButton("Hapus Obat");
		buttonDelete.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));

		buttonTambah.setBounds(397, 54, 123, 50);
		buttonTambah.addActionListener(new CustActionListener(core, this,tbl,
				buttonTambah, CustActionListener.TAMBAH_OBAT));
		buttonDelete.setBounds(117, 376, 316, 30);
		buttonDelete.addActionListener(new CustActionListener(core, this,tbl,
				buttonDelete, CustActionListener.HAPUS_OBAT));
		// Add Content
		container.add(tfid);
		container.add(tfNama);
		container.add(tfstok);
		container.add(tfidSupp);
		container.add(tfHarga);
		container.add(panTbl);
		container.add(lbid);
		container.add(lbNama);
		container.add(lbidSupp);
		container.add(lbharga);
		container.add(lbstok);

		container.add(buttonDelete);
		container.add(buttonTambah);
		
		JLabel BackD = new JLabel();
		BackD.setBounds(0, 0, 136, 165);
		getContentPane().add(BackD);
		
		JLabel Backg = new JLabel();
		Backg.setBounds(0, 0, 552, 438);
		getContentPane().add(Backg);
		
		ImageIcon back = new ImageIcon(FormUtama.class.getResource("/Gambar/back.jpg"));
		ImageIcon backD = new ImageIcon(FormUtama.class.getResource("/Gambar/iconDoc.png"));
		BackD.setIcon(backD);
		Backg.setIcon(back);
	}

	public Vector<Obat> getListObat() {
		return obat;
	}

	public Obat getSelectedObat() {
		return obat.get(tbl.getSelectedRow());
	}

	public void submitToDB() {
		if (Syntax.tambahObat(getObat(), core.getConnection())) {
			JOptionPane.showMessageDialog(this, "Data telah ditambahkan!");
		} else {
			JOptionPane.showMessageDialog(this, "Terjadi kesalahan!");
		}
		
		((DefaultTableModel)tbl.getModel()).addRow(new Object[]{tfid.getText(),tfNama.getText(),tfidSupp.getText(),tfHarga.getText(),tfstok.getText()});

		tfid.setText("");
		tfNama.setText("");
		tfstok.setText("");
		tfidSupp.setText("");
		tfHarga.setText("");
	}

	public void resetForm() {
		tfid.setText("");
		tfNama.setText("");
		tfstok.setText("");
		tfidSupp.setText("");
		tfHarga.setText("");

		if (tbl.getSelectedRow() >= 0) {
			((DefaultTableModel) tbl.getModel()).removeRow(tbl.getSelectedRow());
		}
	}


	public Obat getObat() {
		return new Obat(tfid.getText(),tfNama.getText(),tfidSupp.getText(),Integer.parseInt(tfHarga.getText()),Integer.parseInt( tfstok.getText()));
	}
}
