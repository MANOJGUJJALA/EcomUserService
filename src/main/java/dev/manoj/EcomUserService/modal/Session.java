package dev.manoj.EcomUserService.modal;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


import java.util.Date;

@Entity
@Getter
@Setter

public class Session extends BaseClass{
    private String token;
    private Date expiryAt;
    private Date loginAt;

    @ManyToOne
    private User user;

    @Enumerated(EnumType.ORDINAL)
    private SessionStatus sessionStatus;


}
