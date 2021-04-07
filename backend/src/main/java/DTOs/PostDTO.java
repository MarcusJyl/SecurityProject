/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTOs;

import entities.Post;

/**
 *
 * @author marcg
 */
public class PostDTO {
    
    private String title;
    private String content;
    private String username;
    private int id;
    private int likes;

    public PostDTO(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public PostDTO(Post post) {
        this.title = post.getTitle();
        this.content = post.getContent();
        this.username = post.getUser().getUserName();
        this.id = post.getId();
        this.likes = 0;
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
}
