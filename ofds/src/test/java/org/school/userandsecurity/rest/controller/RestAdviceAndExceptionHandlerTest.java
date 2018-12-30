/**
 * 
 */
package org.school.userandsecurity.rest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openframework.common.rest.vo.UserVO;
import org.school.userandsecurity.rest.config.TestMetadataWebConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

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
public class RestAdviceAndExceptionHandlerTest extends BaseControllerTest {

	@Autowired
	private WebApplicationContext ctx;
	private MockMvc mockMvc;

	@Override
	public String getBaseUrl() {
		return "/groups";
	}

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}

	@Test
	public void testInvalidRequestUrl_BadRequest400() throws Exception {
		mockMvc.perform(get(getBaseUrl()+"/invalidUrl").accept("application/vnd.shop.app-v0.1+json")
				.contentType("application/vnd.shop.app-v0.1+json"))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isBadRequest());
	}

	@Test
	public void testInvalidRequestBody_BadRequest400() throws Exception {

		String requestBody = "{\r\n" + "  \"invalidProperty\": \"ProductTest.test\",\r\n"
				+ "  \"invalidDesc\": \"description\"\r\n" + "}";
		System.out.println("requestBody: " + requestBody);
		mockMvc.perform(post(getBaseUrl()).content(requestBody).contentType("application/vnd.shop.app-v0.1+json"))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isBadRequest());
	}

	/**
	 * Update status http method should be PATCH, but is PUT
	 * 
	 * @throws Exception
	 */
	@Test
	public void testHttpMethod_MethodNotAllowed405() throws Exception {
		UserVO ad = createUserVO("UserControllerTest.testUpdateCategory()");
		String requestBody = saveRequestJsonString(ad);
		mockMvc.perform(put(getBaseUrl()).param("uid", "1").accept(APP_JSON)
				.content(requestBody))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isMethodNotAllowed());
	}

	@Test
	public void testInvalidAcceptHeader_NotAcceptable406() throws Exception {
		mockMvc.perform(get(getBaseUrl()).accept("application/vnd.shop.invalid.header"))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isNotAcceptable());
	}

	@Test
	public void testInvalidContentTypeHeader_UnsupportedMediaType415() throws Exception {

		UserVO ad = createUserVO("UserControllerTest.testCreateCategory()");
		String requestBody = saveRequestJsonString(ad);
		System.out.println("requestBody: " + requestBody);
		mockMvc.perform(post(getBaseUrl()).content(requestBody).contentType("application/vnd.shop.invalid.header"))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isUnsupportedMediaType());
	}

	@Test
	public void testFindCategoryById_throwsKeywordNotFoundException() throws Exception {
		mockMvc.perform(get(getBaseUrl()+"/1"))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data").exists());
	}

	@Test
	public void testFindCategoryById_throwsApplicationValidationException() throws Exception {
		mockMvc.perform(get(getBaseUrl()+"/1").accept(APP_JSON))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.data").exists());
	}
}
