package kr.code.webapp.vo.board;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class PageVO {

    private int countPerPage;  // 한페이지 당 보여줄 개수
    private int totalCount;   // 전체 리스트 개수
    private int currentPage;  // 현재 페이지 (이동할 페이지)
    private int lastRow; // 현재 페이지 마지막 개시물
    private int startRow; // 현재 페이지 시작 게시물

    private int totalPage; // 전체 페이지 개수
    private int pageRangeStart;  //  한 블럭당 보여줄 페이지의 시작점 - 계산용
    private int pageRangeEnd;  // 한 블럭당 보여줄 페이지의 끝점 - 계산용
    private int prevPage;  // 이전 페이지 번호
    private int nextPage;  // 다음 페이지 번호

    private int startRowNumCurrentPage;  // 계산용


    //그냥 생성하면 기본 10개씩
    public PageVO() {
        this.countPerPage = 10; // 한페이지에 기본으로 보여줄 개수
    }

    //임의로 한페이지에 보여줄 개수를 지정 가능
    public PageVO(int  countPerPage) {
        this.countPerPage = countPerPage;
    }


    //전체 리스트 개수 입력
    public void setTotalCount(int totalCount) {
        if( totalCount < 1) {
            this.totalCount = 1;
        }else {
            this.totalCount = totalCount;
        }
    }

    //현재 페이지설정
    public void setCurrentPage(int currentPage) {
        if(countPerPage < 1) {
            this.currentPage = 1;
        }else {
            this.currentPage = currentPage;
        }

        this.makePage();
    }

    //계산만
    private void makePage() {

        lastRow  = this.currentPage * countPerPage;
        startRow = (this.currentPage-1) * countPerPage + 1;
        //startRow = this.lastRow - countPerPage ; //mysql


        if(startRow < 0) {
            startRow = 1;
        }

        if(lastRow <=0) {
            lastRow = countPerPage;
        }

        if(lastRow > totalCount) {
            lastRow = totalCount;
        }

        //전체 페이지 계산  11 / 10  (double) / (int)  = double
        totalPage = (int) Math.ceil((double)totalCount / countPerPage);

        //한블럭당 보여줄 페이지의 마지막 번호
        pageRangeEnd = (int) Math.ceil((double) currentPage / countPerPage) * countPerPage;

        //한 블럭당 보여줄 페이지의 시작 번호
        pageRangeStart = pageRangeEnd  - countPerPage + 1;

        if(pageRangeStart <= 0) {
            pageRangeStart = 1;
        }

        if(pageRangeEnd > totalPage) {
            pageRangeEnd = totalPage;
        }


        if( pageRangeEnd == 0 ) {
            pageRangeEnd = 1;
        }

        if( currentPage > 1 ) {
            prevPage = currentPage - 1;
        }else {
            prevPage = 1;
        }

        if( nextPage < totalPage ) {
            nextPage = currentPage + 1;
            if( nextPage >= totalPage ) {
                nextPage = totalPage;
            }
        }else {
            nextPage = totalPage;
        }

    }


    public String getPager() {

        StringBuilder sb = new StringBuilder();


        sb.append("<a class=\"btn_paging_first\" href=\"javascript:goPage('1')\"><span class=\"fas fa-angle-double-left\"><em>처음페이지</em></span> </a></a>\n");
        sb.append("<a class=\"btn_paging_prev\" href=\"javascript:goPage('"+ this.getPrevPage() +"')\"><span class=\"fas fa-angle-left\"><em>이전페이지</em></span></a>\n");


        for(int i = this.getPageRangeStart() ; i <= this.getPageRangeEnd(); i++ ) {

            if(this.getCurrentPage() == i) {
                sb.append("<strong>"+i+"</strong>" + "\n");
            }else {
                sb.append("  <a href=\"javascript:goPage('"+i+"')\">" +i +"</a> \n");
            }

        }

        sb.append(" <a class=\"btn_paging_next\" href=\"javascript:goPage('"+this.getNextPage()+"')\"><span class=\"fas fa-angle-right\"><em>다음페이지</em></span></a> \n");
        sb.append(" <a class=\"btn_paging_end\" href=\"javascript:goPage('"+this.getTotalPage()+"')\"><span class=\"fas fa-angle-double-right\"><em>다음페이지</em></span></a> \n");

        return sb.toString();
    }

}
