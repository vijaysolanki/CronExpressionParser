# CronExpressionParser


Parser for unix-like cron expressions: Cron expressions allow specifying combinations of criteria for time such as: "Each Monday-Friday " or "1,2,3 days of month" or "every 5 minutues starting from 20" or "Every last friday of the month " etc.

A cron expressions consists of 6 mandatory fields separated by space. 
These are:

Field |   | Allowable values |   | Special Characters
-- | -- | -- | -- | --
Minutes |   | 0-59 |   | , - * /
Hours |   | 0-23 |   | , - * /
Day of month |   | 1-31 |   | , - * / L W
Month |   | 1-12 or JAN-DEC  |   | , - * /
Day of week |   | 1-7 or MON-SUN |   | , - * / L #
Command |   | User command ex. /usr/bin/find |  

  <P><br>
  '*' Can be used in all fields and means 'for all values'. E.g. &quot;*&quot;
  in minutes, means 'for all minutes'
  <P> <br>
  '-' Used to specify a time interval. E.g. &quot;10-12&quot; in Hours field
  means 'for hours 10, 11 and 12'
  <P><br>
  ',' Used to specify multiple values for a field. E.g. &quot;MON,WED,FRI&quot;
  in Day-of-week field means &quot;for monday, wednesday and friday&quot;
  <P><br>
  '/' Used to specify increments. E.g. &quot;0/15&quot; in Minutes field means
  &quot;for minutes 0, 15, 30, ad 45&quot;. And &quot;5/15&quot; in minutes
  field means &quot;for minutes 5, 20, 35, and 50&quot;. If '*' s specified
  before '/' it is the same as saying it starts at 0. For every field there's a
  list of values that can be turned on or off. For Minutes these
  range from 0-59. For Hours from 0 to 23, For Day-of-month it's 1 to 31, For
  Months 1 to 12. &quot;/&quot; character help turn some of these values back
  on. Thus &quot;7/6&quot; in Months field specify just Month 7. It doesn't
  turn on every 6 month following, since cron fields never roll over
  <P><br>
  'L' Can be used on Day-of-month and Day-of-week fields. It signifies last day
  of the set of allowed values. In Day-of-month field it's the last day of the
  month (e.g.. 31 jan, 28 feb (29 in leap years), 31 march, etc.). In
  Day-of-week field it's Sunday. If there's a prefix, this will be subtracted
  (5L in Day-of-month means 5 days before last day of Month: 26 jan, 23 feb,
  etc.)
  <P><br>
  'W' Can be specified in Day-of-Month field. It specifies closest weekday
  (monday-friday). &quot;15W&quot; in
  Day-of-Month field means 'closest weekday to 15  in given month'. If the
  15th is a Saturday, it gives Friday. If 15th is a Sunday, the it gives
  following Monday.
  <P><br>
  '#' Can be used in Day-of-Week field. For example: &quot;5#3&quot; means
  'third friday in month' (day 5 = friday, #3 - the third). If the day does not
  exist (e.g. &quot;5#5&quot; - 5th friday of month) and there aren't 5 fridays
  in the month, then it won't capture until the next month with 5 fridays.
  <P><br>
  <b>Case-sensitive</b> No fields are case-sensitive
  <p>
  <b>Dependencies between fields</b> Fields are always evaluated independently,
  but the expression doesn't match until the constraints of each field are met.
  Overlap of intervals are not allowed. That is: for Day-of-week field
  &quot;FRI-MON&quot; is invalid,but &quot;FRI-SUN,MON&quot; is valid


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
* After the build is done excute the expression parser with followings:

    `./CronExpressionParser.sh <cron expression>`
      
 OR  
* With deliveroo.jar created after ./build.sh and will be available in `build/libs` directory:
      
   `java -jar {path}/deliveroo.jar <cron expression>`

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

![image](https://user-images.githubusercontent.com/20536693/140689723-4a8430cc-cd73-4ca2-8a4c-96061a86cb57.png)

![image](https://user-images.githubusercontent.com/20536693/140689850-bd2db7cd-f496-46ed-912f-f905d8550e32.png)

![image](https://user-images.githubusercontent.com/20536693/140689908-75c14b91-7c54-4cc5-aeb3-141437dd2199.png)


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

![image](https://user-images.githubusercontent.com/20536693/140690167-26bd4b24-5a8b-471f-b672-fd7def7aa3b9.png)

![image](https://user-images.githubusercontent.com/20536693/140690219-2ad34502-deae-46f1-9e9d-f0f161c41d6b.png)

![image](https://user-images.githubusercontent.com/20536693/140690359-5ba89d24-de25-4601-8121-e32b03e8a874.png)

## Test Coverage

![image](https://user-images.githubusercontent.com/20536693/140690778-23bdb48a-3c68-460f-b29f-872b6febb622.png)



**Full report screenshot below:**
      
![image](https://user-images.githubusercontent.com/20536693/140690878-4bcf16aa-8ca8-45e5-8cb1-affa9fea7420.png)




-------------------------------------------------------------------------------------------------------------------
# Future Enhancement
- ? (Ignore) special characters.


# References
- [A Guide To Cron Expressions](https://www.baeldung.com/cron-expressions)
- [A Guide To Java Regular Expressions API](https://www.baeldung.com/regular-expressions-java)
- [Cron Expression Generator & Explainer - Quartz](https://www.freeformatter.com/cron-expression-generator-quartz.html)
- [Regular Expression ](https://regex101.com/r/vH8gX6/3)
