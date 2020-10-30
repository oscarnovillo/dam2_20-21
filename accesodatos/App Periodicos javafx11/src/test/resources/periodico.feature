Feature: admin periodicos

  Scenario: add un peridico

    Given un periodico valido
    And un administrador que no exista
    When añado el periodico
    Then se añade el administrador como usuario
    And se añade el peridico

    Given un periodico valido
    And un administrador que exista
    When añado el periodico
    Then no se añade el periodico


  Scenario: update periodico


  Scenario: delete periodico
    Given periodico que existe sin nada relacionado
    When se borrar el periodico
    Then se quita de base de datos

    Given periodico que existe con datos relacionado
    When se borrar el peridico
    Then no se borra de base de datos


  Scenario: ver periodicos