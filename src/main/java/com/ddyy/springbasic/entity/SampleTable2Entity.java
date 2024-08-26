package com.ddyy.springbasic.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "st2")
@Table(name = "sample_table_2")
public class SampleTable2Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)             // - GenerationType.IDENTITY: auto_increment 전략 선택
    private Integer samplaeAi;
    private Boolean sampleColumn;
}
