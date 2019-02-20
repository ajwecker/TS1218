package il.haifa.ac.dh.tikkounsofrim.impl;
	import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedSet;



	public class TSVUtils {
		public interface DoParse {
			void doIt(String[] item);

		}

	    private static final char DEFAULT_SEPARATOR = '\t';

	    public static void writeLine(Writer w, List<String> values) throws IOException {
	        writeLine(w, values, DEFAULT_SEPARATOR, ' ');
	    }

	    public static void writeLine(Writer w, List<String> values, char separators) throws IOException {
	        writeLine(w, values, separators, ' ');
	    }
	    
	    public static void printLn(String[] line) {
	    	String ps ="";
	    	for(String s : line) {
	    		ps=ps+", "+s;
	    	}
	    	System.out.println("Line: "+ps);
	    }

	    //https://tools.ietf.org/html/rfc4180
	    private static String followCVSformat(String value) {

	        String result = value;
	        if (result.contains("\"")) {
	            result = result.replace("\"", "\"\"");
	        }
	        return result;

	    }
	    
	    public static String readIn(String csvFile, DoParse parser) {
			  String line = "";
		        String cvsSplitBy = String.valueOf(DEFAULT_SEPARATOR);
		
		        try {
		        	
		        	 List<String> lines = Files.readAllLines(Paths.get(csvFile), Charset.forName("UTF-8"));//("ISO-8859-1"));//("UTF-8"));
		        	 String line0 = lines.remove(0);
		        	 String[]  fitem = line0.split(",");
		        	 line = fitem[0];
		        	 System.out.println("First line header "+ line0);
		        	 
		        	 for(String line1 :lines) {
		        		 String[]  item = line1.split(cvsSplitBy);
			                if (!"//".equals(item[0])) {
								try {
									parser.doIt(item);
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									return line;
								}
							}
		        		 
		        	 }
		        	
		        	/*
		        	
		        	BufferedReader br = new BufferedReader(new InputStreamReader(
		                      new FileInputStream(csvFile), "UTF-8")); //new BufferedReader(new FileReader(csvFile));
		        
		            while ((line = br.readLine()) != null) {
		
		                // use comma as separator
		                String[]  item = line.split(cvsSplitBy);
		                if (!"//".equals(item[0])) {
							parser.doIt(item);
						}
					
		            }
		            br.close();
			*/
		        } catch (IOException e) {
		            e.printStackTrace();
		        }
		
			return line;
		}

	    public static String readFileAll(String filename, String path) {
			
			Path fullfilename = Paths.get(FilePathUtils.getFilePath()+path+filename);
	//		System.out.println("Path is: "+fullfilename.toString());
			List<String> contentLines=null;
			try {
				contentLines = Files.readAllLines(fullfilename, Charset.forName("UTF-8"));
			} catch (Exception e) {
				
				System.err.println("================Problem reading in"+fullfilename);
				e.printStackTrace();
				return "problem with"+fullfilename;
			}
			StringBuffer content = new StringBuffer();
			for(String c : contentLines){
				content.append(c+" ");
			}
			return content.toString();
		}
	    public static void writeLine(Writer w, List<String> values, char separators, char customQuote) throws IOException {

	        boolean first = true;

	        //default customQuote is empty

	        if (separators == ' ') {
	            separators = DEFAULT_SEPARATOR;
	        }

	        StringBuilder sb = new StringBuilder();
	        for (String value : values) {
	            if (!first) {
	                sb.append(separators);
	            }
	            if (customQuote == ' ') {
	                sb.append(followCVSformat(value));
	            } else {
	                sb.append(customQuote).append(followCVSformat(value)).append(customQuote);
	            }

	            first = false;
	        }
	        sb.append("\n");
	        w.append(sb.toString());


	    }

		public static void writeFile(String csvFile, SortedSet<Entry<String, String>> sortedTable) {
			FileWriter writer;
			try {
				writer = new FileWriter(csvFile);
			
			for(Entry<String, String> item: sortedTable)  {
				   TSVUtils.writeLine(writer, Arrays.asList(item.getKey(), item.getValue()));
			}
	     
	
	        
	
	
	        writer.flush();
	        writer.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		
			
		}


}
