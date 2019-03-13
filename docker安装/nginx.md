1.  docker run -d --name nginx -p 80:80 -v /docker/nginx/conf:/etc/nginx -v /docker/nginx/logs:/var/log/nginx -v /docker/nginx/html:/usr/share/nginx/html  docker.io/nginx
