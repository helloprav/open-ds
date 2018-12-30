package org.school.userandsecurity.service.adaptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.openframework.common.rest.vo.UserVO;
import org.school.userandsecurity.enums.Gender;
import org.school.userandsecurity.enums.UserRole;
import org.school.userandsecurity.enums.UserStatus;
import org.school.userandsecurity.service.entity.User;
import org.school.userandsecurity.service.entity.UserGroup;
import org.school.userandsecurity.utils.EnumUtility;
import org.school.userandsecurity.vo.GroupVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserAdaptor {

	@Autowired
	private GroupAdaptor groupAdaptor;

	public List<UserVO> toVO(List<User> users) {

		List<UserVO> productCategoryVOList = new ArrayList<>();
		ListIterator<User> listIterator = users.listIterator();
		while (listIterator.hasNext()) {
			User productCategory = listIterator.next();
			UserVO productCategoryVO = toVO(productCategory);
			productCategoryVOList.add(productCategoryVO);
		}
		return productCategoryVOList;
	}

	public UserVO toVO(User user) {
		UserVO userVO = new UserVO();
		BeanUtils.copyProperties(user, userVO);
		updateFromEnumTypesToVO(user, userVO);
		updateGroupAndFunction(user, userVO);
		return userVO;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void updateGroupAndFunction(User user, UserVO userVO) {

		List<GroupVO> groupList = new ArrayList<>();
		if (null != user.getUserGroups()) {
			List<UserGroup> userGroups = user.getUserGroups();
			ListIterator<UserGroup> iterator = userGroups.listIterator();
			while (iterator.hasNext()) {
				UserGroup userGroup = iterator.next();
				GroupVO groupVO = groupAdaptor.toVO(userGroup.getGroup());
				groupList.add(groupVO);
			}
		}
		Map map = new HashMap<>();
		map.put("groupList", groupList);
		userVO.setOtherData(map);
	}

	public User fromVO(UserVO userVO) {
		User user = new User();
		BeanUtils.copyProperties(userVO, user);
		if(null!=userVO.getPassword()) {
			Character[] passwd = new Character[userVO.getPassword().length];
			for(int i=0; i<userVO.getPassword().length; i++) {
				passwd[i] = new Character(userVO.getPassword()[i]);
			}
			user.setPassword(passwd);
		}
		updateEnumTypesFromVO(userVO, user);
		return user;
	}

	private void updateEnumTypesFromVO(UserVO userVO, User user) {

		user.setGender((Gender) EnumUtility.getEnumConstant(Gender.class, userVO.getGender()));
		user.setRole((UserRole) EnumUtility.getEnumConstant(UserRole.class, userVO.getRole()));
		user.setStatus((UserStatus) EnumUtility.getEnumConstant(UserStatus.class, userVO.getStatus()));
	}

	private void updateFromEnumTypesToVO(User user, UserVO userVO) {

		System.out.println(EnumUtility.getEnumString(Gender.class, user.getGender()));
		userVO.setGender(user.getGender() == null ? null : user.getGender().toString());
		userVO.setRole(user.getRole() == null ? null : user.getRole().toString());
		userVO.setStatus(user.getStatus() == null ? null : user.getStatus().toString());
	}

}
