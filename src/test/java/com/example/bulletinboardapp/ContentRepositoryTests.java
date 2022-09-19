package com.example.bulletinboardapp;


import com.example.bulletinboardapp.model.Content;
import com.example.bulletinboardapp.repository.ContentRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Date;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class ContentRepositoryTests {
    @Autowired private ContentRepository repository;

    @Test
    public void testAddNew() {
        Content content =  new Content();
        content.setName("biki");
        content.setComment("something");
        content.setCreatedDate(new Date());

        Content savedContent = repository.save(content);

        Assertions.assertThat(savedContent).isNotNull();
        Assertions.assertThat(savedContent.getId()).isGreaterThan(0);
    }
}
