# TravelAgency - Microservicios

Plataforma de paquetes turisticos con arquitectura de microservicios.
USACH - Metodos de Ingenieria de Software 2026-1.

## Stack
- Spring Boot 3.2.5 + Gradle + Java 21
- Spring Cloud 2023.0.1 (Config Server, Eureka, Gateway)
- PostgreSQL
- React + Vite
- Docker + Kubernetes (minikube)

## Servicios

| Servicio | Puerto | Descripcion |
|----------|--------|-------------|
| config-service | 8888 | Spring Cloud Config |
| eureka-service | 8761 | Service Discovery |
| api-gateway | 8080 | API Gateway |
| ms-usuarios | 0 | Epica 1 |
| ms-paquetes | 0 | Epica 2 y 3 |
| ms-busqueda | 0 | Epica 3 |
| ms-reservas | 0 | Epica 4 |
| ms-pagos | 0 | Epica 5 |
| ms-seguimiento | 0 | Epica 6 |
| ms-reportes | 0 | Epica 7 |

## Bases de datos

`sql
CREATE DATABASE ta_ms_usuarios;
CREATE DATABASE ta_ms_paquetes;
CREATE DATABASE ta_ms_busqueda;
CREATE DATABASE ta_ms_reservas;
CREATE DATABASE ta_ms_pagos;
CREATE DATABASE ta_ms_seguimiento;
CREATE DATABASE ta_ms_reportes;
`

## Repo
https://github.com/dialtamiranoh/usach-mingeso-travelagency-microservices
