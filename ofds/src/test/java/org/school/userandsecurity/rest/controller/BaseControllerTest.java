package org.school.userandsecurity.rest.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.openframework.common.rest.constants.ApplicationConstants;
import org.openframework.common.rest.vo.UserVO;
import org.openframework.commons.config.constants.AppConstants;
import org.school.userandsecurity.vo.FunctionVO;
import org.school.userandsecurity.vo.GroupVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BaseControllerTest {

	public static final String MORE_THAN_50_CHAR_LONG_STRING = "org.springframework.web.util.NestedServletException";
	public static final String APP_JSON = MediaType.APPLICATION_JSON_VALUE;
	public static final String SUPPORTED_CONTENT_TYPE = MediaType.APPLICATION_JSON_VALUE;


	@Autowired
	protected WebApplicationContext ctx;
	protected MockMvc mockMvc;

	@Rule
	public TestName testName = new TestName();

	static {
		System.setProperty(AppConstants.SHARED_PATH, "D:\\Work\\Repository\\JavaTechPoc\\school-parent\\school-user-security\\src\\main\\resources\\env-config\\dev");
	}

	@Before
	public void setUpBase() {
		System.out.println("\n-------------------------------------" + this.getClass().getSimpleName()+"."+testName.getMethodName());
		//String[] userProfileHandlerMethodArgumentResolver = this.ctx.getBeanNamesForType(UserProfileHandlerMethodArgumentResolver.class);
		//System.out.println(userProfileHandlerMethodArgumentResolver);
		BeanDefinitionRegistry registry = (BeanDefinitionRegistry) ctx.getAutowireCapableBeanFactory();
	    for(String beanName : ctx.getBeanDefinitionNames()){
	    	if("userProfileHandlerMethodArgumentResolver".equals(beanName)) {
	    		registry.removeBeanDefinition("userProfileHandlerMethodArgumentResolver");
	    	}
	        //System.out.println(beanName);
	        //registry.removeBeanDefinition(beanName);
	    }
		this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}

	public static String saveRequestJsonString(UserVO userVO) {
		StringBuilder sb = new StringBuilder().append("{\n ");
		if (null != userVO.getFirstName()) {
			sb.append("  \"firstName\": \"" + userVO.getFirstName() + "\" ");
		}
		if (null != userVO.getDescription()) {
			if (sb.toString().endsWith(" ")) {
				sb.append(",\n ");
			}
			sb.append("  \"description\": \"" + userVO.getDescription() + "\" ");
		}
		sb.append("}");
		return sb.toString();
		// return "{\n" + " \"name\": \"" + ad.getName() + "\",\n" + " \"description\":
		// \"" + ad.getDescription()+ "\"\n" + "}";
	}

	public UserVO createUserVO(String pcName) {
		UserVO userVO = new UserVO();
		userVO.setFirstName("Praveen Kumar");
		userVO.setLastName("Mishra");
//		userVO.setAdmissionNo(20000172L);
		userVO.setEmail("praveen@email.com");
		userVO.setGender("male");
		userVO.setMobile("9900990099");
		userVO.setPhone("+91 80 4463 1280");
		userVO.setPassword("secret_password".toCharArray());
		userVO.setRole("student");
		userVO.setStatus("active");
		userVO.setId(1L);
		userVO.setIsValid(true);
		userVO.setDescription("This is a very good boy");
		return userVO;
	}

	public GroupVO createGroupVO(String groupName) {
		long random = getRandomNumber();
		GroupVO groupVO = new GroupVO();
		if(null == groupName) {
			groupVO.setGroupName("My Test Group "+random);
		} else {
			groupVO.setGroupName(groupName+random);
		}
		groupVO.setDescription("My Test Group Description");
		groupVO.setIsValid(true);
		groupVO.setFunctionList(createFunctionList());
		return groupVO;
	}

	private List<FunctionVO> createFunctionList() {

		List<FunctionVO> functionVOs = new ArrayList<>();
		for(int i=0; i<2; i++) {
			FunctionVO functionVO = createFunctionVO(i, "My-test-function-"+ ++i);
			functionVOs.add(functionVO);
		}
		return functionVOs;
	}

	private FunctionVO createFunctionVO(int i, String functionName) {

		FunctionVO functionVO = new FunctionVO((long)i);
		functionVO.setName(functionName);
		return functionVO;
	}

	public String convertVoToJsonString(Object obj) {

		String jsonBody = "";
		ObjectMapper mapper = new ObjectMapper();
		try {
			jsonBody = mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println("jsonBody: " + jsonBody);
		return jsonBody;
	}

	protected String getBaseUrl() {
		return "/";
	}

	protected String getStringForLength(int length) {
		StringBuffer sb = new StringBuffer(MORE_THAN_50_CHAR_LONG_STRING);
		for (int i=0; i<=length/MORE_THAN_50_CHAR_LONG_STRING.length(); i++) {
			sb.append(MORE_THAN_50_CHAR_LONG_STRING);
		}
		return sb.toString();
	}

	protected int getRandomNumber() {

		Random rand = new Random();
		return rand.nextInt(Integer.MAX_VALUE) + 1;
	}
}
