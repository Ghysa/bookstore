package com.capgemini.bookstore.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "book_table")
public class Book {

    @Id
    @GeneratedValue
    private Integer id;
    private String title;
    private String author;
    private LocalDate releaseDate;
    private BigDecimal price;
}
