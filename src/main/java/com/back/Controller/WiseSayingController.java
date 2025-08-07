package com.back.Controller;

import com.back.Service.WiseSayingService;
import com.back.domain.WiseSaying;

import java.util.List;
import java.util.Scanner;

public class WiseSayingController {
    private final Scanner sc;
    private final WiseSayingService service = new WiseSayingService();

    public WiseSayingController(Scanner sc) {
        this.sc = sc;
    }

    public void register() {
        System.out.print("명언 : ");
        String content = sc.nextLine();
        System.out.print("작가 : ");
        String author = sc.nextLine();

        WiseSaying ws = service.register(content, author);
        System.out.println(ws.getId() + "번 명언이 등록되었습니다.");
    }

    public void list() {
        List<WiseSaying> list = service.findAll();
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");
        for (int i = list.size() - 1; i >= 0; i--) {
            WiseSaying ws = list.get(i);
            System.out.println(ws.getId() + " / " + ws.getAuthor() + " / " + ws.getContent());
        }
    }

    public void delete(String cmd) {
        int id = Integer.parseInt(cmd.substring("삭제?id=".length()));
        boolean result = service.delete(id);
        if (result) {
            System.out.println(id + "번 명언이 삭제되었습니다.");
        } else {
            System.out.println(id + "번 명언은 존재하지 않습니다.");
        }
    }

    public void modify(String cmd) {
        int id = Integer.parseInt(cmd.substring("수정?id=".length()));
        WiseSaying target = service.findById(id);

        if (target == null) {
            System.out.println(id + "번 명언은 존재하지 않습니다.");
            return;
        }

        System.out.println("명언(기존) : " + target.getContent());
        System.out.print("명언 : ");
        String newContent = sc.nextLine();

        System.out.println("작가(기존) : " + target.getAuthor());
        System.out.print("작가 : ");
        String newAuthor = sc.nextLine();

        service.modify(id, newContent, newAuthor);
        System.out.println(id + "번 명언이 수정되었습니다.");
    }

    public void build() {
        service.build();
        System.out.println("data.json 파일의 내용이 갱신되었습니다.");
    }
}