/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import DTOs.CommentsDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import entities.Comment;
import entities.Post;
import entities.User;
import errorhandling.DatabaseException;
import errorhandling.InvalidInputException;
import facades.PostFacade;
import facades.UserFacade;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import utils.EMF_Creator;
import java.util.List;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
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
    private static final PostFacade facade = PostFacade.getUserFacade(EMF);
    private static final UserFacade userFacade = UserFacade.getUserFacade(EMF);

    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"user", "admin"})
    public String addComment(String body) throws InvalidInputException, DatabaseException {
        EntityManager em = EMF.createEntityManager();

        String username = securityContext.getUserPrincipal().getName();
        User user = userFacade.findUser(username);

        JsonObject bodyObj = GSON.fromJson(body, JsonObject.class);
        int postID = bodyObj.get("post").getAsInt();
        String text = InputValidator.validateInput(bodyObj.get("text").getAsString(),1,255);

        try {
            Post post = em.find(Post.class, postID);
            Comment comment = new Comment(post, text, user);
            em.getTransaction().begin();
            em.persist(comment);
            em.persist(post);
            em.merge(user);
            em.getTransaction().commit();

            return GSON.toJson(postID);
        } catch (Exception e) {
            throw new InvalidInputException("Cloud not find post with id: " + postID);
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)

    @Path("{postID}")
    public String getCommetForPost(@PathParam("postID") String postID) throws InvalidInputException {
        EntityManager em = EMF.createEntityManager();

        try {
            Query q = em.createQuery("SELECT c FROM Comment c WHERE c.post.id = :postID", Comment.class);

            q.setParameter("postID", Integer.parseInt(postID));
            List<Comment> comments = q.getResultList();
            CommentsDTO res = new CommentsDTO(comments);
            return GSON.toJson(res);
        } catch (Exception e) {
            throw new InvalidInputException("fuck af so");
        }
    }
}
