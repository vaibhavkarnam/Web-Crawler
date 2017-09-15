INTRODUCTION:
------------
This is a Web Crawling application developed in JAVA.

PRE-REQUISITS:
-------------
JRE 1.8.101 or higher

EXTERNAL LIBRARIES USED:
-----------------------
jsoup-1.10.2

ITEMS INCLUDED IN THE ZIP FOLDER
--------------------------------
jsoup-1.10.2 external JAR which is used in our program.

Vaibhav_Web_Crawler windows installer which can be run on a windows system. No need to install JRE 1.8.121 if
you run the installer. 

WebCrawler folder which contains the text files with output URLs - 
Text files Task 1-E, Task 2-A, Task 2-B and Task 3 containing the URLs crawled.

Java source files for the program - WebCrawler.java

Executable JAR file Vaibhav_Web_Crawler

Text files Task 2-C and Task_3_explanation contating the explanations for TASK 2 and Task 3 respectively.

INSTRUCTIONS TO RUN:
-------------------
This is a platform independent applivation and can be run on Windows, Linux or Mac OS.
PLEASE USE THE LATEST VERSION OF JAVA JRE 1.8.101 or higher.

1. Windows Installer:
		- Run the Vaibhav_Web_Crawler.exe on your Windows machine.
		- Select the location to install the application. Click NEXT -> FINISH.
		- Open a cmd.exe as an Administrator.
		- Navigate to the Application install location.
		- Use the following command to run Vaibhav_Web_Crawler.jar:
					java -jar Vaibhav_Web_Crawler.jar
					
					
2. Running the JAR file on Windows, Linux and Mac OS	
		- Download and install JRE 1.8.101 or higher.
		- Use the following command on the terminal to run Vaibhav_Web_Crawler.jar:
					<<path_to_jre1.8>>/bin/java -jar Vaibhav_Web_Crawler.jar

Task 1
-------
Enter Task-number (1,2 or 3)  // After you run the JAR file you will be prompted with this 
1                            // Enter option 1 for task 1

Enter the web link to crawl
https://en.wikipedia.org/wiki/Sustainable_energy
 
crawled 1000 URls           // Crawling successful.


TASK 2
--------
Enter Task-number (1,2 or 3)  // After you run the JAR file you will be prompted with this 
2                            // Enter option 2 for task 2

Enter the Keyword to be matched
solar                        // Enter the keyword you want to match here


options:
1.Breadth First Crawling
2.Depth First Crawling
Select Option
1                      // For Breadth First Crawling. If you want Depth First Crawling enter 2 here.

Enter the web link to crawl
https://en.wikipedia.org/wiki/Sustainable_energy
 
TASK 3
------------
The results for this task will be appended to the text file for task 1. Please move the file for task 1
out of this folder so that a new file with name Task_1E will be created for this task. 

Enter Task-number (1,2 or 3)  // After you run the JAR file you will be prompted with this 
1                            // Enter option 1 for task 1

Enter the web link to crawl
https://en.wikipedia.org/wiki/Solar_power.

crawled 1000 URls           // Crawling successful.

RESULTS:
----------
A folder called WebCrawler is created.
Task_1E is the text file containing the links for TASK 1.
Task_2A is the text file containing the links for TASK 2-A which is Breadth First Crawling.
Task_2B is the text file containing the links for TASK 2-B which is Depth First Crawling.
for Task 3 results will be appended to file Task_1E or if that file has been moved, a new file
called Task_1E will be created for this task.
A file seed which contains the HTML for the URLs crawled.


CITATIONS:
----------------
https://jsoup.org/                            // used JSOUP library for fetching the html from the URLs
https://jsoup.org/cookbook/                   // used for information on retrieving data from html
https://jsoup.org/apidocs/                    // used for information on retrieving data from html
Search Engines Information Retrieval in Practice  by W.BruceCroft DonaldMetzler TrevorStrohman   // for web crawling concepts
An Introduction to Information Retrieval Christopher D. Manning Prabhakar Raghavan Hinrich Schütze // for web crawling concepts
http://www.mkyong.com/java/jsoup-basic-web-crawler-example/                    // Example for how to use jsoup in java
http://www.advancedinstaller.com/               // for creating the installer for the web crawler on windows system.
