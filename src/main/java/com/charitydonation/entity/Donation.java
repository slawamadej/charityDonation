package com.charitydonation.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "donations")
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantity;

    @ManyToMany(fetch=FetchType.LAZY)
    @JoinTable(name = "donationCategories")
    private List<Category> categories = new ArrayList<>();

    @ManyToOne
    private Institution institution;

    @ManyToOne
    private User user;

    private String street;

    private String city;

    private String zipCode;

    private String phone;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate pickUpDate;

    @DateTimeFormat(pattern = "kk:mm")
    private LocalTime pickUpTime;

    private String pickUpComment;

}
