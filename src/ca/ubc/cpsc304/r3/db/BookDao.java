package ca.ubc.cpsc304.r3.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ca.ubc.cpsc304.r3.dto.BookDto;

public class BookDao {

	private ConnectionService connService;

	public BookDao(ConnectionService connService){
		this.connService = connService;
	}

	/**
	 * A sample query to demonstrate how the data access will work.
	 * @return a list of database table names.
	 * @throws SQLException If there was a database error
	 */
	public List<TableDto> getDatabaseTableNames() throws SQLException{
		List<TableDto> queryResult = new ArrayList<TableDto>();
		Connection conn = null; 
		try {
			conn = connService.getConnection();
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(
					"SELECT table_name, table_rows " + 
					"FROM information_schema.tables " + 
					"WHERE table_schema='crazycoollibrary'");
			while(rs.next()){
				// for each row, put the data in the dto
				// and add it to list of results
				TableDto dto = new TableDto();
				dto.setTableName(rs.getString("table_name"));
				dto.setNumRows(rs.getInt("table_rows"));
				queryResult.add(dto);
			}
		} catch (SQLException e) {
			// two options here. either don't catch this expection and 
			// make the caller handle it, or wrap it in a more 
			// descriptive exception depending on the situation.
			// I'll just throw it
			throw e;

		} finally {
			// don't forget to close the connection
			// when you're done with it
			if(conn != null){
				conn.close();
			}
		}
		return queryResult;
	}


	/**
	 * 
	 * Encapsulates the results of my table query.
	 * 
	 * Each Dto should be in it's own class file, I'm just 
	 * doing this for a quick example
	 *
	 */
	public static class TableDto {

		private String tableName;

		private int numRows;

		public TableDto(){}

		public int getNumRows() {
			return numRows;
		}

		public void setNumRows(int numRows) {
			this.numRows = numRows;
		}

		public String getTableName() {
			return tableName;
		}

		public void setTableName(String tableName) {
			this.tableName = tableName;
		}
	}

