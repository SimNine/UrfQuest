<!-- markdownlint-disable-file MD024 -->

# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/).

This project **does not** adhere to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).


## [Unreleased]

- Nothing yet

## [0.19.0] - 2022-08-11

### Added
- Multiplayer
	- Separated game into client and server components, each of which can be run independently
	- Multiple clients can connect to a single server
- Created "Logger" logging management framework
	- There are multiple log levels; ALL, DEBUG, VERBOSE, ERRROR, etc
- Created static class for constants that are shared between the client and the server
- Added startup GUI for selection of client or server role, port, client name
- Added chat overlay, through which a client can send arbitrary chat messages to other clients
- Added commands and parsing of command messages
	- /me, speaks a chat message in third person
	- /tp, teleports issuing playing to given postion
	- /give, gives issuing player the given item
	- several more
- Added integration tests of client/server features
- Added mock interfaces for client/server

### Changed
- The client now scans for keys each tick rather than listening for key presses and releases
- Manual print statements have been replaced with Logger.log statements
- IDs are now assigned to every entity
- IDs are now generated by an incremental value, rather than being randomly generated
- Map, entity position, and all randomly generated features are now based off of a seed 

## [0.18.0_pre4] - 2017-09-16

### Added
- Added a subtype of grass: grass_flower
- Added preliminary "winter mode" - purely aesthetic so far
- Added new GameWeatherOverlay
	- functions as a visual filter applied overtop of the board
	- currently adds a blue tint to the board and projects snowflakes
	- drawback: adding this overlay considerably drops framerate

### Changed
- Changed magnetic pickup to have a range specific to each player
- Redid tile display system
	- Tile images are now stored in a 3d array: `[type][subtype][animStage]`
	- "Type" is the overall type of the tile; all tiles of a type have the same collision properties
	- "Subtype" affects the appearance of a tile, and what items it drops
	- "animStage" represents all the frames
- Moved the render ticker back to having a delay of 5ms between renders

## [0.18.0_pre3] - 2017-02-23

### Added
- Added ambient blockchanges; done by helper method that executes during QuestMap's update()
- Added new setblock command:
	- Syntax: setblock x y type
- Added magnetic item effect: items are now drawn towards all players within a certain radius
- Added item pickup delay: items can only be picked up after a certain number of ticks (500) of being on the ground
- Added a 'held' item: this refers to an item that is held by the mouse pointer this feature will be more useful once more/better crafting GUIs are implemented

### Changed
- Minimap now updates immediately once a tile is changed
- MapLink has been generalized to ActiveTile
- QuestMap now contains a grid of "ActiveTile"s (most of which are null) that will contain the locations on-grid of MapLinks, Chests, Furnaces, and all interactive tiles

### Fixed
- Fixed bug causing the game to crash when digging into a cave
- Fixed a bug that allowed two players to pick up the same one item

## [0.18.0_pre2] - 2017-02-21

### Added
- Added new "tick" command
	- Ticks the game a given number of times (without re-rendering each time)
- Added a new "tile interaction" highlight
	- Highlights whatever tile the mouse is over within three tiles of the player

### Changed
- Overhauled the item system
	- Items no longer have their own classes and are now generic
	- Items have an "itemType" property which determines what effects and properties they have
	- Much like the tile system now
- Redid the crafting GUI
	- It's a bit hackish
	- A display window is added within the crafting window
	- The display window displays the inputs of whichever recipe is selected

## [0.18.0_pre1] - 2017-02-10 (Particles, caves, ores, and mores)

### Added
- Added two more water tiles, identical in behavior, with slight visual changes
	- The GameBoardOverlay now increments a "tileAnimStage" counter each call to draw
	- The Tiles class now contains additional BufferedImage arrays for animated tiles
- Added new "Particle" entity type; doesn't interact with other entities at all, purely for effect
	- Added new BulletSplash particles; they are released when a bullet collides with something
- Added new RPG weapon
	- Fires rockets in direction of facing
	- Rockets do 5 damage, plus 0.15 damage for each tick within their blast radius
	- Rockets release puffs of smoke as they fly
	- Explosions destroy all trees within their blast radius
