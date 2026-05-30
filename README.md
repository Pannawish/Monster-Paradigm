# Monster Paradigm

<div align="center">

[![Java Version](https://img.shields.io/badge/Java-15%2B-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![GUI Framework](https://img.shields.io/badge/GUI-Swing%20%2F%20AWT-0073EC?style=for-the-badge&logo=java&logoColor=white)](https://docs.oracle.com/javase/tutorial/uiswing/)
[![Platform](https://img.shields.io/badge/Platform-macOS%20%7C%20Windows%20%7C%20Linux-4eb827?style=for-the-badge)](https://github.com/Pannawish/Monster-Paradigm)

**A dynamic, interactive pet-care simulation and mini-game built entirely with Java Swing.**

*Nurture your monster, customize its appearance, level it up through multiple forms, and challenge yourself in the Game Room mini-game!*

[Key Features](#key-features) • [Learning Objectives](#learning-objectives) • [Screenshots](#screenshots) • [Getting Started](#getting-started) • [Project Structure](#project-structure) • [Gameplay Guide](#gameplay-guide) • [Contributors](#contributors)

</div>

---

## Key Features

*   **Interactive Pet Care**:
    *   **Petting**: Directly interact with your monster to show affection and gain XP.
    *   **Grooming**: Keep your pet squeaky clean using the washing sponge tool.
    *   **Feeding**: Choose from different food types and control the portions to keep your pet satisfied.
*   **Evolution & Progression**:
    *   Earn XP through daily care and mini-games.
    *   Watch your monster evolve across **3 distinct levels** and change into larger, more advanced monster forms!
*   **Customization**:
    *   Dynamic skin selector supporting **Red**, **Blue**, and **Green** skins with custom visual frames.
*   **Game Room Mini-Game**:
    *   Control your monster with the keyboard arrow keys.
    *   Catch falling meat to rack up points while avoiding falling swords that penalize your score.
    *   Convert your final high score into XP to power up your pet in the Main Room!
*   **Rich Sensory Experience**:
    *   Immersive background loops (including custom titles and the mini-game theme *Megalovania*).
    *   Responsive audio effects for petting, feeding, bubble washes, level-ups, game wins, and getting hurt.

---

## Learning Objectives

This project was developed to apply and consolidate foundational Java and GUI design principles:
*   **Object-Oriented Programming (OOP)**: Designing cohesive models, state inheritance, and modular separation of screens.
*   **Desktop UI Development**: Customizing layouts, borders, flow, and absolute layouts in Java Swing/AWT.
*   **Interactive Event Loops**: Capturing key-press actions and mouse events, and handling visual/audio reactive triggers.
*   **Resource Handling**: Scaling graphics programmatically and implementing robust audio playback threads.
*   **Game Physics & Loop**: Developing dynamic falling-object algorithms and collision engines.

---

## Screenshots

<div align="center">

### Title & Setup Screen
*Name your companion and start the journey*
<img src="screenshots/title.png" alt="Monster Paradigm Title Screen" width="750" style="border-radius: 8px; box-shadow: 0 4px 8px rgba(0,0,0,0.2); margin-bottom: 20px;">

### Main Pet Care Room
*Feed, pet, groom, and level up your monster*
<img src="screenshots/mainroom.png" alt="Monster Paradigm Main Room" width="750" style="border-radius: 8px; box-shadow: 0 4px 8px rgba(0,0,0,0.2); margin-bottom: 20px;">

### Game Room Mini-Game
*Catch rewards, dodge swords, and farm XP*
<img src="screenshots/gameroom.png" alt="Monster Paradigm Game Room" width="750" style="border-radius: 8px; box-shadow: 0 4px 8px rgba(0,0,0,0.2);">

</div>

---

## System Requirements

| Requirement | Specification |
| :--- | :--- |
| **Java JDK** | Version `15` or newer (Tested in workspace with OpenJDK `25.0.2`) |
| **GUI Support** | Swing / AWT (Standard in JDK) |
| **OS Compatibility** | macOS, Windows, Linux |

> [!IMPORTANT]
> The source code makes use of modern Java features, such as multi-line **Text Blocks** and **Switch Arrow Expressions**. Compilation will fail on JDKs older than JDK 15.

---

## Getting Started

### Quick Command Line Run
Open a terminal in the project directory and run the following commands to compile and launch the game:

```bash
# 1. Compile the source code into the current package directory
javac -d . *.java

# 2. Run the application main class
java Monster_Paradigm.Mainapplication
```

### Running in VS Code
This project includes a pre-configured `.vscode/launch.json` for seamless runs.
1. Open this project folder in VS Code.
2. Ensure you have the **Extension Pack for Java** installed.
3. Go to the **Run and Debug** panel (`Ctrl+Shift+D` or `Cmd+Shift+D`).
4. Select **"Run Monster Paradigm"** from the drop-down menu and press `F5` to start.

---

## Project Structure

```text
Monster-Paradigm/
├── Mainapplication.java        # Game launcher, instructions & title screen UI
├── mainroom.java               # Main pet-care interactive screen and UI
├── GameRoom.java               # Falling-item mini-game keyboard and game loop UI
├── util_title.java             # Constants and asset paths for the title room
├── util_main.java              # Constants and asset paths for the main room
├── Utilities_gameroom.java     # Game room constants, MyImageIcon & MySound engine
├── resource_title/             # Visual and audio assets for the title screen
├── resource_mainroom/          # Assets for pet care, level sprites & care sounds
├── resource_gameroom/          # Sprites and sounds for the game room mini-game
├── screenshots/                # Showcase preview images
└── .vscode/                    # IDE launch configurations
```

### Class Breakdown

*   **`Mainapplication`**: Handles the entry point (`main`), naming validation, instruction modal, credits screen, and launches the core application.
*   **`mainroom`**: Manages the main pet simulator, rendering progress bars for XP and leveling, skin changes, mouse listeners for petting and washing tools, and UI buttons.
*   **`GameRoom`**: Implements the mini-game loop, keyboard controls (`KeyAdapter`), physics collision detection with items, and timers for falling items.
*   **`MyImageIcon`**: An extension of `ImageIcon` that introduces a chainable, high-fidelity `.resize(width, height)` utility using smooth scaling.
*   **`MySound`**: A streamlined audio engine wrapping standard Java `Clip` and `FloatControl` to play sound effects once, loop theme songs, and dynamically configure decibel volume levels.

---

## Gameplay Guide

### Phase 1: Setup
1. Click **`NAME MONSTER`**.
2. Enter your desired monster's name in the text box.
3. Click **`Ok!`** to confirm the name.
4. Click **`PLAY`** to enter the main pet care environment.

### Phase 2: Pet Simulator
*   **Affection**: Click on your monster to pet it (earns XP).
*   **Cleanliness**: Select the **Sponge** and click on your monster to wash away bubbles and keep it happy.
*   **Feeding**: Choose your food type and quantity (e.g. snack, full meal) to keep your monster fed.
*   **Evolutions**: Accumulate enough XP to watch your monster level up and transition into stronger forms!
*   **Skins**: Toggle the skin selector drop-down to switch between vibrant **Red**, **Blue**, and **Green** skins.
*   **Access Mini-Game**: Click the **`Game`** button to open the Game Room.

### Phase 3: Mini-Game (Game Room)
*   Press the **`START`** button to initiate the falling item loops.
*   Use your keyboard's **`Left Arrow`** and **`Right Arrow`** keys to steer your monster across the bottom of the screen.
*   **Catch Meat**: Earns points and increases your score.
*   **Dodge Swords**: Reduces your points when hit.
*   Press **`QUIT`** to end the game loop, return to the main room, and automatically convert your mini-game score into XP for your pet!

---

## Technical Notes

*   **Asset Management**: Sound effects and imagery are loaded relative to the runtime execution folder. Do not run the compiled program from outside the workspace root directory.
*   **UI Layouts**: All screens have been fine-tuned and tested for macOS and Windows to prevent UI clipping issues.

---

## Contributors

This project was built by:

*   **Pannawish Kriengyakul**
*   **Papon Suramanont**
*   **Premwiss Seenumngernmee**
*   **Rapeepat Pokpattanakul**
*   **Panya Mahasrisaengpetch**


---

## Disclaimer

*This project was created strictly for educational purposes as part of a university programming course. It is not intended for commercial use or distribution.*

*Visual and audio assets used in this project were gathered from publicly available online resources. All copyrights and intellectual property belong to their respective owners.*
