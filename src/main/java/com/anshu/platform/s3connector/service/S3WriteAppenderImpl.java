package com.anshu.platform.s3connector.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.anshu.platform.s3connector.model.Wish;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class S3WriteAppenderImpl implements S3WriteAppender {

	private static final Logger LOGGER = LoggerFactory.getLogger(S3WriteAppenderImpl.class);

	@Value(value = "${s3.bucketName:defaultBucket}")
	private String bucketName;

	@Value(value = "${s3.keyName:defaultKey}")
	private String keyName;
	
	@Autowired
	private S3Reader<List<Wish>> s3Reader;

	@Override
	public List<Wish> addWish(Wish wish) {
		LOGGER.info("fetching from s3...");
		LOGGER.info("bucketName = " + bucketName);
		LOGGER.info("keyName = " + keyName);
		List<Wish> wishList = new ArrayList<>();
		final AmazonS3 s3 = AmazonS3ClientBuilder.standard().build();
		if (s3.doesBucketExistV2(bucketName)) {
			if (s3.doesObjectExist(bucketName, keyName)) {
				wishList = s3Reader.readObj();
				wishList.add(wish);
				ObjectMapper objectMapper = new ObjectMapper();
				String wishedMsgs = "";
				try {
					wishedMsgs = objectMapper.writeValueAsString(wishList);
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				s3.putObject(bucketName, keyName, wishedMsgs);				
			}

		}
		return wishList;
	}
}
