package il.haifa.ac.dh.tikkounsofrim.model;

public interface ManuscriptData {

	

	int getNumberOfPages();

	int getNumberOfLines(int pageNumber);
	String getTranscriptionVersion();

}