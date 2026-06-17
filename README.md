# TravelAgency - Microservicios

Plataforma de paquetes turísticos desarrollada con arquitectura de microservicios.

## Estructura

| Servicio | Puerto | Descripción |
|----------|--------|-------------|
| config-service | 8888 | Spring Cloud Config Server |
| eureka-service | 8761 | Service Discovery |
| api-gateway | 8080 | API Gateway (NodePort en K8s) |
| ms-usuarios | 0 (dinámico) | Épica 1 - Usuarios y Keycloak |
| ms-paquetes | 0 (dinámico) | Épica 2 - Paquetes turísticos |
| ms-busqueda | 0 (dinámico) | Épica 3 - Búsqueda de paquetes |
| ms-reservas | 0 (dinámico) | Épica 4 - Reservas y descuentos |
| ms-pagos | 0 (dinámico) | Épica 5 - Pagos simulados |
| ms-seguimiento | 0 (dinámico) | Épica 6 - Seguimiento de reservas |
| ms-reportes | 0 (dinámico) | Épica 7 - Reportes |
| frontend | 3000 | React + Vite |

## Orden de arranque local

1. config-service
2. eureka-service
3. api-gateway
4. ms-usuarios
5. ms-paquetes
6. ms-busqueda
7. ms-reservas
8. ms-pagos
9. ms-seguimiento
10. ms-reportes
11. frontend

## Bases de datos PostgreSQL requeridas

`sql
CREATE DATABASE ta_ms_usuarios;
CREATE DATABASE ta_ms_paquetes;
CREATE DATABASE ta_ms_busqueda;
CREATE DATABASE ta_ms_reservas;
CREATE DATABASE ta_ms_pagos;
CREATE DATABASE ta_ms_seguimiento;
CREATE DATABASE ta_ms_reportes;
`

## GitHub

Repo: https://github.com/dialtamiranoh/usach-mingeso-travelagency-microservices
