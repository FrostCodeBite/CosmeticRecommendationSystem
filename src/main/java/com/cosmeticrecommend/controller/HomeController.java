package com.cosmeticrecommend.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	//RETURN BACK THE INDEX.HTML
	@GetMapping({"","/"})
	public String index() {
		return "index";
	}
}
