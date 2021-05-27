/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Tag;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author marcg
 */
public class TagFacade {

    private static EntityManagerFactory emf;
    private static TagFacade instance;

    private TagFacade() {
    }

    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static TagFacade getTagFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new TagFacade();
        }
        return instance;
    }

    public List<Tag> getTags(List<String> strings) {
        List<Tag> tags = new ArrayList();
        for (String string : strings) {
            Tag tag = getTagIfExist(string.toLowerCase());
            if (!(tag instanceof Tag)) {
                tag = addTag(string.toLowerCase());
            }
            tags.add(tag);
        }
        return tags;
    }

    public Tag getTagIfExist(String tagString) {
        EntityManager em = emf.createEntityManager();

        try {
            Tag tag = em.find(Tag.class, tagString);
            return tag;
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

    private Tag addTag(String tagString) {
        EntityManager em = emf.createEntityManager();

        Tag tag = new Tag(tagString);
        try {
            em.getTransaction().begin();
            em.persist(tag);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        return tag;
    }

}
