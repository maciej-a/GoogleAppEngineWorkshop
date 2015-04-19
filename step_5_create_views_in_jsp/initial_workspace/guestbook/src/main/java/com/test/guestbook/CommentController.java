/**
 * 
 */
package com.test.guestbook;

import java.util.logging.Logger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Maciek
 *
 */
@Controller
public class CommentController {
	
	private static final Logger LOGGER = Logger.getLogger(CommentController.class.getName());
	
	@RequestMapping(value="/getAllComments", method=RequestMethod.GET)
	public ModelAndView getAllComments() {
		return new ModelAndView("index");
	}
	
	@RequestMapping(value="/getCommentsForUser", method=RequestMethod.GET)
	public ModelAndView getCommentsForUser(@RequestParam(value="userEmail", required=true) String userEmail) {
		return new ModelAndView("index");
	}
	
	@RequestMapping(value="/addComment", method=RequestMethod.POST)
	public ModelAndView addComment() {
		return new ModelAndView("index");
	}
	
	@RequestMapping(value="/removeOldComments", method=RequestMethod.GET)
	public ModelAndView removeOldComments() {
		return new ModelAndView("index");

	}
	
}
