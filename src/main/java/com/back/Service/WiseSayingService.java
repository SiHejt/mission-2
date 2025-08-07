package com.back.Service;

import com.back.Repository.WiseSayingRepository;
import com.back.domain.WiseSaying;

import java.util.List;

public class WiseSayingService {
    private final WiseSayingRepository repository = new WiseSayingRepository();
    private final List<WiseSaying> sayings;

    public WiseSayingService() {
        this.sayings = repository.loadAll();
    }

    public WiseSaying register(String content, String author) {
        WiseSaying ws = new WiseSaying(content, author);
        sayings.add(ws);
        repository.save(ws);
        return ws;
    }

    public List<WiseSaying> findAll() {
        return sayings;
    }

    public boolean delete(int id) {
        for (int i = 0; i < sayings.size(); i++) {
            if (sayings.get(i).getId() == id) {
                sayings.remove(i);
                repository.delete(id);
                return true;
            }
        }
        return false;
    }

    public WiseSaying findById(int id) {
        return sayings.stream()
                .filter(ws -> ws.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void modify(int id, String content, String author) {
        WiseSaying target = findById(id);
        if (target != null) {
            target.setContent(content);
            target.setAuthor(author);
            repository.save(target);
        }
    }

    public void build() {
        repository.buildDataJson(sayings);
    }
}
