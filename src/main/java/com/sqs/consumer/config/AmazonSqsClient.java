package com.sqs.consumer.config;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;


@Service
public class AmazonSqsClient {
    @Value("${aws.ur}")
    private String url;
    @Value("${aws.region}")
    private String region;
    @Value("${aws.access-ke}")
    private String accessKey;
    @Value("${aws.secret-ke}")
    private String secretKey;
    private AmazonSQS client;


    @PostConstruct
    private void initializeAmazonSqsClient() {
        this.client =
                AmazonSQSClientBuilder.standard()
                        .withCredentials(getAwsCredentialProvider())
                        .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(url, region))
                        .build();
    }

    private AWSCredentialsProvider getAwsCredentialProvider() {
        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        return new AWSStaticCredentialsProvider(awsCredentials);
    }

    public AmazonSQS getClient() {
        return client;
    }

}
