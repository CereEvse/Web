package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

@ToString
@AllArgsConstructor
//@NoArgsConstructor
@Getter
@Setter
@Entity(name="resume")
@Table(name="resume")
public class Resume {

    @Id
    @Column(name="id_resume")
    @GeneratedValue(generator = "id_resume_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "id_resume_seq", sequenceName = "id_resume_seq", initialValue = 1, allocationSize = 1)
    private Long idResume;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_status")
    private Status status;

    @Column(name="work_experience")
    private Integer workExperience;

    @Column(name="portfolio")
    private String portfolio;

    @Column(name="skills")
    private String skills;

    @Column(name="comment")
    private String comment;

    public Resume(Integer workExperience, String portfolio, String skills, String comment, Status status) {
        this.workExperience = workExperience;
        this.portfolio = portfolio;
        this.skills = skills;
        this.comment = comment;
        this.status = status;
    }

    public Resume() {
    }

}
