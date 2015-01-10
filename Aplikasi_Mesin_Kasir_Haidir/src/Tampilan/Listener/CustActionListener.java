package Tampilan.Listener;

import java.awt.event.*;
import java.sql.Connection;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import Database.*;
import Setting.*;
import Tampilan.*;

import com.sun.org.apache.bcel.internal.generic.FMUL;

public class CustActionListener implements ActionListener {

	public final static int LOGIN = 0, FORM_TRANSAKSI_ADD_OBAT = 1,
			FORM_TRANSAKSI_SUBMIT = 2, FORM_TRANSAKSI_SELECTITEM = 3,
			LOGOUT = 4, SHOW_DATA_OBAT = 5,SHOW_DATA_USER = 6, SHOW_DATA_TRANSAKSI = 7,
			HAPUS_OBAT = 8, TAMBAH_OBAT = 9,
			HAPUS_TRANS = 10,EDIT_USER = 11,SHOW_DATA_STOK = 12,TAMBAH_STOK = 13,
			HAPUS_USER = 14, TAMBAH_USER = 15,USERS_GANTI_PASS = 16,HAPUS_SUPP = 17, TAMBAH_SUPP = 18,SHOW_DATA_SUPP = 19;
	private Utama core;

	private JFrame jf;
	private Login login;
	private Laporan lpran;
	private FormUtama frmUtama;
	private FormTransaksi frmTrans;
	private DBTransaksi dbTrans;
	private DBObat dbobat;
	private UserGantiPass gantiPass;
	private DataStok dstok;
	private DataUser duser;
	private DataSupp dsupp;
	
	private JButton btn;
	private JComboBox cb;
	private JMenuItem mi;
	private JTable tbl;

	private JTextField tf;

	private int mode;

	public CustActionListener(Utama core, Login frm, JButton btn) {
		this.core = core;
		this.login = frm;
		this.btn = btn;
		mode = LOGIN;
	}

	public CustActionListener(Utama core, FormTransaksi frm, JButton btn) {
		this.core = core;
		this.frmTrans = frm;
		this.btn = btn;
		this.mode = FORM_TRANSAKSI_SUBMIT;
	}

	public CustActionListener(FormTransaksi frm, JButton btn) {
		this.frmTrans = frm;
		this.btn = btn;
		this.mode = FORM_TRANSAKSI_ADD_OBAT;
	}

	public CustActionListener(FormTransaksi frm, JComboBox cb) {
		this.frmTrans = frm;
		this.cb = cb;
		mode = FORM_TRANSAKSI_SELECTITEM;
	}

	public CustActionListener(Utama core, JFrame jf, JMenuItem mi, int mode) {
		this.core = core;
		this.jf = jf;
		this.mi = mi;
		this.mode = mode;
	}

	public CustActionListener(Utama core, FormUtama frm, JMenuItem mi,
			int mode) {
		this.core = core;
		this.frmUtama = frm;
		this.mi = mi;
		this.mode = mode;
	}

	public CustActionListener(Utama core, FormUtama frm, JTable tbl,JButton btn,
			int mode) {
		this.core = core;
		this.frmUtama = frm;
		this.btn = btn;
		this.tbl = tbl;
		this.mode = mode;
	}
	
	public CustActionListener(Utama core, UserGantiPass frm, JTable tbl,JButton btn,
			int mode) {
		this.core = core;
		this.gantiPass = frm;
		this.btn = btn;
		this.tbl = tbl;
		this.mode = mode;
	}
	
	public CustActionListener(Utama core, DataUser frm, JTable tbl,JButton btn,
			int mode) {
		this.core = core;
		this.duser = frm;
		this.btn = btn;
		this.tbl = tbl;
		this.mode = mode;
	}
	
	public CustActionListener(Utama core, DataSupp frm, JTable tbl,JButton btn,
			int mode) {
		this.core = core;
		this.dsupp = frm;
		this.btn = btn;
		this.tbl = tbl;
		this.mode = mode;
	}
	
	public CustActionListener(Utama core, DataStok frm, JTable tbl,JButton btn,
			int mode) {
		this.core = core;
		this.dstok = frm;
		this.btn = btn;
		this.tbl = tbl;
		this.mode = mode;
	}

