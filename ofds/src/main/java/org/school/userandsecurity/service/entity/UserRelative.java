package org.school.userandsecurity.service.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.school.userandsecurity.enums.RelationType;

/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name = "USER_RELATIVE")
//@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
public class UserRelative extends AbstractCommonEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Enumerated(EnumType.STRING)
	@Column(name = "RELATION_TYPE", length = 50)
	private RelationType relationType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private User ownerUser;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RELATIVE_ID")
	private User relativeUser;

	public UserRelative() {
		// no argument constructor
	}

	public UserRelative(Long id) {
		this.setId(id);
	}

	public User getOwnerUser() {
		return ownerUser;
	}

	public void setOwnerUser(User ownerUser) {
		this.ownerUser = ownerUser;
	}

	public User getRelativeUser() {
		return relativeUser;
	}

	public void setRelativeUser(User relativeUser) {
		this.relativeUser = relativeUser;
	}

	public RelationType getRelationType() {
		return relationType;
	}

	public void setRelationType(RelationType relationType) {
		this.relationType = relationType;
	}

}