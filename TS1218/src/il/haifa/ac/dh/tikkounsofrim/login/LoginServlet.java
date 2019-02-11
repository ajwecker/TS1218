package il.haifa.ac.dh.tikkounsofrim.login;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import il.haifa.ac.dh.tikkounsofrim.impl.*;
import il.haifa.ac.dh.tikkounsofrim.model.*;
import il.haifa.ac.dh.tikkounsofrim.model.TaskProvider.Task;

@WebServlet(urlPatterns = { "/LoginServlet", "/index.html" })
public class LoginServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static UserDBase userValidationService = null;
	static ManuscriptProvider mp = null;
	static TaskProvider taskProvider = null;

	// private TodoService todoService = new TodoService();
	@Override
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		super.init(config);
		FilePathUtils.setFilePath(config);
		userValidationService = UserDBaseJDBC.instance();
		mp = ManuscriptProviderImpl.instance();
		ChapterAssignmentData chapterAssignmentData = new ChapterAssignmentDataImpl();
		taskProvider = new TaskProviderImpl(mp, chapterAssignmentData, userValidationService);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userid = (String) request.getSession().getAttribute("userid");

		request.setCharacterEncoding("UTF-8");

		String newlang = request.getParameter("lang");
//TODO avoid unnecessary resets		String currentlang = (String) request.getSession().getAttribute("lang");
		if (newlang != null) {
			setLanguage(request.getSession(), newlang);
			String currentpage = (String) request.getSession().getAttribute("page");
			if (currentpage == null) {
				currentpage = "views/login.jsp";
			}
			System.out.println("New Lang" + newlang);
			request.getRequestDispatcher("/WEB-INF/" + currentpage).forward(request, response);
			return;
		} else {
			setDefaultLanguage(request.getSession());
		}

		String page = request.getParameter("page");
		if (page == null) {
			request.getSession().setAttribute("username", request.getParameter("u"));

			saveRequestParameterInSession(request, "ntn");
			saveRequestParameterInSession(request, "m");
			saveRequestParameterInSession(request, "p");
			saveRequestParameterInSession(request, "l");
			saveRequestParameterInSession(request, "b");
			saveRequestParameterInSession(request, "c");

			if (userid != null && userid != "") {
				page = "views/transcribe.jsp";
			} else {
				page = "views/login.jsp";
			}
		}
		if ("views/logout.jsp".equals(page)) {
			request.getSession().setAttribute("userLineCount", UserDBaseJDBC.instance().getCount(userid));
		}
		System.out.println("page=" + page);
		request.getSession().setAttribute("page", page);
		request.getRequestDispatcher("/WEB-INF/" + page).forward(request, response);

	}

	/**
	 * @param request
	 */
	private void setDefaultLanguage(HttpSession session) {
		if (session.getAttribute("lang") == null) {

			setLanguage(session, "HE");
		}
	}

	private void setLanguage(HttpSession session, String lang) {
		String dir = "ltr";
		String msglang = "en";
		switch (lang) {
		case "EN":
			dir = "ltr";
			msglang = "en";

			break;
		case "FR":
			dir = "ltr";
			msglang = "fr";

			break;
		case "HE":
			dir = "rtl";
			msglang = "es";

			break;
		default:
			break;
		}

		session.setAttribute("lang", lang);
		session.setAttribute("msglang", msglang);
		session.setAttribute("dir", dir);

	}

	/**
	 * @param request
	 * @param param
	 */
	private void saveRequestParameterInSession(HttpServletRequest request, String param) {
		String value = request.getParameter(param);
		if (value != null) {
			request.getSession().setAttribute(param, value);
		} else {
			request.getSession().removeAttribute(param);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		setDefaultLanguage(request.getSession());

		String login = request.getParameter("login");
		System.out.println("login exists " + login);
		if (login != null) {
			handleLogin(request, response);
			return;
		}
		String register = request.getParameter("register");
		if (register != null) {
			handleRegister(request, response);
			return;
		}

		String page = request.getParameter("page");
		if (page == null) {
			page = "views/login.jsp";
		}
		System.out.println("page2=" + page);
		request.getSession().setAttribute("page", page);
		request.getRequestDispatcher("/WEB-INF/" + page).forward(request, response);
	}

	private int convert(String str) {
		if (str != null && str != "" && str != " ") {
			try {
				int i = Integer.parseInt(str);
				return i;
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 0;
	}

	private void handleRegister(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter("usernamesignup");
		String password = request.getParameter("passwordsignup");
		String email = request.getParameter("emailsignup");
		String age = request.getParameter("age");
		String hebrew = request.getParameter("hebrew");
		String midrashim = request.getParameter("midrashim");

		UserInfo uInfo = new UserInfo(convert(age), convert(hebrew), convert(midrashim));
		if (!userValidationService.checkUser(name)) {
			int userRegisterd = userValidationService.registerUser(name, password, email, uInfo);
			if (userRegisterd != 0) {
				request.setAttribute("errorMessageRegister", "Problem registering");
				response.sendRedirect("/TS1218/LoginServlet#toregister");
				return;
			}
			request.getSession().setAttribute("username", name);
			response.sendRedirect("/TS1218/LoginServlet");
		} else {
			request.setAttribute("errorMessageRegister", "Invalid Username exists");
			response.sendRedirect("/TS1218/LoginServlet#toregister");

		}

	}

	private void handleLogin(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		Task task = null;
		String name = request.getParameter("username");
		String password = request.getParameter("password");

		boolean isUserValid = userValidationService.isUserValid(name, password);

		if (isUserValid) {
			TikunUser user = new TikunUser(name);
			request.getSession().setAttribute("userid", name);
			request.getSession().setAttribute("user", user);
			request.getSession().setAttribute("userDB", userValidationService);
			int b = convert((String) request.getSession().getAttribute("b"));
			int c = convert((String) request.getSession().getAttribute("c"));

			int ntn = convert((String) request.getSession().getAttribute("ntn"));

			if (task == null && ntn > 0) {
				task = taskProvider.getTask(user, ntn);
			}

			if (task == null && b > 0 && c > 0) {
				task = taskProvider.getTask(user, b, c);
			}
			String m = (String) request.getSession().getAttribute("m");
			int p = convert((String) request.getSession().getAttribute("p"));
			int l = convert((String) request.getSession().getAttribute("l"));
			if (task == null && m != null && m.length() > 0 && p > 0 && l > 0) {
				task = taskProvider.getTask(user, new ManuscriptPlace(m, p, l));
			}

			if (task == null) {
				task = taskProvider.getTask(user);

			}
			request.getSession().setAttribute("taskProvider", taskProvider);
			request.getSession().setAttribute("task", task);
			response.sendRedirect("/TS1218/TranscribeServlet");
		} else {
			request.setAttribute("errorMessageLogin", "Invalid Credentials!");
			request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);

		}
	}

}
