/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package security;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import entities.InvalidJWT;
import entities.User;
import errorhandling.GenericExceptionMapper;
import facades.UserFacade;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import security.errorhandling.AuthenticationException;
import utils.EMF_Creator;

/**
 *
 * @author marcg
 */
@Path("logout")
public class LogoutEndpoint {

    public static final int TOKEN_EXPIRE_TIME = 1000 * 60 * 30; //30 min
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    public static final UserFacade USER_FACADE = UserFacade.getUserFacade(EMF);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String logout(@HeaderParam("x-access-token") String token) throws AuthenticationException, ParseException, KeyLengthException, JOSEException {
        
        EntityManager em = EMF.createEntityManager();
        
        SignedJWT signedJWT = SignedJWT.parse(token);
        JWTClaimsSet c = signedJWT.getJWTClaimsSet();
        String username = c.getClaims().get("username").toString();
        String exp = c.getClaims().get("exp").toString();

        DateFormat dateFormat = new SimpleDateFormat(
                "EEE MMM dd HH:mm:ss zzzz yyyy", Locale.ENGLISH);

        InvalidJWT jwt = new InvalidJWT(token, username ,dateFormat.parse(exp));
        em.getTransaction().begin();
        em.persist(jwt);
        em.getTransaction().commit();
        
        return signedJWT.serialize(false);
    }

}
