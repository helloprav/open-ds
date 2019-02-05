/**
 * 
 */
package org.school.userandsecurity.service.bo.impl;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
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
import org.openframework.commons.utils.HashingUtils;
import org.school.userandsecurity.constant.CookieConstants;
import org.school.userandsecurity.constant.UserSecurityConstants;
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

		User user = findByUsernameOrEmailOrMobile(userCredentialsVO);
		// if user entered email
		if (checkIfValidUser(user) && isValidPassword(userCredentialsVO, user)) {
			loginSuccess = true;
		}
		if (loginSuccess) {
			userVO = userAdaptor.toVO(user);
		} else {
			throw new ApplicationValidationException("Invalid Credentials. Please enter correct username and password.");
		}

		return userVO;
	}

	/**
	 * Validates that 1) both of email & mobile are not empty, 2) Password is not
	 * empty
	 * 
	 * @param userCredentialsVO
	 */
	private void validateLoginParameters(UserCredentialsVO userCredentialsVO) {

		boolean usernameMissing = StringUtils.isBlank(userCredentialsVO.getUsername());
		boolean passwordMissing = null == userCredentialsVO.getPassword() || StringUtils.isBlank(String.valueOf(userCredentialsVO.getPassword()));

		if((usernameMissing || passwordMissing)) {
			throw new ApplicationValidationException("username or email or mobile and password is required");
		}
	}

	private boolean isValidPassword(UserCredentialsVO userVO, User user) {

		String hashedPassword;
		try {
			String plainMessage = String.valueOf(userVO.getPassword());
			System.out.println("plainMessage: "+plainMessage);
			hashedPassword = HashingUtils.sha1(plainMessage);
			System.out.println("hashedPassword: "+hashedPassword);
			return hashedPassword.equals(user.getPassword());
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			// TODO log error here.
			throw new ApplicationValidationException("There is some error matching password");
		}
		//return (Arrays.toString(user.getPassword()).equals(Arrays.toString(userVO.getPassword())));
	}

	private User findByUsernameOrEmailOrMobile(UserCredentialsVO userVO) {

		return userAS.findUserByUsernameOrEmailOrMobile(userAdaptor.fromVO(userVO));
	}

	private boolean checkIfValidUser(User user) {
		boolean validUser = true;
		if (null == user || null == user.getPassword()) {
			validUser = false;
		}
		return validUser;
	}

}
