# Parallel Web Crawler

## Project Overview

The Parallel Web Crawler is a Java project aimed at demonstrating the implementation of multithreading to efficiently crawl and process web pages concurrently. This application is designed as a part of the Udacity Front-End Web Developer Nanodegree to showcase advanced Java programming skills, focusing on concurrency, web data extraction, and multithreaded processing techniques.

### Features

**Concurrent Web Crawling:** Leverages Java threads to crawl multiple web pages in parallel, enhancing efficiency.
**Customizable Thread Pool:** Allows for the adjustment of the number of threads used for crawling, enabling performance tuning based on system capabilities.
**Link Extraction:** Parses HTML content to extract and follow links for further crawling.
**Data Processing:** Includes a basic framework for processing extracted data from web pages, which can be customized for various scraping needs.

#### Technologies Used

* Java
* jsoup (for HTML parsing)
* Project Structure

```
05_Parallel_Web_Crawler
│   README.md
└───src
│   │───main
│   │───test [Java test files]
│   
└───lib
    │   jsoup-1.13.1.jar [Jar file not included]
```

#### Getting Started

**Prerequisites**
* JDK 11 or later.
* An IDE that supports Java and Maven (e.g., IntelliJ IDEA, Eclipse).

#### Installation

Clone the repository to your local machine.
```
git clone https://github.com/amnotme/Udacity_FEND.git
```
* Navigate to the 05_Parallel_Web_Crawler directory.
* Import the project into your IDE as a Maven project.

#### Adding Dependencies

Ensure jsoup is added as a dependency in your pom.xml file for HTML parsing:

```
<dependency>
    <groupId>org.jsoup</groupId>
    <artifactId>jsoup</artifactId>
    <version>1.13.1</version>
</dependency>
```

#### Running the Application

* Build the project using Maven to resolve dependencies.
* Run Main.java to start the web crawler.
* The application is set to crawl predefined websites, but you can modify Main.java to target specific URLs or adjust concurrency settings.

#### Customization

To customize the web crawler for specific scraping tasks:

**Modify Target URLs:** Change the URLs in Main.java to specify which websites to crawl.
**Adjust Thread Count:** In Main.java, configure the number of threads in the thread pool according to your system's capabilities and the scale of your crawling task.
**Extend Data Processing:** Implement custom data processing logic within the crawling threads to extract and store specific information from web pages.
