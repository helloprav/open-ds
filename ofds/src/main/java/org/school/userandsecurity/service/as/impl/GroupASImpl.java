package org.school.userandsecurity.service.as.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.openframework.commons.domain.exceptions.EntityConflictsException;
import org.school.userandsecurity.service.as.GroupAS;
import org.school.userandsecurity.service.entity.Group;
import org.school.userandsecurity.service.entity.GroupFunction;
import org.school.userandsecurity.service.repository.GroupFunctionRepository;
import org.school.userandsecurity.service.repository.GroupRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class GroupASImpl extends BaseASImpl implements GroupAS {

	@Inject
	private GroupRepository groupRepository;

	@Inject
	private GroupFunctionRepository groupFunctionRepository;

	@Override
	public List<Group> findGroups() {
		return groupRepository.findAll();
	}

	@Override
	public List<Group> findGroupsByStatus(Boolean status) {
		return groupRepository.findByIsValid(status);
	}

	@Override
	public Group findGroupById(Long id) {
		return returnIfEntityExists(id, groupRepository);
	}

	@Override
	public Long createGroup(Group group) {

		Group createdGroup = groupRepository.save(group);
		List<GroupFunction> groupFunctionsList = group.getGroupFunctions();
		List<GroupFunction> updatedGroupFunctionsList = new ArrayList<>();
		for (GroupFunction groupFunctions : groupFunctionsList) {
			groupFunctions.setGroup(createdGroup);
			GroupFunction savedGroupFunctions = groupFunctionRepository.save(groupFunctions);
			updatedGroupFunctionsList.add(savedGroupFunctions);
		}
		createdGroup.setGroupFunctions(null);
		createdGroup.setGroupFunctions(updatedGroupFunctionsList);
		return createdGroup.getId();
	}

	@Override
	public void checkUniqueGroupName(String groupName) {

		Group groupInDB = groupRepository.findByGroupName(groupName);
		if (null != groupInDB) {
			throw new EntityConflictsException(String.format("Requested group name %s already exists", groupName));
		}
	}

	/**
	 * Returns true if there is no group name conflict. Other wise throws exception.
	 * 
	 * @param groupName
	 * @param id
	 */
	@Override
	public void checkUniqueGroupNameNotId(Group inputGroup) {

		Group groupInDB = groupRepository.findByGroupName(inputGroup.getGroupName());
		if (null != groupInDB && null != groupInDB.getId() && !groupInDB.getId().equals(inputGroup.getId())) {
			throw new EntityConflictsException(
					String.format("Requested group name [%s] already exists with Id [%d]", inputGroup.getGroupName(),
							groupInDB.getId()));
		}
	}

	@Override
	public Group saveGroup(Group group) {

		Group createdGroup = groupRepository.save(group);
		List<GroupFunction> groupFunctionsList = group.getGroupFunctions();
		List<GroupFunction> updatedGroupFunctionsList = new ArrayList<>();
		for (GroupFunction groupFunctions : groupFunctionsList) {
			groupFunctions.setGroup(createdGroup);
			GroupFunction savedGroupFunctions = groupFunctionRepository.save(groupFunctions);
			updatedGroupFunctionsList.add(savedGroupFunctions);
		}
		createdGroup.setGroupFunctions(null);
		createdGroup.setGroupFunctions(updatedGroupFunctionsList);
		return createdGroup;
	}

	@Override
	public void deleteGroupFunctions(Long id) {

		groupFunctionRepository.deleteByGroupID(id);
	}

	private void removeGroupFunctionMapping(Group createGroup) {

		Optional<Group> optionalDbGroup = groupRepository.findById(createGroup.getId());
		Group groupToDB = null;

		if (optionalDbGroup.isPresent()) {
			for (GroupFunction groupFunction : optionalDbGroup.get().getGroupFunctions()) {
				// log the id in each iteration
			}
			Group dbGroup = optionalDbGroup.get();
			// if old group name is NOT same as group name in request
			// i.e. groupName is updated in request, then check if updated groupName already
			// exists
			if (!dbGroup.getGroupName().equals(createGroup.getGroupName().trim())) {
				Group groupByName = groupRepository.findByGroupName(createGroup.getGroupName());
				// if updated groupName already exists, throw exception
				if (groupByName != null) {
					throw new EntityConflictsException(
							String.format("Requested group name [%s] already exists with Id [%d]"));
				}
			}
			// logger.debug("Table exist");
			// remove any ManyToMany mapping in join table i.e. GroupFunction
			BeanUtils.copyProperties(dbGroup, createGroup);
			groupToDB = dbGroup;
			GroupFunction groupFunctions = new GroupFunction();
			groupFunctions.setGroup(groupToDB);
			groupFunctionRepository.deleteByGroupID(createGroup.getId());

		}
	}

	/*
	 * @Override public List<Group> findGroupsByStatus(Boolean status) { // TODO
	 * Auto-generated method stub return null; }
	 * 
	 * @Override public Group updateGroup(Group group) { // TODO Auto-generated
	 * method stub return null; }
	 * 
	 * @Override public Group updateStatus(Group group) { // TODO Auto-generated
	 * method stub return null; }
	 * 
	 */}
