/**
 * 
 */
package org.school.userandsecurity.rest.controller;

import java.util.List;

import javax.inject.Inject;

import org.openframework.common.rest.controller.BaseController;
import org.school.userandsecurity.service.bo.FunctionService;
import org.school.userandsecurity.vo.FunctionVO;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
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
@RequestMapping("/functions")
@Api(value = "Function Controller", description = "REST APIs related to Function Entity!!!!", consumes = "JSON", produces = "JSON")
public class FunctionController extends BaseController {

	@Inject
	private FunctionService functionService;

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
	@GetMapping(path = { "", "/" })
	public List<FunctionVO> findFunctions() {
		return functionService.findFunctions();
	}

}
