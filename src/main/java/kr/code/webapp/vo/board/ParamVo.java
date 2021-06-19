package kr.code.webapp.vo.board;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ParamVo {

    private String boardTitle;
    private String BoardContents;
    private MultipartFile file;

}
