package com.example.sp.domain;

import com.example.sp.web.rest.vm.UpdateVM;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Block {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String requestor;
    private String target;

    public Block(UpdateVM block) {
        this.requestor = block.getRequestor();
        this.target = block.getTarget();
    }

    public Block(String requestor, String target) {
        this.requestor = requestor;
        this.target = target;
    }
}
