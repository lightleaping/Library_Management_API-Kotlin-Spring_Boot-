-- V3__indexes.sql
-- books.title 인덱스가 없을 때만 생성
SET @idx_exists := (
  SELECT COUNT(1)
  FROM information_schema.statistics
  WHERE table_schema = DATABASE()
    AND table_name   = 'books'
    AND index_name   = 'idx_books_title'
);

SET @sql := IF(@idx_exists = 0,
  'CREATE INDEX idx_books_title ON books(title)',
  'SELECT 1'
);

PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;