/**
 * 
 */
package com.test.guestbook.dao;

import java.util.List;

import com.test.guestbook.domain.Comment;

/**
 * TODO: Implement methods in this class.
 * @author Maciek
 *
 */
public class CommentDatastoreDAOImpl implements CommentDAO {

	/* (non-Javadoc)
	 * @see com.test.guestbook.dao.CommentDAO#getAllComments()
	 */
	@Override
	public List<Comment> getAllComments() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.test.guestbook.dao.CommentDAO#getComments(java.lang.String)
	 */
	@Override
	public List<Comment> getComments(String authorAuthEmail) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.test.guestbook.dao.CommentDAO#addComment(com.test.guestbook.domain.Comment)
	 */
	@Override
	public void addComment(Comment comment) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.test.guestbook.dao.CommentDAO#removeOldEntries(int)
	 */
	@Override
	public void removeOldEntries(int olderThanMinutes) {
		// TODO Auto-generated method stub

	}

}