- Added new stone tile, can be mined (drops nothing)
- Added new iron ore tile, can be mined to get iron ore
- Added new copper ore tile, can be mined to get copper ore
- Added RPG crafting recipe: 100 gems -> 1 RPG

### Changed
- Each entity now contains a "map" property holding the map that it is in
- Bullets now disappear when they hit trees or rocks
- Eliminated old AstralRune effect, replaced it with bullets radiating outward from player
- Bullets can now be given a custom speed
- Redid cave system; now generates much like the upper world generates, but with closed-off spaces and minable spaces

## [0.17.0] - 2017-02-09 (The Internals Update)

### Added
- Added a second Player entity
	- Enabled switching between the original player and the backup player by hitting 'p'
	- Each player has independent inventory
	- Players now have an associated current map, and maps now contain a list of players
	- Projectiles now remember their source, and damage any mob other than their source
	- The client will only control whichever player is selected
- Added "Rogue" mob
	- Copied largely from player
	- Given a shotgun, a pistol, and an SMG; chooses a different one each time it stops
	- Attacks in the same manner as cyclops, but is a bit faster
	- 3Has a chance of spawning in caves

### Changed
- Gave hunger and mana fields to all mobs
- All items can now be (theoretically) used by all mobs
- Gave cyclopses a shotgun. Because why not
	- The cyclops fires the shotgun when it is 10u away from the current player and it has a line of sight
- Cyclops now spawns in caves
- Cooldowns are now managed within items themselves
	- Inventory no longer has to manage item cooldowns

## [0.16.0] - 2017-01-29 (Gameplay Update 2, continued)

### Added
- Added an AttackRoutine class in which the attacking enemy goes directly toward the player
	- Set cyclops to have AttackRoutine by default
- Added a CraftingOverlay
	- Items (such as tools) can be crafted using the crafting overlay
	- The user must have enough of the required items to craft an item
- Added an InventoryEntry class, a generalized display for items
- Added crafting system
	- Default key: c
	- Opens CraftingOverlay GUIContainer
	- Contains CraftingRecipies
	- CraftingRecipies contain lists of input items and output items
	- When a CraftingRecipie is clicked, the player finds out if the appropriate items are available. If they are, the required items are removed, and the product items are added
	- Added recipe for Hatchet and Pickaxe

### Changed
- Turned InventoryBar into a GUIContainer
	- InventoryBar now holds ten InventoryEntries
	- Replicates previous behavior, but differently

### Removed
- Removed the "Overlay" class, replaced it with generalized GUIContainer class
	- GUIContainers are GUIObjects that hold other GUIObjects
	- Setting a GUIContainer to have a null parent makes it behave as an overlay (covering the whole screen)

## [0.16.0_pre3] - 2017-01-27 (Gameplay Update 2, continued)

### Added
- Added cosmic rune
	- When used, spawns a chicken at the user's position
	- 1000t cooldown (same as law rune)
	- Stackable
- Added mana mechanics
	- Cosmic rune use drains mana by 5.0
	- Law rune use drains mana by 30.0
	- Mana regenerates automatically, 0.1 per 200t
- Added a Camera class, which wraps a mob
	- It is now possible to attach the camera to a mob, which it will follow
- Added a CameraMob (essentially just a dummy mob to attach a camera to)
- Added grenade item and projectile
	- Short(ish) cooldown
	- Has 1000t long fuse
	- Explodes, creating 20 bullets which spray in all directions
- Added astral rune
	- 1000t cooldown
	- Disconnects the camera from the player for 1000t
	- The camera remains stationary while the player can move
	- Added appropriate functions in QuestGame, as there was no other reasonable place to put these methods
	- Costs 50.0 mana to use

### Changed
- Cave maps now have their center tile set as home coords
- Separated the camera completely from the player entity
- Renamed "Particle" class to "Bullet"
- Runes now generate in slightly higher frequency under rocks in caves

### Fixed
- Fixed potential concurrency issue with adding new items, mobs, and projectiles.
	- New items, mobs, and projectiles are now added *after* all processing of current entites is done

