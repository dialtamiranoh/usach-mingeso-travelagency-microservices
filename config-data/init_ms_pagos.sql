-- ms-pagos - Datos iniciales
-- psql -U postgres -d ta_ms_pagos -f init_ms_pagos.sql

INSERT INTO statuses (created_at, entity_type, name) VALUES
(NOW(), 'PAYMENT', 'APPROVED');

SELECT 'ms-pagos init OK' as resultado;
