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

    @GET
    @Path("/{commentId}")
    @Produces("application/json")
    public Comment getComment(@PathParam("commentId") Long id){
        Comment c = cm.getComment(id);
        return c;
    }

    @GET
    @Path("/post/{postId}")
    @Produces("application/json")
    public List<Comment> getAllCommentsByPost(@PathParam("postId") Long id){
        List<Comment> comments = cm.getAllCommentsByPost(id);
        return comments;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addComment(Comment comment){
        cm.addComment(comment);
        return Response.status(201).entity(comment).build();
    }

    @PUT
    @Path("/{commentId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateComment(Comment comment){
        cm.updateComment(comment);
        return Response.status(201).entity(comment).build();
    }

    @DELETE
    public Response deleteAllComments(){
        cm.deleteAllComments();
        return Response.status(204).build();
    }

    @DELETE
    @Path("/{commentId}")
    public Response deleteComment(@PathParam("commentId") Long id){
        cm.deleteComment(id);
        return Response.status(204).build();
    }

    @DELETE
    @Path("/post/{postId}")
    public Response deleteCommentsByPost(@PathParam("postId") Long id){
        cm.deleteCommentsByPost(id);
        return Response.status(204).build();
    }
}
