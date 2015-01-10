package Database;

public class DetilTransaksi {
	private Obat obat;
	private int kuantitas;
	private Transaksi transaksi;

	public DetilTransaksi(Transaksi transaksi, Obat obat, int kuantitas) {
		this.transaksi = transaksi;
		this.obat = obat;
		this.kuantitas = kuantitas;
	}

	public DetilTransaksi(Obat obat, int kuantitas) {
		this.obat = obat;
		this.kuantitas = kuantitas;
	}

	public Obat getObat() {
		return obat;
	}

	public int getKuantitas() {
		return kuantitas;
	}

	public Transaksi getTransaksi() {
		return transaksi;
	}

}
