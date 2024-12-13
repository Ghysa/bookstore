package com.capgemini.bookstore.entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "item_table")
public class Item {

    @Id
    @GeneratedValue
    private Integer id;

    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;
}
