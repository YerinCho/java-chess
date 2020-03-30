package chess.view2;

import chess.domain2.File;
import chess.domain2.Rank;
import chess.domain2.Square;
import chess.domain2.piece.Piece;

import java.util.Map;

public class OutputView {

    public static void printGameInformation() {
        System.out.println("> 체스 게임을 시작합니다.\n" +
                "> 게임 시작 : start\n" +
                "> 게임 종료 : end\n" +
                "> 게임 이동 : move source위치 target위치 - 예. move b2 b3");
    }

    public static void printChessBoard(Map<Square, Piece> chessBoard) {
        for (Rank rank : Rank.values()) {
            printOneChessBoardRank(chessBoard, rank);
        }
    }

    private static void printOneChessBoardRank(Map<Square, Piece> chessBoard, Rank rank) {
        for (File file : File.values()) {
            if (chessBoard.containsKey(Square.of(file.getName(), rank.getName()))) {
                System.out.print(chessBoard.get(Square.of(file.getName(), rank.getName())).getLetter());
                continue;
            }
            System.out.print(".");
        }
        System.out.println();
    }

    public static void printInputError() {
        System.out.println("입력이 잘못되었습니다. 바르게 입력해주세요.");
    }

    public static void printStartGameFirstMessage() {
        System.out.println("게임이 시작되지 않았습니다. 먼저 시작해주세요.");
    }

    public static void printAlreadyGameStartedMessage() {
        System.out.println("이미 게임이 시작되었습니다.");
    }

    public static void printCanNotMoveMessage() {
        System.out.println("이동할 수 없는 위치입니다.");
    }
}
