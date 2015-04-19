/**
 * 
 */
package com.test.guestbook.dao;

import java.util.List;

import com.test.guestbook.domain.Comment;

/**
 * @author Maciek
 *
 */
public interface CommentDAO {
	
	/**
	 * 
	 * @return
	 */
	public List<Comment> getAllComments();
	
	/**
	 * Returns comments for given user.
	 * @param authorAuthEmail
	 * @return
	 */
	public List<Comment> getComments(String authorAuthEmail);
	
	/**
	 * 
	 * @param comment
	 */
	public void addComment(Comment comment);
	
	/**
	 * Removes comments older than given time in minutes
	 * @param olderThanMinutes
	 */
	public void removeOldEntries(int olderThanMinutes);	

}
