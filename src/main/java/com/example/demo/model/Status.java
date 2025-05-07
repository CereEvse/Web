package com.example.demo.model;

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
public class Status {

    @Id
    @Column(name="id_status")
    @GeneratedValue(generator = "id_status_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "id_status_seq", sequenceName = "id_status_seq", initialValue = 1, allocationSize = 1)
    private Long idStatus;

    @Column(name="status_title")
    private String statusTitle;

    @JsonIgnore
    @OneToMany(mappedBy = "status")
    private List<Resume> resumes;

//    @Enumerated
//    private StatusList statusList;

    public Status(String statusTitle) {
        this.statusTitle = statusTitle;
    }

    //public Status() {
    //}
}
