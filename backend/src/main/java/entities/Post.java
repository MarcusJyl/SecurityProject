/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author marcg
 */
@Entity
@Table(name = "post")
public class Post implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "username")
    @NotNull
    @ManyToOne()
    private User user;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;

    @Size(min = 1, max = 40)
    @Column(name = "title")
    private String title;

    @Size(min = 1, max = 255)
    @Column(name = "content")
    private String Content;

    @OneToMany(mappedBy = "post")
    private List<Like> likes;
    
    @Column(name = "img_link")
    @Size(max = 255)
    private String link;

    public Post() {
    }

    public Post(User user, String title, String Content) {
        this.user = user;
        this.title = title;
        this.Content = Content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
