package com.example.bulletinboardapp.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@Entity
public class Content {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Size(max = 20)
    private String name;

    @NotBlank
    @Size(max = 200)
    private String comment;

    private Date createdDate;

    public Content(Long id, String name, String comment, Date createdDate) {
        this.id = id;
        this.name = name;
        this.comment = comment;
        this.createdDate = createdDate;
    }

    public Content(){};
}
