Feature: suscripcion

  Scenario: Alta suscripcion

    Given un lector valido logueado
    And un periodico en el que no tiene suscripcion (no hay fecha_baja == null o no existe suscripcion alguna)
    When hace una suscripcion a ese periodico
    Then se suscribe al periodico con fecha baja = null

    Given un lector valido logueado
    And un periodico en el que tiene suscripcion
    When hace una suscripcion a ese periodico
    Then no se puede suscribir al periodico con fecha baja = null

    Scenario: ver suscripcion
      Given suscripciones
      When pido todas
      Then se muestran las activas , fecha_baja == null

    Scenario: borrar suscripcion
      Given un lector valido logueado
      And un periodico en el que tiene suscripcion
      When se borra la suscripcion a ese periodico
      Then fecha baja tendrá ese día como baja
      And el lector dejará de estar suscrito al periodico
