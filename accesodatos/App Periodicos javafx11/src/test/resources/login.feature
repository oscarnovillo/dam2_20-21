Feature:  Login

  Scenario: un usuario hace login en la aplicación

    Given un usuario y una password
    When es valido
    Then se pasa a pantalla de bienvenida

    Given un usuario logueado
    When se hace logout
    Then se muestra el login
    And se quita el usuario logueado cacheado

    Given un usuario y una password
    When no es valido
    Then se saca mensaje de advertencia

    Given un usuario logueado
    When es la primera vez que entra
    Then se le pide cambiar su contraseña

    Given administrador  logueado
    When se pasa a pantalla de bienvenida
    Then todos los menus estan habilitados

    Given lector logueado
    When se pasa a pantalla de bienvenida
    Then el menu de admin datos de lector esta visible
    And el menu de suscripciones esta visible
    And el menu de leer articulos esta visible

    Given administrador de periodico logueado
    When se pasa a pantalla de bienvenida
    Then el menu de periodico,
    And el menu crear articulos estan visibles

