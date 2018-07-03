package com.example.sp;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
public class Block extends Update {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
