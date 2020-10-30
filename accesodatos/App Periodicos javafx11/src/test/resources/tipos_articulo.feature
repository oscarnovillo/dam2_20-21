Feature: admin tipos arituclos

  Scenario: add un tipo de articulo

  Scenario: update un tipo de articulo


  Scenario: delete tipo articulo
    Given tipo articulo que existe sin articulos relacionado
    When se borrar el articulo
    Then se quita de base de datos

    Given tipo articulo que existe con articulos relacionado
    When se borrar el articulo
    Then no se borrar de base de datos

  Scenario: ver tipos articulos