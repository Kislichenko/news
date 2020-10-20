package com.kislichenko.news.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Table(name = "requests")
public class ReqData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String subject;
    private Date startDate;
    private Date endDate;
    private String legalData;

    @Column(nullable = false)
    private boolean payed;

    @Column(nullable = false)
    private boolean confirm;

    @Column(columnDefinition = "TEXT")
    private String wishes;

    @Column(unique = true)
    private Date creationDate;

    private AdBlockTypes type;

    @OneToOne(fetch = FetchType.EAGER)
    private AppUser creator;
}
