/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author marcg
 */
@Entity
@Table(name = "tag")
public class Tag implements Serializable {

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "tag_name", length = 25)
    private String tagName;

    @ManyToMany(mappedBy = "tagList")
    private List<Post> postList = new ArrayList();

    public Tag() {
    }
    
    public void addPost(Post post){
        this.postList.add(post);
    }

    public Tag(String tagName) {
        this.tagName = tagName;
    }

    public String getTagName() {
        return tagName;
    }

}
