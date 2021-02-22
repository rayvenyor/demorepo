package com.demo.phms3.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name="client")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Client implements Serializable {

    private static final long serialVersionUID = -9215002687932954331L;

    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Id
    @Column(name = "project_id")
    private UUID id;

//    @NotNull
    @Column(name = "first_name")
    private String firstName;
//    @NotNull
    @Column(name = "last_name")
    private String lastName;
//    @Email
//    @NotNull
    @Column(name = "email")
    private String email;
//    @NotNull
    @Column(name = "gender")
    private String gender;
//    @NotNull
    @Column(name = "img")
    private String img;
//    @NotNull
    @Column(name = "card_type")
    private String cardType;
//    @NotNull
    @Column(name = "monthly_fee")
    private Double monthlyFee;
//    @NotNull
    @Column(name = "is_paid")
    private boolean isPaid;
//    @NotNull
    @Column(name = "is_inactive")
    private boolean isInactive;
//    @NotNull
    @Column(name = "city")
    private String city;

}

