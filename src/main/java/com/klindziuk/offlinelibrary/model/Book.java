package com.klindziuk.offlinelibrary.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Book implements Serializable {
	private static final long serialVersionUID = 3496552795406068119L;
	private int id;
	private String name;
	private int year;
	private String author;
	private boolean isAvailable;
	private boolean isDeprecated;
	private Timestamp additionDate;

	public Book() {
	}

	public Book(String name, String author, int year) {
		this.name = name;
		this.year = year;
		this.author = author;
		this.isAvailable = true;
		this.isDeprecated = false;
	}

	public Book(int id, String name, String author, int year, boolean isAvailable, boolean isDeprecated,
			Timestamp additionDate) {
		this.id = id;
		this.name = name;
		this.year = year;
		this.author = author;
		this.isAvailable = isAvailable;
		this.isDeprecated = isDeprecated;
		this.additionDate = additionDate;
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

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public Timestamp getAdditionDate() {
		return additionDate;
	}

	public void setAdditionDate() {
		this.additionDate = new Timestamp(System.currentTimeMillis());
	}

	public boolean isDeprecated() {
		return isDeprecated;
	}

	public void setDeprecated(boolean isDeprecated) {
		this.isDeprecated = isDeprecated;
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + id;
		result = prime * result + (isAvailable ? 1231 : 1237);
		result = prime * result + (isDeprecated ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + year;
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
		Book other = (Book) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (id != other.id)
			return false;
		if (isAvailable != other.isAvailable)
			return false;
		if (isDeprecated != other.isDeprecated)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (year != other.year)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", name=" + name + ", year=" + year + ", author=" + author + ", isAvailable="
				+ isAvailable + ", isDeprecated=" + isDeprecated + "]";
	}
}
