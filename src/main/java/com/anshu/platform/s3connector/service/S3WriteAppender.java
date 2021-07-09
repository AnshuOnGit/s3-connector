package com.anshu.platform.s3connector.service;

import java.util.List;

import com.anshu.platform.s3connector.model.Wish;

public interface S3WriteAppender {
	
	public List<Wish> addWish(Wish wish);

}
