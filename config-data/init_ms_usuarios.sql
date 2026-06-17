-- ms-usuarios - Datos iniciales
-- psql -U postgres -d ta_ms_usuarios -f init_ms_usuarios.sql

INSERT INTO statuses (created_at, entity_type, name) VALUES
(NOW(), 'USER', 'ACTIVE'),
(NOW(), 'USER', 'INACTIVE');

SELECT 'ms-usuarios init OK' as resultado;
