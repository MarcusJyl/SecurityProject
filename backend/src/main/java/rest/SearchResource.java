/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import errorhandling.InvalidInputException;
import facades.SearchFacade;
import facades.UserFacade;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import utils.EMF_Creator;

/**
 *
 * @author Marcus
 */
@Path("search")
public class SearchResource {
     private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    public static final SearchFacade facade = SearchFacade.getSearchFacade(EMF);
    private static Gson GSON = new Gson();

    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{input}")
    public String Search(@PathParam("input")String input) throws InvalidInputException {
        //Skal ikke validers da længden er lige meget og der ikke skere noget ved at få html <> ind
        return GSON.toJson(facade.Search(input));
    }
}
