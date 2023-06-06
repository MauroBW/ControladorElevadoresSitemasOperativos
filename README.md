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
  "pisoActual": 3,
  "pisoObjetivo": 7
  }

ToDo:
- Pasajero tiene que tener un tiempo. Momento en el cual realizará la llamada
- Definir que es el balanceo de uso de elevadores
- ~~Terminar Logger~~
- Sacar logs dentro de elevador