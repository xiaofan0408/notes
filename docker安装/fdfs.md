docker run -d --network=host --name tracker -v /docker/fdfs/tracker:/var/fdfs delron/fastdfs tracker
docker run -d --network=host --name storage -e TRACKER_SERVER=118.25.217.97:22122 -v /docker/fdfs/storage:/var/fdfs  -v /docker/fdfs/nginx/nginx.conf:/usr/local/nginx/nginx.conf -e GROUP_NAME=group1 delron/fastdfs storage

nginx.conf 
server {
    listen 8888;
    server_name localhost;
    location ~/group[0-9]/ {
       ngx_fastdfs_module;
    }
    error_page 500 502 503 504 /50x.html;
    location = /50x.html {
        root html;
    }

}
group1/M00/00/00/rBsABlw7ZU6AOq6rAACoqlOGRwA891.jpg
