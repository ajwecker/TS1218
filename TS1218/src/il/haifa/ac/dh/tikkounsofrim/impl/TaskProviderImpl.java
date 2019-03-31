
package il.haifa.ac.dh.tikkounsofrim.impl;

import il.haifa.ac.dh.tikkounsofrim.model.*;

public class TaskProviderImpl implements TaskProvider {
	private static final int SEEN_LIMIT = 20;
	private static final int CORRECT_LIMIT = 5;
	ManuscriptProvider mprov = null;
	UserDBase userDB = null;
	ChapterAssignmentData chapterAssignmentData = null;
	private static ManuscriptPlace globalSeed = getDefaultManuscriptPlace();

	public TaskProviderImpl(ManuscriptProvider mprov, ChapterAssignmentData chapterAssignmentData, UserDBase userDB) {
		super();
		this.mprov = mprov;
		this.userDB = userDB;
		this.chapterAssignmentData = chapterAssignmentData;
	}

	@Override
	public Task getTask(TikunUser user) {

		
		ManuscriptPlace seed = getNextFree(globalSeed,user);
		return getTask(user, seed);
	}

	private ManuscriptPlace getNextFree(ManuscriptPlace seed, TikunUser user) {
		// TODO Auto-generated method stub
		boolean free = checkIfFree(seed, user);
		ManuscriptPlace start = new ManuscriptPlace(seed.manuscriptId.getName(), seed.line, seed.line);
		while (!free) {
			seed = seed.getNext(mprov.getManuscriptDescription(seed.manuscriptId).getTotalLineNumbers(seed.page),
					mprov.getManuscriptDescription(seed.manuscriptId).getTotalPageNumber());
			free = checkIfFree(seed, user);
			if(start.equals(seed)){
				break;
			}
		}
		return seed;
	}

	private boolean checkIfFree(ManuscriptPlace seed, TikunUser user) {
		// check whether the line hase been seen opr corrected more that the threshold.
		// Also, skip the line if user already saw it, unless it the guest account
		int retCode = userDB.isLineDone(seed, SEEN_LIMIT, CORRECT_LIMIT, user);
		if (retCode == 0) {
			return false;
		}
		if(retCode == 1 || retCode==2) {
			globalSeed = seed;
		}
		return true;
	}

	public Task getTask(TikunUser user, ManuscriptPlace firstPlace) {

		return new Task(user, firstPlace, null, null);
	}

	@Override
	public Task getNextTask(Task task) {
		int currentLineNumber = task.getLineNumber();
		int currentPage = task.getPageNumber();
		currentLineNumber++;
		if (currentLineNumber <= mprov.getManuscriptDescription(task.getmId()).getTotalLineNumbers(currentPage)) {
			return task.setLineNumber(currentLineNumber);
		} else if (currentPage < mprov.getManuscriptDescription(task.getmId()).getTotalPageNumber()) {
			currentPage++;
			currentLineNumber = 1;
			return task.setPageNumber(currentPage);
		}

		return getTask(task.getUser());
	}

	@Override
	public int getNumberOfLinesTranscribed(Task task) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Task getTask(TikunUser user, int ntnChapterId) {
		ManuscriptPlace place = lookup(ntnChapterId);
		return getFreeTask(user, place);
	}
	
	@Override
	public Task getTask(TikunUser user, int book, int chapter) {
		ManuscriptPlace place = lookup(book, chapter);
		return getFreeTask(user, place);
	}
	
	private Task getFreeTask(TikunUser user, ManuscriptPlace place) {
		Task task = new Task(user, place, null, null);		
		while (true) {
			if (checkIfFree(task.getPlace(), user)) {
				return task;
			}
			
			// else, place not free
			task = getNextTask(task);		
		}
	}

	private ManuscriptPlace lookup(int book, int chapter) {
		// TODO do actual lookup
		ManuscriptPlace seed = getDefaultManuscriptPlace();
		return seed;
	}

	/**
	 * @return
	 */
	private static ManuscriptPlace getDefaultManuscriptPlace() {
		return new ManuscriptPlace(ChapterAssignmentDataImpl.GENEVA_MANUSRIPT_NAME,250, 1);
	}

	/**
	 * Look for the beginning of a bible (929) chapter
	 * @param ntnChapterId
	 * @return
	 */
	private ManuscriptPlace lookup(int ntnChapterId) {
		ChapterAssignment chapterAssignment = chapterAssignmentData.getChapterAssignment(ntnChapterId);
		if (chapterAssignment == null) {
			return globalSeed;
		}

		ManuscriptPlace seed = new ManuscriptPlace(chapterAssignmentData.getName(),
				chapterAssignment.getStartPage(), chapterAssignment.getStartLine());
		return seed;
	}

}
