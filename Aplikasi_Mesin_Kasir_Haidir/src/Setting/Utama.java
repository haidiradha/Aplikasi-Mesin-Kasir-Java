package Setting;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.sql.rowset.JdbcRowSet;
import javax.swing.*;
import javax.swing.table.*;

import Tampilan.*;
import Database.*;

public class Utama {

	public Login login = new Login(this);
	public Laporan lpran;
	public FormTransaksi frmTrans;
	public FormUtama frmUtama;
	public DBTransaksi dbTrans;
	public DBObat dbobat;
	public UserGantiPass gantiPass;
	public DataStok dstok;
	public DataUser duser;
	public DataSupp dsupp;
	
	Config conf = new Config();
	final public static boolean Run = true;
	final public static int OBAT = 0, DETIL_TRANSAKSI = 1, TRANSAKSI = 2,USER = 3;
	
	private Connection con;
	private User loggedUser;

	private static Calendar tgl = Calendar.getInstance();
	private static SimpleDateFormat formatter = new SimpleDateFormat(
			"E, dd MMMM yyyy");

	public Utama(boolean GUI) {
		tesKoneksi();
		if (GUI) {
			login.setVisible(true);
		} else {}
	}

	private void tesKoneksi() {
		try {
			Class.forName(conf.DATABASE_DRIVER);
			con = DriverManager.getConnection(conf.URL,conf.username,conf.password);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			System.exit(0);
		}
	}

	public void printReport(Transaksi trns) {
		int ID = Syntax.getLastIDTrans(con);

		String header = "\n"
				+ "Apotik OKBOY"
				+ "\nSemampir Tengah Surabaya "
				+ "\nNo. "
				+ ID
				+ "\nTgl " + trns.getTglAsString()
				+ "\n\nKasir : "
				+ loggedUser.getUsername()
				+ "\n=================================================================", data = "", footer = "\n"
				+ "\n---------------------------------------"
				+ "\nTotal : "
				+ trns.getTotalItem()
				+ " Item      "
				+ trns.getTotalHrg()
				+ "\n=================================================================";
		for (int i = 0; i < trns.getDetilTransaksi().size(); i++) {
			DetilTransaksi dt = trns.getDetilTransaksi().get(i);
			data = data + "\n" + dt.getObat().getNama() + "\t"
					+ dt.getKuantitas() + "x\t" + dt.getKuantitas()
					* dt.getObat().getHarga();
		}
		lpran = new Laporan(this,
				new String[] { header, data, footer });
		lpran.setVisible(true);
	}

	public void login(User usr) {
		this.loggedUser = new User(usr);
		if (usr.admin()) {
			frmUtama = new FormUtama(this);
			frmUtama.setVisible(true);
		} else {
			frmTrans = new FormTransaksi(this);
			frmTrans.setVisible(true);
		}
	}

	public void logout() {
		if (loggedUser.admin()) {
			frmUtama.dispose();
		} else {
			frmTrans.dispose();
		}
		login.dispose();
		login = new Login(this);
		login.setVisible(true);
		loggedUser = null;
	}

	public User getLoggedInUser() {
		return loggedUser;
	}

	public Connection getConnection() {
		return con;
	}

	public Date getDate() {
		return (Date) tgl.getTime();
	}

	public String getDateAsString() {
		return formatter.format(tgl.getTime());
	}

	public void reloadForm() {

	}
}
