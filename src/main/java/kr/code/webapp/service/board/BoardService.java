package kr.code.webapp.service.board;

import kr.code.webapp.mapper.board.BoardMapper;
import kr.code.webapp.vo.board.BoardDataVO;
import kr.code.webapp.vo.board.ParamVo;
import kr.code.webapp.vo.board.SearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BoardService {

    private BoardMapper boardMapper;

    //Spring file Path
    @Value("${server.file.upload.folder}")
    String filePath;

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

    @Transactional //rollBack
    public Map<String,Object> insertBoard(ParamVo paramVo) throws Exception {

        Map<String,Object> resultMap = new HashMap<>();

        MultipartFile file = paramVo.getFile();
        //원본파일 이름
        String originFileName = file.getOriginalFilename();
        //파일 이름을 바꾸기 위해서 확장자만 분리
        String ext = originFileName.substring(originFileName.lastIndexOf(",")+1);
        //실제 저장하는 이름.. 중복을 피하기 위해서 파일이름을 만든다.
        String fileName = "file_"+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) +"."+ ext;

        String fullPath = filePath + fileName;

        //원본파일이 존재하는 때에!
        if(originFileName != null && originFileName.trim().length() > 0) {
            //새로쓸 파일 객체 만들기
            File newFile = new File(fullPath);

            //새로 저장할 폴더나 파일이 존재하지 않을 경우 폴더와 복사할 빈 파일을 만든다.
            if(!newFile.exists()){
                //저장할 디렉토리 만들기 //mkdirs -> 풀경로
                if(newFile.getParentFile().mkdirs()){
                    //실제 파일을 복사할 빈파일 하나 생성
                    newFile.createNewFile();
                }
            }
            //원본파일을 복사대상(빈 파일)에 다가 복사한다.
            file.transferTo(newFile);

        }
        Map<String ,Object> param = new HashMap<String, Object>();

        param.put("boardTitle", paramVo.getBoardTitle());
        param.put("boardContents",paramVo.getBoardContents());
        param.put("boardAuthor","admin");
        param.put("originFileName",originFileName);
        param.put("storedFileName",fileName);
        param.put("filePath",filePath);


        int result = boardMapper.insertBoard(param);

        if(result > 0 ) {
            result = boardMapper.insertFile(param);
        }

        if(result > 0) {
            resultMap.put("resultCode",200);
            resultMap.put("resultMsg","OK");
        } else {
            resultMap.put("resultCode",400);
            resultMap.put("resultMsg","Fail");

        }

        return resultMap;
    }
}
