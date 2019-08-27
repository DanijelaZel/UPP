package upp.model;

import java.util.Arrays;

import org.springframework.web.multipart.MultipartFile;

public class UploadModel {

private String naslov;
    
    private String kljucniPojmovi;
    
    private String apstrakt;

    private MultipartFile[] files;
    
    private String koautori;

	public String getNaslov() {
		return naslov;
	}

	public void setNaslov(String naslov) {
		this.naslov = naslov;
	}

	public String getKljucniPojmovi() {
		return kljucniPojmovi;
	}

	public void setKljucniPojmovi(String kljucniPojmovi) {
		this.kljucniPojmovi = kljucniPojmovi;
	}

	public String getApstrakt() {
		return apstrakt;
	}

	public void setApstrakt(String apstrakt) {
		this.apstrakt = apstrakt;
	}

	public MultipartFile[] getFiles() {
		return files;
	}

	public void setFiles(MultipartFile[] files) {
		this.files = files;
	}

	public String getKoautori() {
		return koautori;
	}

	public void setKoautori(String koautori) {
		this.koautori = koautori;
	}

	@Override
	public String toString() {
		return "UploadModel [naslov=" + naslov + ", kljucniPojmovi=" + kljucniPojmovi + ", apstrakt=" + apstrakt
				+ ", files=" + Arrays.toString(files) + ", koautori=" + koautori + "]";
	}

	

	
}
