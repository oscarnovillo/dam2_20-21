Feature: administracion de articulos

  Scenario: add articulo

    Given un periodico valido
    And un articulo con un autor que no existe en el sistema
    When se añade al articulo al peridico
    Then se añade el articulo al peridico en bd
    And el autor se crea en los autores

    Given un periodico valido
    And un articulo con un autor que existe en el sistema
    When se añade al articulo al peridico
    Then se añade el articulo al peridico en bd
    And el autor se enlaza al articulo

    Given un periodico valido
    And un articulo con un autor que existe en el sistema
    When se añade al articulo al peridico
    Then se añade el articulo al peridico en bd


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

  Scenario: delete articulo
    Given articulo que existe sin ser leido
    When se borrar el articulo
    Then se quita de base de datos

    Given articulo que existe leido
    When se borrar el articulo
    Then se pide confirmacion al usuario

    Given articulo que existe leido
    And confirmacion de borrar
    When se borrar el articulo
    Then se borrar el articulo de bd
    And se borrar las lecturas relacionadas de bd
