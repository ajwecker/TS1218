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
	
	
	
	public final static String tvsGeneva = "Geneva146";
	
	
	
	
	
    private int numberOfPages=0;
	private List <Integer> pageLengths = new ArrayList<Integer>();
	private List <List <LineData>> manuscriptLineData = new ArrayList<List<LineData>>();
	private String transcriptionVerion ="";





	static int iImageFile=10;
	static int iAT = 6;
	static int iPage = 0;
	static int iLine =1 ;
	static int iTop = 2;
	static int iBottom = 3; 
	static int iRight =4;
	static int iLeft=5;
	
	
	static ManuscriptData loadData(String tvsFile, int datafileversion){
		//So we can access the data in the inner class
		final int fileversion = datafileversion;
		final int[] currentPage= new int[1]; currentPage[0]=0;
		final int[] currentLine= new int[1]; currentLine[0]=0;
		final List<List<LineData>> currentPageLineData = new ArrayList<List<LineData>>(1);
					currentPageLineData.add(0, new ArrayList<LineData>());
		loadIndex(fileversion);			
		
		
		ManuscriptDataImpl mData = new ManuscriptDataImpl();
	
		String version = TSVUtils.readIn(FilePathUtils.getFilePath()+File.separatorChar+tvsFile+".txt", new DoParse() {
			
			@Override
			public void doIt(String[] item) {
				// TODO Auto-generated method stub
				int page = Integer.parseInt(item[iPage]);
				if(page != currentPage[0]) {
					while (page != currentPage[0]+1) {
						//add skipped pages
						mData.pageLengths.add(currentPage[0], (currentLine[0]-1));
						mData.manuscriptLineData.add(currentPage[0], currentPageLineData.get(0));
						currentPage[0]++;
					}
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
					System.out.println("T8="+item[iAT]+"-"+item[iImageFile]);
				}
				int line = Integer.parseInt(item[iLine]);
				if(line == 0) { // include = 0;
					return;
				}
				while (line != currentLine[0]) {
					System.err.println("!!!Missing lines at page="+currentPage[0]+" line="+line+" expecting line="+currentLine[0]);
					
					currentPageLineData.get(0).add(currentLine[0], null);
					currentLine[0]++;
				}
				int top = Integer.parseInt(item[iTop]);
				int bottom = Integer.parseInt(item[iBottom]);
				int left = Integer.parseInt(item[iLeft]);
				int right = Integer.parseInt(item[iRight]);
				int height = bottom - top;
				int width = right -left;
				BoundingBox bbox = new BoundingBox(left, top, width, height);
				String transcription = item[iAT];  //was item[7]; GT now AT
				if(transcription == null || transcription.length()==0) {
					transcription = ""; // was item[6]; AT
				}
				if(currentLine[0]==13) {
					System.out.println("load page="+page+" Transcription line="+currentLine[0]+" ="+transcription);
				}
				String imgName = item[iImageFile];
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
	private static void loadIndex(int fileversion) {
		// 		0	1			    2           3               4               5                	6   	7      8        9               10					11				12			13				14				15				16	17	18				19					20					21			22			23			24			25				26
		//V1  page	line4Filename	top_on_page	bottom_on_page	left_on_page	right_on_page		AT		GT01	GT02	enriched_y_n	color_img_file_name
		//V2: DanOrder	alanOrder	lineID	    page			page_on_photo	col_on_single_page	x1col	y1col	x2col	y2col			newLineNuAlan	GTLineNumberDaniel	top_on_page	bottom_on_page	left_on_page	right_on_page	AT	GT2	enriched_y_n	color_img_file_name	include	regionID	regionx1	regiony1	regionx2	regiony2	enriched_y_n	color_img_file_name				

		switch (fileversion) {
		case 1:
			 iImageFile=10;
			 iAT = 6;
			 iPage = 0;
			 iLine =1 ;
			 iTop = 2;
			 iBottom = 3; 
			 iRight =5;
			 iLeft=4;
			break;
		case 2:
			 iImageFile=19;
			 iAT = 16;
			 iPage = 3;
			 iLine =10 ;
			 iTop = 12;
			 iBottom = 13; 
			 iRight =15;
			 iLeft=14;
			break;	

		default:
			break;
		}
		
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
