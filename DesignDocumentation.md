# Design Documentation
***Snail Impact’s technical implementation details and reasoning behind design decisions.***



## Language & IDE 

#### Java

​	We decided to use Java because there is an API specifically for creating Discord bots in Java called the Java Discord API (JDA).  We also wanted to use the techniques and concepts we learned throughout the quarter to create something fun. The JDA provides many methods and classes that allows developers to customize commands and messages. While the JDA had a lot of cool features that involved the voice chat and video; we stuck with chat commands and messages as  our vision for Snail Impact was for it to be something easy to access and use. 

#### Eclipse

​	We chose Eclipse as our IDE instead of the ED Workspace because the JDA required .JAR file dependencies that we did not have space to download on the ED Workspace. There were options in the JDA documentation to download these dependencies through Maven, but we found it easiest to use a feature on Eclipse that let us use external .JARs in the Java Build Path option. Eclipse also organizes the Java files by class and packages, making the structure of our project easy to understand.

## Design Decisions

​	Instead of putting all the code into one Main class, we decided to put relevant commands into their own classes. The whole project contains 5 classes; Main, BotInfo, CurrencySystem, Roll, and UserInfo. Main creates and builds the bot itself, and then calls on the other classes. 

​	When trying to implement the currency and prize systems, we thought that Maps would be the best way to store points, cool down timers, and prizes. JDA provides a method that returns the member ID of the user who sent the command, which we used as the keys in our maps. Currency system had two maps, one for storing the amount of currency a user had, and one for storing the cool down timer for the ```!getPoints``` command. The Roll class only had one map that stored the prizes for each user. Since the currency system and the roll system were the most complex parts of the project, we kept them in separate classes and had other classes extend them to access things like the player currency map or the player prize map. Roll would extend CurrencySystem to subtract points from the same user currency map whenever that user rolled for a prize. UserInfo would extend Roll so it could access both the prizes of a user and the currency balance. We could have put all of these into one class instead of extending to each other, however this way classes were organized and we could still keep data changes persistent.

​	We wanted to keep commands simple and accessible. To do this we decided that each command would be prefixed by an "!", which is also the default prefix for other commands on discord. Each command is pretty self explanatory and provides some error handling if a user tries to use the command incorrectly. 



