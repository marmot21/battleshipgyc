# Introduction #

This article is here to provide a project vision for development and progress.


# What the game should do #

The user should open the game and be greeted with the main menu which will consist of:

  * Music playing
  * An animating background - not scrolling
  * Buttons that animate when the mouse moves over them
  * A title which has little pictures of ships in it
  * Single player and multiplayer buttons
  * Options to change:
    1. The size of the game (grid)
    1. Number of ships - which will be bases on the size
The user will be able to easily tell how to start a game and leave one.

##### When a game is started: #####

The user will see their status screen and will be able to add ships to it with a drag and drop interface. It will be apparent how to play the game - but a help button is provided anyway. Also present will be:
  * Different music to main menu - apprehensive
  * A similar background to the main menu
  * A list of available ships and quantities
  * The status of the other player
    * Finished placing ships - waiting for you
    * Still placing ships
  * Information about each ship
    * Number of hits required to sink (same as how many grid spaces it uses)
    * Number of points the ship is worth

When user has finished placing ships, a button to start the game will become active. Upon starting the game the users take it in turn to bomb each other - until all ships of one player have been sunk. Also music changes - battle music

When bombs are dropped an animation of water splashing, where the bomb dropped, is played as well as a sound.

_When in single player mode:_

> The computer randomly places its own ships. When dropping bombs a FuSM is used to determine the most likely place where a ship could be - adjacent to previous hit - and uses weights to determine if this should be hit or another random spot.