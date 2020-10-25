Feature:  Login

  Scenario: un usuario hace login en la aplicación

    Given un usuario y una password
    When es valido
    Then se pasa a pantalla de bienvenida

    Given un usuario y una password
    When no es valido
    Then se saca mensaje de advertencia

    Given administrador  logueado
    When se pasa a pantalla de bienvenida
    Then todos los menus estan habilitados

    Given lector logueado
    When se pasa a pantalla de bienvenida
    Then el menus de admin datos de lector esta visible
    And el menu de subscripciones esta visible
    And el menu de leer articulos esta visible

    Given administrador de periodico logueado
    When se pasa a pantalla de bienvenida
    Then solo los menus de periodico, subscripciones, leer articulos estan visibles

