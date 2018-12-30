/**
 * 
 */
package org.school.userandsecurity.rest.controller;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.openframework.common.rest.annotations.PostMappingConsumesProduces;
import org.openframework.common.rest.controller.BaseController;
import org.openframework.common.rest.vo.UserVO;
import org.school.userandsecurity.constant.UserSecurityConstants;
import org.school.userandsecurity.service.bo.AuthenticationService;
import org.school.userandsecurity.utils.CookieUtils;
import org.school.userandsecurity.vo.UserCredentialsVO;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;

/**
 * 
 * CRUD Operations:
 * 
 * 
 * @author JavaDeveloper
 *
 */
@RestController
@RequestMapping("/auth")
@Api(value = "User Controller", description = "REST APIs related to Security & Authentication!!!!", consumes = "JSON", produces = "JSON")
public class AuthenticationController extends BaseController {

	@Inject
	private AuthenticationService authenticationService;

	@InitBinder
	public void dataBinding(WebDataBinder binder) {
		// do nothing
	}

	@SuppressWarnings("unchecked")
	@PostMappingConsumesProduces("/login")
	public UserVO login(@Valid @RequestBody UserCredentialsVO userVO, HttpServletResponse response) {

		logger.debug("Login Requested");
		Map<String, Object> validUserDetailsMap = authenticationService.authenticateUser(userVO);
		UserVO loggedInUser = (UserVO)validUserDetailsMap.get(UserSecurityConstants.USERVO);

		List<Cookie> loginCookieList = (List<Cookie>)validUserDetailsMap.get(UserSecurityConstants.LOGIN_COOKIE_LIST);
		CookieUtils.createLoginCookies(loginCookieList, response);
		return loggedInUser;
	}

}
