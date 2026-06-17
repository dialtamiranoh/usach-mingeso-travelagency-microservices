-- ============================================================
-- ms-paquetes - Datos iniciales
-- Ejecutar DESPUÉS de levantar ms-paquetes (Spring crea tablas)
-- psql -U postgres -d ta_ms_paquetes -f init_ms_paquetes.sql
-- ============================================================

-- STATUSES (solo los de ms-paquetes)
INSERT INTO statuses (created_at, entity_type, name) VALUES
(NOW(), 'PACKAGE', 'AVAILABLE'),
(NOW(), 'PACKAGE', 'SOLD_OUT'),
(NOW(), 'PACKAGE', 'CANCELLED'),
(NOW(), 'PROMOTION', 'ACTIVE'),
(NOW(), 'PROMOTION', 'INACTIVE'),
(NOW(), 'PROMOTION', 'EXPIRED'),
(NOW(), 'CATEGORY', 'ACTIVE'),
(NOW(), 'CATEGORY', 'INACTIVE'),
(NOW(), 'SEASON', 'ACTIVE'),
(NOW(), 'SEASON', 'INACTIVE'),
(NOW(), 'DESTINATION', 'ACTIVE'),
(NOW(), 'DESTINATION', 'INACTIVE'),
(NOW(), 'PACKAGE_TYPE', 'ACTIVE'),
(NOW(), 'PACKAGE_TYPE', 'INACTIVE'),
(NOW(), 'SERVICE', 'ACTIVE'),
(NOW(), 'SERVICE', 'INACTIVE');

-- CATEGORIAS
INSERT INTO categories (created_at, name, description, status_id) VALUES
(NOW(), 'ADVENTURE', 'Paquetes de aventura y actividades al aire libre', (SELECT id FROM statuses WHERE entity_type='CATEGORY' AND name='ACTIVE')),
(NOW(), 'CITY ESCAPE', 'Escapadas urbanas y turismo cultural', (SELECT id FROM statuses WHERE entity_type='CATEGORY' AND name='ACTIVE')),
(NOW(), 'FOOD', 'Turismo gastronomico y culinario', (SELECT id FROM statuses WHERE entity_type='CATEGORY' AND name='ACTIVE')),
(NOW(), 'RELAX', 'Paquetes de descanso y bienestar', (SELECT id FROM statuses WHERE entity_type='CATEGORY' AND name='ACTIVE'));

-- TIPOS DE PAQUETE
INSERT INTO package_types (created_at, name, status_id) VALUES
(NOW(), 'NACIONAL', (SELECT id FROM statuses WHERE entity_type='PACKAGE_TYPE' AND name='ACTIVE')),
(NOW(), 'INTERNACIONAL', (SELECT id FROM statuses WHERE entity_type='PACKAGE_TYPE' AND name='ACTIVE'));

-- TEMPORADAS
INSERT INTO seasons (created_at, name, status_id) VALUES
(NOW(), 'VERANO', (SELECT id FROM statuses WHERE entity_type='SEASON' AND name='ACTIVE')),
(NOW(), 'PRIMAVERA', (SELECT id FROM statuses WHERE entity_type='SEASON' AND name='ACTIVE')),
(NOW(), 'OTONO', (SELECT id FROM statuses WHERE entity_type='SEASON' AND name='ACTIVE')),
(NOW(), 'INVIERNO', (SELECT id FROM statuses WHERE entity_type='SEASON' AND name='ACTIVE'));

