package org.school.userandsecurity.service.entity;

import java.io.Serializable;
import javax.persistence.*;

import java.util.List;

/**
 * The persistent class for the groups database table.
 * 
 */
@Entity
@Table(name = "FUNCTION")
@NamedQuery(name = "Function.findAll", query = "SELECT g FROM Function g")
public class Function extends AbstractCommonEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "FUNCTION_NAME", length = 50)
	private String name;

	@OneToMany(mappedBy = "function")
	private List<GroupFunction> groupFunctions;

	public List<GroupFunction> getGroupFunctions() {
		return groupFunctions;
	}

	public void setGroupFunctions(List<GroupFunction> groupFunctions) {
		this.groupFunctions = groupFunctions;
	}

	public String getName() {
		return name;
	}

	public void setName(String functionName) {
		this.name = functionName;
	}

	public Function() {
		// no argument constructor
	}

}