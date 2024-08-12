package com.subrutin.catalog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Author {
  private Long id;
  private String name;
  private Long dateOfBirth;
}
