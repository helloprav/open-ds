package org.school.userandsecurity.service.as;

import java.util.List;

import org.school.userandsecurity.service.entity.Group;

public interface GroupAS {

	List<Group> findGroups();

	List<Group> findGroupsByStatus(Boolean status);

	Group findGroupById(Long id);

	Long createGroup(Group group);

	Group saveGroup(Group group);

	/**
	 * Deletes all the GroupFunction for the given group id
	 * 
	 * @param id
	 *            group id
	 */
	void deleteGroupFunctions(Long id);


	/**
	 * validates if the given groupName doesn't exists in db. Other wise throws exception.
	 * 
	 * @param groupName
	 * @param id
	 */
	public void checkUniqueGroupName(String groupName);

	/**
	 * Returns true if there is no group name conflict. Other wise throws exception.
	 * 
	 * @param groupName
	 * @param id
	 */
	void checkUniqueGroupNameNotId(Group inputGroup);
}
/*
 * 
 * List<Group> findGroupsByStatus(Boolean status);
 * 
 * Long createGroup(Group group);
 * 
 * 
 * Group updateStatus(Group group);
 * 
 * 
 */
