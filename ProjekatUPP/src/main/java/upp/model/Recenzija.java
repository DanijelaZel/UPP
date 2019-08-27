package upp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Recenzija  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "recenzija_id", nullable = false)
	private Long id;
	
	
	@NotNull
	@OneToOne
	private User autorRecenzije;
	
	@NotNull
	@OneToOne
	private Rad rad;
	
	@NotNull
	private String komentar;
	
	private String komentarZaUrednika;
	
	public Recenzija() {}
	
	
	

	public Recenzija(@NotNull User autorRecenzije, @NotNull Rad rad, @NotNull String komentar,
			String komentarZaUrednika) {
		super();
		this.autorRecenzije = autorRecenzije;
		this.rad = rad;
		this.komentar = komentar;
		this.komentarZaUrednika = komentarZaUrednika;
	}




	public User getAutorRecenzije() {
		return autorRecenzije;
	}

	public void setAutorRecenzije(User autorRecenzije) {
		this.autorRecenzije = autorRecenzije;
	}

	public Rad getRad() {
		return rad;
	}

	public void setRad(Rad rad) {
		this.rad = rad;
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
