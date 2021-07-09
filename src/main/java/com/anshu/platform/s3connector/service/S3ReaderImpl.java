package com.anshu.platform.s3connector.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.anshu.platform.s3connector.model.Wish;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class S3ReaderImpl implements S3Reader<List<Wish>> {

	private static final Logger LOGGER = LoggerFactory.getLogger(S3ReaderImpl.class);

	@Value(value = "${s3.bucketName:defaultBucket}")
	private String bucketName;

	@Value(value = "${s3.keyName:defaultKey}")
	private String keyName;

	@Override
	@Cacheable(value = "wishes")
	public List<Wish> readObj() {
		LOGGER.info("fetching from s3...");
		LOGGER.info("bucketName = " + bucketName);
		LOGGER.info("keyName = " + keyName);
		List<Wish> wishList = new ArrayList<>();
		final AmazonS3 s3 = AmazonS3ClientBuilder.standard().build();
		if (s3.doesBucketExistV2(bucketName)) {
			if (s3.doesObjectExist(bucketName, keyName)) {
				S3Object o = s3.getObject(bucketName, keyName);
				S3ObjectInputStream s3is = o.getObjectContent();
				ObjectMapper objectMapper = new ObjectMapper();
				CollectionType javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, Wish.class);
				try {
					wishList = objectMapper.readValue(s3is, javaType);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return wishList;

	}

}
