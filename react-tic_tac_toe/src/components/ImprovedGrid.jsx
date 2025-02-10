import { useState } from 'react';
import Swal from 'sweetalert2';

const API_BASE = 'http://localhost:8080/api/tic-tac-toe';

const DynamicTicTacToe = () => {
  // Game state
  const [gridSizeInput, setGridSizeInput] = useState(3);
  const [currentGridSize, setCurrentGridSize] = useState(3);
  const [loading, setLoading] = useState(false);
  const [gameState, setGameState] = useState({
    nextMove: null,
    player1Score: 0,
    player2Score: 0,
    board: [],
    anyWinner: true,
  });

  // Style constants
  const containerStyle = {
    padding: '20px',
    maxWidth: '600px',
    margin: '0 auto',
    position: 'relative',
  };

  const gridStyle = (size) => ({
    display: 'grid',
    gridTemplateColumns: `repeat(${size}, 1fr)`,
    gap: '5px',
    background: '#333',
    padding: '5px',
    borderRadius: '5px'
  });

  const cellStyle = {
    background: '#fff',
    aspectRatio: '1',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    fontSize: '24px',
    cursor: 'pointer',
    borderRadius: '3px',
    transition: 'background-color 0.3s',
  };

  // Helper functions
  const handleApiCall = async (fn) => {
    setLoading(true);
    try {
      return await fn();
    } catch (error) {
      Swal.fire({
        icon: 'error',
        title: 'Oops...',
        text: error.message || 'Something went wrong!',
      });
    } finally {
      setLoading(false);
    }
  };

  const updateGameState = (data) => {
    setGameState(prev => ({
      ...prev,
      nextMove: data.nextMove,
      player1Score: data.player1Score,
      player2Score: data.player2Score,
      board: data.board.value,
      anyWinner: data.anyWinner,
    }));
  };

  // Game actions
  const checkWinner = async () => {
    const response = await fetch(`${API_BASE}/getWinner`, {credentials: "include"});
    const data = await response.json();
    
    if (data.finalStatus === 'WIN') {
      Swal.fire({
        icon: 'success',
        title: `Player ${data.playerWinner} Wins!`,
        confirmButtonText: 'Play Again',
      }).then(() => startNewGame());
    } else if (data.finalStatus === 'DRAW') {
      Swal.fire({
        icon: 'info',
        title: 'Draw!',
        text: 'The game ended in a draw',
        confirmButtonText: 'Try Again',
      }).then(() => startNewGame());
    }
  };

  const startNewGame = async () => {
    const validatedSize = Math.min(Math.max(parseInt(gridSizeInput), 3), 10);
    
    await handleApiCall(async () => {
      const response = await fetch(`${API_BASE}/newGame/${validatedSize}`, {credentials: "include",});
      const data = await response.json();
      setCurrentGridSize(validatedSize);
      updateGameState(data);
    });
  };

  const initializeGame = async () => {
    const validatedSize = Math.min(Math.max(parseInt(gridSizeInput), 3), 10);
    
    await handleApiCall(async () => {
      const response = await fetch(`${API_BASE}/initialize/${validatedSize}`, {credentials: "include"});
      const data = await response.json();
      setCurrentGridSize(validatedSize);
      updateGameState(data);
    });
  };

  const handleMove = async (row, col) => {
    if (!gameState.nextMove || gameState.anyWinner) return;

    await handleApiCall(async () => {
      const response = await fetch(`${API_BASE}/move`, {
        method: 'POST',
        credentials: "include",
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          player: gameState.nextMove,
          row,
          col,
        }),
      });

      const data = await response.json();
      if (response.ok) {
        updateGameState(data);
        if (data.anyWinner) await checkWinner();
      }
      if (!response.ok) {
        if (data.error_code != null) {
            Swal.fire({
                icon: "error",
                title: "Oops...",
                text: data.error_message || "Something went wrong!",
                });
        }
      }
    });
  };

  return (
    <div style={containerStyle}>
      <h1 style={{ textAlign: 'center' }}>Dynamic Tic Tac Toe</h1>
      
      {/* Score display */}
      <div style={{ textAlign: 'center', margin: '20px 0' }}>
        Player 1: {gameState.player1Score} | Player 2: {gameState.player2Score}
      </div>

      {/* Game controls */}
      <div style={{ textAlign: 'center', marginBottom: '20px' }}>
        {gameState.anyWinner && (<label>
          Grid Size:
          <input
            type="number"
            value={gridSizeInput}
            onChange={(e) => setGridSizeInput(e.target.value)}
            min="3"
            max="10"
            style={{ margin: '0 10px', padding: '5px', width: '60px' }}
          />
        </label>)}
        
        {gameState.anyWinner && (
          <button 
            onClick={initializeGame}
            style={{ marginLeft: '20px' }}
            disabled={loading}
          >
            Start Game!
          </button>
        )}

        {/* Reset all of the game button */}
        {!gameState.anyWinner && (
          <button 
            onClick={initializeGame}
            style={{ marginLeft: '20px' }}
            disabled={loading}
          >
            Reset Game
          </button>
        )}

        {gameState.nextMove && (
          <div style={{ marginTop: '10px' }}>
            Next Move: Player {gameState.nextMove}
          </div>
        )}
      </div>

      {/* Game grid */}
      <div style={gridStyle(currentGridSize)}>
        {gameState.board.flatMap((row, rowIndex) =>
          row.map((cell, colIndex) => (
            <div
              key={`${rowIndex}-${colIndex}`}
              onClick={() => handleMove(rowIndex, colIndex)}
              style={{
                ...cellStyle,
                color: cell === 1 ? '#ff4444' : '#4444ff',
                backgroundColor: loading ? '#e0e0e0' : '#fff',
              }}
              onMouseOver={(e) => !loading && (e.target.style.backgroundColor = '#f0f0f0')}
              onMouseOut={(e) => !loading && (e.target.style.backgroundColor = '#fff')}
            >
              {cell === 1 ? 'X' : cell === 2 ? 'O' : ''}
            </div>
          ))
        )}
      </div>

      {/* Loading overlay */}
      {loading && (
        <div style={{
          position: 'fixed',
          top: 0,
          left: 0,
          right: 0,
          bottom: 0,
          backgroundColor: 'rgba(255, 255, 255, 0.8)',
          display: 'flex',
          justifyContent: 'center',
          alignItems: 'center',
          fontSize: '24px',
        }}>
          Loading...
        </div>
      )}
    </div>
  );
};

export default DynamicTicTacToe;