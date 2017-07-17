package com.klindziuk.offlinelibrary.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Subscription implements Serializable {
	private static final long serialVersionUID = -4238864085148679154L;
	private int id;
	private int userId;
	private int bookId;
	private Timestamp start;
	private Timestamp finish;
	private boolean isActive;
	
	public Subscription() {}

	public Subscription(int userId, int bookId, boolean isActive) {
		this.userId = userId;
		this.bookId = bookId;
		this.isActive = isActive;
	}

	public Subscription(int id, int userId, int bookId, Timestamp start, Timestamp finish, boolean isActive) {
		this(userId, bookId, isActive);
		this.id = id;
		this.start = start;
		this.finish = finish;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public Timestamp getStart() {
		return start;
	}

	public void setStart(Timestamp start) {
		this.start = start;
	}

	public Timestamp getFinish() {
		return finish;
	}

	public void setFinish(Timestamp finish) {
		this.finish = finish;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + bookId;
		result = prime * result + ((finish == null) ? 0 : finish.hashCode());
		result = prime * result + id;
		result = prime * result + (isActive ? 1231 : 1237);
		result = prime * result + ((start == null) ? 0 : start.hashCode());
		result = prime * result + userId;
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
		Subscription other = (Subscription) obj;
		if (bookId != other.bookId)
			return false;
		if (finish == null) {
			if (other.finish != null)
				return false;
		} else if (!finish.equals(other.finish))
			return false;
		if (id != other.id)
			return false;
		if (isActive != other.isActive)
			return false;
		if (start == null) {
			if (other.start != null)
				return false;
		} else if (!start.equals(other.start))
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Subscription [id=" + id + ", userId=" + userId + ", bookId=" + bookId + ", start=" + start + ", finish="
				+ finish + ", isActive=" + isActive + "]";
	}
}
