package com.majumbum.youtube_clone.scopes.auth.payload.response;

import com.majumbum.youtube_clone.scopes.user.entities.Gender;

import java.util.List;

public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private Long id;
	private String nickname;
	private String firstName;
	private String lastName;
	private String email;
	private Gender gender;
	private List<String> roles;

	public JwtResponse(String accessToken, Long id, String nickname, String email, List<String> roles , String firstName ,
					   String lastName, Gender gender) {
		this.token = accessToken;
		this.id = id;
		this.nickname = nickname;
		this.email = email;
		this.roles = roles;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
	}

	public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String username) {
		this.nickname = nickname;
	}

	public List<String> getRoles() {
		return roles;
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

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}
}
