/**
 * 
 */
package org.school.userandsecurity.rest.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.school.userandsecurity.config.SpringWebApplicationInitializer;
import org.school.userandsecurity.rest.config.TestMetadataWebConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

/**
 * 
 * CRUD Operations:
 * 
 * 
 * @author pmis30
 *
 */

@TestMetadataWebConfig
@RunWith(SpringJUnit4ClassRunner.class)
public class ValidValuesControllerTest extends BaseControllerTest {

	private static final Logger loggers = LoggerFactory.getLogger(SpringWebApplicationInitializer.class);

	static {
		System.out.println("ValidValuesControllerTest.static{} ");
		loggers.debug("ValidValuesControllerTest.static{} ");
	}

	@Test
	public void testFindAddressTypes() throws Exception {
		mockMvc.perform(get("/validvalues/addresstypes").accept(SUPPORTED_CONTENT_TYPE)).andExpect(status().isOk())
				.andDo(MockMvcResultHandlers.print()).andExpect(jsonPath("$[0]", is("present")))
				.andExpect(jsonPath("$[1]", is("permanent")));
	}

	@Test
	public void testFindGenders() throws Exception {
		mockMvc.perform(get("/validvalues/genders").accept(SUPPORTED_CONTENT_TYPE)).andExpect(status().isOk())
				.andDo(MockMvcResultHandlers.print()).andExpect(jsonPath("$[0]", is("male")))
				.andExpect(jsonPath("$[1]", is("female")));
	}

	@Test
	public void testFindRelationTypes() throws Exception {
		mockMvc.perform(get("/validvalues/relationtypes").accept(SUPPORTED_CONTENT_TYPE)).andExpect(status().isOk())
				.andDo(MockMvcResultHandlers.print()).andExpect(jsonPath("$[0]", is("mother")))
				.andExpect(jsonPath("$[1]", is("father")));
	}

	@Test
	public void testFindUserRoles() throws Exception {
		mockMvc.perform(get("/validvalues/userroles").accept(SUPPORTED_CONTENT_TYPE)).andExpect(status().isOk())
				.andDo(MockMvcResultHandlers.print()).andExpect(jsonPath("$[0]", is("student")))
				.andExpect(jsonPath("$[1]", is("teacher")));
	}

	@Test
	public void testFindUserStatuses() throws Exception {
		mockMvc.perform(get("/validvalues/userstatuses").accept(SUPPORTED_CONTENT_TYPE)).andExpect(status().isOk())
				.andDo(MockMvcResultHandlers.print()).andExpect(jsonPath("$[0]", is("active")))
				.andExpect(jsonPath("$[1]", is("suspended")));
	}

}