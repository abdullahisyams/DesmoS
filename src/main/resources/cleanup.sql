-- Remove duplicate users keeping only the first occurrence
DELETE t1 FROM users t1
INNER JOIN users t2
WHERE t1.id > t2.id
AND t1.email = t2.email;

-- Add unique constraint to email column if not exists
ALTER TABLE users
ADD UNIQUE INDEX idx_email_unique (email); 