package il.haifa.ac.dh.tikkounsofrim.logout;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet(urlPatterns = "/LogoutServlet")
public class LogoutServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {


		HttpSession session = request.getSession(false);
		if (session != null) {
		    session.invalidate();
		}
		request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(
				request, response);
	}
}