package il.haifa.ac.dh.tikkounsofrim.transcribe;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import il.haifa.ac.dh.tikkounsofrim.impl.*;
import il.haifa.ac.dh.tikkounsofrim.model.ImageData;
import il.haifa.ac.dh.tikkounsofrim.model.ManuscriptDescriptor;
import il.haifa.ac.dh.tikkounsofrim.model.ManuscriptPlace;
import il.haifa.ac.dh.tikkounsofrim.model.ManuscriptProvider;
import il.haifa.ac.dh.tikkounsofrim.model.TaskProvider;
import il.haifa.ac.dh.tikkounsofrim.model.TaskProvider.Task;
import il.haifa.ac.dh.tikkounsofrim.model.TikunUser;
import il.haifa.ac.dh.tikkounsofrim.model.TranscribedString;
import il.haifa.ac.dh.tikkounsofrim.model.UserDBase;



@WebServlet(urlPatterns = {"/TranscribeServlet"})
public class TranscribeServlet extends HttpServlet {
	//TODO have these first 7 shared between all Servlets
	private static final String USER = "user";
	private static final String USER_DB = "userDB";
	private static final String TASK_PROVIDER = "taskProvider";
	private static final String LANG = "lang";
	private static final String PAGE = "page";
	private static final String UTF_8 = "UTF-8";
	private static final String TASK = "task";
	
	private static final String M__TRANSCIPTION_STARTTIME = "starttime";
	private static final String M_TRANSCRIBEDLINEIMGSRC = "transcribedlineimgsrc";
	private static final String MANUSCRIPT_TOTAL_LINES = "manuscriptTotalLines";
	private static final String MANUSCRIPT_TOTAL_PAGES = "manuscriptTotalPages";
	private static final String MANUSCRIPT_LINE = "manuscriptLine";
	private static final String MANUSCRIPT_PAGE = "manuscriptPage";
	private static final String M_TRANSCRIPTION_VERSION = "mTranscriptionVersion";
	private static final String M_TRANSCRIBEDLINE = "transcribedline";
	private static final String MANUSCRIPT_ATTRIBUTION = "manuscriptAttribution";
	private static final String MANUSCRIPT_DESC_LINK = "manuscriptDescLink";
	private static final String MANUSCRIPT_SHORT_DESC = "manuscriptShortDesc";
	private static final String MANUSCRIPT_NAME = "manuscriptName";
	static ManuscriptProvider manuscriptProvider = null;
	static TaskProvider taskProvider;
	
//	static TranscriptionProvider transcriptionProvider =fi;
	
	long start = 0;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		
		super.init(config);
		FilePathUtils.setFilePath(config);
		manuscriptProvider = ManuscriptProviderImpl.instance();
	}
