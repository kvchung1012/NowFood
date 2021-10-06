package com.cntt2.nowfood.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

/**
 * @author Vanh
 * @version 1.0
 * @date 10/6/2021 12:22 AM
 */
@MappedSuperclass
@Getter
@Setter
public class BaseObject{
    private static final long serialVersionUID = 1L;
    @Id
    @Type(
            type = "uuid-char"
    )
    @Column(
            name = "id",
            unique = true,
            nullable = false
    )
    private UUID id;
    @Column(
            name = "uuid_key",
            nullable = true
    )
    @Type(
            type = "uuid-char"
    )
    private UUID uuidKey;
    @Column(
            name = "voided",
            nullable = true
    )
    private Boolean voided;
    public BaseObject() {
        if (this.id == null) {
            this.id = UUID.randomUUID();
        }

        this.uuidKey = UUID.randomUUID();
    }

}
