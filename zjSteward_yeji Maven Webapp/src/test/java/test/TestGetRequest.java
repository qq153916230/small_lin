package test;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Controller
public class TestGetRequest {
	
	static ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
	static HttpServletRequest request ;
	
	public static HttpServletRequest getReq(){
		System.out.println("in getReq()");
		
		request = attributes.getRequest();
		
		return request;
	}
	
}
