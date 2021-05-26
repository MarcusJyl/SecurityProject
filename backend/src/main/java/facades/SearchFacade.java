/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import DTOs.PostsDTO;
import DTOs.SearchDTO;
import DTOs.UserDTO;
import DTOs.UsersDTO;
import entities.User;
import errorhandling.InvalidInputException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Marcus
 */
public class SearchFacade {

    private static EntityManagerFactory emf;
    private static SearchFacade instance;

    public static SearchFacade getSearchFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new SearchFacade();
        }
        return instance;
    }

    public SearchDTO Search(String input) throws InvalidInputException {
        EntityManager em = emf.createEntityManager();
        input = "%" + input + "%";
        try {
            Query userQuery = em.createQuery("SELECT u FROM User u WHERE u.userName LIKE :input", User.class);
            userQuery.setParameter("input", input);
            UsersDTO usersDTO = new UsersDTO(userQuery.getResultList());
            Query postQuery = em.createQuery("SELECT p FROM Post p WHERE p.Content LIKE :input");
            postQuery.setParameter("input", input);
            PostsDTO postsDTO = new PostsDTO(postQuery.getResultList());
            SearchDTO searchDTO = new SearchDTO(usersDTO, postsDTO);
            return searchDTO;
        } catch (Exception e) {
            throw new InvalidInputException("No post or user for input " + input);
        } finally {
            em.close();
        }

    }

}