-- DESTINOS
INSERT INTO destinations (created_at, name, description, status_id) VALUES
(NOW(), 'Ciudad de Mexico, Mexico', 'Capital cultural y gastronomica de Mexico', (SELECT id FROM statuses WHERE entity_type='DESTINATION' AND name='ACTIVE')),
(NOW(), 'Paris, Francia', 'La ciudad de la luz y el amor', (SELECT id FROM statuses WHERE entity_type='DESTINATION' AND name='ACTIVE')),
(NOW(), 'San Pedro de Atacama, Chile', 'Desierto mas arido del mundo con paisajes unicos', (SELECT id FROM statuses WHERE entity_type='DESTINATION' AND name='ACTIVE')),
(NOW(), 'Santiago, Chile', 'Capital de Chile con vida urbana y cultural', (SELECT id FROM statuses WHERE entity_type='DESTINATION' AND name='ACTIVE')),
(NOW(), 'Buenos Aires, Argentina', 'Capital del tango y la gastronomia rioplatense', (SELECT id FROM statuses WHERE entity_type='DESTINATION' AND name='ACTIVE')),
(NOW(), 'Cusco, Peru', 'Puerta de entrada a Machu Picchu y la cultura inca', (SELECT id FROM statuses WHERE entity_type='DESTINATION' AND name='ACTIVE'));

-- SERVICIOS
INSERT INTO services (created_at, name, description, status_id) VALUES
(NOW(), 'Vuelo incluido', 'Vuelos de ida y vuelta incluidos', (SELECT id FROM statuses WHERE entity_type='SERVICE' AND name='ACTIVE')),
(NOW(), 'Hotel 4 estrellas', 'Alojamiento en hotel de 4 estrellas con desayuno', (SELECT id FROM statuses WHERE entity_type='SERVICE' AND name='ACTIVE')),
(NOW(), 'Hotel 3 estrellas', 'Alojamiento en hotel de 3 estrellas con desayuno', (SELECT id FROM statuses WHERE entity_type='SERVICE' AND name='ACTIVE')),
(NOW(), 'Traslados incluidos', 'Traslados aeropuerto-hotel-aeropuerto incluidos', (SELECT id FROM statuses WHERE entity_type='SERVICE' AND name='ACTIVE')),
(NOW(), 'Guia turistico', 'Guia turistico local en espanol', (SELECT id FROM statuses WHERE entity_type='SERVICE' AND name='ACTIVE')),
(NOW(), 'Seguro de viaje', 'Seguro de viaje con cobertura medica internacional', (SELECT id FROM statuses WHERE entity_type='SERVICE' AND name='ACTIVE')),
(NOW(), 'Desayuno incluido', 'Desayuno buffet diario incluido', (SELECT id FROM statuses WHERE entity_type='SERVICE' AND name='ACTIVE')),
(NOW(), 'Todas las comidas', 'Desayuno, almuerzo y cena incluidos', (SELECT id FROM statuses WHERE entity_type='SERVICE' AND name='ACTIVE')),
(NOW(), 'Spa y bienestar', 'Acceso a spa, piscina y actividades de bienestar', (SELECT id FROM statuses WHERE entity_type='SERVICE' AND name='ACTIVE'));

-- PAQUETES TURISTICOS
INSERT INTO tourist_packages (name, description, destination_id, start_date, end_date, duration_days, price, total_slots, available_slots, conditions, restrictions, package_type_id, category_id, season_id, status_id, created_at) VALUES
('Escapada a Paris',
 'Descubre la ciudad del amor con visitas a la Torre Eiffel, el Louvre y Montmartre.',
 (SELECT id FROM destinations WHERE name='Paris, Francia'),
 '2026-07-01', '2026-07-07', 6, 1850000, 20, 20,
 'Pago total requerido antes de la fecha de salida.',
 'Mayores de 18 anios. Pasaporte con vigencia minima de 6 meses.',
 (SELECT id FROM package_types WHERE name='INTERNACIONAL'),
 (SELECT id FROM categories WHERE name='CITY ESCAPE'),
 (SELECT id FROM seasons WHERE name='INVIERNO'),
 (SELECT id FROM statuses WHERE entity_type='PACKAGE' AND name='AVAILABLE'),
 NOW()),

