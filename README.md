# java-clsrestapi
### Java Class Library for CLS REST API

Welcome to the Java Class Library for the CLS REST API. The CLS REST API is hosted on [http://api.cloudylogic.com](http://api.cloudylogic.com), and this class library provides you with a Java interface to access that API. If you have questions, or would like to provide feedback and/or to report a bug, feel free to contact the author, Ken Lowrie, at [www.kenlowrie.com](http://www.kenlowrie.com/).

#### Attributions

Attributions here...

<a id="install">&nbsp;</a>

### Layout of this Project

This project was built using NetBeans, and the source tree is a reflection of that. If you don't have/use NetBeans, it should be easy enough to integrate into whatever your particular Java environment happens to be (hopefully). Please review this documentation to get a better understanding of what's here, especially if you plan on using something other than NetBeans to consume it.

This project consists of two packages:

1. clsrestapi package - this is the Java class library for the CLS REST API
3. test/clsrestapi package - this is a set of JUNIT test classes for the clsrestapi package

#### clsrestapi Package

The clsrestapi Package provides a set of classes for Java developers to use for accessing the CLS REST API.

#### The test/clsrestapi JUNIT test classes

These are JUNIT v4 tests integrated into the NetBeans project, and are executed like any other JUNIT test inside the NetBeans IDE. Refer to their documentation for more details on that. These tests exercise the underlying class library to ensure it is working properly.

### Why a REST API for the Cloudy Logic App?

This section is probably best explained by simply reviewing the server side documentation for CLS REST. The source code and documentation for this is available in [this](https://github.com/kenlowrie/clsrestapi) GitHub repository.

#### The clsrestapi Java Package

The [CLS REST API server side](https://github.com/kenlowrie/clsrestapi) is written in PHP, and uses [JSON](http://www.json.org/) encoding to expose the data associated with each API call. The clsrestapi Java Package contains a set of classes that expose the server APIs to Java clients. The available classes are:

1. [clsrestapi.Versions](#versions) - Returns the versions of a specific (or all) API(s). 
2. [clsrestapi.AboutUs](#about-us) - Returns a text description of what Cloudy Logic Studios does.
3. [clsrestapi.ContactInfo](#contact-info) - Returns contact information for Cloudy Logic.
4. [clsrestapi.Reels](#reels) - Returns information about demo reels including a streaming URL for each demo reel.
5. [clsrestapi.OurWork](#our-work) - Returns information about select video projects that showcase the company.

The API relies on Google's [Gson 2.8.1 library](https://mvnrepository.com/artifact/com.google.code.gson/gson/2.8.1), and also relies on Java's built-in URL class. Everything thing else is built using the Java v8 built-in libraries.

Each high-level class in the library has three (3) objects contained within it:

1. DbgObj() - Some debugging information about the API call
2. ApiVer() - The version related information abou the API call
3. ApiObj() - This object is API-specific; that is, each API defines a custom object to hold information related to that call.

<a id="dbgObj"></a>
##### clsrestapi.DbgObj Class

The DbgObj class is common to all API calls, and contains information about the network request. 

<a id="apiVer"></a>
##### clsrestapi.ApiVer Class

The ApiVer class is common to all API calls, and contains information about the version of the API, as well as the data version returned by the call. 

<a id="versions"></a>
##### clsrestapi.Versions Class

This class is used to determine the version number of a specific API or all APIs.

<a id="about-us"></a>
##### clsrestapi.AboutUs Class

This class is used to return some general information about Cloudy Logic.

<a id="contact-info"></a>
##### clsrestapi.ContactInfo Class

This class is used to obtain the contact information for Cloudy Logic, including the various social networks that the business maintains a presence on.

<a id="reels"></a>
##### clsrestapi.Reels Class

This class is used to obtain information about demo reels that the company has produced. It can return all demo reels, or a specific one, depending on how it is called.

<a id="our-work"></a>
##### clsrestapi.OurWork Class

This class is used to obtain information on showcase videos that highlight what the company has done in the past. It can the details for a specific showcase video, or all videos currently being promoted by the company.


#### Javadocs

The clsrestapi package contains full Javadocs for the API, and you are encouraged to use that to learn more about each specific API. This README file is not intended to replace the API documentation, so you need to review that for more detailed information about using the API.

### Summary

This concludes a summary of the Java class library for the CLS REST API.

