package rest;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import entities.User;
import errorhandling.InvalidInputException;
import facades.UserFacade;
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

/**
 * @author lam@cphbusiness.dk
 */
@Path("user")
public class DemoResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    public static final UserFacade facade = UserFacade.getUserFacade(EMF);

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

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String make() {
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

    @POST
    @Path("picture")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    public String profilPic(String link) throws InvalidInputException {
        String thisuser = securityContext.getUserPrincipal().getName();

        return facade.setProfileImageLink(thisuser, "https://firebasestorage.googleapis.com/v0/b/tallans-imageupload-tutorial.appspot.com/o/images%2FUdklip.png?alt=media&token=eabf4882-ee23-47ce-b9a8-54aad3cef315");

//        return obj.toString();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("user")
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

}
