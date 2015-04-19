package com.test.guestbook.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.test.guestbook.domain.Comment;

/**
 * CommentDAO implementation based on GAE Datastore API.
 * @author Maciek
 *
 */
public class CommentDatastoreDAOImpl implements CommentDAO {
	
	private static final Logger LOGGER = Logger.getLogger(CommentDatastoreDAOImpl.class.getName());
	
	private static final String AUTHOR_AUTH_EMAIL = "authorAuthEmail";
	private static final String AUTHOR_NICKNAME = "authorNickname";
	private static final String TEXT = "text";
	private static final String TIMESTAMP = "timestamp";
	private static final String IMAGE_URL = "image";
	private static final String IMAGE_BLOB_KEY = "imageBlobKey";
	
	@Override
	public List<Comment> getAllComments() {
		LOGGER.info("Getting all comments");
		List<Comment> result = new ArrayList<Comment>(20);
		DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
		//sort comments so that newest will appear on top of the page
		Query query = new Query(Comment.class.getSimpleName()).addSort(TIMESTAMP, SortDirection.DESCENDING);
		
		PreparedQuery preparedQuery = datastoreService.prepare(query);
		
		for( Entity entity : preparedQuery.asIterable() ) {
			result.add(toComment(entity));
		}
		
		return result;
	}
	
	@Override
	public List<Comment> getComments(String authorAuthEmail) {
		List<Comment> result = new ArrayList<Comment>(20);
		DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
		//sort comments so that newest will appear on top of the page
		
		Key ancestorKey = KeyFactory.createKey("User", authorAuthEmail);
		
		Query query = new Query(Comment.class.getSimpleName()).
				setAncestor(ancestorKey).
				addSort(TIMESTAMP, SortDirection.DESCENDING);
		
		PreparedQuery preparedQuery = datastoreService.prepare(query);
		
		for( Entity entity : preparedQuery.asIterable() ) {
			result.add(toComment(entity));
		}
		
		return result;
	}
	
	@Override
	public void addComment(Comment comment) {
		LOGGER.info("Adding new comment: " + comment);
		comment.setTimestamp(new Date());
		UserService userService = UserServiceFactory.getUserService();
		User currentUser = userService.getCurrentUser();
		if( currentUser != null ) {
			comment.setAuthorAuthEmail(currentUser.getEmail());
			comment.setAuthorNickname(currentUser.getNickname());
		} else {
			//ensure that auth email will be
			//set only for authenticated users
			comment.setAuthorAuthEmail(null);
		}
		
		Entity entity = toEntity(comment);
		DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
		Transaction tx = datastoreService.beginTransaction();
		datastoreService.put(entity);
		tx.commit();
	}
	

	@Override
	public void removeOldEntries(int olderThanMinutes) {
		LOGGER.info("Removing comments older than: " + olderThanMinutes + " minutes");
		DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
		BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, -olderThanMinutes);
		Date expirationDate = cal.getTime();
		LOGGER.info("Expiration date: " + expirationDate);
		Filter oldCommentsFilter = new Query.FilterPredicate(TIMESTAMP,
				FilterOperator.LESS_THAN, expirationDate);
		
		Query query = new Query(Comment.class.getSimpleName()).setFilter(oldCommentsFilter);
		PreparedQuery oldComments = datastoreService.prepare(query);
		
		int count = 0;
		for( Entity oldComment : oldComments.asIterable() ) {
			String imageBlobKey = (String)oldComment.getProperty(IMAGE_BLOB_KEY);
			datastoreService.delete(oldComment.getKey());
			
			//clean up images if any
			if( imageBlobKey != null ) {
				LOGGER.info("Removing image: " + imageBlobKey);
				BlobKey blobKey = new BlobKey(imageBlobKey);
				blobstoreService.delete(blobKey);
			}
			
			++count;
		}
		
		LOGGER.info("Removed: " + count + " items");
		
	}
	
	/**
	 * 
	 * @param entity
	 * @return
	 */
	private Comment toComment(Entity entity) {
		Comment result = new Comment();
		result.setAuthorAuthEmail((String)entity.getProperty(AUTHOR_AUTH_EMAIL));
		result.setAuthorNickname((String)entity.getProperty(AUTHOR_NICKNAME));
		result.setText((String)entity.getProperty(TEXT));
		result.setTimestamp((Date)entity.getProperty(TIMESTAMP));
		result.setImageUrl((String)entity.getProperty(IMAGE_URL));
		result.setImageBlobKey((String)entity.getProperty(IMAGE_BLOB_KEY));
		return result;
	}
	
	/**
	 * 
	 * @param comment
	 * @return
	 */
	private Entity toEntity(Comment comment) {
		
		Entity entity = null;
		
		if( comment.getAuthorAuthEmail() != null ) {
			//For authenticated user, his comments
			//will be persisted under parent key.
			//It will allow then easily fetch all comments
			//for this particular user using ancestor key filter:
			//see: https://developers.google.com/appengine/docs/java/datastore/queries#Java_Ancestor_filters
			//see" https://developers.google.com/appengine/docs/java/datastore/queries#Java_Ancestor_queries
			Key parentKey = KeyFactory.createKey("User", comment.getAuthorAuthEmail());
			entity = new Entity(Comment.class.getSimpleName(), parentKey);
		} else {
			entity = new Entity(Comment.class.getSimpleName());
		}
		
		entity.setProperty(AUTHOR_AUTH_EMAIL, comment.getAuthorAuthEmail());
		entity.setProperty(AUTHOR_NICKNAME, comment.getAuthorNickname());
		entity.setProperty(TEXT, comment.getText());
		entity.setProperty(TIMESTAMP, comment.getTimestamp());
		entity.setProperty(IMAGE_URL, comment.getImageUrl());
		entity.setProperty(IMAGE_BLOB_KEY, comment.getImageBlobKey());
		return entity;
	}
	
	/**
	 * 
	 * @return
	 */
	private List<Comment> getMockComments() {
		List<Comment> comments = new ArrayList<Comment>();
		Comment c1 = new Comment();
		c1.setAuthorNickname("Vincent");
		c1.setText("Hello");
		c1.setTimestamp(new Date());
		comments.add(c1);
		
		Comment c2 = new Comment();
		c2.setAuthorNickname("John");
		c2.setAuthorAuthEmail("john.authenticated@gmail.com");
		c2.setText("Thanks for great time!");
		c2.setTimestamp(new Date());
		comments.add(c2);
		
		Comment c3 = new Comment();
		c3.setAuthorNickname("Adam");
		c3.setText("It was great time to stay at your hotel! I will definitelly get back again!");
		c3.setTimestamp(new Date());
		comments.add(c3);
		return comments;
	}

}
