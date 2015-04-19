/**
 * 
 */
package com.test.guestbook;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Maciek
 *
 */
@Controller
public class TestController {
	
	@RequestMapping(value="/test", method=RequestMethod.GET, produces="text/plain")
	@ResponseBody
	public String test() {
		return "Test: " + new Date();
	}

}
