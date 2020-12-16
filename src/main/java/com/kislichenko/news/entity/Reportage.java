package com.kislichenko.news.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Table(name = "reportages")
public class Reportage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    private AppUser infoManager;

    @OneToOne(fetch = FetchType.EAGER)
    private AppUser reporter;

    @OneToOne(fetch = FetchType.EAGER)
    private News news;

    private Date startDate;
    private Date endDate;

    private String text;
    private String comment;

    @Column(nullable = false)
    private boolean confirm;

    private ReportageStatus status;
}
