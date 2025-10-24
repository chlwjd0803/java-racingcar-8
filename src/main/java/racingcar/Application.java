package racingcar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import camp.nextstep.edu.missionutils.Randoms;
import camp.nextstep.edu.missionutils.Console;

public class Application {
    private HashMap<String, Integer> carPosition; // 각 차량의 이름과 위치
    private Integer tryCount; // 경기에서 자동차들이 총 전진 여부를 결정하는 횟수
    private Integer maxPosition; // 우승 자동차들의 위치(여러명일 수 있음)
    private List<String> winners; // 우승 자동차들의 이름

    /**
     *  각 인스턴스 별 필드 초기화
     */
    public Application(){
        carPosition = new HashMap<>();
        tryCount = 0;
        maxPosition = 0;
        winners = new ArrayList<>();
    }

    /**
     * 등록된 차량 이름을 HashMap에 삽입한다.
     * @param name 차량 이름
     */
    private void mapping(String name){
        // 이름은 4글자 이하만 가능
        if(!(name.length() <= 5))
            throw new IllegalArgumentException("이름 글자수가 초과하였습니다.");

        // 자동차 이름은 중복되어선 안됨
        if(carPosition.containsKey(name))
            throw new IllegalArgumentException("중복된 자동차 이름이 존재합니다.");
        carPosition.put(name, 0); // 모두 출발지점으로 초기화
    }

    /**
     * 해당 차량에 대해서 전진 여부를 결정하는 메소드이다.
     * @param name 차량 이름
     */
    private void moveCar(String name){
        // 랜덤 0~9에서 4이상의 숫자가 나올경우 전진
        if(Randoms.pickNumberInRange(0, 9) >= 4)
            carPosition.put(name, carPosition.get(name) + 1);

        // maxPosition값 갱신
        if(carPosition.get(name) > maxPosition)
            maxPosition = carPosition.get(name);
    }

    /**
     * 한번의 시도마다 해당 차량의 현재 전진 위치를 표시해주는 메소드이다.
     * @param name 차량 이름
     */
    private void printTryPosition(String name){
        System.out.print(name + " : ");

        for(int i = 0; i < carPosition.get(name); i++)
            System.out.print("-");

        System.out.println();
    }

    /**
     * 자동차들의 경기를 시작하는 메소드이다.
     * @param names 경기에 참여하는 모든 차량들의 이름
     */
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

    /**
     * 저장된 최대 전진 거리를 이용하여 우승 차량(들)을 찾는다.
     * @param names 경기에 참여한 모든 차량들의 이름
     */
    private void setWinners(String[] names){
        for(String name : names){
            if(carPosition.get(name).equals(maxPosition))
                winners.add(name);
        }
    }

    /**
     * 최종 우승자(들)를 출력한다.
     * 우승자는 최소 한명으므로 winners에서 첫번째 원소는 반드시 꺼낸다.
     * 중복될 경우 다음 인덱스인 1부터 존재하므로 반복문의 구성이 다음과 같이 된다.
     */
    private void printWinners(){
        System.out.print("최종 우승자 : ");
        System.out.print(winners.get(0));
        for(int i = 1; i < winners.size(); i++)
            System.out.print(", " + winners.get(i));

    }

    /**
     * 자동차 경주 한 경기의 시작 메소드이다.
     */
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

    /**
     * 프로그램의 시작 메소드이다.
     * @param args 필요시 입력받는 인자들
     */
    public static void main(String[] args){
        new Application().run();
    }
}
