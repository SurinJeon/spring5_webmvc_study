package spring5_webmvc_study.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mysql.jdbc.NonRegisteringReplicationDriver;

@Controller
@RequestMapping("/register")
public class RegistController {
	
	@Autowired
	private MemberRegisterService memberRegisterService;
	
	@RequestMapping("/step1")
	public String handleStep1() {
		return "register/step1";
	}
	
	@PostMapping("/step2") // PostMapping이므로 주소창에 바로 주소 넣어서 엔터치면 안 넘어감(그건 get방식)
	public String handleStep2Post(@RequestParam(value = "agree", defaultValue = "false") Boolean agree, Model model) { // name값 agree로 넘어온 value를 Boolean타입의 agree라는 변수에 넣겠다
		// value로 넣은 agree와 step1.jsp에서 설정한 name의 agree 철자 같아야됨 꼭! (parameter로 넘어온 것 받는거기 때문)
		if(!agree) {
			return "register/step1";
		}
		model.addAttribute("registRequest", new RegistRequest()); // 새로운 객체를 만들어서 던져준 다음, step2에서 form태그로 바로 쓸 수 있도록 함
		return "register/step2";
	}
	
	@GetMapping("/step2") // 위에 step2는 반드시 step1통해서 post방식으로 넘어오게끔 설정함 >> 그래서 get으로 왔을 때는 다시 step1로 redirect하도록 하는 method
	public String handleStep2Get() {
		return "redirect:/register/step1";
	}
	
	@PostMapping("/step3")
	public String handleStep3(RegistRequest request) {
		// 하나하나 getParameter()로 가져오는게 아니라, 
		// 매개변수에 dto가져오면
		// spring이 알아서 dto에 setting해주기 때문에
		// 그 객체를 service에 있는 method로 바로 쓰면 됨
		
//		System.out.println(request); << debugging sysout
		
		try {
			memberRegisterService.regist(request);
			return "register/step3";
		} catch (DuplicateMemberException e) {
			return "register/step2";
		}		
	}
}
