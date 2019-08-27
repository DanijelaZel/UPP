package upp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
public class Rad implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "rad_id", nullable = false)
	private Long id;
	
	@NotNull
	private String naslov;
	
	@NotNull
	@JsonIgnore
	@OneToOne
	private User autor;
	
	@NotNull
	private String kljucniPojmovi;
	
	@NotNull
	private String apstrakt;
	
	@NotNull
	private String koautori;
	
	@NotNull
	@OneToOne
	private NaucnaOblast naucnaOblast;
	
	@NotNull
	private String pdf;
	
	private RadStatus radStatus;
	
	private String konacnaVerzija;
	
	@ManyToOne
	private Casopis casopisRad;
	
	@OneToMany
	private List<User> recezenti;
	
	
	public Rad() {
		this.recezenti = new ArrayList<User>();
		
	}



	public Rad(@NotNull String naslov, @NotNull User autor, @NotNull String kljucniPojmovi, @NotNull String apstrakt,
			@NotNull String koautori, @NotNull NaucnaOblast naucnaOblast, @NotNull String pdf, RadStatus radStatus,
			String konacnaVerzija, Casopis casopisRad, List<User> recezenti) {
		super();
		this.naslov = naslov;
		this.autor = autor;
		this.kljucniPojmovi = kljucniPojmovi;
		this.apstrakt = apstrakt;
		this.koautori = koautori;
		this.naucnaOblast = naucnaOblast;
		this.pdf = pdf;
		this.radStatus = radStatus;
		this.konacnaVerzija = konacnaVerzija;
		this.casopisRad = casopisRad;
		this.recezenti = recezenti;
	}







	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNaslov() {
		return naslov;
	}

	public void setNaslov(String naslov) {
		this.naslov = naslov;
	}

	public User getAutor() {
		return autor;
	}

	public void setAutor(User autor) {
		this.autor = autor;
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

	public NaucnaOblast getNaucnaOblast() {
		return naucnaOblast;
	}

	public void setNaucnaOblast(NaucnaOblast naucnaOblast) {
		this.naucnaOblast = naucnaOblast;
	}

	public String getPdf() {
		return pdf;
	}

	public void setPdf(String pdf) {
		this.pdf = pdf;
	}

	public String getKonacnaVerzija() {
		return konacnaVerzija;
	}

	public void setKonacnaVerzija(String konacnaVerzija) {
		this.konacnaVerzija = konacnaVerzija;
	}

	public RadStatus getRadStatus() {
		return radStatus;
	}

	public void setRadStatus(RadStatus radStatus) {
		this.radStatus = radStatus;
	}

	public Casopis getCasopisRad() {
		return casopisRad;
	}

	public void setCasopisRad(Casopis casopisRad) {
		this.casopisRad = casopisRad;
	}



	public List<User> getRecezenti() {
		return recezenti;
	}



	public void setRecezenti(List<User> recezenti) {
		this.recezenti = recezenti;
	}



	public String getKoautori() {
		return koautori;
	}



	public void setKoautori(String koautori) {
		this.koautori = koautori;
	}
	
	
	
}
