# java-chess
체스 게임 구현을 위한 저장소

## 기능요구사항 - 1단계

- 콘솔 UI에서 체스 게임을 할 수 있는 기능을 구현한다.

- 1단계는 체스 게임을 할 수 있는 체스판을 초기화한다.

- 체스판에서 말의 위치 값은 가로 위치는 왼쪽부터 a ~ h이고, 세로는 아래부터 위로 1 ~ 8로 구현한다.

```
RNBQKBNR| 8 (rank 8) - Black(말)
PPPPPPPP| 7                       
........| 6
........| 5
........| 4
........| 3
pppppppp| 2
rnbqkbnr| 1 (rank 1) - White(말)
--------
abcdefgh (file - letter)

체스보드 색 : H8 - 검정 / A1 - 검정
선 : 화이트

piece - 피스
R - 룩
N - 나이트
B - 비숍
Q - 퀸
K - 킹
P - 폰
FreeSquare - 빈사각형 "."
```

- 체스판에서 각 진영은 검은색(대문자)과 흰색(소문자) 편으로 구분한다.

```
프로그램 실행 결과
체스 게임을 시작합니다.
게임 시작은 start, 종료는 end 명령을 입력하세요.
start
RNBQKBNR
PPPPPPPP
........
........
........
........
pppppppp
rnbqkbnr

end
```

## 기능목록 - 1단계

- 입력

  - [ ] start - 게임시작 / end - 게임 종료
  
    - [ ] 아니면 예외 발생
  
- 말

  - [X] 말 12개 캐싱
  
     - [X] 캐싱한 말만 사용
  
  - [X] 색깔 : Black / White

  - [X] 종류 : Rook / kNight / Bishop / Queen / King / Pawn
  
- 판

  - [ ] 가로 8 세로 8
  
  - [ ] 말 넣음 

- 출력

  - [ ] 판 출력함
  
  - [ ] 빈 것 '.' 처리