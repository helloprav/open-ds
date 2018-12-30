/**
 * 
 */
package org.school.userandsecurity.rest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.school.userandsecurity.rest.config.TestMetadataWebConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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
public class FunctionControllerTest extends BaseControllerTest {

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}

	@Test
	public void testFindFunctions() throws Exception {
		mockMvc.perform(get(getBaseUrl()).param("page", "0").accept(SUPPORTED_CONTENT_TYPE)).andExpect(status().isOk())
				.andDo(MockMvcResultHandlers.print()).andExpect(jsonPath("$.data").exists());
	}

	@Override
	public String getBaseUrl() {
		return "/functions";
	}
}
