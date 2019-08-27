package upp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Casopis implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "casopis_id", nullable = false)
	private Long id;
	
	@NotNull
	private String ime;
	
	@NotNull
	private String issnBr;
	
	@NotNull
	@ManyToMany
	@JoinTable(
	  name = "casopis_naucneoblasti", 
	  joinColumns = @JoinColumn(name = "casopis_id"), 
	  inverseJoinColumns = @JoinColumn(name = "naucnaOblast_id"))
	private List<NaucnaOblast> naucneOblasti;
	
	@NotNull
	private boolean openAccess;
	
	@NotNull
	@OneToMany(fetch = FetchType.LAZY)
	@JsonIgnore
	private List<User> urednici;
	
	@NotNull
	@OneToMany
	private List<User> recezenti;
	
	@NotNull
	@OneToMany(fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Rad> radovi;
	

	public Casopis() {
		this.recezenti = new ArrayList<User>();
		this.radovi = new ArrayList<Rad>();
		this.naucneOblasti = new ArrayList<NaucnaOblast>();
		this.urednici = new ArrayList<User>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getIssnBr() {
		return issnBr;
	}

	public void setIssnBr(String issnBr) {
		this.issnBr = issnBr;
	}

	public List<NaucnaOblast> getNaucneOblasti() {
		return naucneOblasti;
	}

	public void setNaucneOblasti(List<NaucnaOblast> naucneOblasti) {
		this.naucneOblasti = naucneOblasti;
	}

	public boolean isOpenAccess() {
		return openAccess;
	}

	public void setOpenAccess(boolean openAccess) {
		this.openAccess = openAccess;
	}

	
	public List<User> getRecezenti() {
		return recezenti;
	}

	public void setRecezenti(List<User> recezenti) {
		this.recezenti = recezenti;
	}

	public List<Rad> getRadovi() {
		return radovi;
	}

	public void setRadovi(List<Rad> radovi) {
		this.radovi = radovi;
	}

	public List<User> getUrednici() {
		return urednici;
	}

	public void setUrednici(List<User> urednici) {
		this.urednici = urednici;
	}

	
	
	
}
