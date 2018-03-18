package com.relics.backend.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@SuppressWarnings("serial")
@Entity
@Table(name = "users", schema = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="SEQMYCLASSID")
    @SequenceGenerator(name="SEQMYCLASSID", sequenceName="SEQMYCLASSID")
    @Column(name = "id", updatable = false)
	private long id;

	private String username;
	private String password;
	
    @ManyToOne
	private UserTypes type;
	private boolean enabled;
	private String uuid;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		if (username != null) {
			return username.split("@")[0];
		}
		return null;
	}

	@Override
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

	@Override
	public boolean isAccountNonExpired() {
		return enabled;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", type=" + type + ", enabled="
				+ enabled + ", uuid=" + uuid + "]";
	}
	
	

}
