package il.haifa.ac.dh.tikkounsofrim.impl;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import il.haifa.ac.dh.tikkounsofrim.model.ManuscriptPlace;
//import il.haifa.ac.dh.tikkounsofrim.model.TikunUser;
import il.haifa.ac.dh.tikkounsofrim.model.UserDBase;
import il.haifa.ac.dh.tikkounsofrim.model.UserInfo;
public class UserDBaseJDBC implements UserDBase {
	//private static final String DB_SERVER = "tikkoun-sofrim.haifa.ac.il";
	private static final String DB_SERVER = "localhost";
	
	private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
	private static UserDBaseJDBC instance = null;

	private UserDBaseJDBC() {
		
	}
	
	public static synchronized UserDBaseJDBC instance() {
		if (instance == null) {
			instance = new UserDBaseJDBC();
		}
		return instance;
	}
	
    public void readDataBase() throws Exception {
        try {
            // This will load the MySQL driver, each DB has its own driver
      //      Class.forName("com.mysql.jdbc.Driver");
            // Setup the connection with the DB
            connect();

            // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();
            // Result set get the result of the SQL query
            resultSet = statement
                    .executeQuery("select * from tikkoun.users");
            writeResultSet(resultSet);
//
//            // PreparedStatements can use variables and are more efficient
//            preparedStatement = connect
//                    .prepareStatement("insert into  feedback.comments values (default, ?, ?, ?, ? , ?, ?)");
//            // "myuser, webpage, datum, summary, COMMENTS from feedback.comments");
//            // Parameters start with 1
//            preparedStatement.setString(1, "Test");
//            preparedStatement.setString(2, "TestEmail");
//            preparedStatement.setString(3, "TestWebpage");
//            preparedStatement.setDate(4, new java.sql.Date(2009, 12, 11));
//            preparedStatement.setString(5, "TestSummary");
//            preparedStatement.setString(6, "TestComment");
//            preparedStatement.executeUpdate();
//
//            preparedStatement = connect
//                    .prepareStatement("SELECT myuser, webpage, datum, summary, COMMENTS from feedback.comments");
//            resultSet = preparedStatement.executeQuery();
//            writeResultSet(resultSet);
//
//            // Remove again the insert comment
//            preparedStatement = connect
//            .prepareStatement("delete from feedback.comments where myuser= ? ; ");
//            preparedStatement.setString(1, "Test");
//            preparedStatement.executeUpdate();

            resultSet = statement
            .executeQuery("select * from tikkoun.transcriptions");
            writeMetaData(resultSet);

        } catch (Exception e) {
            throw e;
        } finally {
            close();
        }

    }

	private void connect() throws SQLException, ClassNotFoundException {
	if (connect == null || connect.isClosed()) {
		DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
		connect = DriverManager
		        .getConnection("jdbc:mysql://"
		        		+ DB_SERVER
		        		+ "/tikkoun?"
		                + "user=tikun&password=Paris2019!&serverTimezone=UTC&characterEncoding=utf-8&useUnicode=true");
	}
	
	}

