# ControladorElevadoresSitemasOperativos

Proyecto dockerizado con imágen: maven:3.8.1-openjdk-11

### Requisitos:
- IntelliJ Idea
- Docker

Run:
`docker compose up`

API:
- Pasajeros path: http://localhost:8080/pasajero
- Body: {
  "nombre": "Juan",
  "peso": 80,
  "pisoActual": 3,
  "pisoObjetivo": 7,
  "tiempoInicio": 10
  }

ToDo:
- Definir que es el balanceo de uso de elevadores
- Sacar logs dentro de elevador
- Actualizar el piso Actual de los pasajeros en cada movimiento del elevador (Elevador)
- Elevador debe levantar mas pasajeros por piso

Done:
- ~~Terminar Logger~~
~~- Pasajero tiene que tener un tiempo. Momento en el cual realizará la llamada (LlamadosElevadoresManager)~~