package net.board.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.corba.se.impl.interceptors.PINoOpHandlerImpl;

import net.board.db.BoardBean;
import net.board.db.BoardDAO;

public class BoardDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		request.setCharacterEncoding("utf-8");
		
		ActionForward forward = new ActionForward();
		boolean result = false;
		boolean usercheck = false;
		
		int num = Integer.parseInt(request.getParameter("num"));
		
		BoardDAO boarddao = new BoardDAO();
		usercheck = boarddao.isBoardWriter(num, request.getParameter("BOARD_PASS"));
		
		if (usercheck == false) {
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			
			out.println("<script>");
			out.println("alert('삭제할 권한 없음')");
			out.println("location.href='./BoardList.bo';");
			out.println("</script>");
			out.close();
			
			return null;
		}
		
		result = boarddao.boardDelete(num);
		if (result == false) {
			System.out.println("삭제 실패");
			return null;
		}
		
		System.out.println("삭제 성공");
		
		forward.setRedirect(true); 
		// false라면 현재의 값을 가지고 간다.
		// true면 여기 있는 값은 놓고 그냥 간다.(path로 간다)
		
		forward.setPath("./BoardList.bo");
		return forward;
		
	}

}
