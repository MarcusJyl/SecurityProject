/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import DTOs.CommentDTO;
import DTOs.CommentsDTO;
import DTOs.PostDTO;
import entities.Comment;
import entities.Post;
import entities.User;
import errorhandling.DatabaseException;
import errorhandling.InvalidInputException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

/**
 *
 * @author marcg
 */
public class CommentFacade {

    private static EntityManagerFactory emf;
    private static CommentFacade instance;
    private static UserFacade userFacade;

    public static CommentFacade getCommentFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CommentFacade();
            userFacade = UserFacade.getUserFacade(_emf);
        }
        return instance;
    }

    public CommentDTO addComment(int postID, String text, String username) throws InvalidInputException, DatabaseException {
        EntityManager em = emf.createEntityManager();
        User user = userFacade.findUser(username);

        try {
            Post post = em.find(Post.class, postID);
            Comment comment = new Comment(post, text, user);
            em.getTransaction().begin();
            em.persist(comment);
            em.persist(post);
            em.merge(user);
            em.getTransaction().commit();

            return new CommentDTO(comment);
        } catch (Exception e) {
            throw new InvalidInputException("Cloud not find post with id: " + postID);
        } finally {
            em.close();
        }
    }

    public CommentsDTO getComments(int postID) throws InvalidInputException {
        EntityManager em = emf.createEntityManager();

        try {
            Query q = em.createQuery("SELECT c FROM Comment c WHERE c.post.id = :postID AND c.isHidden = 0", Comment.class);

            q.setParameter("postID", postID);
            List<Comment> comments = q.getResultList();
            return new CommentsDTO(comments);
        } catch (Exception e) {
            throw new InvalidInputException("fuck af so");
        } finally {
            em.close();
        }
    }

    public CommentDTO deleteComment(int commentID) throws DatabaseException {
        EntityManager em = emf.createEntityManager();

        try {
            Comment comment = em.find(Comment.class, commentID);
            comment.setIsHidden(true);

            em.getTransaction().begin();
            em.merge(comment);
            em.getTransaction().commit();
            return new CommentDTO(comment);
        } catch (Exception e) {
            throw new DatabaseException("Unable to delete post try again");
        } finally {
            em.close();
        }
    }
}
