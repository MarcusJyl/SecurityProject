package rest;

import DTOs.UserDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entities.User;
import errorhandling.InvalidInputException;
import facades.UserFacade;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import security.UserPrincipal;
import utils.EMF_Creator;
import utils.InputValidator;

/**
 * @author lam@cphbusiness.dk
 */
@Path("signup")
public class SignupEndpoint {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final UserFacade FACADE = UserFacade.getUserFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

//    @Context
//    private UriInfo context;
//
//    @Context
//    SecurityContext securityContext;
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getInfoForAll() {
        return "{\"msg\":\"Hello anonymous\"}";
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String make(String user) throws InvalidInputException {
        JsonObject json = JsonParser.parseString(user).getAsJsonObject();
        String username = InputValidator.validateInput(json.get("username").getAsString(), 1, 25);
        String password = InputValidator.validateInput(json.get("password").getAsString(), 8, 64);

        List<String> roles = new ArrayList();
        roles.add("user");
        UserDTO userDTO = new UserDTO(username, password, roles);
        userDTO = new UserDTO(userDTO.getName(), userDTO.getPassword(), roles);
        userDTO = FACADE.addUser(userDTO);

        return GSON.toJson(userDTO);
    }
}
