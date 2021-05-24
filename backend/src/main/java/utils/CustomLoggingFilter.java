//package utils;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.lang.annotation.Annotation;
//import java.util.Iterator;
//import java.util.List;
//import java.util.logging.Logger;
// 
//import javax.ws.rs.container.ContainerRequestContext;
//import javax.ws.rs.container.ContainerRequestFilter;
//import javax.ws.rs.container.ContainerResponseContext;
//import javax.ws.rs.container.ContainerResponseFilter;
//import javax.ws.rs.container.ResourceInfo;
//import javax.ws.rs.core.Context;
//import javax.ws.rs.ext.Provider;
//
// 
//@Provider
//public class CustomLoggingFilter implements ContainerRequestFilter, ContainerResponseFilter {
//    @Context
//    private ResourceInfo resourceInfo;
// 
//    private static final Logger log = Logger.getLogger(CustomLoggingFilter.class.getName());
// 
//    @Override
//    public void filter(ContainerRequestContext requestContext) throws IOException {
//        //Note down the start request time...we will use to calculate the total
//        //execution time
// 
//        log.debug("Entering in Resource : /{} ", requestContext.getUriInfo().getPath());
//        log.debug("Method Name : {} ", resourceInfo.getResourceMethod().getName());
//        log.debug("Class : {} ", resourceInfo.getResourceClass().getCanonicalName());
//        logQueryParameters(requestContext);
//        logMethodAnnotations();
//        logRequestHeader(requestContext);
// 
//    }
// 
//    private void logQueryParameters(ContainerRequestContext requestContext) {
//        Iterator iterator = requestContext.getUriInfo().getPathParameters().keySet().iterator();
//        while (iterator.hasNext()) {
//            String name = (String) iterator.next();
//            List obj = requestContext.getUriInfo().getPathParameters().get(name);
//            String value = null;
//            if(null != obj && obj.size() > 0) {
//                value = (String) obj.get(0);
//            }
//            log.debug("Query Parameter Name: {}, Value :{}", name, value);
//        }
//    }
// 
//    private void logMethodAnnotations() {
//        Annotation[] annotations = resourceInfo.getResourceMethod().getDeclaredAnnotations();
//        if (annotations != null && annotations.length > 0) {
//            log.debug("----Start Annotations of resource ----");
//            for (Annotation annotation : annotations) {
//                log.debug(annotation.toString());
//            }
//            log.debug("----End Annotations of resource----");
//        }
//    }
// 
//    private void logRequestHeader(ContainerRequestContext requestContext) {
//        Iterator iterator;
//        log.debug("----Start Header Section of request ----");
//        log.debug("Method Type : {}", requestContext.getMethod());
//        iterator = requestContext.getHeaders().keySet().iterator();
//        while (iterator.hasNext()) {
//            String headerName = (String) iterator.next();
//            String headerValue = requestContext.getHeaderString(headerName);
//            log.debug("Header Name: {}, Header Value :{} ",headerName, headerValue);
//        }
//        log.debug("----End Header Section of request ----");
//    }
//
//    @Override
//    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
// 
//
//}