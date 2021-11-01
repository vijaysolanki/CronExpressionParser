# CronExpressionParser


Parser for unix-like cron expressions: Cron expressions allow specifying combinations of criteria for time such as: "Each Monday-Friday " or "1,2,3 days of month"
or "every 5 minutues starting from 20" etc.

A cron expressions consists of 6 mandatory fields separated by space. 
These are:

Field |   | Allowable values |   | Special Characters
-- | -- | -- | -- | --
Minutes |   | 0-59 |   | , - * /
Hours |   | 0-23 |   | , - * /
Day of month |   | 1-31 |   | , - * /
Month |   | 1-12 or JAN-DEC  |   | , - * /
Day of week |   | 1-7 or MON-SUN |   | , - * /
Command |   | User command ex. /usr/bin/find |  

'*' Can be used in all fields and means 'for all values'. E.g. "*" in minutes, means 'for all minutes'

'-' Used to specify a time interval. E.g. "10-12" in Hours field means 'for hours 10, 11 and 12'

',' Used to specify multiple values for a field. E.g. "MON,WED,FRI" in Day-of-week field means "for monday, wednesday and friday"

'/' Used to specify increments. E.g. "0/15" in Minutes field means "for minutes 0, 15, 30, ad 45". And "5/15" in minutes field means "for minute 5, 20, 35, and 50". If '*' s specified before '/' it is the same as saying it starts at 0. For every field there's a list of values that can be turned on or off. Minutes these range from 0-59. For Hours from 0 to 23, For Day-of-month it's 1 to 31, For Months 1 to 12. "/" character helps turn some of these values back on. Thus "7/6" in Months field specify just Month 7. It doesn't turn on every 6 month following, since cron fields never roll over

**Case-sensitive** No fields are case-sensitive

**Dependencies between fields** Fields are always evaluated independently, but the expression doesn't evaluate until the constraints of each field are met. Overlap of intervals are not allowed. That is: for Day-of-week field "FRI-MON" is invalid,but "FRI-SUN,MON" is valid

--------------------------------------------------------------------------------------------------------------------------------------------------------
# Tools and Technologies
A Java project for Deliveroo Cron Expression. The project is built with following:
* Openjdk 11 
* Build tool: Gradle 7.2
* IntelliJ IDEA 2021.2.3

## Howto's:

### Build the project: 
* Checkout the project.
* Build the project with build script compose gradle tasks: 
    
    `./build.sh`

### Execution:
* After the build is done excute the expression parser with following:

    `./CronExpressionParser.sh <cron expression>`

## Supported Expression Syntex: 

Following are the cron expression syntex which are supported. 
### Minutes:
Minutes against which cron job will execute.
  
**example**


![image](https://user-images.githubusercontent.com/20536693/139684944-9391db86-8319-473a-92f0-8fc294c7e609.png)


### Hour:
Hour against which cron job will execute.

**curl example**


![image](https://user-images.githubusercontent.com/20536693/139685017-eb257072-7242-428f-b020-f5d91a2d5cd2.png)


### Day of month:
Days on month when cron job will execute.

**curl example**


![image](https://user-images.githubusercontent.com/20536693/139685068-b83b5af0-a2dc-44ed-94b8-4fecd5b3cbc4.png)


### Month:
Months when cron job will execute.

**curl example**


![image](https://user-images.githubusercontent.com/20536693/139685225-bfef98fe-aec2-418f-b219-e5185d048dea.png)


![image](https://user-images.githubusercontent.com/20536693/139685282-76340b2a-204c-4896-8f62-3f98367a1224.png)


### Day of week:
Days of week when cron job will execute.

**curl example**


![image](https://user-images.githubusercontent.com/20536693/139685343-8740ad43-c6ae-4d48-af06-88b4cc30ad10.png)


![image](https://user-images.githubusercontent.com/20536693/139685404-425e57e9-c617-4904-beec-d202c16ecddf.png)


## Test Coverage

![image](https://user-images.githubusercontent.com/20536693/139686517-a5d5e6d1-4931-454c-b52e-1c6ae240eb63.png)


**Full report screenshot below:**

![image](https://user-images.githubusercontent.com/20536693/139687700-79a3c8ea-65d3-489c-997e-a16979d2eba1.png)


-------------------------------------------------------------------------------------------------------------------
# Future Enhancement
- Support for L (last), W (Weekday), ? (Ignore) and # (“N-th” occurrence)  special characters.


# References
- [A Guide To Cron Expressions](https://www.baeldung.com/cron-expressions)
- [A Guide To Java Regular Expressions API](https://www.baeldung.com/regular-expressions-java)
- [Cron Expression Generator & Explainer - Quartz](https://www.freeformatter.com/cron-expression-generator-quartz.html)
- [Regular Expression ](https://regex101.com/r/vH8gX6/3)
