package com.example.sp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Subscribe extends Update {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
