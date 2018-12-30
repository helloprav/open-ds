package org.school.userandsecurity.service.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the usergroups database table.
 * 
 */
@Entity
@Table(name="USER_GROUP")
//@NamedQuery(name="UserGroup.findAll", query="SELECT u FROM UserGroup u join fetch u.")
public class UserGroup extends AbstractCommonEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	//bi-directional many-to-one association to Group
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="GROUP_ID")
	private Group group;

	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="USER_ID")
	private User user;

	public UserGroup() {
		super();
	}

	public Group getGroup() {
		return this.group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


}