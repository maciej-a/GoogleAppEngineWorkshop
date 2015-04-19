/**
 * 
 */
package com.test.guestbook.domain;

import java.util.Date;

/**
 * @author Maciek
 *
 */
public class Comment {
	
	private String authorNickname;
	/**
	 * If user is signedIn to his
	 * Google account, this field will
	 * be populated
	 */
	private String authorAuthEmail;
	private String text;
	
	/**
	 * Direct URL to get image for this comment
	 */
	private String imageUrl;
	
	/**
	 * BLOB key - used to manipulate blob image data
	 * ex. to remove blob from Cloud.
	 */
	private String imageBlobKey;
	
	private Date timestamp;
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getAuthorNickname() {
		return authorNickname;
	}

	public void setAuthorNickname(String authorNickname) {
		this.authorNickname = authorNickname;
	}

	public String getAuthorAuthEmail() {
		return authorAuthEmail;
	}

	public void setAuthorAuthEmail(String authorAuthEmail) {
		this.authorAuthEmail = authorAuthEmail;
	}

	public Date getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	public Boolean getIsAnonymous() {
		return this.authorAuthEmail == null;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getImageBlobKey() {
		return imageBlobKey;
	}

	public void setImageBlobKey(String imageBlobKey) {
		this.imageBlobKey = imageBlobKey;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((authorAuthEmail == null) ? 0 : authorAuthEmail.hashCode());
		result = prime * result
				+ ((authorNickname == null) ? 0 : authorNickname.hashCode());
		result = prime * result
				+ ((imageBlobKey == null) ? 0 : imageBlobKey.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		result = prime * result
				+ ((timestamp == null) ? 0 : timestamp.hashCode());
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
		Comment other = (Comment) obj;
		if (authorAuthEmail == null) {
			if (other.authorAuthEmail != null)
				return false;
		} else if (!authorAuthEmail.equals(other.authorAuthEmail))
			return false;
		if (authorNickname == null) {
			if (other.authorNickname != null)
				return false;
		} else if (!authorNickname.equals(other.authorNickname))
			return false;
		if (imageBlobKey == null) {
			if (other.imageBlobKey != null)
				return false;
		} else if (!imageBlobKey.equals(other.imageBlobKey))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Comment [authorNickname=" + authorNickname
				+ ", authorAuthEmail=" + authorAuthEmail + ", text=" + text
				+ ", imageUrl=" + imageUrl + ", imageBlobKey=" + imageBlobKey
				+ ", timestamp=" + timestamp + "]";
	}

}
