/**
 * 
 */
package org.school.userandsecurity.service.bo;

import java.util.List;

import org.openframework.common.rest.vo.UserVO;
import org.school.userandsecurity.enums.UserRole;
import org.school.userandsecurity.enums.UserStatus;

/**
 * @author Java Developer
 *
 */
public interface UserService {

	List<UserVO> findUsersByRoleAndStatus(UserRole role, UserStatus userStatus);

	Long createUser(UserVO userVO);

	UserVO findUserById(Long id);

	UserVO updateUser(UserVO userVO);

	/*
	UserVO updateStatus(UserVO userVO);
	*/
}
