package com.act.ucenter.common.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
// * @Title: CommonInterceptor.java
// * @Package com.act.web.interceptor
// * @Description: (登入拦截器)
// * @author   fmj
// * @date 2017-2-14 上午11:25:19
// * @version V1.0
// */

//
//import com.act.web.constant.CommonContant;
//import com.act.web.util.ConfigLoadUtil;
//import com.act.web.util.SpringContextUtil;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//
//public class CommonInterceptor extends HandlerInterceptorAdapter {
//
//    private final Logger log = LoggerFactory.getLogger(CommonInterceptor.class);
//
//    /**
//     * FIXME 独立部署时config.properties 为 N
//     * 在业务处理器处理请求之前被调用
//     * 如果返回false
//     *     从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
//     * 如果返回true
//     *    执行下一个拦截器,直到所有的拦截器都执行完毕
//     *    再执行被拦截的Controller
//     *    然后进入拦截器链,
//     *    从最后一个拦截器往回执行所有的postHandle()
//     *    接着再从最后一个拦截器往回执行所有的afterCompletion()
//     */
//    @Override
//    public boolean preHandle(HttpServletRequest request,
//            HttpServletResponse response, Object handler) throws Exception {
//    	ConfigLoadUtil conf = SpringContextUtil.getBean("configLoadUtil");
//    	String interceptor = conf.getSubSystem();
//    	if("N".equals(interceptor)){
//    		return true;
//    	}
//    	String requestUri = request.getRequestURI();
//        String contextPath = request.getContextPath();
//        String url = requestUri.substring(contextPath.length());///common/login/forget
//        if(url != null){
//        	if(("/common/login/login.do").equals(url) ||("/common/login/loginSub.do").equals(url) || ("/common/login/verification.do").equals(url)||("/loginChildSystem!createRandom.action").equals(url)){
//           	 return true;
//           }
//        }
//        Object object = request.getSession().getAttribute(CommonContant.SESSION_USERINFO);
//        if(null == object && !"Y".equalsIgnoreCase(request.getParameter("RemoteOpenData"))){
//            log.info("Interceptor：跳转到login页面！");
//            request.getRequestDispatcher("/index.jsp").forward(request, response);
//            return false;
//        }else
//            return true;
//
//
//    }
//
//}


public class CommonInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        response.setHeader("Access-Control-Allow-Headers", "X-Requested-With, accept, content-type, xxxx");
        response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
        response.setHeader("Access-Control-Allow-Origin", "*");


        return true;
    }
}