# Victor's MBTA Analyzer

This project is a preliminary assessment for Broad Institute. The project interacts with the
MBTA API, retrieves subway data, and performs calculations and analysis on it. The program
was coded over the course of four days and about 7 hours of work.

### How to use / What is what

Each Question class has a main() method which prints its answer to System.out. Since the questions
are cumulative, lower Question classes call the methods in higher Question classes first before
producing their own output. In the case of Question 3, which requires user input of two station names,
static string variables are listed at the top of the class which can be set as the user wishes.

All the other classes in /src are for the representation of subways and communicating with the MBTA
website. In the /src/model package, classes for Routes, Stops, and the overarching SubwayMap are defined
with relevant methods. In the /src/api package, the class Link provides a static method which returns the
Boston subway system in the form of a SubwayMap.

The /test package contains JUnit tests for each of the model classes. All the methods as well as constructors
are tested. Corrupt/invalid inputs are also tested in the form of expected error tests.

### Approximate timeline
Despite knowing the fundamentals of HTTP interactions and their components, this is my first
time handling HTTP client code from a programming standpoint, so the majority of my time was
actually spent learning how to handle HTTP requests and responses in Java. Nonetheless, I was able
to produce a (hopefully) fully-working project with no imported libraries or package downloads
necessary. The approximate timeline was as follows:
- 2 hrs: Create model classes, create model methods, test model methods, implement model methods
  - Methods should provide enough utility for all Questions
- 3 hrs: Learn the basics of creating HTTP requests and responses in Java
  - Set up Swagger documentation
  - Correctly set the parameters passed in the request link (i.e. filtering stops by route)
- 1 hr: Create Question classes, implement main() methods, fix bugs found during usage
- 1 hr: Improve documentation project-wide, add tests, prettify code
- 15 minutes: Write README.md

Any longer in the above timeline and I'll mess up reality's time-space in some capacity, so
that's it for the README! If there are any questions/concerns, please don't hesitate to reach out
to me via email.
