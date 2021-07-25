package net.mandrik;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true, length = 45)
	private String email;
	
	@Column(nullable = false, length = 64)
	private String password;
	
	@Column(name = "name", nullable = false, length = 20)
	private String name;
	
	@Column(name = "status", nullable = false, length = 20)
	private String status;

	@Column(name = "dateregistration", nullable = false)
	private LocalDateTime dateregistration;

	@Column(name = "datelastlogin", nullable = false)
	private LocalDateTime  datelastlogin;

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	public LocalDateTime getDateregistration() {
		return dateregistration;
	}

	public void setDateregistration(LocalDateTime dateregistration) {
		this.dateregistration = dateregistration;
	}
	public LocalDateTime getDatelastlogin() {
		return datelastlogin;
	}

	public void setDatelastlogin(LocalDateTime datelastlogin) {
		this.datelastlogin = datelastlogin;
	}
}
