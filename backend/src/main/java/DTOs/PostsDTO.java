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
public class PostsDTO {

    private ArrayList<PostDTO> all = new ArrayList();

    public PostsDTO(List<Post> posts) {
        for (Post post : posts) {
            all.add(new PostDTO(post));
        }
    }

    public ArrayList<PostDTO> getPostsDTO() {
        return all;
    }
    
}
