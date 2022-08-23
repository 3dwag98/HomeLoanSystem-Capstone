package com.example.HomeLoan.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(name = "users")
@Getter
@Setter
public class Users {

	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int userId;

	@Column(name = "user_name")
	@NotEmpty
	private String name;

	@Column(name = "user_password")
	@NotEmpty
	@Size(min = 4, max = 10, message = "invalid password")
	private String password;

	@Column(name = "user_email", unique = true)
	@Email(message = "invalid email")
	private String email;

	@Column(name = "user_phone")
	private long phone;

	@Column(name = "user_salary")
	@NotEmpty
	private long salary;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getPhone() {
		return phone;
	}

	public void setPhone(long phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getSalary() {
		return salary;
	}

	public void setSalary(long salary) {
		this.salary = salary;
	}

	public Users(int userId, String password, String email, long phone) {
		// TODO Auto-generated constructor stub
		this.userId = userId;
		this.password = password;
		this.email = email;
		this.phone = phone;
	}

	public Users(int userId, @NotEmpty String name,
			@NotEmpty @Size(min = 4, max = 10, message = "invalid password") String password,
			@Email(message = "invalid email") String email, long phone, @NotEmpty long salary) {
		this.userId = userId;
		this.name = name;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.salary = salary;
	}

	public Users() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Users [userId=" + userId + ", name=" + name + ", password=" + password + ", email=" + email + ", phone="
				+ phone + ", salary=" + salary + "]";
	}

}