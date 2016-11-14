WITH data AS (
    SELECT 1 AS studio, t.* FROM znamenka.trainings t
)
SELECT * FROM data d WHERE d.client_id = ?
