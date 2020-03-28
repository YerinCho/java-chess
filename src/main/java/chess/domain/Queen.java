package chess.domain;

import java.util.*;

public class Queen extends Piece {

    private final static Map<String, Queen> CACHE = new HashMap<>();

    static {
        for (Color color : Color.values()) {
            CACHE.put(color.getName(), new Queen(color));
        }
    }

    private final Type type = Type.QUEEN;

    public Queen(Color color) {
        super(color);
    }

    public static Queen of(Color color) {
        validateInput(color);
        return CACHE.get(color.getName());
    }

    private static void validateInput(Color color) {
        if (Objects.isNull(color)) {
            throw new IllegalArgumentException("잘못된 입력입니다");
        }
    }

    @Override
    public Set<Square> calculateScope(Square square) {
        Set<Square> availableSquares = new HashSet<>();
        for (int index = -7; index < 8; index++) {
            availableSquares.add(getMovedSquareByFileAndRank(square, index, 0)); //가로
            availableSquares.add(getMovedSquareByFileAndRank(square, 0, index)); //세로
            availableSquares.add(getMovedSquareByFileAndRank(square, index * -1, index)); //왼쪽대각선
            availableSquares.add(getMovedSquareByFileAndRank(square, index, index)); // 오른쪽대각선
        }
        availableSquares.remove(square);
        return availableSquares;
    }

    @Override
    public Set<Square> calculateMoveBoundary(Square square, Map<Square, Piece> board) {
        validateNotNull(square, board);
        Set<Square> squares = calculateScope(square);
        for (Square queenScopeSquare : calculateScope(square)) {
            if (board.containsKey(queenScopeSquare)) {
                Map<String, Integer> fileAndRankDifference = new HashMap<>();
                fileAndRankDifference.put("file", queenScopeSquare.getFile() - square.getFile());
                fileAndRankDifference.put("rank", queenScopeSquare.getRank() - square.getRank());
                findCanNotMovableSquaresInNorthAndSouth(squares, queenScopeSquare, fileAndRankDifference);
                findCanNotMovableSquaresInDiagonal(squares, queenScopeSquare, fileAndRankDifference);
            }
            removeSquareWhenSameColor(board, squares, queenScopeSquare);
        }
        return squares;
    }

    @Override
    public void removeSquareIfSameColor(Map<Square, Piece> board, Set<Square> squares, Square pawnScopeSquare) {
        if (board.containsKey(pawnScopeSquare) && color == board.get(pawnScopeSquare).color) {
            squares.remove(pawnScopeSquare);
        }
    }

    @Override
    public double getScore() {
        return type.getScore();
    }

    @Override
    public String getLetter() {
        if (color == Color.BLACK) {
            return type.getName();
        }
        return type.getName().toLowerCase();
    }
}
