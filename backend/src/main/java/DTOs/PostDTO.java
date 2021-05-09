/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTOs;

import entities.Post;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author marcg
 */
public class PostDTO {

    private String title;
    private String content;
    private List<String> tags = new ArrayList();
    private String username;
    private int id;
    private int likes;
    private String profileImg;

    public PostDTO(String title, String content, List<String> tags) {
        this.tags = tags;
        this.title = title;
        this.content = content;
    }

    public PostDTO(Post post) {
        this.title = post.getTitle();
        this.content = post.getContent();
        this.username = post.getUser().getUserName();
        this.id = post.getId();
        this.likes = 0;
        this.profileImg = post.getUser().getLinkToProfileImg();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }
    
    
}
