package kr.code.webapp.service.board;

import kr.code.webapp.mapper.board.BoardMapper;
import kr.code.webapp.vo.board.BoardDataVO;
import kr.code.webapp.vo.board.SearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BoardService {

    private BoardMapper boardMapper;

    @Autowired
    public void setBoardMapper(BoardMapper boardMapper) {
        this.boardMapper = boardMapper;
    }


    public int getBoardListCount (SearchVo searchVo) throws Exception {
        return this.boardMapper.getBoardListCount(searchVo);
    }

    public List<BoardDataVO> getBoardList (SearchVo searchVo) throws Exception {
        return this.boardMapper.getBoardList(searchVo);
    }
}
