package kr.code.webapp.mapper.board;

import kr.code.webapp.vo.board.BoardDataVO;
import kr.code.webapp.vo.board.SearchVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface BoardMapper {

    int getBoardListCount(SearchVo searchVo) throws Exception;

    List<BoardDataVO> getBoardList(SearchVo searchVo) throws Exception;


    int insertBoard(Map<String, Object> param) throws Exception;

    int insertFile(Map<String, Object> param) throws Exception;
}
