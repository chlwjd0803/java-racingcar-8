package racingcar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import camp.nextstep.edu.missionutils.Randoms;
import camp.nextstep.edu.missionutils.Console;

public class Application {
    private HashMap<String, Integer> carPosition;
    private Integer tryCount;
    private Integer maxPosition;
    private List<String> winners;

    public Application(){
        carPosition = new HashMap<>();
        tryCount = 0;
        maxPosition = 0;
        winners = new ArrayList<>();
    }

    private void mapping(String name){
        // 이름은 4글자 이하만 가능
        if(!(name.length() <= 5))
            throw new IllegalArgumentException("이름 글자수가 초과하였습니다.");

        // 자동차 이름은 중복되어선 안됨
        if(carPosition.containsKey(name))
            throw new IllegalArgumentException("중복된 자동차 이름이 존재합니다.");
        carPosition.put(name, 0); // 모두 출발지점으로 초기화
    }

    private void moveCar(String name){
        // 랜덤 0~9에서 4이상의 숫자가 나올경우 전진
        if(Randoms.pickNumberInRange(0, 9) >= 4)
            carPosition.put(name, carPosition.get(name) + 1);

        // maxPosition값 갱신
        if(carPosition.get(name) > maxPosition)
            maxPosition = carPosition.get(name);
    }

    // 중간과정 출력
    private void printTryPosition(String name){
        System.out.print(name + " : ");

        for(int i = 0; i < carPosition.get(name); i++)
            System.out.print("-");

        System.out.println();
    }

    private void raceStart(String[] names){
        System.out.println("실행 결과");
        for(int i = 0; i < tryCount; i++) {
            for (String name : names) {
                moveCar(name);
                printTryPosition(name);
            }
            System.out.println();
        }
    }

    private void setWinners(String[] names){
        for(String name : names){
            if(carPosition.get(name).equals(maxPosition))
                winners.add(name);
        }
    }

    private void printWinners(){
        System.out.print("최종 우승자 : ");
        System.out.print(winners.get(0));
        for(int i = 1; i < winners.size(); i++)
            System.out.print(", " + winners.get(i));

    }

    public void run() {

        // 쉼표 기준으로 입력받기
        System.out.println("경주할 자동차 이름을 입력하세요.(이름은 쉼표(,) 기준으로 구분)");
        String input = Console.readLine();
        String[] names = input.split(",", -1);

        // 해시맵에 이름과 초기 위치 등록하기
        for(String name : names){
            if(name.isBlank()){
                throw new IllegalArgumentException("연속된 구분자를 입력하였거나, 빈 요소가 존재합니다.");
            }
            mapping(name);
        }

        // 시도할 횟수 입력받기
        System.out.println("시도할 횟수는 몇 회인가요?");
        try{
            tryCount = Integer.parseInt(Console.readLine());
        } catch (Exception e){
            throw new NumberFormatException("정수 입력 포맷이 일치하지 않습니다.");
        }
        if(tryCount < 0) throw new IllegalArgumentException("음의 값은 입력할 수 없습니다.");

        // 경기 시작
        raceStart(names);

        // 우승자 출력
        setWinners(names);
        printWinners();
    }

    public static void main(String[] args){
        new Application().run();
    }
}
