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
import org.openframework.common.rest.constants.ApplicationConstants;
import org.openframework.common.rest.vo.UserVO;
import org.school.userandsecurity.config.SpringWebApplicationInitializer;
import org.school.userandsecurity.rest.config.TestMetadataWebConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class UserControllerTest extends BaseControllerTest {

	private static final Logger loggers = LoggerFactory.getLogger(SpringWebApplicationInitializer.class);

	// private static int setUpCounter = 0;
	private static int testCounter = 0;
	private static boolean done = true;

	static {
		System.out.println("UserControllerTest.static{} " + testCounter);
		loggers.debug("UserControllerTest.static{} " + testCounter);
	}

	@Autowired
	private WebApplicationContext ctx;
	private MockMvc mockMvc;

	@Before
	public void setUp() {
		if (!done) {
			printBeans();
		}
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}

	private void printBeans() {
		// TODO Auto-generated method stub
		String[] beanNames = ctx.getBeanDefinitionNames();
		for (String beanName : beanNames) {
			System.out.println("\n\n" + beanName + ":" + ctx.getBean(beanName));
		}
		done = true;
	}

	protected String getBaseUrl() {
		return "/users";
	}

	@Test
	public void testFindUserBy_InvalidRole() throws Exception {
		mockMvc.perform(get("/users/roles/invalidRole").accept(SUPPORTED_CONTENT_TYPE))
				.andExpect(status().isBadRequest()).andDo(MockMvcResultHandlers.print())
				.andExpect(jsonPath("$.data").doesNotExist());
	}

	@Test
	public void testFindUserByStudentsRole() throws Exception {
		mockMvc.perform(get("/users/roles/student").param("page", "0").accept(SUPPORTED_CONTENT_TYPE)
				.requestAttr(ApplicationConstants.SPRING_TEST_ENV, true)).andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk()).andExpect(jsonPath("$.data").exists());
	}

	@Test
	public void testFindUserByStudentsRoleAnd_InvalidStatus() throws Exception {
		mockMvc.perform(get("/users/roles/student/status/invalid").accept(SUPPORTED_CONTENT_TYPE))
				.andExpect(status().isBadRequest()).andDo(MockMvcResultHandlers.print())
				.andExpect(jsonPath("$.data").doesNotExist());
	}

	@Test
	public void testFindUserByStudentsRoleAndActiveStatus() throws Exception {
		mockMvc.perform(get("/users/roles/student/status/active").accept(SUPPORTED_CONTENT_TYPE))
				.andExpect(status().isOk()).andDo(MockMvcResultHandlers.print()).andExpect(jsonPath("$.data").exists());
	}

	@Test
	public void testCreateUser_InvalidGenderEnumValue() throws Exception {
		UserVO user = createUserVO("Ctrl.testCreateUser");
		user.setGender("Praveen");
		user.setStatus(null);
		user.setRole(null);
		String jsonBody = convertVoToJsonString(user);
		mockMvc.perform(post("/users").content(jsonBody).contentType(SUPPORTED_CONTENT_TYPE))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isBadRequest());
	}

	@Test
	public void testCreateUser_MissingMandatoryFields() throws Exception {
		UserVO user = createUserVO("Ctrl.testCreateUser");
		user.setFirstName(null);
		user.setGender(null);
		user.setStatus(null);
		user.setRole(null);
		String jsonBody = convertVoToJsonString(user);
		mockMvc.perform(post("/users").content(jsonBody).contentType(SUPPORTED_CONTENT_TYPE))
				.andExpect(status().isBadRequest()).andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testCreateUser_IncorrectStringFieldsSize() throws Exception {
		UserVO user = createUserVO("Ctrl.testCreateUser");
		user.setFirstName("Raj");
		user.setLastName(MORE_THAN_50_CHAR_LONG_STRING);
		user.setEmail(MORE_THAN_50_CHAR_LONG_STRING);
		user.setMobile(MORE_THAN_50_CHAR_LONG_STRING);
		user.setPhone(MORE_THAN_50_CHAR_LONG_STRING);
		String jsonBody = convertVoToJsonString(user);
		mockMvc.perform(post("/users").content(jsonBody).contentType(SUPPORTED_CONTENT_TYPE))
				.andExpect(status().isBadRequest()).andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testCreateUser_IncorrectIntegerFieldsSize() throws Exception {
		UserVO user = createUserVO("Ctrl.testCreateUser");
		user.setAdmissionNo(19999999l);
		String jsonBody = convertVoToJsonString(user);
		mockMvc.perform(post("/users").content(jsonBody).contentType(SUPPORTED_CONTENT_TYPE))
				.andExpect(status().isBadRequest()).andDo(MockMvcResultHandlers.print());
	}

	//@Test
	public void testCreateUser_UniqueAdmissionNo() throws Exception {
		UserVO user = createUserVO("Ctrl.testCreateUser");
		user.setAdmissionNo(20000000l);
		String jsonBody = convertVoToJsonString(user);
		mockMvc.perform(post("/users").content(jsonBody).contentType(SUPPORTED_CONTENT_TYPE))
				.andExpect(status().isBadRequest()).andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testCreateUser() throws Exception {
		UserVO user = createUserVO("Ctrl.testCreateUser");
		String jsonBody = convertVoToJsonString(user);
		mockMvc.perform(post("/users").content(jsonBody).contentType(SUPPORTED_CONTENT_TYPE))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isCreated());
	}

	@Test
	public void testFindUserById_InvalidId() throws Exception {
		mockMvc.perform(get("/users/invalidId").contentType(SUPPORTED_CONTENT_TYPE)).andExpect(status().isBadRequest())
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testCreateUser_InvalidEmail() throws Exception {
		UserVO user = createUserVO("Ctrl.testCreateUser");
		user.setEmail("Invalid_Email");
		String jsonBody = convertVoToJsonString(user);
		mockMvc.perform(post("/users").content(jsonBody).contentType(SUPPORTED_CONTENT_TYPE))
				.andExpect(status().isBadRequest()).andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testFindUserById_IdNotExists() throws Exception {
		mockMvc.perform(get("/users/9999999").contentType(SUPPORTED_CONTENT_TYPE)).andExpect(status().isNotFound())
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testFindUserById() throws Exception {
		mockMvc.perform(get("/users/3").contentType(SUPPORTED_CONTENT_TYPE))
			.andDo(MockMvcResultHandlers.print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data").exists());
	}

	@Test
	public void testUpdateUser() throws Exception {
		UserVO user = createUserVO("Ctrl.testUpdateUser");
		String jsonBody = convertVoToJsonString(user);
		mockMvc.perform(put(getBaseUrl() + "/3").content(jsonBody).contentType(SUPPORTED_CONTENT_TYPE)
				.requestAttr(ApplicationConstants.SPRING_TEST_ENV, true)).andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk());
	}

	/**
	 * http://www.programcreek.com/java-api-examples/index.php?api=org.springframework.test.web.servlet.request.MockMvcRequestBuilders
	 * http://www.programcreek.com/java-api-examples/index.php?source_dir=spring-rest-black-market-master/src/test/java/org/vtsukur/spring/rest/market/AdsHttpApiTests.java
	 * 
	 * @throws Exception
	 */
	//@Test
}
