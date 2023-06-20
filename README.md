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
- Diagrama de flujo para documentacion (Lucid)



Done:
- ~~Terminar Logger~~
- ~~- Pasajero tiene que tener un tiempo. Momento en el cual realizará la llamada (LlamadosElevadoresManager)~~
- ~~- - Elevador debe levantar mas pasajeros por piso~~
- ~~- Sacar logs dentro de elevador~~
- ~~- Definir que es el balanceo de uso de elevadores~~
- ~~Actualizar el piso Actual de los pasajeros en cada movimiento del elevador (Elevador)~~
~~- Tracker cantidad de viajes por elevador~~
~~- Analizar update en Manager para que reordene (segun que criterio?)~~
~~- Clean up en clase de Elevador y Pasajero~~