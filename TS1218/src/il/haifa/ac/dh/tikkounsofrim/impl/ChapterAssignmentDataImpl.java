package il.haifa.ac.dh.tikkounsofrim.impl;

import java.io.File;
import java.util.*;

import il.haifa.ac.dh.tikkounsofrim.impl.TSVUtils.DoParse;
import il.haifa.ac.dh.tikkounsofrim.model.*;

/**
 * Read a table of biblical chapter (929 numbering) to page/lines mapping. the
 * table is read from a TSV file.
 * 
 * @author urischor
 *
 */
public class ChapterAssignmentDataImpl implements ChapterAssignmentData {

	public final static String TSV_GENEVA_FILENAME = "Geneva_146_929.tsv.txt";
	
	public final static String GENEVA_MANUSCRIPT_NAME = "Geneva_146";
	public final static String BNF_MANUSCRIPT_NAME = "BNF_150";

	private Map<Integer, ChapterAssignment> chapterLines = new HashMap<Integer, ChapterAssignment>();

	private String name;

	public ChapterAssignmentDataImpl() {
		String tsvFile = TSV_GENEVA_FILENAME;

		TSVUtils.readIn(FilePathUtils.getFilePath() + File.separatorChar + tsvFile, new DoParse() {

			@Override
			public void doIt(String[] item) {
				// 0                        1                  2                 3                 4               5
				// actual_929_ch_assignment 929_chapter_number Genave_Page_begin Geneva_line_begin Geneva_Page_end Geneva_line_end
				int chapter = Integer.parseInt(item[1]);
				int startPage = Integer.parseInt(item[2]);
				int startLine = Integer.parseInt(item[3]);
				int endPage = Integer.parseInt(item[4]);
				int endLine = Integer.parseInt(item[5]);
				System.out.println(" Assignment: " + chapter + ", " + startPage + ", " + startLine + ", " + endPage + ", " + endLine);
				ChapterAssignment assingment = new ChapterAssignment(GENEVA_MANUSCRIPT_NAME, chapter, startPage,
						startLine, endPage, endLine);
				chapterLines.put(chapter, assingment);
			}
		});
		name = GENEVA_MANUSCRIPT_NAME;
	}
	
	/* (non-Javadoc)
	 * @see il.haifa.ac.dh.tikkounsofrim.impl.ChapterAssignmentProvider#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see il.haifa.ac.dh.tikkounsofrim.impl.ChapterAssignmentProvider#getChapterAssignment(int)
	 */
	@Override
	public ChapterAssignment getChapterAssignment(int chapterNumber) {
		return chapterLines.get(chapterNumber);
	}
}
