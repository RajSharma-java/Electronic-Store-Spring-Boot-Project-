package com.raj.project.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name="user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails
{
	
//	
	
	@Id
	@Column(name = "userId")

	private String id;
	
	@Column(name="user_name")
	private String name;
	
	@Column(name="user_email",unique = true)
	private String email;
	
	@Column(name="user_password")
	private String password;
	
	@Column(name="user_gender")
	private String gender;
	
	@Column(name="user_about")
	private String about;


    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Order> orders = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Role> roles = new ArrayList<>();
    
    
    
// For role -normal and admin
    //important
    @Override
    public String getUsername() {
        return this.getEmail();
    }

    //important
    // lombok
    @Override
    public String getPassword() {
        return password;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // according for Authority
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<SimpleGrantedAuthority> authproties=roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());
		return authproties;
	}
	

}
