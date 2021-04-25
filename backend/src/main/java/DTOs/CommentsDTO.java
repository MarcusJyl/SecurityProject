/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTOs;

import entities.Comment;
import entities.Post;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author marcg
 */
public class CommentsDTO {

    private ArrayList<CommentDTO> all = new ArrayList();

    public CommentsDTO(List<Comment> comments) {
        for (Comment comment : comments) {
            all.add(new CommentDTO(comment));
        }
    }

    public ArrayList<CommentDTO> getPostsDTO() {
        return all;
    }
}
