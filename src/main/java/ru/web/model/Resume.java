package ru.web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name="resume")
@Table(name="resume")
public class Resume {
    
    @Id
    @Column(name="id_resume")
    @GeneratedValue(generator = "id_addr_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "id_addr_seq", sequenceName = "id_addr_seq", initialValue = 1, allocationSize = 1)
    private Long idResume;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "id_statusResume")
    private StatusResume statusResume;

    @Column(name="work_experience")
    private int work_experience;

    @Column(name="portfolio")
    private String portfolio;

    @Column(name="skills")
    private String skills;

    @Column(name="comment")
    private String comment;
}
