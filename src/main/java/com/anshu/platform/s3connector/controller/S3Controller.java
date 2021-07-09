package com.anshu.platform.s3connector.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.anshu.platform.s3connector.model.Wish;
import com.anshu.platform.s3connector.service.S3Reader;
import com.anshu.platform.s3connector.service.S3WriteAppender;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
public class S3Controller {
	
	@Autowired
	private S3Reader<List<Wish>> s3Reader;
	
	@Autowired
	private S3WriteAppender s3Writer;
	
	@RequestMapping(value="/healthCheck", method = RequestMethod.GET)
	@ResponseBody
	public String healthCheck() {
		return "ok";
	}
	
	@RequestMapping(value = "/wishes", method = RequestMethod.GET)
	@ResponseBody
	public List<Wish> getWishes(){
		return s3Reader.readObj();
	}
	
	@RequestMapping(value = "/wish", method = RequestMethod.POST)
	@ResponseBody
	public List<Wish> addWish(@RequestBody Wish wish){
		return s3Writer.addWish(wish);
	}
	
	  @RequestMapping(
	            value = "/**",
	            method = RequestMethod.OPTIONS
	    )
	    public ResponseEntity handle() {
	        return new ResponseEntity(HttpStatus.OK);
	    }
	

}
