package kr.code.webapp.controller.login;

import kr.code.webapp.service.login.LoginService;
import kr.code.webapp.vo.login.UserInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/login")
public class LoginController {

    private LoginService loginService;

    @Autowired
    public void setLoginService(LoginService loginService){
        this.loginService = loginService;
    }

    @RequestMapping("/frm")
    public ModelAndView loginForm(){
        ModelAndView view = new ModelAndView();
        view.setViewName("views/login/login");
        return view;
    }


    @RequestMapping(value = "/access")
    @ResponseBody
    public Map<String, Object> loginProcess(@RequestParam String userId,
                                            @RequestParam String userPw,
                                HttpServletRequest request) {

        Map<String, Object> param = new HashMap<>();
        Map<String, Object> resultMap = new HashMap<>();

        param.put("userId", userId);
        param.put("userPw", userPw);

        try {

            UserInfoVo vo = loginService.getLoginInfo(param);

            //정보가 오지 않았다. 로그인정보가 맞지 않으니 실패
            if (vo == null){
                resultMap.put("resultCode", 500);
                resultMap.put("resultMsg", "로그인 정보가 맞지 않습니다. 다시 확인바람");
            }else {
                resultMap.put("resultCode", 200);
                resultMap.put("resultMsg", "ok");
                // 로그인 유지 위해서 사용자 정보를 세션에 저장
                request.getSession().setAttribute("userInfo",vo);
            }



        }catch (Exception e){
            e.printStackTrace();
        }

        return resultMap;
    }

}
