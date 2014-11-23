Included in this Git repo is my implementation class exercising Gist API of GitHub service. The class will create a gist for the user with a pre-packaged readme file. It can also query for gists included for the specific user.
The Junit testcase will run tests against:
    executePostRequest (2) with bad message and good message
	ExecuteGetRequest
	isConnected (2) with bad credentials and good credentials
	
Prior to running you must have the following dependencies in place:
<dependency>
	<groupId>commons-codec</groupId>
	<artifactId>commons-codec</artifactId>
	<version>1.3</version>
</dependency>
<dependency>
	<groupId>commons-logging</groupId>
	<artifactId>commons-logging</artifactId>
	<version>1.2</version>
</dependency>
<dependency>
	<groupId>org.apache.httpcomponents</groupId>
	<artifactId>httpclient</artifactId>
	<version>4.4-beta1</version>
</dependency>
<dependency>
	<groupId>org.apache.httpcomponents</groupId>
	<artifactId>httpcore</artifactId>
	<version>4.4-beta1</version>
</dependency>
<dependency>
	<groupId>com.googlecode.json-simple</groupId>
	<artifactId>json-simple</artifactId>
	<version>1.1.1</version>
</dependency>
<dependency>
	<groupId>junit</groupId>
	<artifactId>junit</artifactId>
	<version>4.12-beta-3</version>
</dependency>	

Place all dependencies jars in folder according to lib.dir in ant script.
To execute within ant from project directory run ant test

Answers to questions:
Concerns in testing API:
Implementation of authorization method OAuth, 2-step verification or basic.
Which operations need to be supported?
Do we implement a time-out period and how long?

Approach in testing.
I would like to implement a test case tracking system such as, testlink to help track our test cases from version to version. It would also be helpful in showing the history of each test case over plans. 
I would use a bug tracking tools like Jira, to help track our bugs.
Then run unit tests in junit and functional tests in soapUI.

Test worth automating:
List a gist
Create a gist
Edit a gist
Delete a gist
Add a comment

These tests are worth automating since the represent very common actions that will have high execution rates by end-users. They are worth automating because they are easily automated via SoapUI or as unit tests and can be run quickly with no tester input.
