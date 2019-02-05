package org.school.userandsecurity.service.as.impl;

import java.util.Optional;

import org.openframework.commons.domain.exceptions.EntityNotFoundException;
import org.school.userandsecurity.service.entity.Group;
import org.school.userandsecurity.service.entity.User;
import org.school.userandsecurity.service.repository.GroupRepository;
import org.school.userandsecurity.service.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseASImpl {

	/** Logger that is available to subclasses */
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	public User returnIfEntityExists(Long id, UserRepository baseRepository) {

		Optional<User> user = baseRepository.findById(id);
		if (!user.isPresent()) {
			throw new EntityNotFoundException(String.format("Requested entity %s not found", id));
		} else {
			return user.get();
		}
	}

	public Group returnIfEntityExists(Long id, GroupRepository baseRepository) {

		Optional<Group> group = baseRepository.findById(id);
		if (!group.isPresent()) {
			throw new EntityNotFoundException(String.format("Requested entity %s not found", id));
		} else {
			return group.get();
		}
	}

}
