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
@Entity(name="status")
@Table(name="status")
public class StatusResume {

    @Id
    @Column(name="id_statusResume")
    @GeneratedValue(generator = "id_addr_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "id_addr_seq", sequenceName = "id_addr_seq", initialValue = 1, allocationSize = 1)
    private Long idStatusResume;

    @Column(name="status")
    private String status;

    @JsonIgnore
    @OneToMany(mappedBy = "statusResume")
    private List<Resume> resumes;
}
