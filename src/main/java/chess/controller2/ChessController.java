package chess.controller2;

import chess.domain2.ChessBoard;
import chess.domain2.GameState;
import chess.domain2.Square;
import chess.domain2.TeamScore;
import chess.view2.InputView;
import chess.view2.OutputView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChessController {

    public static void run() {
        OutputView.printGameInformation();
        ChessBoard chessBoard = initGame();
        if (chessBoard == null) {
            return;
        }
        boolean isEnd = false;
        while (!isEnd) {
            isEnd = proceed(chessBoard);
        }
        OutputView.printWinner(TeamScore.getWinners(chessBoard.getChessBoard()));
    }

    private static boolean proceed(ChessBoard chessBoard) {
        List<Square> moveSquares = new ArrayList<>();
        List<String> stateInput = Arrays.asList(InputView.inputState().split(" "));
        if (checkState(chessBoard, moveSquares, stateInput)) {
            return true;
        }
        OutputView.printChessBoard(chessBoard.getChessBoard());
        return false;
    }

    private static boolean checkState(ChessBoard chessBoard, List<Square> moveSquares, List<String> stateInput) {
        if (isInputStateMove(stateInput)) {
            moveSquares = getMoveSquare(stateInput);
        }
        if (moveSquares == null) {
            OutputView.printStatus(TeamScore.calculateTeamScore(chessBoard.getChessBoard()));
            return true;
        }
        GameState gameState = GameState.of(stateInput.get(0));
        checkStartRepeatOrError(gameState);
        if (gameState.equals(GameState.END)) {
            OutputView.printStatus(TeamScore.calculateTeamScore(chessBoard.getChessBoard()));
            return true;
        }
        checkMoveOrStatus(chessBoard, moveSquares, gameState);
        return chessBoard.isKingCaptured();
    }

    private static void checkMoveOrStatus(ChessBoard chessBoard, List<Square> moveSquares, GameState gameState) {
        if (gameState.equals(GameState.MOVE)) {
            move(chessBoard, moveSquares);
        }
        if (gameState.equals(GameState.STATUS)) {
            OutputView.printStatus(TeamScore.calculateTeamScore(chessBoard.getChessBoard()));
            OutputView.printWinner(TeamScore.getWinners(chessBoard.getChessBoard()));
        }
    }

    private static void move(ChessBoard chessBoard, List<Square> moveSquares) {
        if (!chessBoard.movePiece(moveSquares)) {
            OutputView.printCanNotMoveMessage();
        }
    }

    private static boolean isInputStateMove(List<String> stateInput) {
        return stateInput.size() != 1;
    }

    private static ChessBoard initGame() {
        if (!startChessGame()) {
            return null;
        }
        ChessBoard chessBoard = new ChessBoard();
        OutputView.printChessBoard(chessBoard.getChessBoard());
        return chessBoard;
    }

    private static List<Square> getMoveSquare(List<String> input) {
        List<Square> moveSquares = new ArrayList<>();
        try {
            moveSquares.add(Square.of(input.get(1)));
            moveSquares.add(Square.of(input.get(2)));
        } catch (Exception e) {
            OutputView.printInputError();
            String inputState = InputView.inputState();
            if (GameState.of(inputState).equals(GameState.END)) {
                return null;
            }
            moveSquares = getMoveSquare(Arrays.asList(inputState.split(" ")));
        }
        return moveSquares;
    }

    private static void checkStartRepeatOrError(GameState gameState) {
        if (gameState.equals(GameState.START)) {
            OutputView.printAlreadyGameStartedMessage();
        }
        if (gameState.equals(GameState.ERROR)) {
            OutputView.printInputError();
        }
    }

    private static boolean startChessGame() {
        String input = InputView.inputState();
        while (!GameState.of(input).equals(GameState.START)) {
            if (GameState.of(input).equals(GameState.END)) {
                return false;
            }
            OutputView.printStartGameFirstMessage();
            input = InputView.inputState();
        }
        return true;
    }
}
