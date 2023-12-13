package net.javaguides.springboot.springsecurity.utilities;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;

public abstract class Utility {
	 public static String getSiteURL(HttpServletRequest request) {
	        String siteURL = request.getRequestURL().toString();
	        return siteURL.replace(request.getServletPath(), "");
	    }
	 
	
}
