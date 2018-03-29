package com.relics.backend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class UserTypes {

    public enum ManagedTypes {
        USER, ADMIN;

        public ManagedTypes getUserType(String type) {
            for (ManagedTypes t : ManagedTypes.values()) {
                if (type.equals("ROLE_" + t.name())) {
                    return t;
                }
            }
            return null;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String code;
    private String description;

/*    @OneToMany( targetEntity=ApplicationUser.class )
    private List<ApplicationUser> usersList;*/

    public UserTypes() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

/*    public List<ApplicationUser> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<ApplicationUser> usersList) {
        this.usersList = usersList;
    }*/

    @Override
    public String toString() {
        return "UserTypes [id=" + id + ", code=" + code + ", description=" + description + "]";
    }


}
