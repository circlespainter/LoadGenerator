package com.webQ.servlet3;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import com.webQ.config.SpringRootConfig;
import com.webQ.config.SpringWebConfig;
public class MyWebInitializer extends
AbstractAnnotationConfigDispatcherServletInitializer {
@Override
protected Class<?>[] getRootConfigClasses() {
return new Class[] { SpringRootConfig.class };
}
@Override
protected Class<?>[] getServletConfigClasses() {
return new Class[] { SpringWebConfig.class };
}
@Override
protected String[] getServletMappings() {
return new String[] { "/" };
}
}
