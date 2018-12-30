/**
 * 
 */
package org.school.userandsecurity.service.bo.impl;

import java.util.List;

import javax.inject.Inject;

import org.openframework.common.rest.service.impl.BaseServiceImpl;
import org.openframework.common.rest.vo.UserVO;
import org.school.userandsecurity.enums.UserRole;
import org.school.userandsecurity.enums.UserStatus;
import org.school.userandsecurity.service.adaptor.UserAdaptor;
import org.school.userandsecurity.service.as.UserAS;
import org.school.userandsecurity.service.bo.UserService;
import org.school.userandsecurity.service.entity.User;
import org.springframework.stereotype.Service;


/**
 * @author Java Developer
 *
 */
@Service
public class UserServiceImpl extends BaseServiceImpl implements UserService {

	@Inject
	private UserAS userAS;

	@Inject
	private UserAdaptor userAdaptor;

	@Override
	public List<UserVO> findUsersByRoleAndStatus(UserRole role, UserStatus userStatus) {
		/*if(null == role) {
			role = UserRole.student;
		}*/
		List<User> users = userAS.findUsersByRoleAndStatus(role, userStatus);
		return userAdaptor.toVO(users);
	}

	@Override
	public Long createUser(UserVO userVO) {

		return userAS.createUser(userAdaptor.fromVO(userVO));
	}

	@Override
	public UserVO findUserById(Long id) {
		return userAdaptor.toVO(userAS.findUserById(id));
	}

	@Override
	public UserVO updateUser(UserVO userVO) {
		return userAdaptor.toVO(userAS.updateUser(userAdaptor.fromVO(userVO)));
	}

	/*
	@Override
	public GroupVO updateStatus(GroupVO groupVO) {
		// TODO Auto-generated method stub
		return null;
	}
	*/
}
