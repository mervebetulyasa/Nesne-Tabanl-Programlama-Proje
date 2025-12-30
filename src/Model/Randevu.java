package Model;

public class Randevu {
	
	private int id;
	private int ogrenciId;
	private int makineId;
	private String tarih;
	private String saat;
	private int aktif;
	
	
	public Randevu() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Randevu(int id, int ogrenciId, int makineId, String tarih, String saat, int aktif) {
		super();
		this.id = id;
		this.ogrenciId = ogrenciId;
		this.makineId = makineId;
		this.tarih = tarih;
		this.saat = saat;
		this.aktif = aktif;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getOgrenciId() {
		return ogrenciId;
	}


	public void setOgrenciId(int ogrenciId) {
		this.ogrenciId = ogrenciId;
	}


	public int getMakineId() {
		return makineId;
	}


	public void setMakineId(int makineId) {
		this.makineId = makineId;
	}


	public String getTarih() {
		return tarih;
	}


	public void setTarih(String tarih) {
		this.tarih = tarih;
	}


	public String getSaat() {
		return saat;
	}


	public void setSaat(String saat) {
		this.saat = saat;
	}


	public int getAktif() {
		return aktif;
	}


	public void setAktif(int aktif) {
		this.aktif = aktif;
	}
	
	private String ogrenciName;
	
	public String getOgrenciName() {
        return ogrenciName;
    }
	
	public void setOgrenciName(String ogrenciName) {
        this.ogrenciName = ogrenciName;
    }
	


}
