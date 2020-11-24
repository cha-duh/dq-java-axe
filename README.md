# dq-java-axe
coding challenge for deque: axe-java integration

## Greetings
Thanks for taking the time to consider my application. If you have any questions, comments, or otherwise, please don't 
hesitate to reach out.

## Running dq-java-axe
`mvn test` should be sufficient to run this solution. If you have any issues, please contact me directly.

## Spoilers
`main-nav` loads fine - the requirements (reiterated below) suggest two separate test cases for these, but I
opted to combine them into a single test case with two Assertions. I believe this is more in line with expectations.

#### Java Axe Setup (original prompt)

For this challenge, you'll be taking our open source axe-java integration, used to run web
accessibility tests in Java projects with the Selenium browser automation tool, and adding it to a
Java testing project that you will create. This will require Maven.

Create the following:
   - A new Java project
   - Use J-Unit and Selenium Webdriver testing frameworks that reach out to the following
test URL: https://dequeuniversity.com/demo/mars
   - Create two (2) test cases:
        1. ensure the main-nav (CSS selector) has been loaded, and
        2. perform an accessibility scan of the page
   - Integrate axe-core-maven-html into the accessibility scan test case (the second bullet
above)
   - Return the results from the scan to the console. Bonus points for using assertions to
check violations.

You may use all publicly available resources to help you get this setup and running.
When you're done, please host the challenge on Github, Bitbucket, Gitlab or another hosted
version control, or send us a package we can stand up ourselves.
