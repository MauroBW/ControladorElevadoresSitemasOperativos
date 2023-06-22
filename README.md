# ControladorElevadoresSistemasOperativos

### Controlador de Elevadores realizado para tarea: Obligatiorio Sistemas Operativos 2023

Proyecto dockerizado con imágen: `maven:3.8.1-openjdk-11`

### Requisitos:
- IntelliJ Idea
- Java 11
- Docker



## Run

El proyecto cuenta con un Makefile para ejecutarlo completo
```
make run
```

En caso de contar con docker instalado:
```
docker compose up
```

### API: 
Al ejecutar el controlador también se ejecuta la aplicación **Spring Boot** la cuál expone un endpoint en el puerto 8080.
- Pasajeros path: http://localhost:8080/generadorCarga
- Body: [{
  "nombre": "Juan",
  "peso": 80,
  "pisoActual": 3,
  "pisoObjetivo": 7,
  "tiempoInicio": 10
  }]

### Simulaciones
En la clase **Main** se puede modificar la simulación que se desee ejecutar.
Actualmente se cuenta con 3 simulaciones contempladas: 
- LOAD_TEST
- STRESS_TEST
- SOAK_TEST

### Velocidad
En la clase **Main** se encuentra la variable que establece la velocidad para 
la ejecución de la simulación. Se cuenta con 3 velocidades preestablecidas
- SLOW_RUN -> Instante: 1s
- MID_RUN -> Instante: 500ms
- FAST_RUN -> Instante: 100ms