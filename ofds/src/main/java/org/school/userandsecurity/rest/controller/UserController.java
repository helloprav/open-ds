/**
 * 
 */
package org.school.userandsecurity.rest.controller;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.openframework.common.rest.annotations.GetMappingProduces;
import org.openframework.common.rest.beans.ResponseBean;
import org.openframework.common.rest.controller.BaseController;
import org.openframework.common.rest.vo.UserVO;
import org.school.userandsecurity.enums.UserRole;
import org.school.userandsecurity.enums.UserStatus;
import org.school.userandsecurity.service.bo.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 
 * CRUD Operations:
 * 
 * 
 * @author JavaDeveloper
 *
 */
@RestController
@RequestMapping("/users")
@Api(value = "User Controller", description = "REST APIs related to Group Entity!!!!", consumes = "JSON", produces = "JSON")
public class UserController extends BaseController {

	@Inject
	private UserService userService;

	@InitBinder
	public void dataBinding(WebDataBinder binder) {
		// do nothing
	}

	/**
	 * This is version [application/vnd.shop.app-v0.1+xml,json] of the url /cat,
	 * which returns all cat
	 * 
	 * @return
	 */
	@GetMappingProduces ("")
	public List<UserVO> findUsers(@ApiIgnore UserVO userProfile) {
		System.out.println("User Email: "+userProfile.getEmail());
		return findUsersByRoleAndStatus(null, null);
	}

	//@SecuredPermissions("student")
	@GetMappingProduces ("/roles/{role}")
	public List<UserVO> findUsersByRole(@PathVariable UserRole role, @ApiIgnore UserVO userProfile) {
		System.out.println("User Email: "+userProfile.getEmail());
		return findUsersByRoleAndStatus(role, null);
	}

	@GetMappingProduces ("/roles/{role}/status/{userStatus}")
	public List<UserVO> findUsersByRoleAndStatus(@PathVariable UserRole role, @PathVariable UserStatus userStatus) {
		return userService.findUsersByRoleAndStatus(role, userStatus);
	}

	@PostMapping({"/register", ""})
	public ResponseBean<Object> createUser(@Valid @RequestBody UserVO userVO) {

		if(null == userVO.getStatus()) {
			userVO.setStatus("active");
		}
		if(null == userVO.getGender()) {
			userVO.setGender("male");
		}
		if(null == userVO.getRole()) {
			userVO.setRole("student");
		}
		Long id = userService.createUser(userVO);
		return new ResponseBean<>(HttpStatus.CREATED.value(), String.format("User %s created successfully", id));
	}

	@GetMapping(path = "/{id}")
	public UserVO findUserById(@PathVariable Long id) {

		return userService.findUserById(id);
	}

	@RequestMapping(method = RequestMethod.PUT, path = "/{id}")
	public UserVO updateUser(@PathVariable Long id, @RequestBody UserVO userVO, @ApiIgnore UserVO userProfile) {
		userVO.setId(id);
		userVO.setLoggedInUserId(userProfile.getLoggedInUserId());
		return userService.updateUser(userVO);
	}

	/*
	@RequestMapping(method = RequestMethod.PATCH, path = "/{id}/status/{status}")
	public GroupVO updateStatus(@PathVariable Long id, @PathVariable Boolean status, @ApiIgnore UserVO userProfile) {
		GroupVO groupVO = new GroupVO();
		groupVO.setId(id);
		groupVO.setValid(status);
		groupVO.setLoggedInUserId(userProfile.getLoggedInUserId());
		return userService.updateStatus(groupVO);
	}
	*/
}
