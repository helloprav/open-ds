package org.school.userandsecurity.service.as.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.openframework.commons.domain.exceptions.EntityConflictsException;
import org.school.userandsecurity.enums.UserRole;
import org.school.userandsecurity.enums.UserStatus;
import org.school.userandsecurity.service.as.UserAS;
import org.school.userandsecurity.service.entity.User;
import org.school.userandsecurity.service.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserASImpl extends BaseASImpl implements UserAS {

	@Inject
	private UserRepository userRepository;

	@Override
	public List<User> findUsersByRoleAndStatus(UserRole role, UserStatus userStatus) {
		if (null == userStatus && null == role) {
			return userRepository.findAll();
		} else {
			if (null == userStatus) {
				return userRepository.findByRole(role);
			} else {
				return userRepository.findByRoleAndStatus(role, userStatus);
			}
		}
	}

	@Override
	public Long createUser(User user) {
		user.setId(null);
		//checkIfAdmissionNoExists(user.getAdmissionNo());
		user.setCreatedDate(new Date());
		user = userRepository.save(user);
		return user.getId();
	}

	@SuppressWarnings("unused")
	private void checkIfAdmissionNoExists(Long admissionNo) {

		List<User> users = userRepository.admissionNoExists(admissionNo);
		if (!users.isEmpty()) {
			throw new EntityConflictsException(String.format("Requested admission no %d already register for User %d",
					admissionNo, users.get(0).getId()));
		}
	}

	@Override
	public User findUserById(Long id) {

		return returnIfEntityExists(id, userRepository);
	}

	@Override
	public User findUserByEmail(String email) {

		List<User> users = userRepository.findByEmail(email);
		if(users.isEmpty()) {
			return null;
		} else {
			return users.get(org.openframework.common.rest.constants.NumberConstants.NUM_ZERO);
		}
	}

	@Override
	public User findUserGroupAndAccess(User user) {

		return userRepository.findUserGroupsAndFunctions(user.getId());
	}

	@Override
	public User updateUser(User user) {
		//checkIfAdmissionNoExists(user.getAdmissionNo());
		user.setModifiedDate(new Date());
		user = userRepository.save(user);
		return user;
	}

	@Override
	public User findUserByUsernameOrEmailOrMobile(User user) {

		List<User> users = userRepository.findByUsernameOrEmailOrMobile(user.getEmail(), user.getUsername(), user.getMobile());
		if(users.isEmpty()) {
			return null;
		} else if(users.size()>1) {
			logger.error("Multiple users found for the user []", user);
			return null;
		} else {
			return users.get(org.openframework.common.rest.constants.NumberConstants.NUM_ZERO);
		}
	}

	/*
	@Override
	public User updateStatus(User user) {
		// TODO Auto-generated method stub
		return null;
	}
	*/
}
