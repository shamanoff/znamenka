SELECT
  c.name || ' ' || c.surname                           AS client_name,
  p.purchase_date,
  pr.product_name,
  pr.price * d.discount_amount / 100                   AS price,
  coalesce((SELECT sum(pay.payment_amount)
            FROM znamenka.payments pay
            WHERE pay.purchase_id = p.purchase_id), 0) AS payd
FROM znamenka.purchase p
  INNER JOIN common.clients c ON p.client_id = c.client_id
  INNER JOIN common.products pr ON p.product_id = pr.product_id
  INNER JOIN common.discounts d ON p.discount_id = d.discount_id
WHERE p.purchase_date BETWEEN :from AND :to
      AND p.product_id = :product_id