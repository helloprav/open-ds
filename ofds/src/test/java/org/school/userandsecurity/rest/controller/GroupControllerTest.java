/**
 * 
 */
package org.school.userandsecurity.rest.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openframework.common.rest.constants.ApplicationConstants;
import org.openframework.common.rest.vo.UserVO;
import org.school.userandsecurity.rest.config.TestMetadataWebConfig;
import org.school.userandsecurity.vo.FunctionVO;
import org.school.userandsecurity.vo.GroupVO;
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
public class GroupControllerTest extends BaseControllerTest {

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
	public void testFindGroups() throws Exception {
		mockMvc.perform(get(getBaseUrl()).param("page", "0").accept(SUPPORTED_CONTENT_TYPE)).andExpect(status().isOk())
				.andDo(MockMvcResultHandlers.print()).andExpect(jsonPath("$.data").exists());
	}

	@Test
	public void testFindGroupsByStatusActive() throws Exception {
		mockMvc.perform(get(getBaseUrl().concat("/status")).param("page", "0").accept(SUPPORTED_CONTENT_TYPE)).andExpect(status().isOk())
				.andDo(MockMvcResultHandlers.print()).andExpect(jsonPath("$.data").exists());
	}

	@Test
	public void testFindGroupsByStatusInActive() throws Exception {
		mockMvc.perform(get(getBaseUrl().concat("/status/false")).param("page", "0").accept(SUPPORTED_CONTENT_TYPE)).andExpect(status().isOk())
				.andDo(MockMvcResultHandlers.print()).andExpect(jsonPath("$.data").exists());
	}

	@Test
	public void testFindGroupById_IdNotExists() throws Exception {
		mockMvc.perform(get("/groups/9999999").contentType(SUPPORTED_CONTENT_TYPE)).andExpect(status().isNotFound())
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testFindGroupById() throws Exception {
		mockMvc.perform(get("/groups/1").contentType(SUPPORTED_CONTENT_TYPE)).andExpect(status().isOk())
				.andDo(MockMvcResultHandlers.print()).andExpect(jsonPath("$.data").exists());
	}

	@Test
	public void testFindGroupByIdWithFunctionList() throws Exception {
		mockMvc.perform(get("/groups/1").contentType(SUPPORTED_CONTENT_TYPE))
		.andDo(MockMvcResultHandlers.print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.data.functionList").exists());
	}

	@Test
	public void testCreateGroup_MissingMandatoryFields() throws Exception {
		GroupVO groupVO = createGroupVO("Ctrl.testCreateUser");
		groupVO.setGroupName(null);
		String jsonBody = convertVoToJsonString(groupVO);
		mockMvc.perform(post(getBaseUrl())
					.content(jsonBody)
					.contentType(SUPPORTED_CONTENT_TYPE))
				.andExpect(status()
					.isBadRequest())
				.andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testCreateGroup_IncorrectStringFieldsSize() throws Exception {
		GroupVO groupVO = createGroupVO(MORE_THAN_50_CHAR_LONG_STRING);
		groupVO.setGroupName(null);
		groupVO.setDescription(getStringForLength(501));
		System.out.println("des: "+groupVO.getDescription().length());
		String jsonBody = convertVoToJsonString(groupVO);
		mockMvc.perform(post(getBaseUrl()).content(jsonBody).contentType(SUPPORTED_CONTENT_TYPE))
				.andExpect(status().isBadRequest()).andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testCreateGroup() throws Exception {
		GroupVO groupVO = createGroupVO("Ctrl.testCreateGroup");
		List<FunctionVO> functionVOList = new ArrayList<>();
		functionVOList.add(new FunctionVO(1l));
		functionVOList.add(new FunctionVO(3l));
		groupVO.setFunctionList(populateFunctionVOList(1l, 3l));
		String jsonBody = convertVoToJsonString(groupVO);
		mockMvc.perform(post(getBaseUrl()).content(jsonBody).contentType(SUPPORTED_CONTENT_TYPE)
				.requestAttr(ApplicationConstants.SPRING_TEST_ENV, true))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isCreated());
	}
	private List<FunctionVO> populateFunctionVOList(long ...id) {
		// TODO Auto-generated method stub
		List<FunctionVO> functionVOList = new ArrayList<>();
		for(int i=0; i<id.length; i++) {
			functionVOList.add(new FunctionVO(id[i]));
		}
		return functionVOList;
	}

	@Test
	public void testFindUserById_InvalidId() throws Exception {
		mockMvc.perform(get("/users/invalidId").contentType(SUPPORTED_CONTENT_TYPE)).andExpect(status().isBadRequest())
				.andDo(MockMvcResultHandlers.print());
	}

	// @Test
	public void testCreateGroup_InvalidEmail() throws Exception {
		UserVO user = createUserVO("Ctrl.testCreateUser");
		user.setEmail("Invalid_Email");
		String jsonBody = convertVoToJsonString(user);
		mockMvc.perform(post("/users").content(jsonBody).contentType(SUPPORTED_CONTENT_TYPE))
				.andExpect(status().isBadRequest()).andDo(MockMvcResultHandlers.print());
	}

	@Test
	public void testUpdateGroup() throws Exception {
		GroupVO groupVO = createGroupVO("Test Update");
		groupVO.setFunctionList(populateFunctionVOList(1l, 3l));
		String jsonBody = convertVoToJsonString(groupVO);
		mockMvc.perform(put(getBaseUrl().concat("/14")).content(jsonBody).contentType(SUPPORTED_CONTENT_TYPE)
				.requestAttr(ApplicationConstants.SPRING_TEST_ENV, true))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(status().isOk());
	}

	/**
	 * http://www.programcreek.com/java-api-examples/index.php?api=org.springframework.test.web.servlet.request.MockMvcRequestBuilders
	 * http://www.programcreek.com/java-api-examples/index.php?source_dir=spring-rest-black-market-master/src/test/java/org/vtsukur/spring/rest/market/AdsHttpApiTests.java
	 * 
	 * @throws Exception
	 */
	// @Test
}
