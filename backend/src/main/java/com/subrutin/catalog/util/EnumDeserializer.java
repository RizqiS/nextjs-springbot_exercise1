package com.subrutin.catalog.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class EnumDeserializer<T extends Enum<T>> extends JsonDeserializer<T> {

  private final Class<T> enumClass;

  protected EnumDeserializer(Class<T> enumClass) {
    this.enumClass = enumClass;
  }

  @Override
  public T deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
    String valueDeserialize = p.getText().trim();

    if (valueDeserialize.isEmpty()) {
      return null;
    }

    try {
      return Enum.valueOf(enumClass, valueDeserialize.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new IOException("Invalid value for MovieStatus: " + valueDeserialize, e);
    }
  }

}