    private void writeMetaData(ResultSet resultSet) throws SQLException {
        //  Now get some metadata from the database
        // Result set get the result of the SQL query

        System.out.println("The columns in the table are: ");

        System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
        for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++){
            System.out.println("Column " +i  + " "+ resultSet.getMetaData().getColumnName(i));
        }
    }

    private void writeResultSet(ResultSet resultSet) throws SQLException {
        // ResultSet is initially before the first data set
       int i = 0;
    	while (resultSet.next()) {
    		i++;
    		String answer = resultSet.getString(i);
    	    System.out.print(answer+", ");
            // It is possible to get the columns via name
            // also possible to get the columns via the column number
            // which starts at 1
            // e.g. resultSet.getSTring(2);
            /*String user = resultSet.getString("myuser");
            String website = resultSet.getString("webpage");
            String summary = resultSet.getString("summary");
            Date date = resultSet.getDate("datum");
            String comment = resultSet.getString("comments");
            System.out.println("User: " + user);
            System.out.println("Website: " + website);
            System.out.println("summary: " + summary);
            System.out.println("Date: " + date);
            System.out.println("Comment: " + comment);*/
    	
        }
    }

    // You need to close the resultSet
    private void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {

        }
    }
    public static void main(String[] args) throws Exception {
        UserDBaseJDBC dao = new UserDBaseJDBC();
        dao.readDataBase();
    }

	@Override
	public int registerUser(String uName, String password, String email, UserInfo uInfo) {
		
		
      // PreparedStatements can use variables and are more efficient
      try {
    	connect();  
		preparedStatement = connect
		          .prepareStatement("insert into  tikkoun.users values (?, ?, ?, ?, ?, ?, ?, ?)");
		preparedStatement.setString(1, uName);
	      preparedStatement.setString(2, email);
	      preparedStatement.setString(3, password);
	      preparedStatement.setInt(4, uInfo.age);
	      preparedStatement.setInt(5, uInfo.hebrewknowledge);
	      preparedStatement.setInt(6, uInfo.midrashknowledge);
	      preparedStatement.setInt(7, 0);
	      preparedStatement.setTimestamp(8, new java.sql.Timestamp(System.currentTimeMillis()));
	      preparedStatement.executeUpdate();
	      return 0;
	} catch (SQLException e) {
	
		e.printStackTrace();
		return -1;
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
     
		return -1;
	}

	@Override
	public boolean checkUser(String uName) {
		// TODO Auto-generated method stub
		
		 try {
			 connect();  
			statement = connect.createStatement();
			// Result set get the result of the SQL query
	         resultSet = statement
	                 .executeQuery("select count(*) from tikkoun.users where userid = '"+uName+"'");
	         resultSet.last();
				int rowcount = resultSet.getInt(1);
				 if(rowcount > 0) {
					 
					 return true;
				 } else {
					 return false;
				 }
	        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
         
		
	}

	@Override
	public boolean isUserValid(String user, String password) {
		// TODO Auto-generated method stub
		try {
			connect();  
			statement = connect.createStatement();
			resultSet = statement
	                 .executeQuery("select count(*) from tikkoun.users where userid = '"+ user + "'and password = '"+password+"'");
			resultSet.last();
			int rowcount = resultSet.getInt(1);
			 if(rowcount > 0) {
				 return true;
			 }
			 
			
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Result set get the result of the SQL query
         
		return false;
	}

	@Override
	public int addTranscription(String user, long endtime, ManuscriptPlace place, String version,
			String automaticTranscription, String userTranscription, int status, long start, String ipAddress) {
		
		
		try {
			connect();  
			preparedStatement = connect
			          .prepareStatement("insert into tikkoun.transcriptions "
			          		+ "(date, userid, manuscript, page, line, transcriptionversion, "
			          		+ "automatictranscription, usertranscription, status, start, host) "
			          		+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			preparedStatement.setTimestamp(1,new Timestamp(endtime));
			preparedStatement.setString(2, user);
		      preparedStatement.setString(3, place.manuscriptId.getName());
		      preparedStatement.setInt(4, place.page);
		      preparedStatement.setInt(5, place.line);
		      preparedStatement.setString(6, version);
		      preparedStatement.setString(7, automaticTranscription);
		      preparedStatement.setString(8, userTranscription);
		      preparedStatement.setInt(9, status);
		      preparedStatement.setTimestamp(10, new Timestamp(start));
		      preparedStatement.setString(11, ipAddress);
		      preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
		return 0;
	}

	@Override
	public int getCount(String user) {
		// TODO Auto-generated method stub
		//user=user & status !=0;
		try {
			connect();
			statement = connect.createStatement();
			resultSet = statement
	                 .executeQuery("select count(*) from tikkoun.users where userid = '"+ user + "'and status <> '0'");
			boolean empty = resultSet.last();
			if (!empty) {
				int rowcount = resultSet.getInt(1);
				if (rowcount > 0) {
					return rowcount;
				} 
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int getTotalTimesLineSeen(ManuscriptPlace place) {
		// TODO Auto-generated method stub
		try {
			connect();
			statement = connect.createStatement();
			resultSet = statement
	                 .executeQuery("select count(*) from tikkoun.transcriptions where manuscript = '"+ place.manuscriptId +
	                		 "'and page = '"+place.page+ 
	                		 "'and line = '"+place.line+ 
	                		  "'");
			boolean empty = resultSet.last();
			if (!empty) {
				int rowcount = resultSet.getInt(1);
				if (rowcount > 0) {
					return rowcount;
				} 
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int getTotalTimesLineCorrected(ManuscriptPlace place) {
		// TODO Auto-generated method stub
		try {
			connect();
			statement = connect.createStatement();
			resultSet = statement
	                 .executeQuery("select count(*) from tikkoun.transcriptions where manuscript = '"+ place.manuscriptId.getName() +
	                		 "' and page = "+place.page+ 
	                		 " and line = "+place.line+ 
	                		 "");
//	                		 "'and status <> '0'");
			if (resultSet.last()) {
				int rowcount = resultSet.getInt(1);
					return rowcount;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;

	}
}


