/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import DTOs.PostDTO;
import DTOs.QuoteDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import facades.PostFacade;
import java.io.IOException;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import utils.EMF_Creator;
import utils.HttpUtils;

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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getQuote() throws IOException {
        String quote = HttpUtils.fetchData("https://api.kanye.rest\n");
        QuoteDTO quoteDTO = GSON.fromJson(quote, QuoteDTO.class);

        return GSON.toJson(quoteDTO);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String addPost(String postString) {
        PostDTO dto = GSON.fromJson(postString, PostDTO.class);
        /*
        
        CHANGE ADMIN TO THE LOGEDIN USER
        
        */
        dto.setUsername("user");
        System.out.println(dto);
        PostDTO post = new PostDTO(facade.addPost(dto));
        return GSON.toJson(post);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("all")
    public String getAllPosts() {
        return GSON.toJson(facade.getAllPosts());
    }
}
