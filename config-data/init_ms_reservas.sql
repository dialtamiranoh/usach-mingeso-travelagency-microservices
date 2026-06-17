-- ms-reservas - Datos iniciales
-- psql -U postgres -d ta_ms_reservas -f init_ms_reservas.sql

INSERT INTO statuses (created_at, entity_type, name) VALUES
(NOW(), 'BOOKING', 'PENDING_PAYMENT'),
(NOW(), 'BOOKING', 'CONFIRMED'),
(NOW(), 'BOOKING', 'CANCELLED'),
(NOW(), 'BOOKING', 'EXPIRED');

SELECT 'ms-reservas init OK' as resultado;
