package il.haifa.ac.dh.tikkounsofrim.model;

public class ManuscriptDescriptor {
	
	
	String  id;
	private String attribution;
	private double leafletFactor;
	int difficulty;
	private int priority;
	
	String[] specialcharacters;
	String[] linefillers;
	private ManuscriptData mdata;
	private SegmentationProvider sdata; 
	private TranscriptionProvider tdata;
	public ManuscriptDescriptor(String id, ManuscriptData mdata, SegmentationProvider sdata, TranscriptionProvider tdata, double leafletFactor,
			int difficulty,
			int priority,
			String[] specialcharacters,
			String[] linefillers,
			String attribution) {
		super();
		this.id = id;
		this.mdata = mdata;
		this.leafletFactor = leafletFactor;
		
		this.specialcharacters = specialcharacters;
		this.attribution = attribution;
		this.difficulty = difficulty;
		this.setPriority(priority);
		this.sdata = sdata;
		this.tdata = tdata;
	}
	public ManuscriptDescriptor() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getTotalPageNumber() {
		
		return mdata.getNumberOfPages();
	}
	public int getTotalLineNumbers(int pageNumber) {
		int lines = mdata.getNumberOfLines(pageNumber); 
		return lines;
		
	}

	
	

public double getLeafletFactor() {
	return leafletFactor;
}
public SegData getSegmentationData(int pageNumber, int activeLine) {
	
	return sdata.getSegmentationData(pageNumber, activeLine);
}

public TranscribedString getTranscribedLine( int pageNumber, int lineNumber, int tagLevel) {
	return tdata.getTranscribedLine( pageNumber, lineNumber, tagLevel);
	
}
public String getAttribution() {
	return attribution;
}
public String getTranscriptionVersion() {
	return mdata.getTranscriptionVersion();
}
public String getId() {
	
	return id;
}
public String getShortDescription(String lang) {
	// TODO Auto-generated method stub
	return null;
}
public String getDescriptionLink(String lang) {
	// TODO Auto-generated method stub
	return null;
}
public int getPriority() {
	return priority;
}
public void setPriority(int priority) {
	this.priority = priority;
}
}
