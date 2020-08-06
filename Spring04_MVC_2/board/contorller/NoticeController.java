package com.kh.welcome.board.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.kh.welcome.board.model.service.NoticeService;
import com.kh.welcome.board.model.vo.Notice;
import com.kh.welcome.member.model.vo.Member;

import common.util.FileUtil;

@Controller
public class NoticeController {
	
	@Autowired
	private NoticeService noticeService;
	
	@RequestMapping("board/boardform.do")
	public String boardForm() {
		return "board/boardForm";
	}
	
	//추후에 수정 될 메서드
	@RequestMapping("/notice/noticeupload.do")
	public ModelAndView noticeUpload(
			//다중파일 업로드임으로
			//여러개의 MultipartFile을 담기 위한 List 생성
			@RequestParam List<MultipartFile> files
			, HttpSession session
			, Notice notice
			) {
			ModelAndView mav = new ModelAndView();
			
			String root = session.getServletContext().getRealPath("/");
			Member sessionMember = (Member)session.getAttribute("logInInfo");
			
			//로그인한 회원이라면
			if(sessionMember != null) {
				//게시글 작성자에 해당 회원의 아이디
				notice.setUserId(sessionMember.getUserId());
			}else {
				//로그인한 회원이 아니라면 비회원으로 등록
				notice.setUserId("비회원");
			}
			
			noticeService.insertNotice(notice, files, root);
			//notice/noticelist.do로 다시 요청
			mav.setViewName("redirect:noticelist.do");
			return mav;
	}
	
	@RequestMapping("notice/noticelist.do")
	public ModelAndView noticeList(
			//@RequestParam 
			//required : 필수 파라미터 여부 지정(default 값은  true)
			//defaultValue : 파라미터가 없을 때 기본값으로 지정할 값
			@RequestParam(required=false, defaultValue="1") 
			int cPage
			) {
			
		ModelAndView mav = new ModelAndView();
		int cntPerPage = 10;
		Map<String, Object> commandMap 
			= noticeService.selectNoticeList(cPage, cntPerPage);
		
		//paging객체를 paging이라는 키로 담아서 보낸다.
		mav.addObject("paging", commandMap.get("paging"));
		mav.addObject("noticeData", commandMap);
		mav.setViewName("board/boardList");
		return mav;
	}
	
	@RequestMapping("/notice/noticedetail.do")
	public ModelAndView noticeDetail(int nIdx) {
		ModelAndView mav = new ModelAndView();
		Map<String,Object> commandMap = noticeService.selectNoticeDetail(nIdx);
		//해당 게시글이 존재하는 지 여부 판단
		//반환되는 Map은 null일 수 없다.
		//Map안의 notice객체가 null인지 여부로 판단.
		if(commandMap.get("notice") != null) {
			mav.addObject("data", commandMap);
			mav.setViewName("board/boardView");
		}else {
			mav.addObject("alertMsg", "해당 게시물이 존재하지 않습니다.");
			mav.addObject("url", "board/boardList");
			mav.setViewName("common/result");
		}
		
		return mav;
	}
	
	
//	@RequestMapping("notice/noticedownload.do")
//	public void noticeDownload(
//			//response header 지정을 위한 response
//			HttpServletResponse response,
//			//파일경로 지정을 위한 session
//			HttpSession session,
//			//사용자가 올린 파일이름
//			String ofname,
//			//서버에 저장된 파일이름
//			String rfname
//			) {
//		
//		//resources/upload 폴더까지의 절대경로를 반환
//		String readFolder = session.getServletContext().getRealPath("/resources/upload");
//		
//		//file 객체 생성
//		File downFile = new File(readFolder + "/" + rfname);
//		OutputStream downOut = null;
//		BufferedInputStream bis = null;
//		
//		try {
//			//response header
//			//Content-Disposition : 브라우저화면에 출력할지, 다운받을 지 결정
//			//attachment : download 받게끔 지정
//			//filename : download 받을 때 파일명
//			response.setHeader("Content-Disposition", "attachment; fliename=" + 
//					URLEncoder.encode(ofname,"UTF-8"));
//			//응답해야하는 대상과 연결되는 OutputStream
//			downOut = response.getOutputStream();
//			//파일 데이터를 읽어옴
//			bis = new BufferedInputStream(new FileInputStream(downFile));
//			
//			int read = 0;
//			
//			while((read = bis.read()) != -1) {
//				downOut.write(read);
//				downOut.flush();
//			}
//			
//		
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		
//	}
	
	@RequestMapping("notice/noticedownload.do")
	@ResponseBody
	public FileSystemResource noticeDownload(
			//response header 지정을 위한 response
			HttpServletResponse response,
			//파일경로 지정을 위한 session
			HttpSession session,
			//사용자가 올린 파일이름
			String ofname,
			//서버에 저장된 파일이름
			String rfname
			) {
				
		String readFolder = session.getServletContext().getRealPath("/resources/upload");
	
		//FileSystemResource
		FileSystemResource downFile = new FileSystemResource(readFolder + "/" + rfname);
		
		try {
			//response header
			//Content-Disposition : 브라우저화면에 출력할지, 다운받을 지 결정
			//attachment : download 받게끔 지정
			//filename : download 받을 때 파일명
			response.setHeader("Content-Disposition", "attachment; fliename=" + 
						URLEncoder.encode(ofname,"UTF-8"));
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return downFile;
		
	}
	
	//첨부파일 삭제
	@RequestMapping("/notice/deletefile.do")
	@ResponseBody
	public String deleteFile(int fIdx) {
		
		int res = noticeService.deleteFileWithFIdx(fIdx);
		
		if(res > 0) {
			
			//jsp에서 사용할 css 선택자를 미리 만들어서 전송
			return "#f"+fIdx;
			
		} else {
			
			return "fail";
			
		}
		
		
	}
	
	@RequestMapping("notice/noticeModify.do")
	public ModelAndView noticeModify(
			int nIdx
			, String userId
			, HttpSession session
			) {
		
		ModelAndView mav = new ModelAndView();
		
		Member sessionMember = (Member) session.getAttribute("logInInfo");
		
		//해당 게시글 작성자 아이디와 
		//session에 담겨있는 회원아이디가 일치하는 경우에만 수정을 허용
		if(sessionMember != null && userId.equals(sessionMember.getUserId())) {
			//게시글 상세정보
			Map<String,Object> commandMap = noticeService.selectNoticeDetail(nIdx);
			
			if(commandMap.get("notice") != null) {
				mav.addObject("data", commandMap);
				mav.setViewName("board/boardModify");
			} else {
				mav.addObject("alertMsg", "게시글 존재하지 않음");
				mav.addObject("url", "noticelist.do");
				mav.setViewName("common/result");
			}
			
		} else {
			mav.addObject("alertMsg", "접근 권한이 없습니다");
			mav.addObject("url", "noticelist.do");
			mav.setViewName("common/result");
		}
		
		return mav;
		
	}
	
	
	
	
	
	

}
