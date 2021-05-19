/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import DTOs.PostDTO;
import DTOs.PostsDTO;
import DTOs.UserDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import entities.Comment;
import entities.Post;
import entities.User;
import errorhandling.DatabaseException;
import errorhandling.InvalidInputException;
import facades.PostFacade;
import facades.TagFacade;
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
import java.util.Arrays;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import utils.InputValidator;
import utils.LoggingTest;

/**
 *
 * @author marcg
 */
@Path("post")
public class PostResource {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final PostFacade facade = PostFacade.getUserFacade(EMF);

    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"admin", "user"})
    public String addPost(String postString) throws ParseException, AuthenticationException, InvalidInputException {

        JsonObject json = JsonParser.parseString(postString).getAsJsonObject();
        String content = InputValidator.validateInput(json.get("content").getAsString(), 1, 255);
        String title = InputValidator.validateInput(json.get("title").getAsString(), 1, 40);
        List<String> tags = Arrays.asList(json.get("tag").toString().replaceAll("[\\[\\]\"]", "").split(","));
        for (String tag : tags) {
            InputValidator.validateInput(tag, 1, 25);
        }

        PostDTO dto = new PostDTO(title, content, tags);
        String username = securityContext.getUserPrincipal().getName();
        EntityManager em = EMF.createEntityManager();

        try {
            User user = em.find(User.class, username);
            PostDTO post = new PostDTO(facade.addPost(dto, user, tags));
            return GSON.toJson(post);
        } catch (Exception ex) {
            throw new InvalidInputException("Invalid Input");
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("all")
    public String getAllPosts() throws IOException {
        LoggingTest.log();
        return GSON.toJson(facade.getAllPosts());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{userName}")
    public String getUserPost(@PathParam("userName") String username) throws InvalidInputException {
        username = InputValidator.validateInput(username, 1, 25);
        try {
            PostsDTO posts = facade.getAllPostsFromUser(username);
            return GSON.toJson(posts);
        } catch (JsonSyntaxException e) {
            return e.getMessage();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("by/tags/{tags}")
    public String getPostByTag(@PathParam("tags") String tagString) throws InvalidInputException {
        String[] tags = tagString.split(",");
        for (String tag : tags) {
            InputValidator.validateInput(tag, 1, 25);
        }
        return GSON.toJson(facade.getAllPostsByTags(tags));
    }
    
    
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"admin"})
    @Path("/delete/{postID}")
    public String deletePost(@PathParam("postID") String postID) throws InvalidInputException, DatabaseException {
        int id = Integer.parseInt(InputValidator.validateInput(postID, 1, 10000));
        return GSON.toJson(facade.deletePost(id));
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public String editPost(String body) throws InvalidInputException, DatabaseException {
        JsonObject json = JsonParser.parseString(body).getAsJsonObject();

        String title = InputValidator.validateInput(json.get("title").getAsString(), 1, 40);
        String id = InputValidator.validateInput(json.get("id").getAsString(), 1, 40);
        String content = InputValidator.validateInput(json.get("content").getAsString(), 1, 255);
        List<String> tags = Arrays.asList(json.get("tags").toString().replaceAll("[\\[\\]\"]", "").split(","));
        for (String string : tags) {
            InputValidator.validateInput(string, 1, 25);
        }
        PostDTO postDTO = new PostDTO(Integer.parseInt(id), title, content, tags);
        
        return GSON.toJson(facade.editPost(postDTO));
    }

//    private UserPrincipal getUserPrincipalFromTokenIfValid(String token)
//            throws ParseException, JOSEException, AuthenticationException {
//        SignedJWT signedJWT = SignedJWT.parse(token);
//        //Is it a valid token (generated with our shared key)
//        JWSVerifier verifier = new MACVerifier(SharedSecret.getSharedKey());
//
//        if (signedJWT.verify(verifier)) {
//            String roles = signedJWT.getJWTClaimsSet().getClaim("roles").toString();
//            String username = signedJWT.getJWTClaimsSet().getClaim("username").toString();
//
//            String[] rolesArray = roles.split(",");
//
//            return new UserPrincipal(username, rolesArray);
////     return new UserPrincipal(username, roles);
//        } else {
//            throw new JOSEException("User could not be extracted from token");
//        }
//    }
}
