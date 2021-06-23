package kr.code.webapp.controller.board;

import kr.code.webapp.service.board.BoardService;
import kr.code.webapp.vo.board.BoardDataVO;
import kr.code.webapp.vo.board.PageVO;
import kr.code.webapp.vo.board.ParamVo;
import kr.code.webapp.vo.board.SearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/board")
public class BoardCotroller {

    private BoardService boardService;

    @Autowired
    public void setBoardService (BoardService boardService) {
        this.boardService = boardService;
    }

    @RequestMapping(value = "/bbs/list")
    public ModelAndView bbsList()  {
        ModelAndView view = new ModelAndView();
        view.setViewName("views/board/board_list");
        return view;
    }

    @RequestMapping(value = "/bbs/data")
    @ResponseBody
    public Map<String, Object> getBbsData(@ModelAttribute SearchVo searchVo) {

        Map<String, Object> resultMap = new HashMap<String, Object>();
        Map<String, Object> param = new HashMap<String, Object>();
        try {

            int totalCnt = boardService.getBoardListCount(searchVo);

            //페이지처리 객체 선언
            PageVO pageVO = new PageVO();

            pageVO.setTotalCount(totalCnt); //전체 리스트 카운트 저
            pageVO.setCurrentPage(searchVo.getCurrentPage());// 이동할 페이지장저장

            //페이지 처리 범위를 등록하기
            searchVo.setStartRow(pageVO.getStartRow());
            searchVo.setRangeCnt(pageVO.getLastRow());
            //searchVo.setRangeCnt(pageVO.getCountPerPage()); //mysql .


            List<BoardDataVO>  list= boardService.getBoardList(searchVo);

            resultMap.put("boardList", list);//리스트 데이터
            resultMap.put("totalCnt",totalCnt); //전체 카운트
            resultMap.put("pageVo",pageVO); //페이지 정보
            resultMap.put("pager",pageVO.getPager());//페이징을 위한 html

        }catch(Exception e) {
            e.printStackTrace();
        }


        return resultMap;
    }

    @RequestMapping("/bbs/add")
    public String bbsAddpopUp(){
        return "/views/board/add_board";
    }

    //file insert
    @RequestMapping("/bbs/add/proc")
    @ResponseBody //리턴형이 제이슨 형태로 에이작스에게 전송.
    public Map<String,Object> insertBoard(@ModelAttribute ParamVo paramVo){
        Map<String,Object> resultMap = new HashMap<>();


        try{

            resultMap = boardService.insertBoard(paramVo);

        }catch (Exception e){
            e.printStackTrace();
        }
        return resultMap;
    }
}
