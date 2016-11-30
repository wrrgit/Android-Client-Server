package com.wqx.test.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import sun.rmi.transport.proxy.HttpReceiveSocket;

public class EncodingFilter implements Filter {

	private String mEncode = "utf-8";
	private FilterConfig mFilterConfig = null;
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		System.out.println("======before doFilter=======");
		response.setContentType("text/html;charset=utf-8");
		//request.setCharacterEncoding("utf-8");//这句话只对post请求有作用，使用过滤器后就不用再使用这句话了
		chain.doFilter(new CustomHttpServletRequestWrapper((HttpServletRequest) request), response);
		System.out.println("======after doFilter=======");
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("======Filter  init=======");
		this.mFilterConfig = filterConfig;
		String encode = mFilterConfig.getInitParameter("encode");
		if(encode != null){
			mEncode = encode;
		}
	}
	class CustomHttpServletRequestWrapper extends HttpServletRequestWrapper{

		private HttpServletRequest request = null;
		private boolean isNotEncode = true;
		public CustomHttpServletRequestWrapper(HttpServletRequest request) {
			super(request);
			this.request = request;
		}
		@Override
		public Map<String, String[]> getParameterMap() {
			// TODO Auto-generated method stub
			try {
				if (request.getMethod().equalsIgnoreCase("POST")) {
					request.setCharacterEncoding(mEncode);
					return request.getParameterMap();
				}else if(request.getMethod().equalsIgnoreCase("GET")){
					System.out.println("EncodingFilter getParameterMap GET charset="+request.getCharacterEncoding());
					Map<String, String[]> map = request.getParameterMap();
					System.out.println("isNotEncode="+isNotEncode);
					if(isNotEncode){
						for(Map.Entry<String, String[]> entry : map.entrySet()){
							// entry  :    name:xxx   pwd:xxx
							//entry.getKey();
							String[] value = entry.getValue();
							System.out.println("value.length="+value.length);
							for(int i = 0; i < value.length;i++){
								System.out.println("before--:"+value[i]);
								value[i] = new String(value[i].getBytes("iso8859-1"),mEncode);
								System.out.println("after--:"+value[i]);
							}
						}
						isNotEncode = false;//第二次再次请求时会使用第一次map(已经转码过了)的缓存，故只需要第一次进行转码，第二次直接返回map就可
					}
					return map;
				}else{
					return request.getParameterMap();
				}
			} catch (UnsupportedEncodingException e) {
				// TODO: handle exception
				e.printStackTrace();
				throw new RuntimeException();
			}
		}
		@Override
		public String getParameter(String name) {
			// TODO Auto-generated method stub
			return getParameterMap().get(name) == null ? null :getParameterMap().get(name)[0];
		}
		@Override
		public String[] getParameterValues(String name) {
			// TODO Auto-generated method stub
			return getParameterMap().get(name);
		}
	}

}