('Aventura en el Desierto de Atacama',
 'Explora el desierto mas arido del mundo. Geisers del Tatio, Valle de la Luna.',
 (SELECT id FROM destinations WHERE name='San Pedro de Atacama, Chile'),
 '2026-06-15', '2026-06-20', 5, 650000, 15, 15,
 'Condicion fisica moderada requerida.',
 'No recomendado para menores de 12 anios.',
 (SELECT id FROM package_types WHERE name='NACIONAL'),
 (SELECT id FROM categories WHERE name='ADVENTURE'),
 (SELECT id FROM seasons WHERE name='INVIERNO'),
 (SELECT id FROM statuses WHERE entity_type='PACKAGE' AND name='AVAILABLE'),
 NOW()),

('Santiago City Break',
 'Recorre lo mejor de Santiago en un fin de semana largo.',
 (SELECT id FROM destinations WHERE name='Santiago, Chile'),
 '2026-06-01', '2026-06-04', 3, 280000, 30, 30,
 'Incluye desayuno diario.',
 NULL,
 (SELECT id FROM package_types WHERE name='NACIONAL'),
 (SELECT id FROM categories WHERE name='CITY ESCAPE'),
 (SELECT id FROM seasons WHERE name='INVIERNO'),
 (SELECT id FROM statuses WHERE entity_type='PACKAGE' AND name='AVAILABLE'),
 NOW()),

('Mexico: Sabores y Cultura',
 'Sumergete en la gastronomia mexicana Patrimonio de la Humanidad.',
 (SELECT id FROM destinations WHERE name='Ciudad de Mexico, Mexico'),
 '2026-08-10', '2026-08-17', 7, 1200000, 25, 25,
 'Pago del 50% al momento de reservar.',
 'Documentacion de viaje vigente.',
 (SELECT id FROM package_types WHERE name='INTERNACIONAL'),
 (SELECT id FROM categories WHERE name='FOOD'),
 (SELECT id FROM seasons WHERE name='VERANO'),
 (SELECT id FROM statuses WHERE entity_type='PACKAGE' AND name='AVAILABLE'),
 NOW()),

('Buenos Aires: Tango y Gastronomia',
 'Descubre la Paris de Sudamerica con shows de tango y la mejor carne argentina.',
 (SELECT id FROM destinations WHERE name='Buenos Aires, Argentina'),
 '2026-07-15', '2026-07-21', 6, 980000, 20, 20,
 'Vuelo y hotel incluidos.',
 'Pasaporte vigente requerido para ciudadanos no chilenos.',
 (SELECT id FROM package_types WHERE name='INTERNACIONAL'),
 (SELECT id FROM categories WHERE name='FOOD'),
 (SELECT id FROM seasons WHERE name='INVIERNO'),
 (SELECT id FROM statuses WHERE entity_type='PACKAGE' AND name='AVAILABLE'),
 NOW()),

('Cusco y Machu Picchu: Imperio Inca',
 'Viaja al corazon del Imperio Inca. Explora Cusco y la maravilla del mundo Machu Picchu.',
 (SELECT id FROM destinations WHERE name='Cusco, Peru'),
 '2026-08-20', '2026-08-27', 7, 1450000, 18, 18,
 'Incluye tren a Machu Picchu y entrada al sitio arqueologico.',
 'Se recomienda aclimatacion previa a la altura.',
 (SELECT id FROM package_types WHERE name='INTERNACIONAL'),
 (SELECT id FROM categories WHERE name='ADVENTURE'),
 (SELECT id FROM seasons WHERE name='VERANO'),
 (SELECT id FROM statuses WHERE entity_type='PACKAGE' AND name='AVAILABLE'),
 NOW());

-- PROMOCIONES
INSERT INTO promotions (created_at, name, discount_percentage, min_passengers, min_bookings_session, min_bookings_history, is_accumulable, start_date, end_date, status_id) VALUES
(NOW(), 'Descuento Grupo Familiar', 10.00, 4, NULL, NULL, true, NOW(), '2026-12-31 23:59:59', (SELECT id FROM statuses WHERE entity_type='PROMOTION' AND name='ACTIVE')),
(NOW(), 'Cliente Frecuente', 8.00, NULL, NULL, 2, true, NOW(), '2026-12-31 23:59:59', (SELECT id FROM statuses WHERE entity_type='PROMOTION' AND name='ACTIVE')),
(NOW(), 'Compra Multiple', 5.00, NULL, 2, NULL, true, NOW(), '2026-12-31 23:59:59', (SELECT id FROM statuses WHERE entity_type='PROMOTION' AND name='ACTIVE'));

