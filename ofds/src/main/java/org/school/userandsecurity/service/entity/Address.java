package org.school.userandsecurity.service.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the groups database table.
 * 
 */
@Entity
@Table(name = "ADDRESS")
@NamedQuery(name = "Address.findAll", query = "SELECT g FROM Address g")
public class Address extends AbstractCommonEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "ADDRESS_1", length = 50)
	private String addressLine1;

	@Column(name = "ADDRESS_2", length = 50)
	private String addressLine2;

	@Column(name = "ADDRESS_3", length = 50)
	private String addressLine3;

	@Column(name = "CITY_DISTRICT", length = 50)
	private String cityDistrict;

	@Column(name = "STATE", length = 50)
	private String state;

	@Column(name = "ZIPCODE", length = 6)
	private Long zipcode;

	public Address() {
		// no argument constructor
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getAddressLine3() {
		return addressLine3;
	}

	public void setAddressLine3(String addressLine3) {
		this.addressLine3 = addressLine3;
	}

	public String getCityDistrict() {
		return cityDistrict;
	}

	public void setCityDistrict(String cityDistrict) {
		this.cityDistrict = cityDistrict;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Long getZipcode() {
		return zipcode;
	}

	public void setZipcode(Long zipcode) {
		this.zipcode = zipcode;
	}

}