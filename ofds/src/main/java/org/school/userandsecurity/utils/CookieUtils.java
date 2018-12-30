package org.school.userandsecurity.utils;

import java.util.List;
import java.util.ListIterator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.openframework.common.rest.vo.UserVO;
import org.school.userandsecurity.constant.CookieConstants;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CookieUtils {

	private CookieUtils() {
		throw new IllegalStateException("Utility class");
	}

	public static String getLoggedInUserCookieValue(UserVO userVO) {

		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		String value;
		try {
			value = objectMapper.writeValueAsString(userVO);
		} catch (JsonProcessingException e) {
			value = StringUtils.EMPTY;
		}
		return value;
	}

	public static Cookie createCookie(String name, String value) {

		Cookie cookie = new Cookie(name, value);
		cookie.setPath(CookieConstants.COOKIE_PATH);
		cookie.setMaxAge(CookieConstants.COOKIE_EXPIRY_TIME * 60);
		return cookie;
	}

	public static void createLoginCookies(List<Cookie> loginCookieList, HttpServletResponse response) {

		ListIterator<Cookie> iterator = loginCookieList.listIterator();
		while (iterator.hasNext()) {
			Cookie cookie = iterator.next();
			response.addCookie(cookie);
		}
	}

}
