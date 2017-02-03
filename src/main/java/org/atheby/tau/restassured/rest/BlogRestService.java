package org.atheby.tau.restassured.rest;

import org.atheby.tau.restassured.service.BlogManager;
import org.atheby.tau.restassured.domain.Blog;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("blogs")
public class BlogRestService {

    private BlogManager bm = new BlogManager();

    @GET
    @Produces("application/json")
    public List<Blog> getAllBlogs(){
        List<Blog> blogs = bm.getAllBlogs();
        return blogs;
    }

    @GET
    @Path("/{blogId}")
    @Produces("application/json")
    public Blog getBlog(@PathParam("blogId") Long id){
        Blog b = bm.getBlog(id);
        return b;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addBlog(Blog blog){
        bm.addBlog(blog);
        return Response.status(201).entity(blog).build();
    }

    @PUT
    @Path("/{blogId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateBlog(Blog blog){
        bm.updateBlog(blog);
        return Response.status(201).entity(blog).build();
    }

    @DELETE
    @Path("/{blogId}")
    public Response deleteBlog(@PathParam("blogId") Long id){
        bm.deleteBlog(id);
        return Response.status(204).build();
    }

    @DELETE
    public Response deleteAllBlogs(){
        bm.deleteAllBlogs();
        return Response.status(204).build();
    }
}
