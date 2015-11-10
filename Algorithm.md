## General ##

**Initially**
  * Create a FiniteStateMachine (FSM)
  * Create two EventManagers (EM) - one for input and one for general events
  * Set up and create the window
  * Add states "Generic", "Menu" and "Game" to FSM
  * Create a new thread, t
  * Start aforementioned thread
  * Set the current state to "Menu"
  * Add FocusListener
    * When focus is gained - add redraw event
  * Add ComponentListener
    * When window re-enters screen add redraw event

**While thread 't' is running:**
  * Flush events of input events
  * Add input EM to general EM
  * 'run' the current state
  * Send all events to the current state
  * Get all the events from the current state
    * _If the 'setState' event is called_
      * Set the current state to specified event
    * _Else if the 'repaint' event is called_
      * Paint the current state to screen
  * Sleep

## States ##
**When state is 'run'**
  * Update all GameObjects in said state

**When paint is called**
  * Clear the screen
  * Paint all GameObjects to the screen

**When getEvents is called**
  * Get events from all GameObjects in said state
  * Return them
  * Clear events

**When pumpEvents is called**
  * Check all events to see state specific ones
  * 'pump' the events to all GameObjects

## Generic State ##
Does nothing.

## Menu State ##
**Initially**
  * Load and position Buttons on screen
  * Load and position the main title on screen
  * Add event to repaint

**When the state is entered**
  * null

**When the state is exited**
  * null

**When the mouse is moved or dragged**
  * _If mouse is over a Button_
    * _If the state of the Button is NORMAL_
      * Change the state of the Button to HOVER
      * Change the image of the Button to suit the state
      * Add event to repaint
    * _Else if the state of the Button is ACTIVE_
      * Change the state of the Button to PRESSED
      * Change the image of the button to suit the state
      * Add event to repaint
  * _Else_
    * _If the state of the Button is HOVER_
      * Change the state of the Button to NORMAL
      * Change the image of the Button to suit the state
      * Add event to repaint
    * _Else if the state of the Button is PRESSED_
      * Change the state of the Button to ACTIVE
      * Change the image of the Button to suit the state
      * Add event to repaint

**When a mouse button is pressed**
  * _If mouse is over a Button and it's the left mouse button_
    * Change the state of the Button to PRESSED
    * Change the image of the Button to suit the state
    * Add event to repaint

**When a mouse button is released**
  * _If the mouse button pressed is the left mouse button_
    * _If the mouse is over a Button which has the state of PRESSED_
      * Change the state of the Button to PRESSED
      * Change the image of the Button to suit the state
      * Add event to repaint
      * Add event that the button has been pressed
    * _Else_
      * Change the state of the Button to NORMAL
      * Change the image of the Button to suit the state
      * Add event to repaint

**When a Button has been clicked**
  * _If the "HostGame" Button was clicked_
    * Add event to set the state to "Game"
    * Add event saying the mode is "Host"
  * _If the "JoinGame" Button was clicked_
    * Add event to set the state to "Game"
    * Add event saying the mode is "Jost"
  * _If the "SinglePlayer" Button was clicked_
    * Add event to set the state to "Game"
    * Add event saying the mode is "Single"

## Game State ##
**Initially**
  * Create two grids, or 'playfields'
  * Add event to repaint

**When Image is clicked:**
  * Store position of ship
  * _While mouse is being dragged_
    * Set position of ship to mouse position
    * Draw ship to screen buffer

**When mouse is released**
  * Check position of ship
    * f position is outside of grid then move back to old grid position
  * Else
    * check if ship will be over another ship
    * check if ship will be over edge of grid
      * If false then snap ship position to grid
    * Else return to previous position