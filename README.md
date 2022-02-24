# Fortify scanning of Amazon Web Services (AWS) SDK (Java)

This is an example project for the demonstration of Fortify vulnerability scanning of a Java application using the [Amazon Web Services (AWS) SDK](https://aws.amazon.com/sdk-for-java/). 

The application is based on the code in the "[Get started with the AWS SDK for Java 2.x](https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/get-started.html "Get started with the AWS SDK for Java 2.x")" documentation.

The only addition is that during AWS bucket creation, the [access control list (ACL)](https://docs.aws.amazon.com/AmazonS3/latest/userguide/acl-overview.html "access control list (ACL)") is set to `"public-read-write"`, granting full anonymous access.

After a scan with the latest Fortify Static Code Analyzer rulepacks, this vulnerability shows up in the `Fortify Project Result (.fpr)` file. A sample scan result is also included in the project. More information about the potential vulnerability can be found [here](https://vulncat.fortify.com/en/detail?id=desc.semantic.java.insecure_storage_s3_full_anonymous_access#Java%2fJSP "here").
