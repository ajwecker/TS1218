package il.haifa.ac.dh.tikkounsofrim.model;

public class ManuscriptDescriptor {
	
	
	private String shortDescriptionEN;
	private String descriptionLinkEN;
	private String shortDescriptionFR;
	private String descriptionLinkFR;
	private String shortDescriptionHE;
	private String descriptionLinkHE;
	private String attribution;
	private double leafletFactor;
	private ManuscriptData mdata;
	private SegmentationProvider sdata; 
	private TranscriptionProvider tdata;
	public ManuscriptDescriptor(ManuscriptData mdata, SegmentationProvider sdata, TranscriptionProvider tdata, double leafletFactor,
			String shortDescriptionEN, String descriptionLinkEN,
			String shortDescriptionFR, String descriptionLinkFR,  
			String shortDescriptionHE, String descriptionLinkHE,
			String attribution) {
		super();
		
		this.mdata = mdata;
		this.leafletFactor = leafletFactor;
		this.shortDescriptionEN = shortDescriptionEN;
		this.descriptionLinkEN = descriptionLinkEN;
		this.shortDescriptionFR = shortDescriptionFR;
		this.descriptionLinkFR = descriptionLinkFR;
		this.shortDescriptionHE = shortDescriptionHE;
		this.descriptionLinkHE = descriptionLinkHE;
		this.attribution = attribution;
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
public String getShortDescription(String lang) {
	    String ret= "";
		switch (lang) {
		case "EN":
			ret = shortDescriptionEN;
			break;
		case "FR":
			ret = shortDescriptionFR;
			break;
		case "HE":
			ret = shortDescriptionHE;
			break;

		default:
			break;
		}
		return ret;
		
	}
public String getDescriptionLink(String lang) {
	 String ret= "";
		switch (lang) {
		case "EN":
			ret = descriptionLinkEN;
			break;
		case "FR":
			ret = descriptionLinkFR;
			break;
		case "HE":
			ret = descriptionLinkHE;
			break;

		default:
			break;
		}
		return ret;
	
	
	
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
}
