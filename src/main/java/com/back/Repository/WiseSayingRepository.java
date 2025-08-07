package com.back.Repository;

import com.back.domain.WiseSaying;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class WiseSayingRepository {
    private static final String baseDir = "db/wiseSaying";

    public WiseSayingRepository() {
        File dir = new File(baseDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public void save(WiseSaying ws) {
        try (FileWriter fw = new FileWriter(baseDir + "/" + ws.getId() + ".json")) {
            fw.write("{\n");
            fw.write("  \"id\": " + ws.getId() + ",\n");
            fw.write("  \"content\": \"" + escape(ws.getContent()) + "\",\n");
            fw.write("  \"author\": \"" + escape(ws.getAuthor()) + "\"\n");
            fw.write("}");
            saveLastId(WiseSaying.getLastId());
        } catch (IOException e) {
            System.out.println("저장 실패: " + e.getMessage());
        }
    }

    public void delete(int id) {
        File file = new File(baseDir + "/" + id + ".json");
        if (file.exists()) file.delete();
    }

    public List<WiseSaying> loadAll() {
        List<WiseSaying> list = new ArrayList<>();
        File dir = new File(baseDir);
        File[] files = dir.listFiles();

        if (files == null) return list;

        for (File file : files) {
            if (file.getName().endsWith(".json")) {
                WiseSaying ws = load(file);
                if (ws != null) {
                    list.add(ws);
                    if (ws.getId() > WiseSaying.getLastId()) {
                        WiseSaying.setLastId(ws.getId());
                    }
                }
            }
        }
        return list;
    }

    private WiseSaying load(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            int id = 0;
            String content = "";
            String author = "";

            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("\"id\"")) {
                    id = Integer.parseInt(line.split(":")[1].replace(",", "").trim());
                } else if (line.startsWith("\"content\"")) {
                    content = line.split(":")[1].trim().replaceAll("^\"|\",?$", "");
                } else if (line.startsWith("\"author\"")) {
                    author = line.split(":")[1].trim().replaceAll("^\"|\"$", "");
                }
            }

            WiseSaying ws = new WiseSaying(content, author);
            ws.setId(id);
            return ws;
        } catch (IOException | NumberFormatException e) {
            System.out.println("파일 읽기 실패: " + file.getName());
            return null;
        }
    }

    private void saveLastId(int id) {
        try (FileWriter fw = new FileWriter(baseDir + "/lastId.txt")) {
            fw.write(id + "");
        } catch (IOException e) {
            System.out.println("lastId 저장 실패");
        }
    }

    private String escape(String str) {
        return str.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    public void buildDataJson(List<WiseSaying> list) {
        try (FileWriter fw = new FileWriter(baseDir + "/data.json")) {
            fw.write("[\n");

            for (int i = 0; i < list.size(); i++) {
                WiseSaying ws = list.get(i);
                fw.write("  {\n");
                fw.write("    \"id\": " + ws.getId() + ",\n");
                fw.write("    \"content\": \"" + escape(ws.getContent()) + "\",\n");
                fw.write("    \"author\": \"" + escape(ws.getAuthor()) + "\"\n");
                fw.write("  }");
                fw.write((i != list.size() - 1) ? ",\n" : "\n");
            }

            fw.write("]");
        } catch (IOException e) {
            System.out.println("data.json 생성 실패: " + e.getMessage());
        }
    }
}