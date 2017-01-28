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

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPost(Post post){
        pm.addPost(post);
        return Response.status(201).entity(post).build();
    }

    @GET
    @Path("/{postId}")
    @Produces("application/json")
    public Post getPost(@PathParam("postId") Long id){
        Post p = pm.getPost(id);
        return p;
    }
}
