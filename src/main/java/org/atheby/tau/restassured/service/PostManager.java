package org.atheby.tau.restassured.service;

import org.atheby.tau.restassured.domain.Post;

import java.sql.*;
import java.util.*;

public class PostManager {

	private Connection connection;

	private static final String URL = "jdbc:hsqldb:hsql://localhost/workdb";
	private static final String CREATE_TABLE_POST = "CREATE TABLE post(id bigint GENERATED BY DEFAULT AS IDENTITY, blogId bigint, title varchar(20), text varchar(250))";

	private PreparedStatement addPostStmt;
	private PreparedStatement getPostStmt;
	private PreparedStatement getAllPostsStmt;
	private PreparedStatement getAllPostsByBlogIdStmt;
	private PreparedStatement updatePostStmt;
	private PreparedStatement deletePostStmt;
	private PreparedStatement deleteAllPostsStmt;
	private PreparedStatement deleteAllPostsByBlogIdStmt;

	private Statement statement;

	private CommentManager cm;

	public PostManager() {
		try {
			connection = DriverManager.getConnection(URL);
			statement = connection.createStatement();

			ResultSet rs = connection.getMetaData().getTables(null, null, null, null);
			boolean tableExists = false;
			while (rs.next()) {
				if ("post".equalsIgnoreCase(rs.getString("TABLE_NAME"))) {
					tableExists = true;
					break;
				}
			}

			if (!tableExists)
				statement.executeUpdate(CREATE_TABLE_POST);

			addPostStmt = connection.prepareStatement("INSERT INTO post (blogId, title, text) VALUES (?, ?, ?)");
			getPostStmt = connection.prepareStatement("SELECT * FROM post WHERE id = ?");
			getAllPostsStmt = connection.prepareStatement("SELECT * FROM post");
			getAllPostsByBlogIdStmt = connection.prepareStatement("SELECT * FROM post WHERE blogId = ?");
			updatePostStmt = connection.prepareStatement("UPDATE post SET title = ?, text = ? WHERE id = ?");
			deletePostStmt = connection.prepareStatement("DELETE FROM post WHERE id = ?");
			deleteAllPostsStmt = connection.prepareStatement("DELETE FROM post");
			deleteAllPostsByBlogIdStmt = connection.prepareStatement("DELETE FROM post WHERE blogId = ?");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int addPost(Post post) {
		int count = 0;
		try {
			addPostStmt.setLong(1, post.getBlogId());
			addPostStmt.setString(2, post.getTitle());
			addPostStmt.setString(3, post.getText());

			count = addPostStmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public List<Post> getAllPosts() {
		List<Post> posts = new ArrayList<Post>();
		cm = new CommentManager();

		try {
			ResultSet rs = getAllPostsStmt.executeQuery();

			while (rs.next()) {
				Post p = new Post();
				p.setId(rs.getInt("id"));
				p.setBlogId(rs.getInt("blogId"));
				p.setTitle(rs.getString("title"));
				p.setText(rs.getString("text"));
				p.setComments(cm.getAllCommentsByPost(p.getId()));
				posts.add(p);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return posts;
	}

	public List<Post> getAllPostsByBlogId(Long id) {
		List<Post> posts = new ArrayList<Post>();
		cm = new CommentManager();

		try {
			getAllPostsByBlogIdStmt.setLong(1, id);
			ResultSet rs = getAllPostsByBlogIdStmt.executeQuery();

			while (rs.next()) {
				Post p = new Post();
				p.setId(rs.getInt("id"));
				p.setBlogId(rs.getInt("blogId"));
				p.setTitle(rs.getString("title"));
				p.setText(rs.getString("text"));
				p.setComments(cm.getAllCommentsByPost(p.getId()));
				posts.add(p);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return posts;
	}

	public Post getPost(Long id) {
		Post p = new Post();
		cm = new CommentManager();
		try {
			getPostStmt.setLong(1, id);
			ResultSet rs = getPostStmt.executeQuery();

			while (rs.next()) {
				p.setId(rs.getInt("id"));
				p.setBlogId(rs.getInt("blogId"));
				p.setTitle(rs.getString("title"));
				p.setText(rs.getString("text"));
				p.setComments(cm.getAllCommentsByPost(p.getId()));
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return p;
	}

	public int updatePost(Post post) {
		int count = 0;
		try {
			updatePostStmt.setString(1, post.getTitle());
			updatePostStmt.setString(2, post.getText());
			updatePostStmt.setLong(3, post.getId());

			count = updatePostStmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public void deletePost(Long id) {
		cm = new CommentManager();

		try {
			cm.deleteCommentsByPost(id);
			deletePostStmt.setLong(1, id);
			deletePostStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteAllPosts() {
		cm = new CommentManager();
		try {
			cm.deleteAllComments();
			deleteAllPostsStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deletePostsByBlog(Long id) {
		cm = new CommentManager();
		try {
			getAllPostsByBlogIdStmt.setLong(1, id);
			ResultSet rs = getAllPostsByBlogIdStmt.executeQuery();

			while (rs.next()) {
				cm.deleteCommentsByPost(rs.getLong("id"));
				break;
			}
			deleteAllPostsByBlogIdStmt.setLong(1, id);
			deleteAllPostsByBlogIdStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
