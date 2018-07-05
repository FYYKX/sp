package com.example.sp.domain;

import com.example.sp.web.rest.vm.UpdateVM;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Subscribe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String requestor;
    private String target;

    public Subscribe(UpdateVM updateVM) {
        this.requestor = updateVM.getRequestor();
        this.target = updateVM.getTarget();
    }
}
