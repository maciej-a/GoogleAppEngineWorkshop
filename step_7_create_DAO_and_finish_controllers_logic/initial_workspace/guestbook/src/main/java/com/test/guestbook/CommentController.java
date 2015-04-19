/**
 * 
 */
package com.test.guestbook;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.test.guestbook.domain.Comment;

/**
 * @author Maciek
 *
 */
@Controller
public class CommentController {
	
	private static final Logger LOGGER = Logger.getLogger(CommentController.class.getName());
	
	@RequestMapping(value="/getAllComments", method=RequestMethod.GET)
	public ModelAndView getAllComments() {
		ModelAndView result = prepareModelAndViewForGuestBook();
		
		LOGGER.info("/getAllComments");
		
		//TODO: Implement fetching all comments
		
		return result;
	}
	
	
	@RequestMapping(value="/getCommentsForUser", method=RequestMethod.GET)
	public ModelAndView getCommentsForUser() {
		ModelAndView result = new ModelAndView();
		
		//TODO: Implement getting comments for specific user by userEmail
		
		LOGGER.info("/getCommentsForUser");
		
		return result;
	}
	
	@RequestMapping(value="/addComment", method=RequestMethod.POST)
	
	public ModelAndView addComment(HttpServletRequest request,
			@ModelAttribute(value="newComment") Comment newComment) {
		ModelAndView result = prepareModelAndViewForGuestBook();
		
		LOGGER.info("/addComment: " + newComment);
		
		//TODO: Implement adding comment
		
		return result;
	}

	/**
	 * Removes old comments
	 * @param retentionInMinutes comments older than this amount of minutes will be removed
	 * @return
	 */
	@RequestMapping(value="/removeOldComments", method=RequestMethod.GET)
	public ModelAndView removeOldComments() {
		ModelAndView result = new ModelAndView("guestbook");
		
		LOGGER.info("/removeOldComments");
		
		return result;
	}
	
	private ModelAndView prepareModelAndViewForGuestBook() {
		ModelAndView result = new ModelAndView("guestbook");
		
		List<Comment> comments = getMockComments();
		result.addObject("comments", comments);
		
		//Providing new object to store comment 
		result.addObject("newComment", prepareNewCommentPlaceholder());
		
		return result;
	}
	
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
	
	private Comment prepareNewCommentPlaceholder() {
		Comment result = new Comment();
		return result;
	}
	
}
