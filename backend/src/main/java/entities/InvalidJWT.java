/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;

/**
 *
 * @author marcg
 */
@Entity
public class InvalidJWT implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "token")
    private String token;

    @Basic(optional = false)
    @NotNull
    @Column(name = "user_name", length = 25)
    private String username;

    private Date exparation;

    public InvalidJWT() {
    }

    public InvalidJWT(String token, String username, Date exparation) {
        this.token = token;
        this.username = username;
        this.exparation = exparation;
    }

    public String getToken() {
        return token;
    }
    
    

}
