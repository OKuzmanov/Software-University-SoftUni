DELETE c FROM customers AS c LEFT JOIN orders AS o ON o.customer_id = c.id
WHERE o.id IS NULL; 
