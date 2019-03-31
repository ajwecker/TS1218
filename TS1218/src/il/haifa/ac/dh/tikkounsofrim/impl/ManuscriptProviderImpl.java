package il.haifa.ac.dh.tikkounsofrim.impl;


import java.util.Iterator;
import java.util.List;

import il.haifa.ac.dh.tikkounsofrim.model.ImageData;

import il.haifa.ac.dh.tikkounsofrim.model.ManuscriptDescriptor;
import il.haifa.ac.dh.tikkounsofrim.model.ManuscriptID;
import il.haifa.ac.dh.tikkounsofrim.model.ManuscriptProvider;

import il.haifa.ac.dh.tikkounsofrim.model.SegData;


import il.haifa.ac.dh.tikkounsofrim.model.TranscribedString;


public class ManuscriptProviderImpl implements ManuscriptProvider {
	private List<ManuscriptDescriptor> manuscripts;
	private static ManuscriptProviderImpl instance = null;
	//ManuscriptDescriptor GENEVA146; 
		
	private ManuscriptProviderImpl() {
		super();
		try {
			manuscripts=ManuscriptDBaseJDBC.instance().loadDataBase();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 
		
	}
	
	synchronized public static ManuscriptProviderImpl instance() {
		if (instance == null) {
			instance = new ManuscriptProviderImpl();
		}
		return instance;
	}
	
	//Task Provider

	
	//Transcription Provider
	@Override
	public TranscribedString getTranscribedLine(ManuscriptID id, int pageNumber, int lineNumber, int tagLevel) {
		// TODO Auto-generated method stub
		TranscribedString ts = getManuscriptDescription(id).getTranscribedLine(pageNumber, lineNumber, tagLevel);
		if(ts != null) {
			System.out.println("From data -"+ts.getString());
		} else {
			System.out.println("From data - null");
		}
		return ts;  
		
		
	//	return new TranscribedString("והכניס צלם בתוכו˙ ד'א' שכך כתי' אשר בידו נפש כל", null);
	}

	
	
	
//	//ManuscriptProvider
//	@Override
//	public PageDescriptor getManuscriptPageDescription(ManuscriptID id, int pageNumber) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public ImageData getManuscriptLine(ManuscriptID id, int pageNumber, int activeLine, int imageNumber, int fromLine, int toLine
			) {
		// TODO Auto-generated method stub
	//	BoundingBox bb = new BoundingBox(511,4684,4155,449);
		//BoundingBox bb = new BoundingBox(0,105,243, 33);
	//	return new ImageResult("bge-cl0146_007.jpg", "localhost:8080", bb, "iiif/2", "http");
		SegData sd = getManuscriptDescription(id).getSegmentationData(pageNumber, activeLine);
		System.out.println("ImageName "+ sd.getImgName());
		System.out.println("BoundingBox "+sd.getBbox());
		return new ImageData(sd.getImgName(), "tikkoun-sofrim.haifa.ac.il/cantaloupe", sd.getBbox(), "iiif/2", "https");
	}

	


	@Override
	public ManuscriptDescriptor getManuscriptDescription(ManuscriptID id) {
		//TODO
		ManuscriptDescriptor manuscriptDescriptor=null;
		for (Iterator<ManuscriptDescriptor> iterator = manuscripts.iterator(); iterator.hasNext();) {
			manuscriptDescriptor = (ManuscriptDescriptor) iterator.next();
			if (manuscriptDescriptor.getId().equals(id.getName())) {
				break;
				
			}
			
		}

		return manuscriptDescriptor;
	}



//	@Override
//	public SegData getSegmentationData(ManuscriptID id, int pageNumber, int activeLine) {
//		
//		return getManuscriptDescription(id).getSegmentationData(pageNumber, activeLine);
//	}


	


	
}
