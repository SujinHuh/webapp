package kr.code.webapp.vo.login;

import lombok.Data;

@Data
public class UserInfoVO {
    private String userId;
    private String userPw;
    private String userName;
    private int userAge;
    private String userAddr;
    private String userAddrDetail;
}
