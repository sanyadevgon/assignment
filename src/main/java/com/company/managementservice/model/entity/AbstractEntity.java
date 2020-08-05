package com.company.managementservice.model.entity;

import com.company.managementservice.constant.Constants;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractEntity<T> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private T id;


    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String createdBy;

    private String updatedBy;

    @PrePersist
    protected void onCreate() {
        this.createdBy=Constants.ADMIN;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedBy=Constants.ADMIN;
        this.updatedAt = LocalDateTime.now();
    }

    public AbstractEntity<T> setId(T id) {
        this.id = id;
        return this;
    }

}