## [0.16.0_pre2] - 2017-01-27 (Gameplay Update 2, continued)

### Added
- Added shotgun weapon
	- Has cooldown of 400t
	- Shoots 15-20 bullets in a single direction
- Added LawRune item
	- Using law rune teleports the player back to their home coordinates on a given map
	- Very long cooldown (1000t - maybe should be longer)
	- Stackable
- Added item durability for Shovels, Pickaxes, and Hatchets
- Added rendering of miniature "durability bar" to inventory slots with durability items

### Changed
- Increased accuracy of pistol and smg from (+- 10 degrees) to (+- 5 degrees)
- Merged InventoryEntry and Item classes
	- Items now have all the properties and functions that InventoryEntries used to have
- Slowed down rendering clock (every 5ms to every 10ms)
- Player now spawns with a toolkit (Pickaxe, Shovel, Hatchet)
- In caves, when mining, the loot chances are as such:
	- 2% to get SMG
	- 2% to get Shotgun
	- 6% to get Pistol
	- 10% to get Law Rune
	- 80% to get Stone
	- Anyplace besides caves, only stone is dropped
- Weapons no longer generate on surface map; only Gems and Cheese

## [0.16.0_pre1] - 2017-01-26 (Gameplay Update 2)

### Added
- Added three methods to entity:
	- distanceTo(e): gets the direct distance from this entity to entity e
	- angleTo(e): gets the angle from this entity to entity e
	- hasClearPathTo(e): computes whether this entity has a direct path to entity e
- Added two methods to mob:
	- tileCoordsAtDistance(d): returns the coords of the tile 'd' away from the center of this mob in the direction it is facing
	- tileTypeAtDistance(d): returns the type of the tile 'd' away from the center of this mob in the direction it is facing
- Added several new items:
	- Added log item
	- Added hatchet item
		- When hatchet is used, if the tile one unit in front of the player is a tree, the tree is removed and a log is dropped
	- Added stone item
	- Added pickaxe item
		- When pickaxe is used, if the tile one unit in front of the player is a boulder, the boulder is removed and stone is dropped
	- Added shovel item
		- When shovel is used, if the tile under the player is grass, it becomes dirt, with a 5% chance of a hole being generated
- Added new "hole" and "dirtboulder" tiles
- Inter-map link system created
	- Each map now stores a hashmap of links to other maps
	- When holes are created, "cave" maps are generated; each hole links to a unique cave map

### Fixed
- Minimap is now generated properly; if the map is smaller than the minimap display window, the minimap is centered in the window

## [0.15.1] - 2017-01-26

### Added
- Added a stackable "ChickenLeg" item dropped by chickens on death 50% of the time
- Added a "fullnessBar" object to GameStatusOverlay, which displays hunger
- Player now contains a hunger counter with default size of 1000, drains hunger by one point per 1000 ticks
	- Health drains by one point per 1000 ticks once fullness is empty
	- Eating chicken legs increases fullness by 5, cheese increases fullness to max

## [0.15.0] - 2017-01-10 (The GUI/AI Update, continued)

### Added
- Added a "build mode" to QuestGame; if buildmode is activated, the player's mouse highlights the current block
- Added helper methods in QuestPanel to convert screen coordinates to game coordinates, and vice versa (some are broken)
- Added a fullscreen toggle which generates a new JFrame and JPanel when called
- Added class "Keybindings", a container for keybindings to toggle in-game functions
- Added keybinding GUI, which allows the user to bind keys to all functions
- Created another timer; one to tick the game, and one to render everything (surprise: speedup due to multithreading)

### Changed
- Clicking or dragging while in build mode puts a grass tile down on the spot clicked
- Decreased bullet size from .2 to .15
- Bullets now spawn from the center of the player sprite

## [0.15.0_pre2] - 2017-01-07 (The GUI/AI Update, continued)

