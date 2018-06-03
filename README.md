## Final Project Delivery

[Javadoc Documentation](https://jotapsa.github.io/Battleship/) :books:


### 1. Setup/Instalation
#### 1.1 Setup (Development)

Just clone this git repository and import the graddle project to your favorite IDE.

#### 1.2 Instalation

[Download APK](http://web.fe.up.pt/~up201503477/battleship.apk) :robot:

### 2. Architecture Design
#### 2.1 UML
##### Package structure:
<img src="img/uml/package_overview.png" width="500" title="Battleship UML">

##### Type Hierarchy
<img src="img/uml/type_hierarchy.png" width="500" title="Battleship UML">

#### 2.2 Design Patterns
*   [Singleton Pattern](https://en.wikipedia.org/wiki/Singleton_pattern) - Avoid creating more than one intance of a object.
*   [DoubleBuffer]() - Implemented by Libgdx in Graphics Management (in GameView Class)
*   [Template Method](https://en.wikipedia.org/wiki/Template_method_pattern) - Implemented by Libgdx in GameLoop (GameView, GameModel, Game Controller classes)


#### 2.3 Design Decisions
*   [MVC Design Pattern](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93controller) - Software architectural pattern

### 2.4 Major Difficulties
Since we are both 3rd year students who attend LBAW class, we only had 4 days to do this project, so the biggest difficulty was time. :trophy: :pray:

### 2.5 Distribution among team members
The work was shared equally.

### 3. User Manual
The game starts on the main menu where you can choose Singleplayer (vs Computer), Local Multiplayer (on the same device), Multiplayer (over lan).

<img src="img/MainMenu.png" height="500" title="Game Menu">

If you choose Singleplayer you are taken to the Placing Screen where you can place your ships.

<img src="img/PlacingViewSinglePlayer1.png" height="500" title="PlacingViewSinglePlayer1"> <img src="img/PlacingViewSinglePlayer2.png" height="500" title="PlacingViewSinglePlayer2">

After placing your ships, press on the screen again and you are taken to the Game Screen, to play just press a cell to choose your target.

![GameViewSinglePlayer1](img/GameViewSinglePlayer1.png "GameViewSinglePlayer1")

![GameViewSinglePlayer2](img/GameViewSinglePlayer2.png "GameViewSinglePlayer2")

If you choose Multiplayer Local you are taken to the Placing screen for the "blue" player

![PlacingViewMultiplayerLocal1](img/PlacingViewMultiplayerLocal1.png "PlacingViewMultiplayerLocal1")

Then to the Placing screen for the "red" player

![PlacingViewMultiplayerLocal2](img/PlacingViewMultiplayerLocal2.png "PlacingViewMultiplayerLocal2")

The screen then rotates accordingly to the turn.

![GameViewMultiPlayerLocal1](img/GameViewMultiPlayerLocal1.png "GameViewMultiPlayerLocal1")

![GameViewMultiPlayerLocal2](img/GameViewMultiPlayerLocal2.png "GameViewMultiPlayerLocal2")

If you choose Multiplayer you are taken to the MultiplayerMenu

![MultiplayerView](img/MultiplayerView.png "MultiplayerView")

Click create room (you become the blue player). shows your ip so that the other player can connect

![CreateRoomView](img/CreateRoomView.png "CreateRoomView")

Click Join room and a dialog asking for ip shows up.

![JoinRoomDialog](img/JoinRoomDialog.png "JoinRoomDialog")

When the connection is established each player is taken to their respective Placing screens

![PlacingViewMultiplayer](img/PlacingViewMultiplayer.png "PlacingViewMultiplayer")

Then the fun starts!

![GamePlayerMultiplayer1](img/GamePlayerMultiplayer1.png "GamePlayerMultiplayer1")
![GamePlayerMultiplayer2](img/GamePlayerMultiplayer2.png "GamePlayerMultiplayer2")
![GamePlayerMultiplayer3](img/GamePlayerMultiplayer3.png "GamePlayerMultiplayer3")

Sometimes you win :)

![GamePlayerMultiplayerWin](img/GamePlayerMultiplayerWin.png "GamePlayerMultiplayerWin")

Sometimes you lose :(

![GamePlayerMultiplayerLost](img/GamePlayerMultiplayerLost.png "GamePlayerMultiplayerLost")

----

GROUP1743, 29/04/2018

> Bernardo Manuel Costa Barbosa, up201503477@fe.up.pt
> João Pedro Teixeira Pereira de Sá, up201506252@fe.up.pt
