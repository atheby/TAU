package org.atheby.tau.restassured.rest;

import org.atheby.tau.restassured.domain.Post;
import org.atheby.tau.restassured.service.PostManager;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("posts")
public class PostRestService {

    private PostManager pm = new PostManager();

    @GET
    @Produces("application/json")
    public List<Post> getAllPosts(){
        List<Post> posts = pm.getAllPosts();
        return posts;
    }

    @GET
    @Path("/{postId}")
    @Produces("application/json")
    public Post getPost(@PathParam("postId") Long id){
        Post p = pm.getPost(id);
        return p;
    }

    @GET
    @Path("/blog/{blogId}")
    @Produces("application/json")
    public List<Post> getPostsByBlog(@PathParam("blogId") Long id){
        List<Post> posts = pm.getAllPostsByBlogId(id);
        return posts;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPost(Post post){
        pm.addPost(post);
        return Response.status(201).entity(post).build();
    }

    @PUT
    @Path("/{postId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePost(Post post){
        pm.updatePost(post);
        return Response.status(201).entity(post).build();
    }

    @DELETE
    public Response deleteAllPosts(){
        pm.deleteAllPosts();
        return Response.status(204).build();
    }

    @DELETE
    @Path("/{postId}")
    public Response deletePost(@PathParam("postId") Long id){
        pm.deletePost(id);
        return Response.status(204).build();
    }

    @DELETE
    @Path("/blog/{blogId}")
    public Response deletePostsByBlog(@PathParam("blogId") Long id){
        pm.deletePostsByBlog(id);
        return Response.status(204).build();
    }
}
