package com.relics.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.relics.backend.View;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class ApplicationUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonView(View.BasicDescription.class)
    private String username;
    private String password;

    @ManyToOne
    private UserTypes type;
    private Boolean enabled;
    private String uuid;

    public ApplicationUser() {
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

    public Boolean isEnabled() {
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

    @Override
    public String toString() {
        return "ApplicationUser [id=" + id + ", username=" + username + ", password=" + password + ", type=" + type + ", enabled="
                + enabled + ", uuid=" + uuid + "]";
    }
}