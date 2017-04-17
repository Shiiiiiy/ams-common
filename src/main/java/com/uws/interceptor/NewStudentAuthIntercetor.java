package com.uws.interceptor;

import java.util.List;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.uws.core.util.WebUtils;

/**
 * 
* @ClassName: NewStudentAuthIntercetor 
* @Description: TODO(新生登录信息采集系统的拦截器) 
* @author wangcl
* @date 2015-7-23 上午9:46:48 
*
 */
public class NewStudentAuthIntercetor implements HandlerInterceptor {

	private PathMatcher pathMatcher = new AntPathMatcher();

	private List<String> excludedUrls;

	public void setExcludedUrls(List<String> excludedUrls) {
		this.excludedUrls = excludedUrls;
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object Response, Exception exception)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		for (String url : this.excludedUrls) {
			if (pathsMatch(url, request)) {
				return true;
			}
		}

		HttpSession session = request.getSession();
		if (session.getAttribute("student_key") == null) {
			response.sendRedirect(request.getContextPath()+ "/newstudent/toLogin.do");
			return false;
		}
		return true;
	}

	protected boolean pathsMatch(String path, ServletRequest request) {
		String requestURI = getPathWithinApplication(request);

		return pathsMatch(path, requestURI);
	}

	protected boolean pathsMatch(String pattern, String path) {
		return this.pathMatcher.match(pattern, path);
	}

	protected String getPathWithinApplication(ServletRequest request) {
		return WebUtils.getPathWithinApplication((HttpServletRequest) request);
	}
}
