package com.worch.util;


import java.util.Arrays;

/**
 * Утилитарный класс для работы с перечислениями (Enums).
 */
public class EnumUtil {

  public static String getEnumValues(Class<? extends Enum<?>> enumClass) {
    return String.join(", ",
        Arrays.stream(enumClass.getEnumConstants())
            .map(Enum::name)
            .toList()
    );
  }
}

