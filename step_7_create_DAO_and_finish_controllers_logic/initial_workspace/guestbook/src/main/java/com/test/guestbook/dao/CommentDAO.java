package com.test.guestbook.dao;

import java.util.List;

import com.test.guestbook.domain.Comment;


public interface CommentDAO {
	
/*
 * TODO: Implement this interface in CommentDatastoreDAOImpl
 * Please use Datastore API for implementation.
 */

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
