package com.klindziuk.offlinelibrary.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {

	private static final long serialVersionUID = -1659201432596003205L;
	private String login;
	private String password;
	private int id;
	private String name;
	private boolean isAdmin;
	private boolean isBanned;
	private Timestamp creationDate;
	private List<Book> giveUpList;
	
	public User() {}
			
	public User(String login, String password,String name) {
		this.login = login;
		this.password = password;
		this.name = name;
	}
	
	public User(int id, String login, String password, String name) {
		this.id = id;
		this.login = login;
		this.password = password;
		this.isAdmin = false;
		this.isBanned = false;
		this.giveUpList = new ArrayList<>();
	}

	public User(int id, String login, String password, String name,  boolean isAdmin, boolean isBanned, Timestamp creationDate) {
		this(id,login,password, name);
		this.name = name;
		this.isAdmin = isAdmin;
		this.isBanned = isBanned;
		this.creationDate = creationDate;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public boolean isBanned() {
		return isBanned;
	}

	public void setBanned(boolean isBanned) {
		this.isBanned = isBanned;
	}

	public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public List<Book> getGiveUpList() {
		return giveUpList;
	}

	public void setGiveUpList(List<Book> giveUpList) {
		this.giveUpList = giveUpList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + (isAdmin ? 1231 : 1237);
		result = prime * result + (isBanned ? 1231 : 1237);
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id != other.id)
			return false;
		if (isAdmin != other.isAdmin)
			return false;
		if (isBanned != other.isBanned)
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [login=" + login + ", password=" + password + ", id=" + id + ", name=" + name + ", isAdmin="
				+ isAdmin + ", isBanned=" + isBanned + ", creationDate=" + creationDate + "]";
	}
}	