	public CustActionListener(Utama core, DBTransaksi frm, JTable tbl,
			JButton btn, int mode) 
	{
		this.core = core;
		this.tbl = tbl;
		this.dbTrans = frm;
		this.btn = btn;
		this.mode = mode;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		switch (mode) {
		case LOGIN:
			User user = Syntax.checkLogin(new User(login.getUser(),
					login.getPass(), false), core.getConnection());
			if (user == null) {
				JOptionPane.showMessageDialog(login,
						"Username atau Password anda Salah!");
			} else {
				login.setVisible(false);
				core.login(user);
			}
			break;
		case FORM_TRANSAKSI_SELECTITEM:
			int index = cb.getSelectedIndex();
			frmTrans.fillFormByIndex(index);
			break;
		case FORM_TRANSAKSI_ADD_OBAT:
			frmTrans.addObatToTable(new DetilTransaksi(frmTrans
					.getSelectedObat(), frmTrans.getQtyObat()));
			break;
		case FORM_TRANSAKSI_SUBMIT:
			frmTrans.submitToDB();
			break;
		case LOGOUT:
			core.logout();
			break;
		case SHOW_DATA_OBAT:
			if (core.dbobat == null) {
			} else {
				core.dbobat.dispose();
			}
			core.dbobat = new DBObat(core);
			core.dbobat.setVisible(true);
			break;
		case SHOW_DATA_USER:
			if (core.duser == null) {
			} else {
				core.duser.dispose();
			}
			core.duser = new DataUser(core);
			core.duser.setVisible(true);
			break;
		case USERS_GANTI_PASS:
			if (core.gantiPass == null) {
			} else {
				core.gantiPass.dispose();
			}
			core.gantiPass = new UserGantiPass(core);
			core.gantiPass.setVisible(true);
			break;	
		case SHOW_DATA_TRANSAKSI:
			if (core.dbTrans == null) {
			} else {
				core.dbTrans.dispose();
			}
			core.dbTrans = new DBTransaksi(core);
			core.dbTrans.setVisible(true);
			break;
		case TAMBAH_OBAT:
			frmUtama.submitToDB();
			
			break;
		case HAPUS_OBAT:
			if(tbl == null){
			}else{
				if (Syntax.hapusObat(frmUtama.getSelectedObat(),
						core.getConnection())) {
					JOptionPane.showMessageDialog(frmUtama,
							"Data Obat dihapus");
				}
				frmUtama.resetForm();
			}
			break;
		case HAPUS_TRANS:
			if(tbl == null){
			}else{
				if (Syntax.hapusTransaksi(dbTrans.getTransaksi(),
						core.getConnection())) {
					JOptionPane.showMessageDialog(dbTrans,
							"Data transaksi dihapus");
				}
				dbTrans.resetForm();
			}
			break;
		case EDIT_USER:
			gantiPass.submitToDB();
			
			break;
		case SHOW_DATA_STOK:
			if (core.dstok == null) {
			} else {
				core.dstok.dispose();
			}
			core.dstok = new DataStok(core);
			core.dstok.setVisible(true);
			break;
		case TAMBAH_STOK:
			dstok.submitToDB();
			
			break;
		case HAPUS_USER:
			if(tbl == null){
			}else{
				if (Syntax.hapusUser(duser.getSelectedUser(),
						core.getConnection())) {
					JOptionPane.showMessageDialog(duser,
							"Data user dihapus");
				}
				duser.resetForm();
			}
			break;
		case TAMBAH_USER:
			duser.submitToDB();
			
			break;
		case HAPUS_SUPP:
			if(tbl == null){
			}else{
				if (Syntax.hapusSupp(dsupp.getSelectedSupplier(),
						core.getConnection())) {
					JOptionPane.showMessageDialog(dsupp,
							"Data supplier dihapus");
				}
				dsupp.resetForm();
			}
			break;
		case TAMBAH_SUPP:
			dsupp.submitToDB();
			
			break;
		case SHOW_DATA_SUPP:
			if (core.dsupp == null) {
			} else {
				core.dsupp.dispose();
			}
			core.dsupp = new DataSupp(core);
			core.dsupp.setVisible(true);
			break;
		}

	}
}
