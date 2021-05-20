package spring5_webmvc_study.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {

	// DispatcherServlet의 매핑 경로를 '/' 주었을 때, jsp/html/css 등을 올바르게 처리하기 위한 설정 추가
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	// JSP통해서 컨트롤러의 실행 결과를 보여주기 위한 설정
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.jsp("/WEB-INF/view/", ".jsp"); 
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) { // main이라는 명령 들어왔을때 main화면 가도록
		registry.addViewController("/main").setViewName("main"); // main오면 위의 configureViewResolvers 안 먹고 이 문장으로 바로 감
	}
	
}
