package kr.code.webapp.vo.board;

import lombok.Data;

@Data
public class BoardDataVO {

    private long boardId;
    private String boardTitle;
    private String boardContents;
    private String boardAuthor;
    private long readCount;
    private String regDate;
    private String modDate;
    private String originFileName;
    private String storedFileName;
    private String filepath;


}
