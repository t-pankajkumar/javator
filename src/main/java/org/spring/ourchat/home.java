package org.spring.ourchat;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class home {

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	static List<String> chat = new ArrayList<String>();

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {



		return "open";
	}

	@RequestMapping(value = "/asncMsg", method = RequestMethod.GET)
	public @ResponseBody String msg(Model model, @RequestParam("msg") String msg) {
		chat.add("\"" + msg + "\"");
		// model.addAttribute("msg", chat);
		/*
		 * sfor(String s : chat){ System.out.println(s); }
		 */
		// System.out.println("{\"msgs\":"+chat+"}");
		return "{\"msgs\":" + chat + "}";
	}

	@RequestMapping(value = "/reset", method = RequestMethod.GET)
	public String reset(Model model) {
		chat.clear();
		return "open";
	}

}
