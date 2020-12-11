# Snail Impact User Guide
Snail Impact is a Discord bot inspired by the gacha game system. A player pays a specific amount of points to “spin” the gacha,
and then receives a random character with different rarity levels. Don’t worry, we won’t actually make you spend real money on
waifus/husbandos. This bot will let Discord users join the game through inputted commands to gain points, spend those points on rolls,
and keep track of their roster & character stats. 

## Commands
*default prefix is !*

**!info**
Responds with the information on what Snail Impact is about

**!cd**
Let's the user know the cool down time for commands

**!points**
Tells the user how much points they have

**!getPoints**
Gives the user X amount of points set

**!roll**
User rolls for a character spending X amount set

**!mySnails**
Shows the user their roster of characters they rolled overall

**!userInfo @(username)**
shows the the mentioned user's prizes and their current balance

# Running the Program
Since we are using Discord as our server, all commands will need to be made on the Discord app for the bot to work. 

Starting up the bot is as simple as downloading the the bot and running the Main program. Something may need to be done prior to running the bot is
adding the JDA jar file to the Java class build path. You should also change the <TOKEN> in Main to our given token.

Some values that can be changed for the bot are the PREFIX value to the desired prefix you want to use for commands, the TIMER value to set the cooldown
timer, the POINTS value to set the amount of points gained from using !getPoints, and the COST value to change the cost to use !roll.

After running, the bot should now appear online. You should now be able to use the commands. Typing !info will cause the bot to message back info about itself.
!cd will display the the current cooldown before they can use !points again. !points will message back how many points the user currently has. !getPoints gives the user
a set amount of points to use for !roll. !roll will spend a set amount of accumulated points in return for a random character, and !mySnails will display all of the 
characters that the user has rolled so far.

To cause the bot to go offline, all you have to do is stop running the program. This however, does cause all of the values stored to be reset.

## Video Presentation