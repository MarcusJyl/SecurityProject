/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import DTOs.PostDTO;
import DTOs.PostsDTO;
import entities.Post;
import entities.Tag;
import entities.User;


import errorhandling.DatabaseException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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
        try {
            em.persist(post);
            for (Tag tag : tags) {
                post.addTag(tag);
                em.merge(tag);
            }
            em.merge(post);
            em.getTransaction().commit();

        } finally {
            em.close();
        }
        return post;
    }

    public PostsDTO getAllPosts() {
        EntityManager em = emf.createEntityManager();
        PostsDTO dtos = null;
        try {
            dtos = new PostsDTO(em.createQuery("SELECT p FROM Post p WHERE p.isHidden = 0").getResultList());
        } finally {
            em.close();
        }

        return dtos;
    }

    public PostsDTO getAllPostsByTags(String[] tagsStrings) {
        EntityManager em = emf.createEntityManager();
        PostsDTO dtos = null;
        try {
            TypedQuery<Tag> query = em.createQuery(
                    "SELECT t FROM Tag t WHERE t.tagName IN :numbers", Tag.class);

            List<String> empNumbers = Arrays.asList(tagsStrings);
            List<Tag> tags = query.setParameter("numbers", empNumbers).getResultList();

            List<Post> posts = new ArrayList();
            for (Tag tag : tags) {
                for (Post post : tag.getPostList()) {
                    if (!post.isIsHidden()) {
                        posts.add(post);
                    }
                }
            }
            dtos = new PostsDTO(posts);
        } finally {
            em.close();
        }
        return dtos;
    }

    public PostsDTO getAllPostsFromUser(String userDTO) {
        EntityManager em = emf.createEntityManager();
        ArrayList<PostsDTO> results = new ArrayList();
        try {
            TypedQuery<Post> query = em.createQuery("SELECT p FROM Post p WHERE p.user.userName = :name AND p.isHidden = 0", entities.Post.class);
            query.setParameter("name", userDTO);
            List<Post> posts = query.getResultList();
            PostsDTO postsDTO = new PostsDTO(posts);

            return postsDTO;
        } finally {
            em.close();
        }
    }

    public PostDTO deletePost(int postID) throws DatabaseException {
        EntityManager em = emf.createEntityManager();

        try {
            Post post = em.find(Post.class, postID);
            post.setIsHidden(true);

            em.getTransaction().begin();
            em.merge(post);
            em.getTransaction().commit();
            return new PostDTO(post);
        } catch (Exception e) {
            throw new DatabaseException("Unable to delete post try again");
        } finally {
            em.close();
        }
    }

    public PostDTO editPost(PostDTO dto) throws DatabaseException {
        EntityManager em = emf.createEntityManager();

        try {
            Post post = em.find(Post.class, dto.getId());
            post.setContent(dto.getContent());
            post.setTitle(dto.getTitle());
            post.setTagList(new ArrayList());
            List<Tag> tags = tagFacade.getTags(dto.getTags());
            for (Tag tag : tags) {
                post.addTag(tag);
                em.merge(tag);
            }
            em.getTransaction().begin();
            em.merge(post);
            em.getTransaction().commit();
            return new PostDTO(post);
        } catch (Exception e) {
            throw new DatabaseException("Unable to edit post");
        } finally {
            em.close();
        }

    }

}
