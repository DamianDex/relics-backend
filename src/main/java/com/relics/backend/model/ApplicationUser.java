package com.relics.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.relics.backend.View;
import com.relics.backend.security.model.RegistrationBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Entity
public class ApplicationUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonView(View.BasicDescription.class)
    private String username;

    @JsonView(View.BasicDescription.class)
    private String password;

    @JsonView(View.BasicDescription.class)
    private String email;

    @JsonView(View.BasicDescription.class)
    private String firstName;

    @JsonView(View.BasicDescription.class)
    private String lastName;

    @JsonView(View.BasicDescription.class)
    private String profileImage;

    @ManyToOne
    private UserTypes type;

    private boolean enabled;
    private String uuid;

    public ApplicationUser() {
    }

    public ApplicationUser(RegistrationBean rUser, UserTypes type){
        this.username = rUser.getUsername();
        this.password = rUser.getPassword();
        this.enabled = false;
        this.uuid =  UUID.randomUUID().toString();
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonIgnore
    public String getName() {
        if (username != null) {
            return username.split("@")[0];
        }
        return null;
    }


    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        GrantedAuthority r = new GrantedAuthority() {
            private static final long serialVersionUID = 5512977826263078494L;

            @Override
            public String getAuthority() {
                return "ROLE_" + getType().getCode();
            }
        };
        authorities.add(r);
        return authorities;
    }

    @JsonIgnore
    public boolean isAccountNonExpired() {
        return enabled;
    }

    @JsonIgnore
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return false;
    }

    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public UserTypes getType() {
        return type;
    }

    public void setType(UserTypes type) {
        this.type = type;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    @Override
    public String toString() {
        return "ApplicationUser [id=" + id + ", username=" + username + ", password=" + password + ", type=" + type + ", enabled="
                + enabled + ", uuid=" + uuid + "]";
    }
}