	public List<BookDto> getByCallNumber(int id) throws SQLException{
		List<BookDto> queryResult = new ArrayList<BookDto>();
		Connection conn = null; 
		try {			
			conn = connService.getConnection();	
			PreparedStatement ps = conn.prepareStatement(
					"SELECT * "+
					"FROM book "  +
					"WHERE callNumber=?");
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				// for each row, put the data in the dto
				// and add it to list of results
				BookDto dto = new BookDto();
				dto.setCallNumber(rs.getInt("callNumber"));
				dto.setIsbn(rs.getInt("isbn"));
				dto.setTitle(rs.getString("title"));
				dto.setMainAuthor(rs.getString("mainAuthor"));
				dto.setPublisher(rs.getString("publisher"));
				dto.setYear(rs.getInt("year"));
				queryResult.add(dto);
			}
		} catch (SQLException e) {
			// two options here. either don't catch this exception and 
			// make the caller handle it, or wrap it in a more 
			// descriptive exception depending on the situation.
			// I'll just throw it
			throw e;

		} finally {
			// don't forget to close the connection
			// when you're done with it
			if(conn != null){
				conn.close();
			}
		}
		return queryResult;
	}

	public List<BookDto> searchAuthorByKeyword(String keyword) throws SQLException{
		List<BookDto> queryResult = new ArrayList<BookDto>();
		Connection conn = null; 
		try {
			conn = connService.getConnection();	
			String modKeyword = "%"+keyword+"%";
			
			// look in main authors
			PreparedStatement ps = conn.prepareStatement(
					"SELECT * "+
					"FROM book "  +
					"WHERE mainAuthor like ?"); // matches any main author that contains <keyword>
			ps.setString(1, modKeyword);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				// for each row, put the data in the dto
				// and add it to list of results
				BookDto dto = new BookDto();
				dto.setCallNumber(rs.getInt("callNumber"));
				dto.setIsbn(rs.getInt("isbn"));
				dto.setTitle(rs.getString("title"));
				dto.setMainAuthor(rs.getString("mainAuthor"));
				dto.setPublisher(rs.getString("publisher"));
				dto.setYear(rs.getInt("year"));
				queryResult.add(dto);
			}
				
			// look in additional authors
			PreparedStatement ps2 = conn.prepareStatement(
					"SELECT B.callNumber, B.isbn, B.title, B.mainAuthor, B.publisher, B.year "+
					"FROM book B, hasauthor H "  +
					"WHERE B.callNumber=H.callNumber AND H.author like ?"); // matches any main author that contains <keyword>
			ps2.setString(1, modKeyword);
			ResultSet rs2 = ps2.executeQuery();
			
			while(rs2.next()){
				// for each row, put the data in the dto
				// and add it to list of results
				BookDto dto2 = new BookDto();
				dto2.setCallNumber(rs2.getInt("B.callNumber"));
				dto2.setIsbn(rs2.getInt("B.isbn"));
				dto2.setTitle(rs2.getString("B.title"));
				dto2.setMainAuthor(rs2.getString("B.mainAuthor"));
				dto2.setPublisher(rs2.getString("B.publisher"));
				dto2.setYear(rs2.getInt("B.year"));
				queryResult.add(dto2);
			}
		} catch (SQLException e) {
			// two options here. either don't catch this exception and 
			// make the caller handle it, or wrap it in a more 
			// descriptive exception depending on the situation.
			// I'll just throw it
			throw e;

		} finally {
			// don't forget to close the connection
			// when you're done with it
			if(conn != null){
				conn.close();
			}
		}
		return queryResult;
	}
	
	public List<BookDto> searchSubjectByKeyword(String keyword) throws SQLException{
		List<BookDto> queryResult = new ArrayList<BookDto>();
		Connection conn = null; 
		try {
			conn = connService.getConnection();	
			String modKeyword = "%"+keyword+"%";
				
			// look for a matching subject
			PreparedStatement ps2 = conn.prepareStatement(
					"SELECT B.callNumber, B.isbn, B.title, B.mainAuthor, B.publisher, B.year "+
					"FROM book B, hassubject S "  +
					"WHERE B.callNumber=S.callNumber AND S.subject like ?"); // matches any main author that contains <keyword>
			ps2.setString(1, modKeyword);
			ResultSet rs2 = ps2.executeQuery();
			
			while(rs2.next()){
				// for each row, put the data in the dto
				// and add it to list of results
				BookDto dto2 = new BookDto();
				dto2.setCallNumber(rs2.getInt("B.callNumber"));
				dto2.setIsbn(rs2.getInt("B.isbn"));
				dto2.setTitle(rs2.getString("B.title"));
				dto2.setMainAuthor(rs2.getString("B.mainAuthor"));
				dto2.setPublisher(rs2.getString("B.publisher"));
				dto2.setYear(rs2.getInt("B.year"));
				queryResult.add(dto2);
			}
		} catch (SQLException e) {
			// two options here. either don't catch this exception and 
			// make the caller handle it, or wrap it in a more 
			// descriptive exception depending on the situation.
			// I'll just throw it
			throw e;

		} finally {
			// don't forget to close the connection
			// when you're done with it
			if(conn != null){
				conn.close();
			}
		}
		return queryResult;
	}

	public List<BookDto> searchTitleByKeyword(String keyword) throws SQLException{
		List<BookDto> queryResult = new ArrayList<BookDto>();
		Connection conn = null; 
		try {
			String modKeyword = "%"+keyword+"%";
			conn = connService.getConnection();	
			PreparedStatement ps = conn.prepareStatement(
					"SELECT * "+
					"FROM book "  +
					"WHERE title like ?"); // matches any title that contains <keyword>
			ps.setString(1, modKeyword);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()){
				// for each row, put the data in the dto
				// and add it to list of results
				BookDto dto = new BookDto();
				dto.setCallNumber(rs.getInt("callNumber"));
				dto.setIsbn(rs.getInt("isbn"));
				dto.setTitle(rs.getString("title"));
				dto.setMainAuthor(rs.getString("mainAuthor"));
				dto.setPublisher(rs.getString("publisher"));
				dto.setYear(rs.getInt("year"));
				queryResult.add(dto);
			}
		} catch (SQLException e) {
			// two options here. either don't catch this exception and 
			// make the caller handle it, or wrap it in a more 
			// descriptive exception depending on the situation.
			// I'll just throw it
			throw e;

		} finally {
			// don't forget to close the connection
			// when you're done with it
			if(conn != null){
				conn.close();
			}
		}
		return queryResult;
	}
	
	

	/**
	 * Adds a new book to the system
	 * 
	 * @param dto the book dto
	 * @throws SQLException when a db error occurs
	 */
	public void addNewBook(BookDto dto) throws SQLException {

		Connection conn = null;
		try {
			conn = this.connService.getConnection();
			PreparedStatement ps = conn.prepareStatement(
					"INSERT INTO book"+
					"(isbn, title, mainAuthor, publisher, year) " +
					"VALUES (?,?,?,?,?)");
			ps.setInt(1, dto.getIsbn());
			ps.setString(2, dto.getTitle());
			ps.setString(3, dto.getMainAuthor());
			ps.setString(4, dto.getPublisher());
			ps.setInt(5, dto.getYear());
			ps.executeUpdate();
		} finally {
			if (conn != null){
				conn.close();
			}
		}

	}

	/**
	 * Removes a book given a 
	 * @param callNumber the book's call number
	 * @return the number of rows deleted
	 * @throws SQLException If there was an error executing the operation
	 */
	public int removeBook(int callNumber) throws SQLException {
		Connection conn = null;
		try {
			conn = this.connService.getConnection();
			PreparedStatement ps = conn.prepareStatement(
					"DELETE FROM book " + 
					"WHERE callNumber=?");
			ps.setInt(1, callNumber);
			return ps.executeUpdate();
		} finally {
			if(conn != null){
				conn.close();
			}
		}
		
	}

	public int addNewBookCopy(int callNumber) throws SQLException {
		Connection conn = null;
		try {
			conn = this.connService.getConnection();
			PreparedStatement ps = conn.prepareStatement(
					"INSERT INTO bookCopy " +
					"(callNumber) " + 
					"VALUES(?)");
			ps.setInt(1, callNumber);
			return ps.executeUpdate();
		} finally {
			if (conn != null){
				conn.close();
			}
		}
		
	}
}
