package facades;

import DTOs.UserDTO;
import entities.Role;
import entities.User;
import errorhandling.DatabaseException;
import errorhandling.InvalidInputException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import security.errorhandling.AuthenticationException;

/**
 * @author lam@cphbusiness.dk
 */
public class UserFacade {

    private static EntityManagerFactory emf;
    private static UserFacade instance;

    private UserFacade() {
    }

    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static UserFacade getUserFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new UserFacade();
        }
        return instance;
    }

    public User getVeryfiedUser(String username, String password) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        User user;
        try {
            user = em.find(User.class, username);
            if (user == null || !user.verifyPassword(password)) {
                throw new AuthenticationException("Invalid user name or password");
            }
        } finally {
            em.close();
        }
        return user;
    }

    public UserDTO addUser(UserDTO userDTO) throws InvalidInputException {
        EntityManager em = emf.createEntityManager();
        String name = null;
        try {
            Query query = em.createQuery("SELECT u.userName FROM User u WHERE u.userName = :name");
            query.setParameter("name", userDTO.getName());
            name = (String) query.getSingleResult();
        } catch (Exception e) {
        }

        if (name != null) {
            throw new InvalidInputException(String.format("The name %s is already taken", name));
        }

        User user = new User(userDTO.getName(), userDTO.getPassword());
        for (String role : userDTO.getRoles()) {
            user.addRole(new Role(role));
        }
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();

        return new UserDTO(user);
    }

    public String setProfileImageLink(String username, String link) throws InvalidInputException {
        EntityManager em = emf.createEntityManager();
        String name = null;
        try {
            em.getTransaction().begin();
            Query query = em.createQuery("UPDATE User u SET U.linkToProfileImg=:link WHERE u.userName=:username ");
            query.setParameter("username", username);
            query.setParameter("link", link).executeUpdate();
            em.getTransaction().commit();

            return "DOne";
        } catch (Exception e) {
            System.out.println(e);
            return "";
        }
    }

    public User findUser(String username) throws DatabaseException {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(User.class, username);
        } catch (Exception e) {
            throw new DatabaseException("Couldt not find user: " + username + " in databases");
        }
    }

    public void changePassword(String username, String newPassword, String oldPassword) throws AuthenticationException, InvalidInputException {
        EntityManager em = emf.createEntityManager();
        try {
            User user = this.getVeryfiedUser(username, oldPassword);
            resetPassword(user, newPassword);
        } catch (Exception e) {
            throw new InvalidInputException("Wrong password for user: " + username);
        }
    }

    private void resetPassword(User user, String newPassword) throws DatabaseException {
        EntityManager em = emf.createEntityManager();
        try {
            user.setUserPass(newPassword);
            em.getTransaction().begin();
            em.merge(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new DatabaseException("Unable to change password in database try again later");
        }

    }

}
