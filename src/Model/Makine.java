package Model;

public class Makine {
	
	private int id;
	private String tip;
	private String durum;
	private int kullanim_sayisi;
	
	
	public Makine(int id, String tip, String durum, int kullanim_sayisi) {
		
		this.id = id;
		this.tip = tip;
		this.durum = durum;
		this.kullanim_sayisi = kullanim_sayisi;
	}
	
	public Makine () {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	public String getDurum() {
		return durum;
	}

	public void setDurum(String durum) {
		this.durum = durum;
	}

	public int getKullanim_sayisi() {
		return kullanim_sayisi;
	}

	public void setKullanim_sayisi(int kullanim_sayisi) {
		this.kullanim_sayisi = kullanim_sayisi;
	}
	
	@Override
	public String toString() {
		return tip + "Makinesi " + id;
	}
	
	
	

}
