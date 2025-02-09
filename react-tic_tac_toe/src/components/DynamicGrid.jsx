// This code isn't used, only for reference
import { useState } from 'react';
import Swal from 'sweetalert2';

const DynamicTicTacToe = () => {
  const [gridSize, setGridSize] = useState(3);
  const [updateSize, setUpdateSize] = useState(3);
  const [loading, setLoading] = useState(false);
  const [response, setResponse] = useState("");
  const [nextMove, setNextMove] = useState(null);
  const [anyWinner, setAnyWinner] = useState(true);

  const [cells, setCells] = useState(
    Array(gridSize * gridSize).fill(null).map(() => Array(gridSize * gridSize).fill(null))
  );

  const checkForTheWinner = async () => {
    setLoading(true);
    try {
      const res = await fetch("http://localhost:8080/api/tic-tac-toe/getWinner" , {
        method: "GET",
        mode: "cors",
      });

      if (!res.ok) {
        throw new Error(`HTTP error! Status: ${res.status}`);
      }

      const data = await res.json();
      const finalStatus = data.finalStatus;
      const winnerName = data.playerWinner;
      if (finalStatus == "ONGOING") {
        Swal.fire({
          icon: "info",
          title: "Ongoing",
          text: "Please continue the game",
          confirmButtonText: `Play Again!`
        }).then(() => {  
          startNewGame();
        });
      } else if (finalStatus == "DRAW") {
        Swal.fire({
          icon: "info",
          title: "DRAW",
          text: "You guys draw!",
          confirmButtonText: `Play Again!`
        }).then(() => {  
          startNewGame();
        });
      } else if (finalStatus == "WIN") {
        Swal.fire({
          icon: "success",
          title: "Player " + winnerName + " WIN!",
          confirmButtonText: `Play Again!`
        }).then(() => {  
          startNewGame();
        });
      }
    } catch (error) {
      Swal.fire({
        icon: "error",
        title: "Oops...",
        text: error.message || "Something went wrong!",
      });
    } finally {
      setLoading(false);
    }
  };

  const initializeGame = async () => {
      setLoading(true);
      try {
        const res = await fetch("http://localhost:8080/api/tic-tac-toe/initialize/" + updateSize , {
          method: "GET",
          mode: "cors",
        });
  
        if (!res.ok) {
          throw new Error(`HTTP error! Status: ${res.status}`);
        }
  
        const data = await res.json();
        setResponse(data);
        setNextMove(data.nextMove)
        setCells(data.board.value)
        setAnyWinner(data.anyWinner)
      } catch (error) {
        Swal.fire({
          icon: "error",
          title: "Oops...",
          text: error.message || "Something went wrong!",
        });
      } finally {
        setLoading(false);
      }
    };

    const startNewGame = async () => {
      setLoading(true);
      try {
        const res = await fetch("http://localhost:8080/api/tic-tac-toe/newGame/" + updateSize , {
          method: "GET",
          mode: "cors",
        });
  
        if (!res.ok) {
          throw new Error(`HTTP error! Status: ${res.status}`);
        }
  
        const data = await res.json();
        setResponse(data);
        setNextMove(data.nextMove)
        setCells(data.board.value)
        setAnyWinner(data.anyWinner)
      } catch (error) {
        Swal.fire({
          icon: "error",
          title: "Oops...",
          text: error.message || "Something went wrong!",
        });
      } finally {
        setLoading(false);
      }
    };

  const handleStartButtonClick = () => {
    initializeGame();
    setGridSize(Math.min(Math.max(parseInt(updateSize) || 1, 1), 10));
  };

  const handleCellClick = async (row, col) => {
    setLoading(true);
      try {
        const res = await fetch("http://localhost:8080/api/tic-tac-toe/move" , {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify({
              player: nextMove,
              row: row,
              col: col,
            }),
        });
  
        if (!res.ok) {
          const errorData = await res.json();
          throw new Error(`${errorData.error_message}`);
        }
  
        const data = await res.json();
        setResponse(data);
        setNextMove(data.nextMove)
        setCells(data.board.value)
        setAnyWinner(data.anyWinner.toString() === 'true')
        if (data.anyWinner.toString() === 'true') {
          checkForTheWinner()
        }
      } catch (error) {
        Swal.fire({
          icon: "error",
          title: "Oops...",
          text: error.message,
        });
      } finally {
        setLoading(false);
      }
  };

  return (
    <div style={{ padding: '20px', maxWidth: '600px', margin: '0 auto' }}>
      <h1 style={{ textAlign: 'center' }}>Dynamic Tic Tac Toe</h1>
      
      <div style={{ marginBottom: '20px', textAlign: 'center' }}>
        <label>
          Player 1 win: {response.player1Score} &emsp; &emsp; Player 2 win: {response.player2Score}
        </label>
      </div>

      <div style={{ marginBottom: '20px', textAlign: 'center' }}>
        <label>
          Grid Size:
          <input
            type="number"
            value={updateSize}
            onChange={(e) => {
                setUpdateSize(e.target.value);
            }}
            min="1"
            max="10"
            style={{ marginLeft: '10px', padding: '5px' }}
          />
          {anyWinner && <button style={{ marginLeft: '20px', textAlign: 'center' }} onClick={() =>{handleStartButtonClick();}}> Start Game!</button>}

          {nextMove && <label style={{ marginLeft: '20px', textAlign: 'center' }}>NEXT MOVE : Player {nextMove}</label>}
        </label>
      </div>
      <div style={{ 
        display: 'grid',
        gridTemplateColumns: `repeat(${gridSize}, 1fr)`,
        gap: '5px',
        background: '#333',
        padding: '5px',
        borderRadius: '5px'
      }}>
        {Array.from({ length: gridSize * gridSize }).map((_, index) => {
          const row = Math.floor(index / gridSize);
          const col = index % gridSize;
          var value = "";
          if (cells[row][col] == 1) {
                value = "X"
          } else if (cells[row][col] == 2) {
                value = "o"
          }
          
          return (
            <div
              key={index}
              onClick={() => handleCellClick(row, col)}
              style={{
                background: '#fff',
                aspectRatio: '1',
                display: 'flex',
                alignItems: 'center',
                justifyContent: 'center',
                fontSize: '24px',
                color: 'black',
                cursor: 'pointer',
                borderRadius: '3px',
                transition: 'background-color 0.3s',
              }}
              onMouseOver={(e) => e.target.style.backgroundColor = '#f0f0f0'}
              onMouseOut={(e) => e.target.style.backgroundColor = '#fff'}
            >
                {value}
            </div>
          );
        })}
      </div>
    </div>
  );
};

export default DynamicTicTacToe;