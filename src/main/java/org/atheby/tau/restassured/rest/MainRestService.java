package org.atheby.tau.restassured.rest;

import org.atheby.tau.restassured.service.*;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/")
public class MainRestService {

    @GET
    @Produces("text/html")
    public String main(){
        new BlogManager();
        new PostManager();
        new CommentManager();
        return "REST Assured - DB Unit";
    }
}
