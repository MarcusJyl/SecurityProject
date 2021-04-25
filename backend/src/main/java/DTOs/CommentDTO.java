/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTOs;

import entities.Comment;

/**
 *
 * @author marcg
 */
public class CommentDTO {
    
    private int postID;
    private int id;
    private String text;

    public CommentDTO(Comment comment) {
        this.postID = comment.getPost().getId();
        this.id = comment.getId();
        this.text = comment.getText();
    }

    public int getPostID() {
        return postID;
    }

    public void setPostID(int postID) {
        this.postID = postID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    
    
}
