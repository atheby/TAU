package org.atheby.tau.restassured.rest;

import org.atheby.tau.restassured.domain.Comment;
import org.atheby.tau.restassured.service.CommentManager;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("comments")
public class CommentRestService {

    private CommentManager cm = new CommentManager();

    @GET
    @Produces("application/json")
    public List<Comment> getAllComments(){
        List<Comment> comments = cm.getAllComments();
        return comments;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addComment(Comment comment){
        cm.addComment(comment);
        return Response.status(201).entity(comment).build();
    }

    @GET
    @Path("/{commentId}")
    @Produces("application/json")
    public Comment getComment(@PathParam("commentId") Long id){
        Comment c = cm.getComment(id);
        return c;
    }
}
