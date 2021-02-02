# Sala de Chat - Cliente Servidor
Sala de chat administrada por un servidor en la cual pueden unirse múltiplos clientes.
  
## Especificaciones
El servidor se abre dentro de un puerto colocado por el usuario.
Una vez iniciado el servidor, los clientes deben especificar la IP y el respectivo puerto para unirse. También deben colocar un identificador, como el nombre, que los diferencie del resto de los usuarios dentro del chat.

## Restricciones
Para que los clientes puedan unirse a una sala, el servidor debe haber iniciado antes y ponerse a la espera de ellos.
Por cada puerto solo puede haber una sala de chat.
No puede haber más de un usuario con el mismo nombre.

## Nota
Para ejecutar el sistema se debe ir a " src > app.progredes > AppCliente.java / AppServidor.java "

