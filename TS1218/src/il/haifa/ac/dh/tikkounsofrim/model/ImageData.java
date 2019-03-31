package il.haifa.ac.dh.tikkounsofrim.model;



public class ImageData {
	private String imageId;
	private String imageServer;
	private BoundingBox bbox;
	private String prefix;
	private String scheme;
	public ImageData(String imageId, String imageServer, BoundingBox bbox, String prefix, String scheme) {
		super();
		this.imageId = imageId;
		this.imageServer = imageServer;
		this.bbox = bbox;
		this.prefix = prefix;
		this.scheme = scheme;
	}
	public String getImageId() {
		return imageId;
	}
	public String getServer() {
		return imageServer;
	}
	public BoundingBox getBoundingBox() {
		return bbox;
	}
	public String getPrefix() {
		return prefix;
	}
	public String getScheme() {
		// TODO Auto-generated method stub
		return scheme;
	}
    
	
	
	public String getPageImgSrc() {
		String src = getScheme()+"://"+getServer()+"/"+getPrefix()+"/"
				+getImageId()+"/info.json";
		System.out.println("TranscribdPageSrc="+src);
		return src;
	}

	
	public String getTranscribedLineImgSrc() {
		String src = getScheme()+"://"+getServer()+"/"+getPrefix()+"/"
				+getImageId()+"/"+getBoundingBox().toString()+"/full/0/default.jpg"; //.jpg
		System.out.println("TranscribdLineSrc="+src);
		return src;
	}
}
