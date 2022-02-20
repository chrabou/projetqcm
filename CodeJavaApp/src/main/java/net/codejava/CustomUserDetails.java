package net.codejava;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import antlr.collections.List;

public class CustomUserDetails implements UserDetails {

	private User user;

	public CustomUserDetails(User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority internaute = new SimpleGrantedAuthority("internaute");
		SimpleGrantedAuthority employe = new SimpleGrantedAuthority("employe");
		SimpleGrantedAuthority responsable = new SimpleGrantedAuthority("responsable");
		SimpleGrantedAuthority directeur = new SimpleGrantedAuthority("directeur");

		ArrayList<SimpleGrantedAuthority> authories = new ArrayList<>();
		authories.add(internaute);
		authories.add(employe);
		authories.add(responsable);
		authories.add(directeur);

		return authories;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getEmail();
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

	public String getFullName() {
		return user.getFirstName() + " " + user.getLastName();
	}

	public boolean hasRole(String roleName) {
		return this.user.hasRole(roleName);
	}
}
