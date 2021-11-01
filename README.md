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
Month |   | 1-12 or JAN-DEC (note: english abbreviations) |   | , - * /
Day of week |   | 1-7 or MON-SUN (note: english abbreviations) |   | , - * /
Command |   | User command ex. /usr/bin/find |  

'*' Can be used in all fields and means 'for all values'. E.g. "*" in minutes, means 'for all minutes'

'-' Used to specify a time interval. E.g. "10-12" in Hours field means 'for hours 10, 11 and 12'

',' Used to specify multiple values for a field. E.g. "MON,WED,FRI" in Day-of-week field means "for monday, wednesday and friday"

'/' Used to specify increments. E.g. "0/15" in Minutes field means "for minutes 0, 15, 30, ad 45". And "5/15" in minutes field means "for minute 5, 20, 35, and 50". If '*' s specified before '/' it is the same as saying it starts at 0. For every field there's a list of values that can be turned on or off. Minutes these range from 0-59. For Hours from 0 to 23, For Day-of-month it's 1 to 31, For Months 1 to 12. "/" character helps turn some of these values back on. Thus "7/6" in Months field specify just Month 7. It doesn't turn on every 6 month following, since cron fields never roll over

**Case-sensitive** No fields are case-sensitive

**Dependencies between fields** Fields are always evaluated independently, but the expression doesn't evaluate until the constraints of each field are met. Overlap of intervals are not allowed. That is: for Day-of-week field "FRI-MON" is invalid,but "FRI-SUN,MON" is valid

