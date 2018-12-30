package org.school.userandsecurity.service.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.school.userandsecurity.enums.Gender;
import org.school.userandsecurity.enums.UserRole;
import org.school.userandsecurity.enums.UserStatus;

/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name = "USER")
@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
public class User extends AbstractCommonEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "ADMISSION_NO", length = 8, unique = true)
	private Long admissionNo;

	@Column(name = "FIRST_NAME", length = 50, nullable = false)
	private String firstName;

	@Column(name = "LAST_NAME", length = 50)
	private String lastName;

	@Column(name = "PASSWORD", length = 500)
	private Character[] password;

	@Enumerated(EnumType.STRING)
	@Column(name = "GENDER", length = 50)
	private Gender gender;

	@Column(name = "EMAIL", length = 50)
	private String email;

	@Column(name = "MOBILE", length = 50)
	private String mobile;

	@Column(name = "PHONE", length = 50)
	private String phone;

	@Column(name = "DESCRIPTION", length = 500)
	private String description;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS", length = 50)
	private UserStatus status;

	@Enumerated(EnumType.STRING)
	@Column(name = "ROLE", length = 50)
	private UserRole role;

	@OneToMany(mappedBy = "ownerUser")
	private List<UserRelative> userRelatives;

	@OneToMany(mappedBy = "user", fetch=FetchType.EAGER)
	private List<UserGroup> userGroups;

	@OneToMany(mappedBy = "user")
	private List<UserAddress> userAddresses;

	public User() {
		// no argument constructor
	}

	public User(Long id) {
		this.setId(id);
	}

	public Long getAdmissionNo() {
		return admissionNo;
	}

	public void setAdmissionNo(Long admissionNo) {
		this.admissionNo = admissionNo;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Character[] getPassword() {
		return password;
	}

	public void setPassword(Character[] password) {
		this.password = password;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public List<UserRelative> getUserRelatives() {
		return userRelatives;
	}

	public void setUserRelatives(List<UserRelative> userRelatives) {
		this.userRelatives = userRelatives;
	}

	public List<UserGroup> getUserGroups() {
		return userGroups;
	}

	public void setUserGroups(List<UserGroup> userGroups) {
		this.userGroups = userGroups;
	}

	public List<UserAddress> getUserAddresses() {
		return userAddresses;
	}

	public void setUserAddresses(List<UserAddress> userAddresses) {
		this.userAddresses = userAddresses;
	}


}