package rest;

import DTOs.UserDTO;
import DTOs.UsersDTO;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entities.User;
import errorhandling.InvalidInputException;
import errorhandling.NotFoundException;
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
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import security.UserPrincipal;
import security.errorhandling.AuthenticationException;
import utils.EMF_Creator;
import utils.InputValidator;

/**
 * @author lam@cphbusiness.dk
 */
@Path("user")
public class UserResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    public static final UserFacade facade = UserFacade.getUserFacade(EMF);
    private static Gson GSON = new Gson();

    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    //Just to verify if the database is setup
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("all")
    public String allUsers() {

        EntityManager em = EMF.createEntityManager();
        try {
            TypedQuery<User> query = em.createQuery("select u from User u", entities.User.class);
            List<User> users = query.getResultList();
            return "[" + users.size() + "]";
        } finally {
            em.close();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"user","admin"})
    public String getSelf() {
        String username = securityContext.getUserPrincipal().getName();
        EntityManager em = EMF.createEntityManager();
        try {
            return GSON.toJson(new UserDTO(em.find(User.class, username)));
        } finally {
            em.close();
        }
    }

//    @POST
//    @Produces(MediaType.APPLICATION_JSON)
//    @Consumes(MediaType.APPLICATION_JSON)
//    public String make() {
//        String thisuser = securityContext.getUserPrincipal().getName();
//        JsonObject obj = new JsonObject();
//        obj.addProperty("name", thisuser);
//        JsonArray array = new JsonArray();
//        array.add("user");
//        boolean s = securityContext.isUserInRole("admin");
//        if (s) {
//            array.add("admin");
//        }
//        obj.add("roles", array);
//
//        return obj.toString();
//    }
    @POST
    @Path("picture")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"user", "admin"})
    public String profilPic(String body) throws InvalidInputException {
        String thisuser = securityContext.getUserPrincipal().getName();
        String link = InputValidator.validateInput(GSON.fromJson(body, JsonObject.class).get("url").toString(), 30, 1500);
        return facade.setProfileImageLink(thisuser, link.substring(1, link.length() - 1));

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("user")
    @Path("user")
    public String getFromUser() {
        String thisuser = securityContext.getUserPrincipal().getName();
        JsonObject obj = new JsonObject();
        obj.addProperty("name", thisuser);
        JsonArray array = new JsonArray();
        array.add("user");
        boolean s = securityContext.isUserInRole("admin");
        if (s) {
            array.add("admin");
        }
        obj.add("roles", array);

        return obj.toString();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("admin")
    @RolesAllowed("admin")
    public String getFromAdmin() {
        String thisuser = securityContext.getUserPrincipal().getName();
        JsonObject obj = new JsonObject();
        obj.addProperty("name", thisuser);
        JsonArray array = new JsonArray();
        array.add("admin");
        boolean s = securityContext.isUserInRole("user");
        if (s) {
            array.add("user");
        }
        obj.add("roles", array);

        return obj.toString();
    }

    @PUT
    @Path("password")
    @RolesAllowed({"user", "admin"})
    public String changePassword(String body) throws AuthenticationException, InvalidInputException {
        String username = securityContext.getUserPrincipal().getName();
        JsonObject json = JsonParser.parseString(body).getAsJsonObject();

        String oldPassword = InputValidator.validateInput(json.get("oldPassword").getAsString(), 8, 64);
        String newPassword = InputValidator.validateInput(json.get("newPassword").getAsString(), 8, 64);

        facade.changePassword(username, newPassword, oldPassword);

        return "Password changed";
    }

    @GET
    @Path("by/usernames/{usernames}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getUSersByIDs(@PathParam("usernames") String usernamesInput) throws AuthenticationException, InvalidInputException, NotFoundException {
        EntityManager em = EMF.createEntityManager();

        String[] usernames = usernamesInput.split(",");
        for (String username : usernames) {
            InputValidator.validateInput(username, 1, 25);
        }
        List<User> users = new ArrayList();

        try {
            for (String username : usernames) {
                User user = em.find(User.class, username);
                users.add(user);
            }
        } catch (Exception e) {
            throw new NotFoundException("got an invalid user id");
        }

        UsersDTO usersDTO = new UsersDTO(users);

        return GSON.toJson(usersDTO);
    }

}
