/**
 * 
 */
package org.school.userandsecurity.rest.controller;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.management.ServiceNotFoundException;
import javax.validation.Valid;
import org.openframework.common.rest.beans.ResponseBean;
import org.openframework.common.rest.controller.BaseController;
import org.openframework.common.rest.exception.KeywordNotFoundException;
import org.openframework.common.rest.vo.UserVO;
import org.openframework.commons.domain.exceptions.ApplicationValidationException;
import org.school.userandsecurity.service.bo.GroupService;
import org.school.userandsecurity.vo.GroupVO;
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
@RequestMapping("/groups")
@Api(value = "Group Controller", description = "REST APIs related to Group Entity!!!!", consumes = "JSON", produces = "JSON")
public class GroupController extends BaseController {

	@Inject
	private GroupService groupService;

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
	// TODO Check an article if the url "" OR "/" is valid
	@GetMapping(path = { "", "/" })
	public List<GroupVO> findGroups() {
		return groupService.findGroups();
	}

	/**
	 * This is version [application/vnd.shop.app-v0.2+xml,json] of the url /cat,
	 * which returns parent cat.
	 * 
	 * @return
	 */
	@GetMapping(path = { "/status" })
	public List<GroupVO> findGroupsByStatus() {
		return findGroupsByStatus(Boolean.TRUE);
	}

	/**
	 * This is version [application/vnd.shop.app-v0.2+xml,json] of the url /cat,
	 * which returns parent cat.
	 * 
	 * @return
	 */
	@GetMapping(path = "/status/{status}")
	public List<GroupVO> findGroupsByStatus(@PathVariable Boolean status) {
		return groupService.findGroupsByStatus(status);
	}

	@PostMapping
	public ResponseBean<Object> createGroup(@Valid @RequestBody GroupVO groupVO, @ApiIgnore UserVO loggedInUser) {

		groupVO.setLoggedInUserId(loggedInUser.getLoggedInUserId());
		Long id = groupService.createGroup(groupVO);
		return new ResponseBean<>(HttpStatus.CREATED.value(), String.format("Group [%s] created successfully", id));
	}

	@GetMapping(path = "/{id}")
	public GroupVO findGroupById(@PathVariable Long id) throws ServiceNotFoundException {

		if (id == 0) {
			throw new KeywordNotFoundException("The ID is: " + id);
		}
		if (id == -1) {
			List<String> validationErrors = new ArrayList<>();
			validationErrors.add("First Name is required");
			validationErrors.add("Last Name is required");
			throw new ApplicationValidationException("ValidationException caught", validationErrors);
		}
		if (id == -2) {
			throw new ServiceNotFoundException("Custome error caused in doing something");
		}
		return groupService.findGroupById(id);
	}

	@RequestMapping(method = RequestMethod.PUT, path = "/{id}")
	public GroupVO updateGroup(@PathVariable Long id, @RequestBody GroupVO groupVO, @ApiIgnore UserVO userProfile) {
		groupVO.setId(id);
		groupVO.setLoggedInUserId(userProfile.getLoggedInUserId());
		return groupService.updateGroup(groupVO);
	}

	@RequestMapping(method = RequestMethod.PATCH, path = "/{id}/status/{status}")
	public GroupVO updateStatus(@PathVariable Long id, @PathVariable Boolean status, @ApiIgnore UserVO userProfile) {
		GroupVO groupVO = new GroupVO();
		groupVO.setId(id);
		groupVO.setIsValid(status);
		groupVO.setLoggedInUserId(userProfile.getLoggedInUserId());
		return groupService.updateStatus(groupVO);
	}
}
