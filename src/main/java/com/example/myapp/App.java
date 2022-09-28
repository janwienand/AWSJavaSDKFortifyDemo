package com.example.myapp;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import javax.sound.sampled.SourceDataLine;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.model.AccessControlPolicy;
import software.amazon.awssdk.services.s3.model.BucketCannedACL;
import software.amazon.awssdk.services.s3.model.CreateBucketConfiguration;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.DeleteBucketRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetBucketAclRequest;
import software.amazon.awssdk.services.s3.model.GetBucketAclResponse;
import software.amazon.awssdk.services.s3.model.Grant;
import software.amazon.awssdk.services.s3.model.Grantee;
import software.amazon.awssdk.services.s3.model.HeadBucketRequest;
import software.amazon.awssdk.services.s3.model.Permission;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.S3Client;

public class App {

  public static void main(String[] args) throws IOException {

    Region region = Region.US_WEST_2;
    S3Client s3 = S3Client.builder().region(region).build();

    String bucket = "jw-testbucket" + System.currentTimeMillis();
    String key = "WxTWBsdoAw4gRva64Ws7Ue050eiz1o";
    String acl = "public-read-write";

    tutorialSetup(s3, bucket, region, acl);

    System.out.println("Uploading object...");
    System.out.println(key);
    System.out.println(acl);
    

    s3.putObject(PutObjectRequest.builder().bucket(bucket).key(key)
            .build(),
        RequestBody.fromString("Testing with the {sdk-java}"));

    System.out.println("Upload complete");
    System.out.printf("%n");

    cleanUp(s3, bucket, key);

    System.out.println("Closing the connection to {S3}");
    s3.close();
    System.out.println("Connection closed");
    System.out.println("Exiting...");
  }

  public static void tutorialSetup(S3Client s3Client, String bucketName, Region region, String acl) {
    try {
      s3Client.createBucket(CreateBucketRequest
          .builder()
          .bucket(bucketName)
          .createBucketConfiguration(
              CreateBucketConfiguration.builder()
                  .locationConstraint(region.id())
                  .build())
          .acl(acl)
          .build());
      System.out.println("Creating bucket: " + bucketName);
      s3Client.waiter().waitUntilBucketExists(HeadBucketRequest.builder()
          .bucket(bucketName)
          .build());
      System.out.println(bucketName +" is ready.");
      System.out.printf("%n");
    } catch (S3Exception e) {
      System.err.println(e.awsErrorDetails().errorMessage());
      System.exit(1);
    }
  }

  public static void cleanUp(S3Client s3Client, String bucketName, String keyName) {
    System.out.println("Cleaning up...");
    try {
      System.out.println("Deleting object: " + keyName);
      DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder().bucket(bucketName).key(keyName).build();
      s3Client.deleteObject(deleteObjectRequest);
      System.out.println(keyName +" has been deleted.");
      System.out.println("Deleting bucket: " + bucketName);
      DeleteBucketRequest deleteBucketRequest = DeleteBucketRequest.builder().bucket(bucketName).build();
      s3Client.deleteBucket(deleteBucketRequest);
      System.out.println(bucketName +" has been deleted.");
      System.out.printf("%n");
    } catch (S3Exception e) {
      System.err.println(e.awsErrorDetails().errorMessage());
      System.exit(1);
    }
    System.out.println("Cleanup complete");
    System.out.printf("%n");
  }
}