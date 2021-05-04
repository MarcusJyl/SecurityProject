/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package security;

import entities.InvalidJWT;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import utils.EMF_Creator;

/**
 *
 * @author marcg
 */
public class TokenFacade {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    public static boolean isJWTBlackListed(String token, String username) {
        EntityManager em = EMF.createEntityManager();

        Query query = em.createQuery("SELECT j FROM InvalidJWT j WHERE j.username = :username", InvalidJWT.class);
        List<InvalidJWT> JWTS = query.setParameter("username", username).getResultList();
        return containsToken(token, JWTS);
    }

    private static boolean containsToken(String target, List<InvalidJWT> tokens) {
        for (InvalidJWT token : tokens) {
            if (token.getToken().equals(target)) {
                return true;
            }
        }
        return false;
    }

}
