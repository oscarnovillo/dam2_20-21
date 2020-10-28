Feature: administracion de articulos

  Scenario: add articulo

    Given un periodico valido
    And un articulo con un autor que no existe en el sistema
    When se añade al articulo al peridico
    Then se añade el articulo al peridico en bd
    And el autor se crea en los autores

  Scenario: update articulo

  Scenario: leer articulo

  Scenario: rating articulo

    Given un lector valido
    And un periodico con el lector suscrito
    And un articulo que no tiene rating del lector
    When el lector añade rating al articulo
    Then el rating se añade a la base de datos

    Given un lector valido
    And un periodico con el lector suscrito
    And un articulo que tiene rating del lector
    When el lector hace rating al articulo
    Then el rating se actualiza en la base de datos