1. docker run --privileged=true -p 6379:6379 -v /docker/redis/conf/redis.conf:/etc/redis/redis.conf -v /docker/redis/data:/data --name redis docker.io/redis redis-server /etc/redis/redis.conf --appendonly yes