### Changed
- Moved the gameboard into its own overlay
- Moved the gui (map, statusbars, inventorybar) into their own overlay
- The QuestGame is now solely a container for the logic of the game
- StatusBar GUIObject must now be instantiated with an anonymous getPercentage() method in order to display information
- Moved the full map view into its own overlay, MapViewOverlay
- When debugging, clicking on a minimap will tp the player to the point clicked

## [0.15.0_pre1] - 2017-01-07 (The GUI/AI Update)

### Added
- Addition of rudimentary AI system
	- Added MobAction abstract class and MoveAction and IdleAction classes
	- Added MobRoutine abstract class and IdleRoutine and FleeRoutine
		- Within each entity class's update() method, the current scenario is evaluated whenever the Mob's think() method is called
		- The think() method determines which routine will be used, and the routine assembles a (possibly random) order of actions
		- The mob then moves according to the mob's current routine's current action
	- Added AI for chicken; idles until the player is within 5 blocks, then runs away from the player until 5+ blocks away

### Changed
- Rebuild of entire GUI system
	- Each GUIelement is now passed its relative anchor point (each corner, or the screen center)
	- Moved status bars into their own class extending GUIObject
	- Moved minimap into its own class extending GUIObject
	- Moved inventory bar into its own class extending GUIObject
	- Converted QuestGame into an overlay
	- Minimap can now be toggled off completely
	- Added rendering of the board's grid itself when debug is on
	- Added vertical and horizontal lines to indicate where the mouse is when it is over a GUIObject

## [0.14.0] - 2017-01-05

### Added
- Added boulder variants of grass, sand, and water tiles
- Added map-specific "home coordinates", initialized as the location of the spawn area
- Added Bone item, dropped by cyclops on death
- Added death behavior for mobs
- Added full map view (opened by pressing M while ingame)

### Changed
- Map terrain is now generated by three simplex noise maps instead of just one
	- A distortion map (essentially just another smaller-factored terrain map) is mixed into the base terrain map
	- A further distortion distribution map can limit the range of the distortion map
- Spawn area now guaranteed to be on land and not generate in the middle of a lake
- Console can now be opened by holding CTRL+F and then pressing P (even when not in debug mode)

### Notes
- NEW NUMBERING SYSTEM:
	- majorelease.update.bugfix_checkpoint
	- This was necessary due to the size of the next update.
	- The checkpoint is to ensure I don't break the code and not know how to fix it.

## [0.13.0] - 2017-01-04

### Added
- Added "tp" command (teleports player to absolute position)
- Added "tprel" command (moves player by an offset)
- Added tile collision for mobs, in the same way that it is implemented for the player
- Added "use()" method to item, delegated pistol and smg's bullet-firing to it
- Added cooldown functionality to items, using inventory slots
- Added health functionality to mobs, and health bars that render and fade automatically
- Added sand tile (walkable and penetrable)

### Changed
- Modified player class (and all mobs, by default) to use a degree-based direction system instead of a hardcoded one

### Removed
- Removed the "getType()" entity method and replaced it with "instanceof" in all applicable cases

### Notes
- Loader has been broken since v0.11.0

## [0.12.1] - 2017-01-04

### Added
- Added 'mob' class as superclass of player and chicken
- Added "Cyclops" mob, moves more slowly than chicken, but in the same pattern
- Added rudimentary console, accessible by pressing F1 when debug mode is on

### Changed
- Cleaned up graphics initialization of entities, tiles, player, etc
- The tree tile is now initialized as a single bufferedimage; it no longer has to be drawn with two images

## [0.12.0] - 2016-12-31 (The Gameplay Update)

### Added
- Added new chicken entity
- Added entities (other than the player) minimap location indicator
- Added very elementary movement for the chicken
- Added new "particle" entity type
- Added SMG weapon item
- Added ability to shoot guns (SMG and pistol); bullets currently go in random directions

### Changed
- Modified game rendering so that only entities and items within 30 blocks of the player are rendered (speedup)
- Changed keybindings; space now uses items, Q now drops items
- Pistol shoots one bullet per keypress of space, SMG shoots bullets constantly
- Changed mapbuilding method privacy settings, rewrote QuestMap constructor for cleaner code
- Debug display now indicated number of items, entities, and particles currently on the map

