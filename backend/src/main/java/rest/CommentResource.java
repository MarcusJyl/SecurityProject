/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import DTOs.CommentsDTO;
import DTOs.PostDTO;
import DTOs.QuoteDTO;
import DTOs.UserDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import entities.Comment;
import entities.Post;
import entities.User;
import errorhandling.InvalidInputException;
import facades.PostFacade;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import security.SharedSecret;
import security.UserPrincipal;
import security.errorhandling.AuthenticationException;
import utils.EMF_Creator;
import utils.HttpUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 *
 * @author marcg
 */
@Path("comment")
public class CommentResource {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final PostFacade facade = PostFacade.getUserFacade(EMF);

    @Context
    private UriInfo context;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String addComment(String body) throws InvalidInputException {
        EntityManager em = EMF.createEntityManager();

        JsonObject bodyObj = GSON.fromJson(body, JsonObject.class);
        int postID = bodyObj.get("post").getAsInt();
        String text = bodyObj.get("text").getAsString();

        try {
            Post post = em.find(Post.class, postID);
            System.out.println(post.toString());
            Comment comment = new Comment(post, text);
            em.getTransaction().begin();
            em.persist(comment);
            em.persist(post);
            em.getTransaction().commit();

            return GSON.toJson(postID);
        } catch (Exception e) {
            throw new InvalidInputException("Cloud not find post with id: " + postID);
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
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
