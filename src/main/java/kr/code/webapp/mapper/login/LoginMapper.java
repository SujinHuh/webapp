package kr.code.webapp.mapper.login;

import kr.code.webapp.vo.login.UserInfoVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Mapper
@Repository
public interface LoginMapper {

    // 로그인 사용자 체크
    UserInfoVo getLoginInfo(Map<String, Object> param) throws Exception;

}
