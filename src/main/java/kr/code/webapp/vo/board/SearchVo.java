package kr.code.webapp.vo.board;

import lombok.Data;

@Data
public class SearchVo {

    private int currentPage;
    private int startRow;
    private int rangeCnt;

}
