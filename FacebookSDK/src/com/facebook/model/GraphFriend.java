package com.facebook.model;

import java.io.Serializable;

public class GraphFriend implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6993451099914450815L;
	private String uid = "";
	private String name = "";
	private String userName = "";
	private String mutual_friend_count = "0";
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMutual_friend_count() {
		return mutual_friend_count;
	}
	public void setMutual_friend_count(String mutual_friend_count) {
		this.mutual_friend_count = mutual_friend_count;
	}

}
