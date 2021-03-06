package il.haifa.ac.dh.tikkounsofrim.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import il.haifa.ac.dh.tikkounsofrim.Config;
import il.haifa.ac.dh.tikkounsofrim.model.ManuscriptPlace;
import il.haifa.ac.dh.tikkounsofrim.model.TikunUser;
import il.haifa.ac.dh.tikkounsofrim.model.UserDBase;
import il.haifa.ac.dh.tikkounsofrim.model.UserInfo;

public class UserDBaseJDBC implements UserDBase {
	private Connection connect = null;
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
		Statement statement = null;
		try {
			// This will load the MySQL driver, each DB has its own driver
			// Class.forName("com.mysql.jdbc.Driver");
			// Setup the connection with the DB
			connect();

			// Statements allow to issue SQL queries to the database
			statement = connect.createStatement();
			// Result set get the result of the SQL query
			ResultSet resultSet = statement.executeQuery("select * from users");
			writeResultSet(resultSet);
			resultSet = statement.executeQuery("select * from transcriptions");
			writeMetaData(resultSet);

		} catch (Exception e) {
			throw e;
		} finally {
			closeStatement(statement);
			close();
		}

	}

	private void connect() throws SQLException, ClassNotFoundException {
		if (connect == null || connect.isClosed()) {
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			String connectionURL = Config.getString("db.url");
			connect = DriverManager.getConnection(connectionURL);
		}

	}

	private void writeMetaData(ResultSet resultSet) throws SQLException {
		// Now get some metadata from the database
		// Result set get the result of the SQL query

		System.out.println("The columns in the table are: ");

		System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
		for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
			System.out.println("Column " + i + " " + resultSet.getMetaData().getColumnName(i));
		}
	}

	private void writeResultSet(ResultSet resultSet) throws SQLException {
		// ResultSet is initially before the first data set
		int i = 0;
		while (resultSet.next()) {
			i++;
			String answer = resultSet.getString(i);
			System.out.print(answer + ", ");
		}
	}

	// You need to close the resultSet
	private void close() {
		try {
			if (connect != null) {
				connect.close();
			}
		} catch (Exception e) {

		}
	}

	/**
	 * Not a real main. Just utility. This one converts the clear password to hashed ones.
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		UserDBaseJDBC dao = new UserDBaseJDBC();
		Statement statement = null;
		PreparedStatement updateStmt = null;
		try {
			dao.connect();
			statement = dao.connect.createStatement();
			updateStmt = dao.connect.prepareStatement("update users set hashed_password=?, password=null where userid=?");
			ResultSet resultSet = statement.executeQuery("select userid, password from users "
					+ "where password is not null and password <> '' and hashed_password is null");
			while (resultSet.next()) {
				String userid = resultSet.getString(1);
				String clearPassword = resultSet.getString(2);
				
				String hashed = Password.getSaltedHash(clearPassword);
				updateStmt.setString(1, hashed);
				updateStmt.setString(2, userid);
				updateStmt.execute();
				System.out.println("Updated " + userid + " to " + hashed);
			}
			// autocommit is set
			//dao.connect.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			dao.closeStatement(statement);
		}
	}

	@Override
	public int registerUser(String uName, String password, String email, boolean consent, UserInfo uInfo) {
		String hpassword;
		try {
			hpassword = Password.getSaltedHash(password);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return -1;
		}
		// PreparedStatements can use variables and are more efficient
		PreparedStatement preparedStatement = null;
		try {
			connect();

			preparedStatement = connect.prepareStatement("insert into users "
					+ "(userid, email, password, age, hebrewknowledge, midrashknowledge, numlines, "
					+ "registered, hashed_password, contact_allowed) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			preparedStatement.setString(1, uName);
			preparedStatement.setString(2, email);
			preparedStatement.setString(3, null); // clear password
			preparedStatement.setInt(4, uInfo.age);
			preparedStatement.setInt(5, uInfo.hebrewknowledge);
			preparedStatement.setInt(6, uInfo.midrashknowledge);
			preparedStatement.setInt(7, 0);
			preparedStatement.setTimestamp(8, new java.sql.Timestamp(System.currentTimeMillis()));
			preparedStatement.setString(9, hpassword);
			preparedStatement.setBoolean(10, consent);
			preparedStatement.executeUpdate();
			return 0;
		} catch (SQLException e) {

			e.printStackTrace();
			return -1;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeStatement(preparedStatement);
		}

		return -1;
	}
	
/*	update user
 * Get all users
 * loop on users
 *     get password
 *     saltAndHash paasword
 *     replace password
 * 
 * (non-Javadoc)
 * @see il.haifa.ac.dh.tikkounsofrim.model.UserDBase#checkUser(java.lang.String)
 */

	@Override
	public boolean checkUser(String uName) {
		Statement statement = null;
		try {
			if ("guest".equals(uName)) {
				return true;
			}
			connect();
			statement = connect.createStatement();
			// Result set get the result of the SQL query
			ResultSet resultSet = statement
					.executeQuery("select count(*) from users where userid = '" + uName + "'");
			resultSet.last();
			int rowcount = resultSet.getInt(1);
			if (rowcount > 0) {

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
		} finally {
			closeStatement(statement);
		}

	}

	@Override
	public boolean isUserValid(String user, String password) {
		Statement statement = null;
		try {
			if ("guest".equals(user)) {
				return true;
			}
			connect();
			statement = connect.createStatement();
			ResultSet resultSet = statement.executeQuery("select password, hashed_password from users where userid = '" + user +  "'");
			boolean exists = resultSet.last();
			if (exists) {
				String clearPassword = resultSet.getString(1);
				String hashedPassword = resultSet.getString(2);
				if (clearPassword != null && clearPassword.equals(password)) {
					return true;
				}
				// else, it's hashed
				return Password.check(password, hashedPassword);
			} 
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeStatement(statement);
		}

		return false;
	}

	/**
	 * @param statement
	 */
	private void closeStatement(Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
			}
		}
	}

	@Override
	public int addTranscription(String user, long endtime, ManuscriptPlace place, String version,
			String automaticTranscription, String userTranscription, int status, long start, String ipAddress) {
		PreparedStatement preparedStatement = null;

		try {
			connect();
			preparedStatement = connect.prepareStatement("insert into transcriptions "
					+ "(date, userid, manuscript, page, line, transcriptionversion, "
					+ "automatictranscription, usertranscription, status, start, host) "
					+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			preparedStatement.setTimestamp(1, new Timestamp(endtime));
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
		} finally {
			closeStatement(preparedStatement);
		}
		return 0;
	}

	@Override
	public int getCount(String user) {
		if (user == null) {
			return 0;
		}
		Statement statement = null;
		try {
			connect();
			statement = connect.createStatement();
			ResultSet resultSet = statement.executeQuery(
					"select count(*) from transcriptions where userid = '" + user + "' and status <> 0");
			if (resultSet.next()) {
				return resultSet.getInt(1);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeStatement(statement);
		}
		return 0;
	}

	@Override
	public int getTotalTimesLineSeen(ManuscriptPlace place) {
		Statement statement = null;
		try {
			connect();
			statement = connect.createStatement();
			ResultSet resultSet = statement
					.executeQuery("select count(*) from transcriptions where manuscript = '"
							+ place.manuscriptId + "'and page = '" + place.page + "'and line = '" + place.line + "'");
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
		} finally {
			closeStatement(statement);
		}
		return 0;
	}

	@Override
	public int getTotalTimesLineCorrected(ManuscriptPlace place) {
		Statement statement = null;
		try {
			connect();
			statement = connect.createStatement();
			ResultSet resultSet = statement.executeQuery(
					"select count(*) from transcriptions where manuscript = '" + place.manuscriptId.getName()
							+ "' and page = " + place.page + " and line = " + place.line + " and status <> 0");
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
		} finally {
			closeStatement(statement);
		}
		return 0;

	}

	@Override
	public boolean userDidLine(ManuscriptPlace place, String user) {
		Statement statement = null;
		try {
			connect();
			statement = connect.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from transcriptions where manuscript = '"
					+ place.manuscriptId.getName() + "' and page = " + place.page + " and line = " + place.line
					+ " and userid = '" + user + '\'');
			return resultSet.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeStatement(statement);
		}
		return false;
	}

	@Override
	public int isLineDone(ManuscriptPlace place, int seenLimit, int correctLimit, TikunUser user) {
		// TODO Auto-generated method stub
		Statement statement = null;
		try {
			connect();
			statement = connect.createStatement();
			ResultSet resultSet = statement
					.executeQuery("select * from transcriptions where manuscript = '"
							+ place.manuscriptId + "'and page = '" + place.page + "'and line = '" + place.line + "'");
			int seenCount = 0;
			int doneCount = 0;
			while (resultSet.next()) {
			   
			  
			   seenCount++;
			   if (seenCount > seenLimit) {
				   return 1;
			   }
			   
			   int status = resultSet.getInt(9);
			   if (status != 0 ) {
				   doneCount++;
				   if (doneCount > correctLimit) {
					   return 2;
				   }
				   if("guest".equals(user.getId())){
					   return 3;
				   }
				   String author = resultSet.getString(2);
				   if(author.equals(user.getId())){
					   return 4;
				   }
					
				}
			   
			  
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeStatement(statement);
		}
		return 0;
	}
}
