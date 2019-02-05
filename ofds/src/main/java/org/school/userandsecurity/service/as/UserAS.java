package org.school.userandsecurity.service.as;

import java.util.List;

import org.school.userandsecurity.enums.UserRole;
import org.school.userandsecurity.enums.UserStatus;
import org.school.userandsecurity.service.entity.User;

public interface UserAS {

	List<User> findUsersByRoleAndStatus(UserRole role, UserStatus userStatus);

	Long createUser(User user);

	User findUserById(Long id);

	User findUserByEmail(String email);

	User findUserByUsernameOrEmailOrMobile(User user);

	User findUserGroupAndAccess(User user);

	User updateUser(User user);

	/*
	User updateStatus(User user);
	*/
}
