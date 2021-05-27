/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import errorhandling.DatabaseException;
import errorhandling.InvalidInputException;
import facades.CommentFacade;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import utils.EMF_Creator;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import utils.InputValidator;

@Path("comment")
public class CommentResource {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final CommentFacade facade = CommentFacade.getCommentFacade(EMF);

    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"user", "admin"})
    public String addComment(String body) throws InvalidInputException, DatabaseException {

        String username = securityContext.getUserPrincipal().getName();

        JsonObject bodyObj = GSON.fromJson(body, JsonObject.class);
        int postID = bodyObj.get("post").getAsInt();
        String text = InputValidator.validateInput(bodyObj.get("text").getAsString(), 1, 255);

        return GSON.toJson(facade.addComment(postID, text, username));
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{postID}")
    public String getCommetForPost(@PathParam("postID") String postID) throws InvalidInputException {
        return GSON.toJson(facade.getComments(Integer.parseInt(postID)));
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{commentID}")
    public String deleteComment(@PathParam("commentID") String commentID) throws InvalidInputException, DatabaseException {
        int id = Integer.parseInt(InputValidator.validateInput(commentID, 1, 10000));
        return GSON.toJson(facade.deleteComment(id));
    }
}
