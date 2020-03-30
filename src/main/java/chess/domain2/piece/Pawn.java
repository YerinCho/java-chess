package chess.domain2.piece;

import chess.domain2.Color;
import chess.domain2.Direction;
import chess.domain2.Square;
import chess.domain2.Type;

import java.util.*;

public class Pawn extends Piece {

    private final static Map<String, Pawn> CACHE = new HashMap<>();
    private static final char BLACK_PAWN_FIRST_RANK = '7';
    private static final char WHITE_PAWN_FIRST_RANK = '2';
    private static final int FIRST_PAWN_MOVE_SCOPE = 2;
    public static final double REDUCED_PAWN_SCORE = 0.5;

    static {
        for (Color color : Color.values()) {
            CACHE.put(color.getName(), new Pawn(color));
        }
    }

    private final Type type = Type.PAWN;

    public Pawn(Color color) {
        super(color);
    }

    public static Pawn of(Color color) {
        if (color == null) {
            throw new IllegalArgumentException("잘못된 입력입니다.");
        }
        return CACHE.get(color.getName());
    }

    @Override
    public Set<Square> getMovableSquares(Square centerSquare, Map<Square, Piece> chessBoard) {
        Set<Square> availableSquares = new LinkedHashSet<>();
        availableSquares.add(centerSquare);
        Direction direction = getRankLevelByColor();
        addMovableSquares(chessBoard, availableSquares, direction);
        addCaptureableSquares(chessBoard, availableSquares);
        availableSquares.remove(centerSquare);
        return availableSquares;
    }

    private void addCaptureableSquares(Map<Square, Piece> chessBoard, Set<Square> availableSquares) {
        Square centerSquare = availableSquares.stream().findFirst().orElseThrow(IndexOutOfBoundsException::new);
        List<Direction> directions = getCaptureDirection(chessBoard, centerSquare);
        for (Direction directionForCapture : directions) {
            Square captureSquare = Square
                    .moveTo(directionForCapture.getFileDegree(), directionForCapture.getRankDegree(), centerSquare);
            addCapcureableSquare(chessBoard, availableSquares, captureSquare);
        }
    }

    private void addCapcureableSquare(Map<Square, Piece> chessBoard, Set<Square> availableSquares, Square captureSquare) {
        Square centerSquare = availableSquares.stream().findFirst().orElseThrow(IndexOutOfBoundsException::new);
        if (chessBoard.containsKey(captureSquare)
                && !chessBoard.get(centerSquare).color.equals(chessBoard.get(captureSquare).color)) {
            availableSquares.add(captureSquare);
        }
    }

    private List<Direction> getCaptureDirection(Map<Square, Piece> chessBoard, Square centerSquare) {
        if (chessBoard.get(centerSquare).color.equals(Color.BLACK)) {
            return Direction.blackPawnCaptureDirection();
        }
        return Direction.whitePawnCaptureDirection();
    }

    @Override
    void addMovableSquares(Map<Square, Piece> chessBoard, Set<Square> availableSquares, Direction direction) {
        Square centerSquare = availableSquares.stream().findFirst().orElseThrow(IndexOutOfBoundsException::new);
        Square squareToAdd = Square.moveTo(
                direction.getFileDegree(), direction.getRankDegree(), centerSquare
        );
        availableSquares.add(squareToAdd);
        if (!centerSquare.equals(squareToAdd)) {
            removeSameColorSquare(chessBoard, availableSquares, squareToAdd);
        }
        if (isFirstMove(centerSquare)) {
            squareToAdd = addSquareWhenFirstPawnMove(availableSquares, direction, centerSquare);
            if (!centerSquare.equals(squareToAdd)) {
                removeSameColorSquare(chessBoard, availableSquares, squareToAdd);
            }
        }
    }

    private boolean isFirstMove(Square centerSquare) {
        return (color.equals(Color.BLACK) && centerSquare.getRank() == BLACK_PAWN_FIRST_RANK) ||
                (color.equals(Color.WHITE) && centerSquare.getRank() == WHITE_PAWN_FIRST_RANK);
    }

    private Square addSquareWhenFirstPawnMove(Set<Square> availableSquares, Direction direction, Square centerSquare) {
        Square squareToAdd;
        squareToAdd = Square.moveTo(
                direction.getFileDegree(), direction.getRankDegree() * FIRST_PAWN_MOVE_SCOPE, centerSquare);
        availableSquares.add(squareToAdd);
        return squareToAdd;
    }

    private Direction getRankLevelByColor() {
        if (color.equals(Color.BLACK)) {
            return Direction.blackPawnDirection();
        }
        return Direction.whitePawnDirection();
    }

    @Override
    public String getLetter() {
        if (color == Color.BLACK) {
            return type.getName();
        }
        return type.getName().toLowerCase();
    }

    @Override
    public double getScore() {
        return type.getScore();
    }

}
