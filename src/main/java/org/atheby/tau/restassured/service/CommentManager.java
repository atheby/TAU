package org.atheby.tau.restassured.service;

import org.atheby.tau.restassured.domain.Comment;

import java.sql.*;
import java.util.*;

public class CommentManager {

	private Connection connection;

	private static final String URL = "jdbc:hsqldb:hsql://localhost/workdb";
	private static final String CREATE_TABLE_COMMENT = "CREATE TABLE comment(id bigint GENERATED BY DEFAULT AS IDENTITY, postId bigint, author varchar(20), text varchar(250))";

	private PreparedStatement addCommentStmt;
	private PreparedStatement getCommentStmt;
	private PreparedStatement getAllCommentsStmt;
	private PreparedStatement getAllCommentsByPostIdStmt;
	private PreparedStatement updateCommentStmt;
	private PreparedStatement deleteCommentStmt;
	private PreparedStatement deleteAllCommentsStmt;
	private PreparedStatement deleteAllCommentsByPostIdStmt;

	private Statement statement;

	public CommentManager() {
		try {
			connection = DriverManager.getConnection(URL);
			statement = connection.createStatement();

			ResultSet rs = connection.getMetaData().getTables(null, null, null, null);
			boolean tableExists = false;
			while (rs.next()) {
				if ("comment".equalsIgnoreCase(rs.getString("TABLE_NAME"))) {
					tableExists = true;
					break;
				}
			}

			if (!tableExists)
				statement.executeUpdate(CREATE_TABLE_COMMENT);

			addCommentStmt = connection.prepareStatement("INSERT INTO comment (postId, author, text) VALUES (?, ?, ?)");
			getCommentStmt = connection.prepareStatement("SELECT * FROM comment WHERE id = ?");
			getAllCommentsStmt = connection.prepareStatement("SELECT * FROM comment");
			getAllCommentsByPostIdStmt = connection.prepareStatement("SELECT * FROM comment WHERE postId = ?");
			updateCommentStmt = connection.prepareStatement("UPDATE comment SET author = ?, text = ? WHERE id = ?");
			deleteCommentStmt = connection.prepareStatement("DELETE FROM comment WHERE id = ?");
			deleteAllCommentsStmt = connection.prepareStatement("DELETE from comment");
			deleteAllCommentsByPostIdStmt = connection.prepareStatement("DELETE FROM comment WHERE postId = ?");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int addComment(Comment comment) {
		int count = 0;
		try {
			addCommentStmt.setLong(1, comment.getPostId());
			addCommentStmt.setString(2, comment.getAuthor());
			addCommentStmt.setString(3, comment.getText());

			count = addCommentStmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public List<Comment> getAllComments() {
		List<Comment> comments = new ArrayList<Comment>();

		try {
			ResultSet rs = getAllCommentsStmt.executeQuery();

			while (rs.next()) {
				Comment c = new Comment();
				c.setId(rs.getInt("id"));
				c.setPostId(rs.getInt("postId"));
				c.setAuthor(rs.getString("author"));
				c.setText(rs.getString("text"));
				comments.add(c);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return comments;
	}

	public List<Comment> getAllCommentsByPost(Long id) {
		List<Comment> comments = new ArrayList<Comment>();

		try {
			getAllCommentsByPostIdStmt.setLong(1, id);
			ResultSet rs = getAllCommentsByPostIdStmt.executeQuery();

			while (rs.next()) {
				Comment c = new Comment();
				c.setId(rs.getInt("id"));
				c.setPostId(rs.getInt("postId"));
				c.setAuthor(rs.getString("author"));
				c.setText(rs.getString("text"));
				comments.add(c);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return comments;
	}

	public Comment getComment(Long id) {
		Comment c = new Comment();
		try {
			getCommentStmt.setLong(1, id);
			ResultSet rs = getCommentStmt.executeQuery();

			while (rs.next()) {
				c.setId(rs.getInt("id"));
				c.setPostId(rs.getInt("postId"));
				c.setAuthor(rs.getString("author"));
				c.setText(rs.getString("text"));
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return c;
	}

	public int updateComment(Comment comment) {
		int count = 0;
		try {
			updateCommentStmt.setString(1, comment.getAuthor());
			updateCommentStmt.setString(2, comment.getText());
			updateCommentStmt.setLong(3, comment.getId());

			count = updateCommentStmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public void deleteComment(Long id) {
		try {
			deleteCommentStmt.setLong(1, id);
			deleteCommentStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteAllComments() {
		try {
			deleteAllCommentsStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteCommentsByPost(Long id) {
		try {
			deleteAllCommentsByPostIdStmt.setLong(1, id);
			deleteAllCommentsByPostIdStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
