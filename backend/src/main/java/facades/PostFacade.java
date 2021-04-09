/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import DTOs.PostDTO;
import DTOs.PostsDTO;
import com.google.gson.Gson;
import entities.Post;
import entities.User;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author marcg
 */
public class PostFacade {
    
    private static EntityManagerFactory emf;
    private static PostFacade instance;

    public static PostFacade getUserFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PostFacade();
        }
        return instance;
    }
    
    
    public Post addPost(PostDTO dto, User user){
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        Post post = new Post(user, dto.getTitle(), dto.getContent());
        em.persist(post);
        em.getTransaction().commit();
        
        return post;
    }
    
    public PostsDTO getAllPosts(){
        EntityManager em = emf.createEntityManager();
        
        PostsDTO dtos = new PostsDTO(em.createQuery("SELECT p FROM Post p").getResultList());
        
        return dtos;
    }
    
}
