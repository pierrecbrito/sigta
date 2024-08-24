package br.com.pierre.sigta.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(filterName="AuthFilter", urlPatterns={"*.jsf"})
public class AuthFilter implements Filter {
	
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
    	HttpServletRequest reqt = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpSession ses = reqt.getSession(false);

		String reqURI = reqt.getRequestURI();
		if (reqURI.indexOf("/index.jsf") >= 0
				|| (ses != null && ses.getAttribute("usuario") != null)
				|| reqURI.indexOf("/public/") >= 0
				|| reqURI.contains("javax.faces.resource")
				|| reqURI.indexOf("cadastroUsuario") >= 0)
			chain.doFilter(request, response);
		else {
			resp.sendRedirect(reqt.getContextPath() + "/index.jsf");
		}
			
    }

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}




}