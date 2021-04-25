/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DTOs;

/**
 *
 * @author Marcus
 */
public class SearchDTO {
    private UsersDTO usersDTO;
    private PostsDTO postsDTO;

    public SearchDTO(UsersDTO usersDTO, PostsDTO postsDTO) {
        this.usersDTO = usersDTO;
        this.postsDTO = postsDTO;    
    }

    public UsersDTO getUsersDTO() {
        return usersDTO;
    }

    public void setUsersDTO(UsersDTO usersDTO) {
        this.usersDTO = usersDTO;
    }

    public PostsDTO getPostsDTO() {
        return postsDTO;
    }

    public void setPostsDTO(PostsDTO postsDTO) {
        this.postsDTO = postsDTO;
    }
   
    
}
