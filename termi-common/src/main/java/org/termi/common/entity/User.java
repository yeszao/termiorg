package org.termi.common.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {
    private String username;
    private String email;
    private String password;
}