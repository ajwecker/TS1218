package il.haifa.ac.dh.tikkounsofrim.impl;


import il.haifa.ac.dh.tikkounsofrim.model.ImageData;
import il.haifa.ac.dh.tikkounsofrim.model.ManuscriptData;
import il.haifa.ac.dh.tikkounsofrim.model.ManuscriptDescriptor;
import il.haifa.ac.dh.tikkounsofrim.model.ManuscriptID;
import il.haifa.ac.dh.tikkounsofrim.model.ManuscriptProvider;

import il.haifa.ac.dh.tikkounsofrim.model.SegData;
import il.haifa.ac.dh.tikkounsofrim.model.SegmentationProvider;

import il.haifa.ac.dh.tikkounsofrim.model.TranscribedString;
import il.haifa.ac.dh.tikkounsofrim.model.TranscriptionProvider;

public class ManuscriptProviderImpl implements ManuscriptProvider {
	
	private static ManuscriptProviderImpl instance = null;
	ManuscriptDescriptor GENEVA146; 
		
	private ManuscriptProviderImpl() {
		super();
		ManuscriptData gmdata= ManuscriptDataImpl.loadData(ManuscriptDataImpl.tvsGeneva);
		GENEVA146 = new ManuscriptDescriptor(gmdata, (SegmentationProvider) gmdata, (TranscriptionProvider) gmdata, 15.9, 
						 "One of the earliest manuscript exemplars of the version of the Tanhuma midrash text known among scholars as the &quot;printed text&quot;"+
						 "(first printed in Constantinople, 1520-22), as distinguished from the version first edited and printed by Solomon Buber in Vilnius, 1885. "+
						 "Copied probably somewhere in the Orient around the 14th century, the Hebrew script is Oriental semi-cursive.,",
						 "http://www.e-codices.unifr.ch/en/description/bge/cl0146/",
						 "Il s&#39;agit de l&#39;un des plus anciens exemplaires manuscrits de la version du texte de la Tanhuma midrash,"+
						 " connu parmi les savants comme le &quot;printed text&quot; (première édition imprimée à Constantinople, 1520-22),"+
						 " et qui se distingue de la version éditée et imprimée pour la première fois en 1885, par Solomon Buber à Vilnius."+
						 " Il a probablement été copié quelque part en Orient aux alentours du XIVe s. L&#39;écriture hébraïque est une semi-cursive"+
						 " orientale. Genève, Bibliothèque de Genève, Comites Latentes 146: Midrash Tanhuma (Lévitique-Nombres-Deutéronome)",
						 "http://www.e-codices.unifr.ch/fr/description/bge/cl0146",
						 "כתב יד זנבה 146 כולל את מדרש תנחומא לחומשים ויקרא, במדבר ודברים. זהו כתב יד מזרחי מן המאה ה-14, והוא מוצג כאן הודות לרשיון הפתוח של ספריית אוניברסיטת זנבה.",
						 "http://www.e-codices.unifr.ch/en/description/bge/cl0146/",
						 "<a href='(http://www.e-codices.unifr.ch/en/list/one/bge/cl0146'>Genève, Bibliothèque de Genève, Comites Latentes 146: Midrash Tanhuma (Leviticus-Numbers-Deuteronomy)</a>"+
						 "<a href=' https://creativecommons.org/licenses/by-nc/4.0/'>Images CC-BY-NC 4.0</a>"
						 
				);
		 
		
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
		

		return GENEVA146;
	}



//	@Override
//	public SegData getSegmentationData(ManuscriptID id, int pageNumber, int activeLine) {
//		
//		return getManuscriptDescription(id).getSegmentationData(pageNumber, activeLine);
//	}


	


	
}
