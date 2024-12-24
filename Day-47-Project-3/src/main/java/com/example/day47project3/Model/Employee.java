package com.example.day47project3.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Employee {

    @Id
    private Integer id;

    @Column(nullable = false)
    private String position;

    @Column(nullable = false)
    private Double salary;

    @OneToOne
    @MapsId
    @JsonIgnore
    private MyUser myUser;


}
