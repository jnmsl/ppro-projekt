[mysqld]
character-set-server=utf8mb4
collation-server=utf8mb4_unicode_ci

# InnoDB settings
innodb_buffer_pool_size=256M
innodb_log_file_size=64M

# Connection settings
max_connections=100

# Query cache is removed in MySQL 8.0 and MariaDB 10.6
# It's often better to rely on the database engine cache
query_cache_type=0
query_cache_size=0