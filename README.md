# ControladorElevadoresSitemasOperativos

Proyecto dockerizado con imágen: maven:3.8.1-openjdk-11

### Requisitos:
- IntelliJ Idea
- Docker

Run:
`docker compose up`

API:
- Pasajeros path: http://localhost:8080/generadorCarga
- Body: [{
  "nombre": "Juan",
  "peso": 80,
  "pisoActual": 3,
  "pisoObjetivo": 7,
  "tiempoInicio": 10
  }]
