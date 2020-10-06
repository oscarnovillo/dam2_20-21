# Cosas que hay que saber:

- **Conexion a BASE DATOS** : 
    - connection String, hay que buscarlo.
    - La conexion se abre y se cierra en cada método del dao.
    - Los parametros de conexion siempre en fichero externo de configuración
    
- **Must y Must NOT General** :
    - A la capa DAO le llegan objetos y devuelve objetos.
    - Los parametros de las querys **NUNCA** se ponen al formar el string de la query
    - Nunca se hace un select y luego se filtra en java.
   

- **Selects**:
    - Como recuperar las filas de datos.
    

- **Insert**:
    - En un insert no se pone el AI
    - Hay que saber recuperar el AI de una fila recién insertada.

- **Insert, Update y Delete**:
    - Recuperar el número de filas alteradas
    - Tratar excepciones de FK.

- **Transacciones**:
    - Son siempre cuando hay varias querys de cambio de datos

