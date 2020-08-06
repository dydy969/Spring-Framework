package com.kh.welcome.board.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.kh.welcome.board.model.dao.NoticeDao;
import com.kh.welcome.board.model.vo.Notice;

import common.util.FileUtil;
import common.util.Paging;

@Service
public class NoticeServiceImpl implements NoticeService {
	
	@Autowired
	private NoticeDao noticeDao;
	
	//@Transactional
			//스프링에게 트랜잭션 관리를 임위하는 어노테이션
			//Method 또는 Class 작성할 수 있다.
			//Class에 지정할 경우, 해당 클래스의 모든 메서드가 
			//스프링에게 트랜잭션 관리를 위임하게 된다.
	public int insertNotice(Notice notice
						,List<MultipartFile> files
						, String root
			) {
		//게시물 등록
		int result = noticeDao.insertNotice(notice);
		
		//에러 발생을 위한 코드
		int errorNumber = 10/0;
		
		if(!(files.size() == 1
			&& files.get(0).getOriginalFilename().equals(""))) {
			
			//파일업로드를 위해 FileUtil.fileUpload() 호출
			List<Map<String,String>> filedata 
					= new FileUtil().fileUpload(files, root);
			
			for(Map<String,String> f : filedata) {
				noticeDao.insertFile(f);
			}
		}
		
		return result;
	}
	
	public Map<String, Object> selectNoticeList(
					//현재 페이지
					 int currentPage
					//페이지당 노출할 게시글 수
					,int cntPerPage){
		
		 Map<String, Object> commandMap 
		 		= new HashMap<String, Object>();
		 //페이징 처리를 위한 객체 생성
		 Paging p = new Paging(noticeDao.selectContentCnt()
				 ,currentPage,cntPerPage);
		 
		 //현재 페이지에 필요한 게시물 목록
		 List<Notice> nlist = noticeDao.selectNoticeList(p);
		 commandMap.put("nlist", nlist);
		 commandMap.put("paging", p);
		return commandMap;
	}
	
	//게시물 상세
	public Map<String,Object> selectNoticeDetail(int nIdx){
		Map<String,Object> commandMap = new HashMap<String, Object>();
		
		Notice notice = noticeDao.selectNoticeDetail(nIdx);
		List<Map<String,String>> flist = noticeDao.selectFileWithNotice(nIdx);
		commandMap.put("notice",notice);
		commandMap.put("flist",flist);
		return commandMap;
	}

	//게시글 파일 삭제
	public int deleteFileWithFIdx(int fIdx) {
		
		//삭제할 파일의 저장경로를 받아온다
		Map<String, String> fileData = noticeDao.selectFileWithFIdx(fIdx);
		
		//저장경로를 fileUtil.deleteFile 메서드의 매개변수로 넘겨서 
		//해당 파일을 삭제한다
		FileUtil fileUtil = new FileUtil();
		fileUtil.deleteFile(fileData.get("savePath"));
		
		//파일 테이블에서 파일경로를 삭제한다
		int res = noticeDao.deleteFileWithFIdx(fIdx);
		
		return res;
		
	}



	
	
	
	
	
	
}
