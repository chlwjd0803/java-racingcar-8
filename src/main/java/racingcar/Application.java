package racingcar;

import java.util.HashMap;
import camp.nextstep.edu.missionutils.Randoms;
import camp.nextstep.edu.missionutils.Console;

public class Application {
    private static HashMap<String, Integer> carPosition = new HashMap<>();
    private static Integer tryCount;

    private static void mapping(String name){
        if(!(name.length() < 5))
            throw new IllegalArgumentException("이름 글자수가 초과하였습니다.");
        carPosition.put(name, 0);
    }


    public static void main(String[] args) {
        // TODO: 프로그램 구현

        // 쉼표 기준으로 입력받기, 이름은 5자 이하만 가능

        System.out.println("경주할 자동차 이름을 입력하세요.(이름은 쉼표(,) 기준으로 구분)");
        String input = Console.readLine();
        String[] names = input.split(",");

        for(String name : names)
            mapping(name);


        // 시도할 횟수 입력받기 (가장 바깥쪽 반복문)
        System.out.println("시도할 횟수는 몇 회인가요?");
        try{
            tryCount = Integer.parseInt(Console.readLine());
        } catch (Exception e){
            throw new NumberFormatException("숫자가 아닙니다.");
        }



        // 안쪽 반복문은 각 차들의 전진여부를 수정하면 됨



        // 랜덤 0~9에서 4이상의 숫자가 나올경우 전진 else 정지


        // 우승자가 여러명 가능, 쉼표 구분하여 출력


        // 잘못된 값을 입력할 경우 IllegalArgumentException 발생



    }
}