//	private TodoService todoService = new TodoService();

	//java -Dcantaloupe.config=C:cantaloupe.properties.sample -Xmx2g -jar cantaloupe-4.0.2.war
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	
		request.setCharacterEncoding(UTF_8);
		Task task =  (Task) request.getSession().getAttribute(TASK);
		if(task==null) {
			redirectToLoginPage(request, response);
			return;
		}
		
		setupTranscription(request.getSession(), task);
		start = System.currentTimeMillis();
		request.getSession().setAttribute(M__TRANSCIPTION_STARTTIME,start);
		String page = "views/transcribe.jsp";
		request.getSession().setAttribute(PAGE, page);
		request.getRequestDispatcher("/WEB-INF/"+page).forward(
				request, response);
	}

	/**
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void redirectToLoginPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(
				request, response);
	}


private void setupTranscription(HttpSession session, Task task) {
	int lineNumber = task.getLineNumber();
	int pageNumber = task.getPageNumber();
	String lang = (String) session.getAttribute(LANG);
	//image
	ImageData imageData= manuscriptProvider.getManuscriptLine(task.getmId(),pageNumber,lineNumber,0,0,0);
	
	// manuscript description 
	ManuscriptDescriptor manuscriptDesc =  manuscriptProvider.getManuscriptDescription(task.getmId());
	
	//transcription
	TranscribedString transcription = manuscriptProvider.getTranscribedLine(task.getmId(), pageNumber, lineNumber, 0);
	
	session.setAttribute(MANUSCRIPT_NAME,task.getmId().getName());
	String manuscriptShortDesc =  manuscriptDesc.getShortDescription(lang);
	session.setAttribute(MANUSCRIPT_SHORT_DESC,manuscriptShortDesc);
	System.out.println("Manuscript ShortDesc="+manuscriptShortDesc);
	String manuscriptDescLink =  manuscriptDesc.getDescriptionLink(lang);
	session.setAttribute(MANUSCRIPT_DESC_LINK,manuscriptDescLink);
	session.setAttribute(MANUSCRIPT_ATTRIBUTION, manuscriptDesc.getAttribution());
	session.setAttribute(M_TRANSCRIPTION_VERSION, manuscriptDesc.getTranscriptionVersion());
	session.setAttribute(MANUSCRIPT_PAGE,pageNumber);
	session.setAttribute(MANUSCRIPT_LINE,lineNumber);
	session.setAttribute(MANUSCRIPT_TOTAL_PAGES,manuscriptDesc.getTotalPageNumber());
	session.setAttribute(MANUSCRIPT_TOTAL_LINES,manuscriptDesc.getTotalLineNumbers(task.getPageNumber()));
	session.setAttribute(M_TRANSCRIBEDLINEIMGSRC,imageData.getTranscribedLineImgSrc());
	
	session.setAttribute("pageimgsrc",imageData.getPageImgSrc());
	
	session.setAttribute("boundingbox",imageData.getBoundingBox().toString());
	double leafletFactor = manuscriptDesc.getLeafletFactor();
	int ytop = (int) (imageData.getBoundingBox().y/leafletFactor);
	int ybottom = (int) ((imageData.getBoundingBox().y+imageData.getBoundingBox().height)/leafletFactor);
	int xleft = (int) (imageData.getBoundingBox().x/leafletFactor);
	int xright = (int) ((imageData.getBoundingBox().x+imageData.getBoundingBox().width)/leafletFactor);
	session.setAttribute("ytop",0-ytop);
	session.setAttribute("ybottom",0-ybottom);
	session.setAttribute("xleft",xleft);
	session.setAttribute("xright",xright);
	
	session.setAttribute(M_TRANSCRIBEDLINE,transcription.getString());
	session.setAttribute("transcribedlength",(transcription.getString().length()+1));
	System.out.println("transcribesdLength="+(transcription.getString().length()+1));
	System.out.println("Original Bounding box+"+imageData.getBoundingBox().toString());
	System.out.println("Leaflet coordinates ytop, ybottom, xleft, xright=("+ytop+","+ybottom+","+xleft+","+xright+")");
}
	
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding(UTF_8);
		Task task = (Task) request.getSession().getAttribute(TASK);
		if (task == null) {
			redirectToLoginPage(request, response);
			return;
		}

		String transcribed = request.getParameter("transcribed");
		
		int pageNumber= (int) request.getSession().getAttribute(MANUSCRIPT_PAGE);
		int lineNumber= (int) request.getSession().getAttribute(MANUSCRIPT_LINE);
		TikunUser user= (TikunUser) request.getSession().getAttribute(USER);
		UserDBase userDB = (UserDBase) request.getSession().getAttribute(USER_DB);
		String version = (String) request.getSession().getAttribute(M_TRANSCRIPTION_VERSION);
		if (version == null) {
			version = "";
		}
	//	ManuscriptDescriptor manuscriptDesc =  manuscriptProvider.getManuscriptDescription(task.getmId());
		ManuscriptPlace place = new ManuscriptPlace(task.getmId().getName(), pageNumber, lineNumber);
		String automaticTranscription = (String) request.getSession().getAttribute(M_TRANSCRIBEDLINE);
		if (automaticTranscription == null) {
			automaticTranscription = "";
			
		}
		if (transcribed == null) {
			transcribed = "";
			
		}
		int status = determineStatus(request);
		start = (long) request.getSession().getAttribute(M__TRANSCIPTION_STARTTIME);
		
		String ipAddress = getClientIpAddr(request);
		userDB.addTranscription(user.getId(), System.currentTimeMillis(), place, version, automaticTranscription, transcribed, status,start, ipAddress);
		System.out.println("!!!!CrowdSourceData: user-"+user.getId()+" status="+status+",page-"+pageNumber+",line-"+lineNumber+",transcribed-"+transcribed
				+"originalTranscribed:"+ automaticTranscription);

		
	    taskProvider = (TaskProvider) request.getSession().getAttribute(TASK_PROVIDER);
		taskProvider.getNextTask(task);
		request.getSession().setAttribute(TASK,task);
		setupTranscription(request.getSession(), task);
		start = System.currentTimeMillis();
		request.getSession().setAttribute(M__TRANSCIPTION_STARTTIME,start);
		request.getRequestDispatcher("/WEB-INF/views/transcribe.jsp").forward(
				request, response);

		
	}

	/**
	 * Try to get the actual client (browser) IP address. Taken from: https://stackoverflow.com/a/15323776
	 * @param request
	 * @return
	 */
	private String getClientIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	private int determineStatus(HttpServletRequest request) {
		//TODO
		int retVal = 0;
		String status = request.getParameter("status");
		if (status == null) {
			return retVal;
			
		}
		switch (status) {
		case "Done":
			retVal = 3;
			break;
		
		case "Skip":
			retVal = 0;
			break;
		default:
			break;
		}
		System.out.println("TTT status -"+status+":"+retVal);
	
		return retVal;
	}
	@Override
	public void init() throws ServletException {
		
		super.init();
		
	}
}