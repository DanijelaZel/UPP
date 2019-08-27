package upp.dto;

public class RecenzijaDTO {

	private String komentar;
	private String komentarZaUrednika;

	
	public RecenzijaDTO() {}


	public RecenzijaDTO(String komentar, String komentarZaUrednika) {
		super();
		this.komentar = komentar;
		this.komentarZaUrednika = komentarZaUrednika;
	}


	public String getKomentar() {
		return komentar;
	}


	public void setKomentar(String komentar) {
		this.komentar = komentar;
	}


	public String getKomentarZaUrednika() {
		return komentarZaUrednika;
	}


	public void setKomentarZaUrednika(String komentarZaUrednika) {
		this.komentarZaUrednika = komentarZaUrednika;
	}
	
	
	
}
