package com.kh.welcome.board.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.kh.welcome.board.model.vo.Notice;

import common.util.FileUtil;
import common.util.Paging;

public interface NoticeService {

	public int insertNotice(Notice notice
				,List<MultipartFile> files
				, String root
	);

	public Map<String, Object> selectNoticeList(
			//현재 페이지
			 int currentPage
			//페이지당 노출할 게시글 수
			,int cntPerPage);

		// 게시물 상세
	public Map<String,Object> selectNoticeDetail(int nIdx);
	
		// 게시글 파일 삭제
	public int deleteFileWithFIdx(int fIdx);

}
