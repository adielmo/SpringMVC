package com.algaworks.brewer.confing.init;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.filter.HttpPutFormContentFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.algaworks.brewer.confing.JPAConfig;
import com.algaworks.brewer.confing.SecurityConfig;
import com.algaworks.brewer.confing.ServiceConfing;
import com.algaworks.brewer.confing.WebConfing;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		
		return new Class<?>[] {JPAConfig.class, ServiceConfing.class, SecurityConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		
		return new Class<?>[] {WebConfing.class};
	}

	@Override
	protected String[] getServletMappings() {
		
		return new String[] {"/"};
	}
	
	@Override
	protected Filter[] getServletFilters() {
		
	HttpPutFormContentFilter httpPutFormContentFilter = new HttpPutFormContentFilter();
		return new Filter[] {httpPutFormContentFilter};
		
	}
	
	@Override
	protected void customizeRegistration(Dynamic registration) {
	
		registration.setMultipartConfig(new MultipartConfigElement(""));
	}

}
