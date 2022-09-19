package com.example.bulletinboardapp.config;

import com.example.bulletinboardapp.model.Content;
import com.example.bulletinboardapp.repository.ContentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DataLoader implements ApplicationRunner {
    private final ContentRepository repository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("アプリの起動時にデータ等をセットするのはここに書く");
//        var content = new Content();
//        content.setName("biki");
//        content.setComment("test comment");
//        repository.save(content);
    }

}