## [0.11.1] - 2016-12-29

### Added
- Added very rare cheese
- Added minimap player location indicator
- Added minimap item location indicator

## [0.11.0] - 2016-12-26 (The Inventory Update)

### Added
- Added inventory GUI
- Added inventory class
	- Each "player" now has a unique inventory
- Added inventoryEntry class
- Added gun item
- Added "isStackable" property to items
- Added dropping of selected items with spacebar
- Added selecting active item with number keys 0-9

### Fixed
-A few minor drawing fixes in GUIstatusbar

### Removed
- Removed keycount display (it's obsolete with the addition of inventory)

## [0.10.1] - 2016-12-24

### Fixed
- Fixed bug where the minimap didn't regenerate after loading a level
- Fixed bug where the GUI wouldn't reappear after loading a level

## [0.10.0] - 2016-12-24 (The Efficiency Update)

### Added
- Added Huffman compression for map saves
	- Created a new utility library
	- Added a modified Huffman compressor (from CIS 121) to utility library
	- Added a binary reader and writer from Princeton's standard library to utility library. This was necessary to write single bits to file
	- Huffman might not be necessary; generating the encoding map takes the vast majority of time. It might be a better idea to use predetermined values for common tiles

### Changed
- Reimplemented minimap more efficiently
	- Only one zoom level of minimap
	- Minimap is now generated after initialization of map
	- Minimap is cropped accordingly on draw
	- Drawback: must re-render minimap every time the map changes, must be done manually
- Switched movement keys to WASD

### Removed
- Disabled keypress of S for saving and L for loading

## [0.9.0] - 2016-12-09

### Added

- Added a minimap; it can be easily changed in size by modifying mapWidth and mapHeight in the drawMinimap(g) method
- Added Tiles.minimapColor(t) which gets the color to be shown on the minimap for a certain type of tile
- Added functions to cycle through minimap size multipliers and zoom multipliers
- Added keyListener to listen for keypress of Z, which toggles map zoom, and X, which toggles map size

## [0.8.1] - 2016-11-25

### Fixed
- Fixed Loader; it now saves and loads the locations of keys

## [0.8.0] - 2016-11-24

### Added
- Added Item abstract class for item representation
- Added Key item
- Added basics of collision detection in QuestMap class; player-key collisions are detected
- Added keyCounter GUI
- Added options menu feature to toggle debug mode

### Changed
- Player entity's images are now loaded into arrays, not loaded individually
- Images and graphics are now initialized BEFORE top classes are initialized in UrfQuest class
- Removed posX and posY variables from entities, they are now contained within its "bounds" object
- The player now uses a "fake" position to determine tile collisions (its actual position +0.5 on each axis, but will use its actual position to determine collision with objects and entities. This will get confusing; look for solution

## [0.7.2] - 2016-11-23

### Added
- The edge of the map now has an automatically generated border wall
- Work begun on a minimap

### Changed
- MapLoader renamed Loader, and has been fixed
- Loader can now be used through GUI menus
- Extra getters/setters added to QuestGame in order to facilitate loading and saving

### Fixed
- Walking off the edge of the map no longer crashes the game, it simply becomes impassable
	
## [0.7.1] - 2016-11-22

### Changed
- Player's graphics are now initialized the same way as tiles and sounds
- All graphics are now imported using an input stream sourced at the main class, allowing importing to work properly when in runnable jar form
- The running instance of the main class is now accessible at UrfQuest.quest

## [0.7.0] - 2016-11-22

### Added
- Added unwalkable water tile
- Added new map generation method using simplex noise; simplex noise generator taken from: 
	http://staffwww.itn.liu.se/~stegu/simplexnoise/simplexnoise.pdf
	This method has been modified to provide a different result every time by permuting the "perm[]" array
- Imported menu GUI from Arcanists Clone 0.1.0 project

### Changed
- Enabled pause menu and main menu
- Player is now placed in an automatically generated open area with no trees
- Gameplay GUI (status bars, etc) is hidden while in menus

## [0.6.0] - 2016-11-22

### Added
- Added support for multiple maps per game
- Added new Tree tile
- Began adding a rudimentary sound engine

### Changed
- Again, major restructuring of the game: all global variables are now accessed through the main class
- Consolidated rendering of tiles and tile properties into Tiles class, using boolean "properties" array
- Put all assets (pictures, sounds) into new "assets" folder and updated paths accordingly

### Broke
- MapLoader is still broken, so are the Shape, Ball, and Square entity classes

## [0.5.0] - 2016-08-16

### Changed
- Completely replaced hardcoded "player" with player entity
- Player no longer converts grass to dirt
- Replaced all tile and entity procedurally drawn graphics with .png images

### Broke
- New bug? game now lags considerably (might be due to running on battery power)

## [0.4.2] - 2016-08-10

### Changed
- Made the player a subclass of entity
- Deglobalized all player-related variables; they are now accessed through the player entity

### Fixed
- Fixed debugging graphics appearing even when V.debug was false

### Broke
- MapLoader is completely broken

## [0.4.1] - 2016-08-09

### Changed
- Replaced integer-based directional facings with string-based
- Separated entities' physics getters and setters into pos, vel, and acc

### Removed
- Removed the need for a "player tile position" global variable
- Level loader no longer saves player facings

## [0.4.0] - 2016-02-10

### Added
- Added an Entity class, designed to represent other creatures (and potentially the player too)
- The framework for making different types of entities is now in place
- Entities' logic is now a rudimentary 2D physics simulation
- Entity's constructor now allows for its position, size, and color

### Changed
- Completely restructured the game using a new game design pattern; all global variables are public static
- The game and the display are now sent separate ticks; the game is sent a tick first
- Entites' logic and drawing are iterated through by putting them into an ArrayList
- Entities are now generated by QuestMap's constructor

## [0.3.0] - 2016-02-08

### Added
- Created procedurally generated "character" sprite (placeholder) that looks like an arrow
- Created a new class "StatusBar", customizable with color, max value, and of course, scale
- Added two StatusBars, one for "mana" and one for "health"
- Added four new blocks, one which increases speed, one which increases mana, one that increases health, one that drains all three
- Added a new method in QuestGame to react to the block the player is currently on

## [0.2.0] - 2016-02-05

### Changed
- Split up the game's logic and its display into separate components (questPanel --> questGame + questPanel)
- Split the GamePanel rendering into several different methods:
	- drawBoard(g)
	- drawStatus(g)
	- drawCursor(g)
- Optimized drawBoard(g) to use temporary variables instead of reiterating through computations for each tile
- The movement-per-tick of the cursor is now constrained to a variable "speed" in questGame
- Movement of the curson in each direction is now constrained to a method "attemptMove()"

### Removed
- Removed the black edges from each tile
- The cursor can no longer enter stone or null tiles

## [0.1.0] - 2016-02-02

### Added
- Created something of a 2D rendering system
	- Renders a grid which is randomly generated of three different types of tiles: dirt, grass, rock
	- Always renders with the GameCenter in the center of the screen
	- Window is resizable, so that only the blocks that would fit on the screen at any time are rendered.
- Four different types of coordinates:
	- GameCenter: the exact position (a decimal double) of the cursor
	- GameCenterBlock: the block that the cursor is currently on
	- DisplayCenter: the center of the QuestPanel relative to top-left (in terms of pixels)
	- DisplayBlockDimensions: the dimensions of the QuestPanel (in terms of tiles)
- A timer goes off every 5ms:
	- Redraws the panel based on the current coordinates and dimensions
	- Checks what arrow keys are pressed down, moving the cursor appropriately
- Whenever an arrow key is pressed, it is added to an array of keys. Whenever released, it is removed.
- Status panel in upper-left corner displays all four coordinates


[UrfJavaUtils]: https://github.com/SimNine/UrfJavaUtils

[Unreleased]: https://github.com/SimNine/UrfQuest/compare/v0.19.0...HEAD
[0.19.0]: https://github.com/SimNine/UrfQuest/compare/v0.18.0_pre4...v0.19.0
[0.18.0_pre4]: https://github.com/SimNine/UrfQuest/compare/v0.18.0_pre3...v0.18.0_pre4
[0.18.0_pre3]: https://github.com/SimNine/UrfQuest/compare/v0.18.0_pre2...v0.18.0_pre3
[0.18.0_pre2]: https://github.com/SimNine/UrfQuest/compare/v0.18.0_pre1...v0.18.0_pre2
[0.18.0_pre1]: https://github.com/SimNine/UrfQuest/compare/v0.17.0...v0.18.0_pre1
[0.17.0]: https://github.com/SimNine/UrfQuest/compare/v0.16.0...v0.17.0
[0.16.0]: https://github.com/SimNine/UrfQuest/compare/v0.16.0_pre3...v0.16.0
[0.16.0_pre3]: https://github.com/SimNine/UrfQuest/compare/v0.16.0_pre2...v0.16.0_pre3
[0.16.0_pre2]: https://github.com/SimNine/UrfQuest/compare/v0.16.0_pre1...v0.16.0_pre2
[0.16.0_pre1]: https://github.com/SimNine/UrfQuest/compare/v0.15.1...v0.16.0_pre1
[0.15.1]: https://github.com/SimNine/UrfQuest/compare/v0.15.0...v0.15.1
[0.15.0]: https://github.com/SimNine/UrfQuest/compare/v0.15.0_pre2...v0.15.0
[0.15.0_pre2]: https://github.com/SimNine/UrfQuest/compare/v0.15.0_pre1...v0.15.0_pre2
[0.15.0_pre1]: https://github.com/SimNine/UrfQuest/compare/v0.14.0...v0.15.0_pre1
[0.14.0]: https://github.com/SimNine/UrfQuest/compare/v0.13.0...v0.14.0
[0.13.0]: https://github.com/SimNine/UrfQuest/compare/v0.12.1...v0.13.0
[0.12.1]: https://github.com/SimNine/UrfQuest/compare/v0.12.0...v0.12.1
[0.12.0]: https://github.com/SimNine/UrfQuest/compare/v0.11.1...v0.12.0
[0.11.1]: https://github.com/SimNine/UrfQuest/compare/v0.11.0...v0.11.1
[0.11.0]: https://github.com/SimNine/UrfQuest/compare/v0.10.1...v0.11.0
[0.10.1]: https://github.com/SimNine/UrfQuest/compare/v0.10.0...v0.10.1
[0.10.0]: https://github.com/SimNine/UrfQuest/compare/v0.9.0...v0.10.0
[0.9.0]: https://github.com/SimNine/UrfQuest/compare/v0.8.1...v0.9.0
[0.8.1]: https://github.com/SimNine/UrfQuest/compare/v0.8.0...v0.8.1
[0.8.0]: https://github.com/SimNine/UrfQuest/compare/v0.7.2...v0.8.0
[0.7.2]: https://github.com/SimNine/UrfQuest/compare/v0.7.1...v0.7.2
[0.7.1]: https://github.com/SimNine/UrfQuest/compare/v0.7.0...v0.7.1
[0.7.0]: https://github.com/SimNine/UrfQuest/compare/v0.6.0...v0.7.0
[0.6.0]: https://github.com/SimNine/UrfQuest/compare/v0.5.0...v0.6.0
[0.5.0]: https://github.com/SimNine/UrfQuest/compare/v0.4.2...v0.5.0
[0.4.2]: https://github.com/SimNine/UrfQuest/compare/v0.4.1...v0.4.2
[0.4.1]: https://github.com/SimNine/UrfQuest/compare/v0.4.0...v0.4.1
[0.4.0]: https://github.com/SimNine/UrfQuest/compare/v0.3.0...v0.4.0
[0.3.0]: https://github.com/SimNine/UrfQuest/compare/v0.2.0...v0.3.0
[0.2.0]: https://github.com/SimNine/UrfQuest/commits/v0.1.0...v0.2.0
[0.1.0]: https://github.com/SimNine/UrfQuest/commits/v0.1.0