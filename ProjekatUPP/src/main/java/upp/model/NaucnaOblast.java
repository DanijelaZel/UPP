package upp.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class NaucnaOblast implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "naucnaOblast_id", nullable = false)
	private Long id;
	
	@NotNull
	private String naziv;
	
	@JsonIgnore
	@OneToOne
	private User urednikNaucneOblasti;

	@NotNull
	@ManyToMany(mappedBy = "naucneOblasti")
	private List<Casopis> casopisi;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public User getUrednikNaucneOblasti() {
		return urednikNaucneOblasti;
	}

	public void setUrednikNaucneOblasti(User urednikNaucneOblasti) {
		this.urednikNaucneOblasti = urednikNaucneOblasti;
	}

	public List<Casopis> getCasopisi() {
		return casopisi;
	}

	public void setCasopisi(List<Casopis> casopisi) {
		this.casopisi = casopisi;
	}

	
	
	

}
