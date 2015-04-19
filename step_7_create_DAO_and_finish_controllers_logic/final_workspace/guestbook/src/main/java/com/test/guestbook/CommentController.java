/**
 * 
 */
package com.test.guestbook;

import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.test.guestbook.dao.CommentDAO;
import com.test.guestbook.domain.Comment;

/**
 * @author Maciek
 *
 */
@Controller
public class CommentController {
	
	private static final Logger LOGGER = Logger.getLogger(CommentController.class.getName());
	
	@Autowired
	private CommentDAO commentDAO;
	
	@Autowired
	private Integer commentsRetentionMinutes;
	
	/**
	 * Returns all comments
	 * @return
	 */
	@RequestMapping(value="/getAllComments", method=RequestMethod.GET)
	public ModelAndView getAllComments() {
		ModelAndView result = new ModelAndView("guestbook");
		
		LOGGER.info("/getAllComments");
		
		List<Comment> comments = this.commentDAO.getAllComments();
		result.addObject("comments", comments);
		
		//Providing placeholder for new comment
		result.addObject("newComment", prepareNewCommentPlaceholder());
		
		return result;
		
	}
	
	
	/**
	 * Returns comments for given user
	 * @return
	 */
	@RequestMapping(value="/getCommentsForUser", method=RequestMethod.GET)
	public ModelAndView getCommentsForUser(@RequestParam(value="userEmail", required=true) String userEmail) {
		ModelAndView result = new ModelAndView("guestbook");
		
		LOGGER.info("/getCommentsForUser");
		
		List<Comment> comments = this.commentDAO.getComments(userEmail);
		result.addObject("comments", comments);
		
		//Providing placeholder for new comment 
		result.addObject("newComment", prepareNewCommentPlaceholder());
		
		return result;
	}
	
	/**
	 * Adds a new comment
	 * @param request
	 * @param newComment
	 * @return
	 */
	@RequestMapping(value="/addComment", method=RequestMethod.POST)
	public ModelAndView addComment(HttpServletRequest request,
			@ModelAttribute(value="newComment") Comment newComment) {
		ModelAndView result = new ModelAndView("redirect:/getAllComments"); 
		
		LOGGER.info("/addComment: " + newComment);
		this.commentDAO.addComment(newComment);
		
		return result;
	}
	
	/**
	 * Removes old comments
	 * @param retentionInMinutes comments older than this amount of minutes will be removed
	 * @return
	 */
	@RequestMapping(value="/removeOldComments", method=RequestMethod.GET)
	public ModelAndView removeOldComments(@RequestParam(value="retentionInMinutes", required=false) Integer retentionInMinutes) {
		LOGGER.info("/removeOldComments");
		
		if( retentionInMinutes == null ) {
			retentionInMinutes = commentsRetentionMinutes;
		}
		
		commentDAO.removeOldEntries(retentionInMinutes);
		return new ModelAndView("redirect:/getAllComments");
	}
	
	private Comment prepareNewCommentPlaceholder() {
		Comment result = new Comment();
		return result;
	}
	
}
