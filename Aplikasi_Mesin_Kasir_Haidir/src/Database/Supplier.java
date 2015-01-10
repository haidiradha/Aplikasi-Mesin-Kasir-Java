package Database;

public class Supplier {
	private String idSupp,nama;

	public Supplier(String idSupp, String nama){
		this.idSupp=idSupp;
		this.nama=nama;
	}
	
	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getIdSupp() {
		return idSupp;
	}

	public void setIdSupp(String idSupp) {
		this.idSupp = idSupp;
	}

	
}
