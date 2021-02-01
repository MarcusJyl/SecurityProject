/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import utils.HttpUtils;
import DTOs.QuoteDTO;

/**
 *
 * @author Marcus
 */
@Path("quote")
public class QuoteResource {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Context
    private UriInfo context;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getQuote() throws IOException {
        String quote = HttpUtils.fetchData("https://api.kanye.rest\n");
        QuoteDTO quoteDTO = GSON.fromJson(quote, QuoteDTO.class);

        return GSON.toJson(quoteDTO);
    }
}
