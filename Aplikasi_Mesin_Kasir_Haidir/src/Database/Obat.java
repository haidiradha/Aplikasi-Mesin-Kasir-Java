package Database;

public class Obat {
	private int stok, harga,tmhStok;
	private String id,nama, idSupp;

	public Obat(String id, String nama, String idSupp, int harga,int stok){
		this.id=id;
		this.nama=nama;
		this.stok=stok;
		this.harga=harga;
		this.idSupp=idSupp;
	}
	
	public Obat(String nama, String idSupp, int harga,int stok){
		this.nama=nama;
		this.stok=stok;
		this.harga=harga;
		this.idSupp=idSupp;
	}

	public Obat(String id, int tmhStok) {
		this.tmhStok=tmhStok;
		this.id=id;
	}

	public String getId() {
		return id;
	}

	public int getTmhStok() {
		return tmhStok;
	}
	
	public int getStok() {
		return stok;
	}

	public int getHarga() {
		return harga;
	}

	public String getNama() {
		return nama;
	}

	public String getidSupp() {
		return idSupp;
	}


}
