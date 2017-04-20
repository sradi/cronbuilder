# cronbuilder
library, that provides a fluent java api for the creation of cron strings

## simple cron strings

## cron strings for periods
If you want something to be executed from a certain point in time, until a later point in time, this can only be expressed by several cron strings.

# Examples
This cron definition runs each minute, from June 15th, 3:25pm until November 27th, 9:25pm:
```25-59 15 15 6 *
* 16-24 15 6 *
* * 16-30 6 *
* * * 7-10 *
* * 1-26 11 *
* 1-20 27 11 *
0-25 21 27 11 *
```
