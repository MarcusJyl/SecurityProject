/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import DTOs.PostDTO;
import DTOs.PostsDTO;
import DTOs.UserDTO;
import com.google.gson.Gson;
import entities.Post;
import entities.Tag;
import entities.User;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author marcg
 */
public class PostFacade {

    private static EntityManagerFactory emf;
    private static PostFacade instance;
    private static TagFacade tagFacade;

    public static PostFacade getUserFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new PostFacade();
            tagFacade = TagFacade.getTagFacade(_emf);
        }
        return instance;
    }

    public Post addPost(PostDTO dto, User user, List<String> tagStrings) {
        EntityManager em = emf.createEntityManager();

        List<Tag> tags = tagFacade.getTags(tagStrings);
        em.getTransaction().begin();
        Post post = new Post(user, dto.getTitle(), dto.getContent());
        
        em.persist(post);
        for (Tag tag : tags) {
            post.addTag(tag);
            em.merge(tag);
        }
        em.getTransaction().commit();
        return post;
    }

    public PostsDTO getAllPosts() {
        EntityManager em = emf.createEntityManager();

        PostsDTO dtos = new PostsDTO(em.createQuery("SELECT p FROM Post p").getResultList());

        return dtos;
    }

    public PostsDTO getAllPostsFromUser(String userDTO) {
        EntityManager em = emf.createEntityManager();
        ArrayList<PostsDTO> results = new ArrayList();
        try {
            TypedQuery<Post> query = em.createQuery("SELECT p FROM Post p WHERE p.user.userName = :name", entities.Post.class);
            query.setParameter("name", userDTO);
            List<Post> posts = query.getResultList();
            PostsDTO postsDTO = new PostsDTO(posts);

            return postsDTO;
        } finally {
            em.close();
        }
    }

}
