/**
 * 
 */
package org.school.userandsecurity.rest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.school.userandsecurity.rest.config.TestMetadataWebConfig;
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
public class AuthenticationControllerTest extends BaseControllerTest {

	@Test
	public void testLogin_MissingParameters() throws Exception {

		String jsonBody = "{\"userName\" : \"praveen\", \"password\" : \"password123\" }";

		mockMvc.perform(post("/auth/login").content(jsonBody).accept(SUPPORTED_CONTENT_TYPE)
		.contentType(SUPPORTED_CONTENT_TYPE))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.data").doesNotExist());
	}

	@Test
	public void testLogin_InvalidEmailFormat() throws Exception {

		String jsonBody = "{\"email\" : \"praveen\", \"password\" : \"password123\" }";

		mockMvc.perform(post("/auth/login").content(jsonBody).accept(SUPPORTED_CONTENT_TYPE)
		.contentType(SUPPORTED_CONTENT_TYPE))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isBadRequest())
		.andExpect(jsonPath("$.data").doesNotExist());
	}

	@Test
	public void testLogin_InvalidEmailValue() throws Exception {

		String jsonBody = "{\"email\" : \"praveen@invalidEmail.com\", \"password\" : \"password\" }";

		mockMvc.perform(post("/auth/login").content(jsonBody).accept(SUPPORTED_CONTENT_TYPE)
		.contentType(SUPPORTED_CONTENT_TYPE))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isUnauthorized())
		.andExpect(jsonPath("$.data").doesNotExist());
	}

	@Test
	public void testLogin_InvalidPassword() throws Exception {

		String jsonBody = "{\"email\" : \"praveen@email.com\", \"password\" : \"invalidPassword\" }";

		mockMvc.perform(post("/auth/login").content(jsonBody).accept(SUPPORTED_CONTENT_TYPE)
		.contentType(SUPPORTED_CONTENT_TYPE))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isUnauthorized())
		.andExpect(jsonPath("$.data").doesNotExist());
	}

	@Test
	public void testLogin() throws Exception {

		String jsonBody = "{\"email\" : \"praveen@email.com\", \"password\" : \"pass1\" }";

		mockMvc.perform(post("/auth/login").content(jsonBody).accept(SUPPORTED_CONTENT_TYPE)
		.contentType(SUPPORTED_CONTENT_TYPE))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.data").exists());
	}

}
