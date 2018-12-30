package org.school.userandsecurity.service.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.school.userandsecurity.enums.AddressType;


/**
 * The persistent class for the usergroups database table.
 * 
 */
@Entity
@Table(name="USER_ADDRESS")
@NamedQuery(name="UserAddress.findAll", query="SELECT u FROM UserGroup u")
public class UserAddress extends AbstractCommonEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Enumerated(EnumType.STRING)
	@Column(name = "ADDRESS_TYPE", length = 50)
	private AddressType addressType;

	//bi-directional many-to-one association to Group
	@ManyToOne
	@JoinColumn(name="ADDRESS_ID")
	private Address address;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="USER_ID")
	private User user;

	public UserAddress() {
		super();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public AddressType getAddressType() {
		return addressType;
	}

	public void setAddressType(AddressType addressType) {
		this.addressType = addressType;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}


}