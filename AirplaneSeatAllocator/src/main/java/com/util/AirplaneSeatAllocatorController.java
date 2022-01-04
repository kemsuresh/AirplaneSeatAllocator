package com.util;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AirplaneSeatAllocatorController {
	
	@RequestMapping("/")
	public String mainPage() {
		return "home";
	}
	@RequestMapping("/js")
	public String js() {
		return "js";
	}
	
	@PostMapping(value="/allocate/{total}", produces = "text/plain")
	public @ResponseBody String postIt(@RequestBody Integer[][] req,@PathVariable int total) {
	    return new AirplaneSeatAllocator()
	    		.getArrangments(req, total)
	    		.entrySet()
	    		.stream()
	    		.map(entry -> Arrays.asList(entry.getValue())
	    				.stream()
	    				.map(subentry -> Arrays.toString(subentry))
	    				.collect(Collectors.toList()))
	    		.collect(Collectors.toList())
	    		.toString();
	}
}
