package com.worch.exceptions;


import lombok.Getter;

/**
 * Исключение, выбрасываемое, когда предоставлено недопустимое значение для типа перечисления.
 */
public class InvalidEnumValueException extends RuntimeException {

  /**
   * -- GETTER -- Возвращает недопустимое значение, вызвавшее это исключение.
   *
   * @return недопустимое значение
   */
  @Getter
  private final String invalidValue;
  private final Class<?> enumClass;

  /**
   * Конструирует новое исключение InvalidEnumValueException с указанным сообщением, недопустимым
   * значением и классом перечисления.
   *
   * @param message      сообщение, описывающее причину исключения
   * @param invalidValue недопустимое значение, вызвавшее исключение
   * @param enumClass    класс перечисления, к которому принадлежит недопустимое значение
   */
  public InvalidEnumValueException(String message, String invalidValue, Class<?> enumClass) {
    super(message);
    this.invalidValue = invalidValue;
    this.enumClass = enumClass;
  }

  /**
   * Возвращает класс перечисления, к которому принадлежит недопустимое значение.
   *
   * @return класс перечисления
   */
  public Class<? extends Enum<?>> getEnumClass() {
    return (Class<? extends Enum<?>>) enumClass;
  }
}