-- ASOCIAR SERVICIOS A PAQUETES
INSERT INTO tourist_package_services (tourist_package_id, service_id) VALUES
((SELECT id FROM tourist_packages WHERE name='Escapada a Paris'), (SELECT id FROM services WHERE name='Vuelo incluido')),
((SELECT id FROM tourist_packages WHERE name='Escapada a Paris'), (SELECT id FROM services WHERE name='Hotel 4 estrellas')),
((SELECT id FROM tourist_packages WHERE name='Escapada a Paris'), (SELECT id FROM services WHERE name='Traslados incluidos')),
((SELECT id FROM tourist_packages WHERE name='Escapada a Paris'), (SELECT id FROM services WHERE name='Guia turistico')),
((SELECT id FROM tourist_packages WHERE name='Escapada a Paris'), (SELECT id FROM services WHERE name='Seguro de viaje')),
((SELECT id FROM tourist_packages WHERE name='Aventura en el Desierto de Atacama'), (SELECT id FROM services WHERE name='Traslados incluidos')),
((SELECT id FROM tourist_packages WHERE name='Aventura en el Desierto de Atacama'), (SELECT id FROM services WHERE name='Guia turistico')),
((SELECT id FROM tourist_packages WHERE name='Aventura en el Desierto de Atacama'), (SELECT id FROM services WHERE name='Desayuno incluido')),
((SELECT id FROM tourist_packages WHERE name='Santiago City Break'), (SELECT id FROM services WHERE name='Hotel 3 estrellas')),
((SELECT id FROM tourist_packages WHERE name='Santiago City Break'), (SELECT id FROM services WHERE name='Desayuno incluido')),
((SELECT id FROM tourist_packages WHERE name='Mexico: Sabores y Cultura'), (SELECT id FROM services WHERE name='Vuelo incluido')),
((SELECT id FROM tourist_packages WHERE name='Mexico: Sabores y Cultura'), (SELECT id FROM services WHERE name='Hotel 4 estrellas')),
((SELECT id FROM tourist_packages WHERE name='Mexico: Sabores y Cultura'), (SELECT id FROM services WHERE name='Guia turistico')),
((SELECT id FROM tourist_packages WHERE name='Buenos Aires: Tango y Gastronomia'), (SELECT id FROM services WHERE name='Vuelo incluido')),
((SELECT id FROM tourist_packages WHERE name='Buenos Aires: Tango y Gastronomia'), (SELECT id FROM services WHERE name='Hotel 4 estrellas')),
((SELECT id FROM tourist_packages WHERE name='Buenos Aires: Tango y Gastronomia'), (SELECT id FROM services WHERE name='Traslados incluidos')),
((SELECT id FROM tourist_packages WHERE name='Cusco y Machu Picchu: Imperio Inca'), (SELECT id FROM services WHERE name='Vuelo incluido')),
((SELECT id FROM tourist_packages WHERE name='Cusco y Machu Picchu: Imperio Inca'), (SELECT id FROM services WHERE name='Hotel 3 estrellas')),
((SELECT id FROM tourist_packages WHERE name='Cusco y Machu Picchu: Imperio Inca'), (SELECT id FROM services WHERE name='Guia turistico')),
((SELECT id FROM tourist_packages WHERE name='Cusco y Machu Picchu: Imperio Inca'), (SELECT id FROM services WHERE name='Seguro de viaje'));

-- ASOCIAR PROMOCIONES A TODOS LOS PAQUETES
INSERT INTO tourist_package_promotions (tourist_package_id, promotion_id)
SELECT tp.id, p.id FROM tourist_packages tp CROSS JOIN promotions p;

SELECT 'ms-paquetes init OK' as resultado;
