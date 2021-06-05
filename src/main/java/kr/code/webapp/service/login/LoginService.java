package kr.code.webapp.service.login;

import kr.code.webapp.mapper.login.LoginMapper;
import kr.code.webapp.vo.login.UserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class LoginService {

    private LoginMapper loginMapper;

    @Autowired
    public void setLoginMapper(LoginMapper loginMapper) {
        this.loginMapper = loginMapper;
    }

    //로그인 유저 체크
    public UserInfoVO getLoginInfo(Map<String, Object> param) throws Exception {
        return loginMapper.getLoginInfo(param);
    }
}
