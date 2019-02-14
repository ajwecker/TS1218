package il.haifa.ac.dh.tikkounsofrim.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import il.haifa.ac.dh.tikkounsofrim.impl.TSVUtils.DoParse;
import il.haifa.ac.dh.tikkounsofrim.model.BoundingBox;
import il.haifa.ac.dh.tikkounsofrim.model.ManuscriptData;
import il.haifa.ac.dh.tikkounsofrim.model.SegData;
import il.haifa.ac.dh.tikkounsofrim.model.SegmentationProvider;
import il.haifa.ac.dh.tikkounsofrim.model.TranscribedString;
import il.haifa.ac.dh.tikkounsofrim.model.TranscriptionProvider;

public class ManuscriptDataImpl implements ManuscriptData, SegmentationProvider, TranscriptionProvider  {
	
	
	
	public final static String tvsGeneva = "Geneva_146";
	
	
	
	
	
    private int numberOfPages=0;
	private List <Integer> pageLengths = new ArrayList<Integer>();
	private List <List <LineData>> manuscriptLineData = new ArrayList<List<LineData>>();
	private String transcriptionVerion ="";
	
	
	
	static ManuscriptData loadData(String tvsFile){
		//So we can access the data in the inner class
		final int[] currentPage= new int[1]; currentPage[0]=0;
		final int[] currentLine= new int[1]; currentLine[0]=0;
		final List<List<LineData>> currentPageLineData = new ArrayList<List<LineData>>(1);
					currentPageLineData.add(0, new ArrayList<LineData>());
		
		
		ManuscriptDataImpl mData = new ManuscriptDataImpl();
	
		String version = TSVUtils.readIn(FilePathUtils.getFilePath()+File.separatorChar+tvsFile+".txt", new DoParse() {
			
			@Override
			public void doIt(String[] item) {
				// TODO Auto-generated method stub
// 0	1			    2           3               4               5                6   7      8        9               10
//page	line4Filename	top_on_page	bottom_on_page	left_on_page	right_on_page	AT	GT01	GT02	enriched_y_n	color_img_file_name
				int page = Integer.parseInt(item[0]);
				if(page != currentPage[0]) {
					mData.pageLengths.add(currentPage[0], (currentLine[0]-1));
					mData.manuscriptLineData.add(currentPage[0], currentPageLineData.get(0));
					mData.numberOfPages=page;
					//new Page
					currentPage[0]=page;
					
					
					currentPageLineData.add(0, new ArrayList<LineData>());
					currentPageLineData.get(0).add(null);
					currentLine[0]=1;
					
				}
				if(page == 8) {
					System.out.println("T8="+item[6]+"-"+item[10]);
				}
				int line = Integer.parseInt(item[1]);
				if (line != currentLine[0]) {
					System.err.println("!!!Failed sanity check at page="+currentPage+" line="+line+" expecting line="+currentLine);
				}
				int top = Integer.parseInt(item[2]);
				int bottom = Integer.parseInt(item[3]);
				int left = Integer.parseInt(item[4]);
				int right = Integer.parseInt(item[5]);
				int height = bottom - top;
				int width = right -left;
				BoundingBox bbox = new BoundingBox(left, top, width, height);
				String transcription = item[6];  //was item[7]; GT now AT
				if(transcription == null || transcription.length()==0) {
					transcription = ""; // was item[6]; AT
				}
				if(currentLine[0]==13) {
					System.out.println("load page="+page+" Transcription line="+currentLine[0]+" ="+transcription);
				}
				String imgName = item[10];
				SegData lineSegData = new SegData(page, line, bbox,  imgName);
				TranscribedString ts = new TranscribedString(transcription, null, "version");
				LineData lineData = new LineData(ts,lineSegData);
				currentPageLineData.get(0).add(line, lineData);
				currentLine[0]++;
			}
		});
		mData.transcriptionVerion = version;
		mData.pageLengths.add(currentPage[0], currentLine[0]-2);
		mData.manuscriptLineData.add(currentPage[0], currentPageLineData.get(0));
		
		return mData;
	}
	/* (non-Javadoc)
	 * @see il.haifa.ac.dh.tikkounsofrim.impl.ManuscriptData#getTranscribedData(int, int)
	 */
	@Override
	public SegData getSegmentationData(int page, int lineNumber) {
		SegData sd = (manuscriptLineData.get(page)).get(lineNumber).getSegData();
		return sd;
		
	}
	@Override
	public int getNumberOfPages() {
		
		return numberOfPages;
	}
	@Override
	public int getNumberOfLines(int pageNumber) {
	
		return manuscriptLineData.get(pageNumber).size()-1; //Don't count array position 0
	}
	@Override
	public TranscribedString getTranscribedLine(int pageNumber, int lineNumber, int tagLevel) {
		
		return (manuscriptLineData.get(pageNumber)).get(lineNumber).getTranscribedString();
	}
	@Override
	public String getTranscriptionVersion() {
		
		return transcriptionVerion;
	}

}
class LineData {
	
	private TranscribedString tstring;
	private SegData sd;
	
	public LineData(TranscribedString ts, SegData lineSegData) {
		tstring = ts;
		sd = lineSegData;
	}
	public TranscribedString getTranscribedString() {
		return tstring;
	}

	public SegData getSegData() {
		return sd;
	}

}
