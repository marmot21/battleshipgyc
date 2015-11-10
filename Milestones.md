## Introduction ##

It might be a bit confusing looking at all the milestones that I have added (all 5 of them) so I have decided to offer a bit of an explanation of these and other stuff.


## Details ##

##### List of Milestones Overview #####

  * EndofExams
  * 1stWeekGUI
  * guiFunctionality
  * algorithm
  * Release1.0
  * Release2.0
Release2.0 will incorporate multiplayer functionality and will build on the game based on user feedback.

### In depth ###

##### EndofExams #####

Tasks listed here are basically those which we want to have done by Monday the 3rd. This should include:

  * Finishing the main menu
  * Adding the title
  * Finalising the design plan for the game
    1. Pics to use
      * Whether to make said pics or to get them from Google
    1. Choose fonts to use
    1. Decide on where everything will go (positions of everything)

##### 1stWeekGUI #####

Milestone for the 7/8/09. This will be the _Pre-guiFunctionality_ version, from here it will only be a small step to achieve the _guiFunctionality_ milestone.

  * The logic side of the **attack window** finished
    * User able to drag ships/add them etc.
    * Does not include the computer storing them as it will in Release1.0
  * Design for ships and mouse cursors finished
  * Be able to transmit info between computers, use seperate upload point for this so it can be reviewed - won't be included into main project untill later

The **attack window** will utilise another FSM which will control things such as dropping bombs, positioning ships, and displaying information such as:
  * Whether ships have been bombed or sunk
  * What ships of the enemy have been hit/sunk - ships that have not been hit will not be displayed.


##### guiFunctionality #####

This milestone should be reached by the end of the weekend after the milestone **1stWeek**, i.e. 9/8/09. This will include:
  1. Implementing Ship pictures
  1. Touching up the graphics
    * Positions of the buttons
    * Minor edits to _titlebar_ and buttons
  1. Implementing the moving background
    * Will be moving water of some sort
    * The background will be decided upon in the _EndofExams_ milestone
  1. Different fonts will be implemented
  1. Get reviews on what the game looks like (I might ask the INTJ-list)

##### Algorithm #####

This is where new issues go before development begins, the algorithm will be updated using a Wiki article, so that we can both edit and add to it. This milestone can be considered an intermediate step to reaching a specific goal. I have decided that the Algorithm goal must be added to/edited within 24hrs of adding an update, but it would be better if we did this before adding said update. You can use _awaiting_ if you are writing the algorithm for an issue.


##### Release1.0 #####

Release 1.0 will consist of basic player functionality but will not have networking functionality included (multiplayer) - but it will have networking framework included not visible to user.

Things it will have:
  * Clicking on Host/Join game will display a **not yet in use** dialog
  * User can add ships and play the game
  * Computer will play with the user (single player)
    * Will randomly choose ship locations for itself and will randomly drop bombs, but it will 'learn' where ships are and use a FuSM to drop bombs once a ship has been found (hit)
    * Ships will take more than one hit to sink them
  * Scoring
    * Based on both number of hits and ratio between hits and misses
Incorporating networking will be done in a later release - Release2.0