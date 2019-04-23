# cloud-api-sdk
Client and SDK for getting access to Real ID Cloud API

# About Real ID Cloud
Real ID Cloud, or [Real ID eKYC Cloud][1], is one of the products launched by © Real ID Pte Ltd. It aims to provide the technology businesses need to perform faster, cheaper and secure identity verification on corporate customers. 

# About This Project
This project is a client for calling Real ID Cloud API as well as an SDK for implementing the Cloud API protocol. It provides a simple and extensible way for developers to perform identity verification tasks. The project is written in Java, so it can be used in all JVM-based language.

# Before Writing Codes
To use the API, you need to be a registered client of [Real ID Cloud][2], and you might need to recharge since there is only 1.0$(coupon) for every new client to try the APIs for identity verification and data processing.  

# Developer's Guide
1. Managing dependencies  
    The project has been released to Maven Central Repository, so you can use your prefer tool(based on Maven) to manage dependencies.  
    
    * Apache Maven   
    Add the snippet below to the `<dependencies>` section in the POM file:
        ```xml
        <dependency>
           <groupId>global.realid.cloud</groupId>
           <artifactId>cloud-api-sdk</artifactId>
           <version>0.0.1</version>
         </dependency>
        ```  
    
    * Gradle Groovy DSL  
    Add the snippet below to the `dependencies` seciton of `build.gradle` file:
        ```
        implementation 'global.realid.cloud:cloud-api-sdk:0.0.1'
        ```
    
    * More...  
    For more information about dependency management, please visit [the maven search result page][3].  


2. Writing client codes  
    It's quite easy to write codes to build a client object that wraps the API calls. We provide `Builder` interface to help you configure the necessary properties.

    * Client Using Access Key(simple but less secure)  
    The simplest way to use a client using access key would be like this:
        ```Java
        // build a client instance
        RealIdCloudApiClient apiClient = RealIdCloudApiClientBuilder.createUsingAccessKey()
            .httpProvider(new ApacheHttpClientHttpProvider())
            .jsonProvider(new FastjsonJsonProvider())
            .accessKey("YOUR_ACCESS_KEY")
            .secretKeyOfBase64("YOUR_SECRET_KEY")
            .build();
        // invoke methods of apiClient to perform API calling
        // ...
        ```  
        * The code above first create a `Builder` for configure access key and other related properties such as secretKey and HMAC algorithm.   
        * The `httpProvider` method specifies an instance of `global.realid.cloud.sdk.provider.http.HttpProvider` that handles HTTP requests, and the `jsonProvider` method specifies a `global.realid.cloud.sdk.provider.json.JsonProvider` instance that perform conversion between JSON strings and Java instances. `ApacheHttpClientHttpProvider` and `FastjsonJsonProvider` are the out-of-box implementation of the two interfaces(though they import a bunch of other dependencies that you might not expect), but you can replace with your own by implementing `HttpProvider` and `JsonProvider` interface and then specify them to the `Builder`.  
        * The `accessKey` method specifies the access key, and `secretKeyOfBase64` or `secretKey` method specifies secret key. They both can be queried on the [Real ID Cloud Console][2] website. 

    * Client Using Digital Certificates  
    Under testing, it will be available soon.

3. More...  
    For more information, please visit [developer section of our website][4]

# Contact Us
Developer: [Benjamin][mailto]  
General Enquiries: <info@realid.global>  
Technical Support: <support@service.realid.global>


[1]: https://www.realid.global/product-2.html "eKYC Cloud"
[2]: https://cloud.realid.global "Cloud Console"
[3]: https://search.maven.org/artifact/global.realid.cloud/cloud-api-sdk/0.0.1/jar "Maven Central Repository Search"
[4]: https://cloud.realid.global/developer "Cloud Console - Developer"
[mailto]: mailto:yujunbin@yiqiniu.com "Benjamin"