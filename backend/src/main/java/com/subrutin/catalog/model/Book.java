package com.subrutin.catalog.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class Book implements Serializable {
  private Long id;
  private String title;
  private String description;
  private Author author;
}
