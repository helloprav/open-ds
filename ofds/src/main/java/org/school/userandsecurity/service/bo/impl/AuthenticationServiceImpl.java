/**
 * 
 */
package org.school.userandsecurity.service.bo.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.Cookie;

import org.apache.commons.lang3.StringUtils;
import org.openframework.common.rest.service.impl.BaseServiceImpl;
import org.openframework.common.rest.vo.UserVO;
import org.openframework.commons.domain.exceptions.ApplicationValidationException;
import org.openframework.commons.domain.exceptions.AuthenticationException;
import org.school.userandsecurity.constant.UserSecurityConstants;
import org.school.userandsecurity.constant.CookieConstants;
import org.school.userandsecurity.service.adaptor.UserAdaptor;
import org.school.userandsecurity.service.as.UserAS;
import org.school.userandsecurity.service.bo.AuthenticationService;
import org.school.userandsecurity.service.entity.User;
import org.school.userandsecurity.utils.CookieUtils;
import org.school.userandsecurity.utils.EncryptionUtil;
import org.school.userandsecurity.vo.UserCredentialsVO;
import org.springframework.stereotype.Service;

/**
 * @author Java Developer
 *
 */
@Service
public class AuthenticationServiceImpl extends BaseServiceImpl implements AuthenticationService {

	@Inject
	private UserAS userAS;

	@Inject
	private UserAdaptor userAdaptor;

	@Inject
	EncryptionUtil encryptionUtil;

	@Override
	public Map<String, Object> authenticateUser(UserCredentialsVO userCredentialsVO) {

		UserVO userVO = validateUserCrenetials(userCredentialsVO);

		Map<String, Object> validUserDetailsMap = new HashMap<>();
		validUserDetailsMap.put(UserSecurityConstants.USERVO, userVO);

		validUserDetailsMap.put(UserSecurityConstants.LOGIN_COOKIE_LIST, getLoginCookieList(userVO));
		return validUserDetailsMap;
	}

	private List<Cookie> getLoginCookieList(UserVO userVO) {

		List<Cookie> loginCookieList = new ArrayList<>();
		String loggedInUserCookieValue = CookieUtils.getLoggedInUserCookieValue(userVO);
		Cookie uid = CookieUtils.createCookie(CookieConstants.COOKIE_UID, Long.toString(userVO.getId()));
		Cookie liu = CookieUtils.createCookie(CookieConstants.COOKIE_LIU,
				encryptionUtil.encrypt(loggedInUserCookieValue));
		loginCookieList.add(uid);
		loginCookieList.add(liu);

		return loginCookieList;
	}

	private UserVO validateUserCrenetials(UserCredentialsVO userCredentialsVO) {

		validateLoginParameters(userCredentialsVO);
		boolean loginSuccess = false;
		UserVO userVO = null;

		User user = getUserByUsername(userCredentialsVO);
		// if user entered email
		if (checkIfValidUser(user) && isValidPassword(userCredentialsVO, user)) {
			loginSuccess = true;
		}
		if (loginSuccess) {
			userVO = userAdaptor.toVO(user);
		} else {
			throw new AuthenticationException("Invalid Credentials");
		}

		return userVO;
	}

	private void validateLoginParameters(UserCredentialsVO userCredentialsVO) {

		if (StringUtils.isBlank(userCredentialsVO.getEmail()) && StringUtils.isBlank(userCredentialsVO.getMobile())) {
			throw new ApplicationValidationException("email or mobile is required");
		}
	}

	private boolean isValidPassword(UserCredentialsVO userVO, User user) {

		return true;
		//return (Arrays.toString(user.getPassword()).equals(Arrays.toString(userVO.getPassword())));
	}

	private User getUserByUsername(UserCredentialsVO userVO) {

		User user = null;
		if (StringUtils.isNotBlank(userVO.getEmail())) {
			user = userAS.findUserByEmail(userVO.getEmail());
		} else if (StringUtils.isNotBlank(userVO.getMobile())) {
			user = userAS.findUserByEmail(userVO.getEmail());
		}
		return user;
	}

	private boolean checkIfValidUser(User user) {
		boolean validUser = true;
		if (null == user || null == user.getPassword()) {
			validUser = false;
		}
		return validUser;
	}

}
