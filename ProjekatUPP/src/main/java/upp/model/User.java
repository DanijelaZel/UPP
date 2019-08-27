package upp.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name = "user_id", nullable = false)
	private Long id;
	
	@NotNull
	private String email;
	@NotNull
	private String password;
	@NotNull
	private String name;
	@NotNull
	private String surname;
	@NotNull
	private String city;
	@NotNull
	private String country;
	@NotNull
	private UserRole userRole;
	@NotNull
	private boolean userStatus;
	
	@ManyToOne
	private Casopis casopis;
	
	private Boolean glavni;
	
	public User() {}
	
	

	public User(@NotNull String email, @NotNull String password, @NotNull String name, @NotNull String surname,
			@NotNull String city, @NotNull String country, @NotNull UserRole userRole, @NotNull boolean userStatus,
			Casopis casopis, Boolean glavni) {
		super();
		this.email = email;
		this.password = password;
		this.name = name;
		this.surname = surname;
		this.city = city;
		this.country = country;
		this.userRole = userRole;
		this.userStatus = userStatus;
		this.casopis = casopis;
		this.glavni = glavni;
	}



	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getSurname() {
		return surname;
	}


	public void setSurname(String surname) {
		this.surname = surname;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public UserRole getUserRole() {
		return userRole;
	}


	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}


	public boolean isUserStatus() {
		return userStatus;
	}


	public void setUserStatus(boolean userStatus) {
		this.userStatus = userStatus;
	}

	public Casopis getCasopis() {
		return casopis;
	}

	public void setCasopis(Casopis casopis) {
		this.casopis = casopis;
	}

	public Boolean getGlavni() {
		return glavni;
	}

	public void setGlavni(Boolean glavni) {
		this.glavni = glavni;
	}
	
	
	
	
	
	
	
	
}
