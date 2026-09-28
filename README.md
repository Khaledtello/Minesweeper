# Minesweeper

A Java Swing implementation of the classic Minesweeper game.

This project was built during my second year of Information Technology Engineering and was one of my earliest complete Java GUI projects.

## Overview

The game lets the player configure the board size, choose a difficulty level, reveal cells, place/remove flags, and restart the game.

Mines are generated after the first cell is selected so the initial choice and its surrounding cells are guaranteed to be safe.

## Features

- Configurable number of rows and columns.
- Three difficulty levels:
  - Easy: approximately `rows × columns / 7` mines.
  - Medium: approximately `rows × columns / 5` mines.
  - Hard: approximately `rows × columns / 3` mines.
- First-click protection: mines are not placed on the first selected cell or its surrounding 8 cells.
- Left-click to reveal cells.
- Right-click to place or remove flags.
- Flood-fill expansion for empty cells.
- Chording behavior on opened numbered cells when neighboring flags satisfy the required count.
- Win detection and automatic flagging of remaining mines.
- Loss state reveals the remaining board.
- Custom image resources for cells, numbers, mines, and flags.

## Implementation

The project is organized into a small set of classes:

- `Minesweeper` — application window, options, difficulty selection, and game lifecycle.
- `Game` — board creation, mouse interaction, mine generation, and number calculation.
- `Cell` — cell state and graphical representation.
- `CheckChoice` — cell-opening logic, recursive flood fill, win/loss handling.
- `GameFile` — shared game state.
- `Main` — application entry point.

### Main concepts used

- Java Swing GUI
- Event-driven programming
- Mouse listeners
- Randomized mine placement
- Recursive flood fill
- 2D arrays
- Object-oriented programming
- File/resource loading

## Requirements

- Java Development Kit (JDK)

The original project was developed in IntelliJ IDEA.

## Running

Open the project in IntelliJ IDEA and run:

```text
minesweeper.Main
```

The image resources are stored under:

```text
src/resources/
```

## Notes

This was an early project and intentionally has a simple architecture. Its main value as a portfolio project is demonstrating early hands-on Java, GUI programming, event handling, recursion, and game-state logic.

## Author

**Khaled Tello**
