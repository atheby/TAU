package org.atheby.tau.restassured.service;

import org.atheby.tau.restassured.domain.Blog;

import java.sql.*;
import java.util.*;

public class BlogManager {

	private Connection connection;

	private static final String URL = "jdbc:hsqldb:hsql://localhost/workdb";
	private static final String CREATE_TABLE_BLOG = "CREATE TABLE blog(id bigint GENERATED BY DEFAULT AS IDENTITY, title varchar(20))";

	private PreparedStatement addBlogStmt;
	private PreparedStatement getBlogStmt;
	private PreparedStatement getAllBlogsStmt;
	private PreparedStatement updateBlogStmt;
	private PreparedStatement deleteBlogStmt;
	private PreparedStatement deleteAllBlogsStmt;

	private Statement statement;
	private PostManager pm;

	public BlogManager() {
		try {
			connection = DriverManager.getConnection(URL);
			statement = connection.createStatement();

			ResultSet rs = connection.getMetaData().getTables(null, null, null, null);
			boolean tableExists = false;
			while (rs.next()) {
				if ("blog".equalsIgnoreCase(rs.getString("TABLE_NAME"))) {
					tableExists = true;
					break;
				}
			}

			if (!tableExists)
				statement.executeUpdate(CREATE_TABLE_BLOG);

			addBlogStmt = connection.prepareStatement("INSERT INTO blog (title) VALUES (?)");
			getBlogStmt = connection.prepareStatement("SELECT * FROM blog WHERE id = ?");
			getAllBlogsStmt = connection.prepareStatement("SELECT * FROM blog");
			updateBlogStmt = connection.prepareStatement("UPDATE blog SET title = ? WHERE id = ?");
			deleteBlogStmt = connection.prepareStatement("DELETE FROM blog WHERE id = ?");
			deleteAllBlogsStmt = connection.prepareStatement("DELETE FROM blog");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int addBlog(Blog blog) {
		int count = 0;
		try {
			addBlogStmt.setString(1, blog.getTitle());

			count = addBlogStmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public List<Blog> getAllBlogs() {
		List<Blog> blogs = new ArrayList<Blog>();
		pm = new PostManager();

		try {
			ResultSet rs = getAllBlogsStmt.executeQuery();

			while (rs.next()) {
				Blog b = new Blog();
				b.setId(rs.getInt("id"));
				b.setTitle(rs.getString("title"));
				b.setPosts(pm.getAllPostsByBlogId(b.getId()));
				blogs.add(b);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return blogs;
	}

	public Blog getBlog(Long id) {
		Blog b = new Blog();
		try {
			getBlogStmt.setLong(1, id);
			ResultSet rs = getBlogStmt.executeQuery();

			while (rs.next()) {
				b.setId(rs.getInt("id"));
				b.setTitle(rs.getString("title"));
				break;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return b;
	}

	public int updateBlog(Blog blog) {
		int count = 0;
		try {
			updateBlogStmt.setString(1, blog.getTitle());
			updateBlogStmt.setLong(2, blog.getId());

			count = updateBlogStmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public void deleteBlog(Long id) {
		pm = new PostManager();

		try {
			pm.deletePostsByBlog(id);
			deleteBlogStmt.setLong(1, id);
			deleteBlogStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteAllBlogs() {
		pm = new PostManager();
		try {
			pm.deleteAllPosts();
			deleteAllBlogsStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
