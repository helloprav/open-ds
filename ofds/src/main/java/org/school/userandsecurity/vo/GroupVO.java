/**
 * 
 */
package org.school.userandsecurity.vo;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.openframework.common.rest.vo.BaseVO;

/**
 * @author Java Developer
 *
 */
public class GroupVO extends BaseVO {

	private Long id;

	@NotNull(message = "error.NotNull.name")
	@Length(min=5, max=50, message="Length.name")
	private String groupName;

	/*
	 * TODO check why this @Size is not working (Ref: https://docs.jboss.org/hibernate/validator/5.1/reference/en-US/html/chapter-message-interpolation.html#section-interpolation-with-message-expressions)
	 *
	 *	@Size(min = 2, max = 14, message = "The license plate '${validatedValue}' must be between {min} and {max} characters long")
	 **/
	@Length(max=500, message="The length should be maximum of 500 characters long")
	private String description;

	private List<FunctionVO> functionList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String name) {
		this.groupName = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<FunctionVO> getFunctionList() {
		return functionList;
	}

	public void setFunctionList(List<FunctionVO> functionList) {
		this.functionList = functionList;
	}


}
