package com.back;

import com.back.Controller.WiseSayingController;

import java.util.Scanner;

public class App {
    private final Scanner sc = new Scanner(System.in);
    private final WiseSayingController controller = new WiseSayingController(sc);

    public void run() {
        System.out.println("== 명언 앱 ==");

        while (true) {
            System.out.print("명령) ");
            String cmd = sc.nextLine().trim();

            if (cmd.equals("종료")) break;

            if (cmd.equals("등록")) {
                controller.register();
            } else if (cmd.equals("목록")) {
                controller.list();
            } else if (cmd.startsWith("삭제?id=")) {
                controller.delete(cmd);
            } else if (cmd.startsWith("수정?id=")) {
                controller.modify(cmd);
            } else if (cmd.equals("빌드")) {
                controller.build();
            } else {
                System.out.println("잘못된 명령어입니다.");
            }
        }

        sc.close();
    }
}
