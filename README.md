# Tic Tac Toe Assessment Project

A full-stack Tic Tac Toe game implementation with Java Spring Boot backend and React frontend. All of the engine of the game runs in backend side (Java Spring Boot) of the application.

## Table of Contents

- [Project Overview](#project-overview)
- [Features](#features)
- [Technologies](#technologies)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [API Documentation](#api-documentation)
- [Project Structure](#project-structure)
- [Known Issues](#known-issues)
- [License](#license)

---

## Project Overview

This project consists of:

- **Backend**: Java Spring Boot application (Java 17) handling game logic
- **Frontend**: React JS application for user interaction
- **Full REST API integration**
- **Dynamic grid size support** (3x3 by default)
- **Score tracking and game state management**

---

## Features

- Start new game with configurable grid size
- Turn-based gameplay
- Win/Draw detection
- Score tracking
- Error handling for invalid moves
- Real-time board updates

---

## Technologies

### Backend:

- [Java 17](https://download.oracle.com/java/17/archive/jdk-17.0.12_windows-x64_bin.exe)
- [Spring Boot 3.x](https://start.spring.io/)
- [Maven](https://maven.apache.org/)

### Frontend:

- [React 18](https://react.dev/blog/2022/03/29/react-v18)
- [Vite 4.x](https://vite.dev/blog/announcing-vite4)
- [SweetAlert2](https://github.com/sweetalert2/sweetalert2) (for notifications)
- [npm 10.9.2](https://www.npmjs.com/package/npm/v/10.9.2?activeTab=readme)

---

## Prerequisites

- Java 17 JDK
- Node.js 18.x+
- npm 10.x+
- Maven 3.8.x+

---

## Installation

### Build with Docker

- Requirements:  
  [Docker](https://www.docker.com/get-started/)

#### Clone this repository

```sh
git clone --recursive https://github.com/iqbalzain99/tictactoe.git
cd tictactoe
```

#### Run the docker image

```sh
docker-compose up -d --build
```

## Manual Installation

### Backend Setup

Navigate to the backend directory:

```sh
cd tictactoe
```

Build and run using Maven:

```sh
mvn clean install
mvn spring-boot:run
```

Or use the pre-built JAR:

```cmd
java -jar ./executable-jar-be/tictactoe.jar
```

### Frontend Setup

Navigate to the frontend directory:

```sh
cd react-tic_tac_toe
```

Install dependencies:

```sh
npm install
```

Start the development server:

```sh
npm run dev
```

## Running Environment

The application will be available at:

- **Backend**: http://localhost:8080
- **Frontend**: http://localhost:5173

## API Documentation

### Endpoints

## Initialize Game

### GET /api/tic-tac-toe/initialize/{size}

#### Response:

```json
{
  "nextMove": 1,
  "player1Score": 0,
  "player2Score": 0,
  "board": {
    "value": [
      [0, 0, 0],
      [0, 0, 0],
      [0, 0, 0]
    ]
  },
  "anyWinner": false
}
```

## Make Move

### POST /api/tic-tac-toe/move

#### Header

`Content-Type: application/json`

#### Request:

```json
{
  "player": 1,
  "row": 0,
  "col": 0
}
```

#### Response:

```json
{
  "nextMove": 2,
  "player1Score": 0,
  "player2Score": 0,
  "board": {
    "value": [
      [1, 0, 0],
      [0, 0, 0],
      [0, 0, 0]
    ]
  },
  "anyWinner": false
}
```

## Check Winner

### GET /api/tic-tac-toe/getWinner

##### Response:

```json
{
  "finalStatus": "WIN",
  "playerWinner": 1
}
```

## New Game

### GET /api/tic-tac-toe/newGame/{size}

#### Response:

```json
{
  "nextMove": 1,
  "player1Score": 0,
  "player2Score": 0,
  "board": {
    "value": [
      [0, 0, 0],
      [0, 0, 0],
      [0, 0, 0]
    ]
  },
  "anyWinner": false
}
```

## Error Responses

```json
{
  "error_code": "PLAYER-02",
  "error_message": "Please select correct player to move!"
}
```

## Project Structure

```
.
├── executable-jar-be/            # Pre-built backend JAR
│   └── tictactoe.jar
├── tictactoe/                    # Spring Boot backend
│   ├── src/
│   └── pom.xml
└── react-tic_tac_toe/            # React frontend
    ├── public/
    ├── src/
    └── package.json
```

## Game Notes

- Grid size currently limited to 3-10 for performance reasons.
- First player is always Player 1 when initializing a new game.
- Player 2 can move first if the last player who moved on the previous player is Player 1.

## ⚠️ Project Notes:

- Make sure both backend and frontend are running simultaneously for full functionality.
  The frontend will automatically connect to the backend running on localhost:8080.
- Since the project is for assess my backend, and I lack of experience of the frontend. I asked DeepSeek 🐋 to help me tidy up my previously functioning code can be accessed at `./react-tic_tac_toe/src/components/DynamicGrid.jsx`, the result is on `./react-tic_tac_toe/src/components/ImprovedGrid.jsx`
