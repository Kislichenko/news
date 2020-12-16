package com.kislichenko.news.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "news")
@ToString
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(columnDefinition = "TEXT", unique = true)
    private String article;

    @Column(columnDefinition = "TEXT")
    private String body;

    @Column(nullable = false)
    private boolean bad;

    @Column(nullable = false)
    private boolean realization;

    @OneToOne(fetch = FetchType.EAGER)
    private AppUser infoManager;
}
