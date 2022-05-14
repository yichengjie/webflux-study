1. 创建目录
   ```
   /usr/local/mysoft
   /usr/local/mysoft/dependents
   /usr/local/mysoft/modules
   ```
2. 机器安装编译环境
    ```txt
    yum -y install gcc+ gcc-c++ lua-devel
    ```
3. 下载nginx(之后解压到/usr/local/mysoft)
   ```
   nginx-1.14.2.tar.gz
   ```
4. 下载软件包（只有解压到/usr/local/mysoft/dependents）
    ```txt
    openssl-1.1.1n.tar.gz   (http://www.openssl.org/)
    pcre-8.37.tar.gz        (http://www.zlib.net/)
    zlib-1.2.12.tar.gz      (http://www.pcre.org/)
    ```
5. 下载nginx依赖module (都不需要安装，解压即可,只有解压到/usr/local/mysoft/modules)
    ```txt
    lua-nginx-module-0.10.13.tar.gz                  (https://github.com/openresty/lua-nginx-module/tags)
    nginx_upstream_check_module-0.3.0.tar.gz         (https://github.com/yaoweibin/nginx_upstream_check_module/tags)
    nginx-http-concat-1.2.2.tar.gz                   (https://github.com/alibaba/nginx-http-concat/tags)
    nginx-module-vts-0.1.18.tar.gz                   (https://github.com/vozlt/nginx-module-vts/tags)
    ngx_devel_kit-0.3.0.tar.gz                       (https://github.com/vision5/ngx_devel_kit/releases)
    nginx-sticky-module-ng.zip                       (https://bitbucket.org/nginx-goodies/nginx-sticky-module-ng/src/master/)
    ```
6. 安装openssl(注意不是./configure)
    ```txt
    ./config, make, make install
    ```
7. 安装zlib
    ```txt
    ./configure, make,  make install
    ```
8. 安装pcre
    ```
    ./configure,  make,  make install
    ```
9. 解压依赖module
    ```
    a. tar -zxvf lua-nginx-module-0.10.13.tar.gz
    b. tar -zxvf nginx_upstream_check_module-0.3.0.tar.gz
    c. tar -zxvf nginx-http-concat-1.2.2.tar.gz
    d. tar -zxvf nginx-module-vts-0.1.18.tar.gz
    e. tar -zxvf ngx_devel_kit-0.3.0.tar.gz
    f. unzip -xvf nginx-sticky-module-ng.zip
    ```
10. 修改nginx-stick-module-ng包中ngx_http_sticky_misc.c源文件，否则nginx安装会报错
    ```
    在头部引入两行
    #include <openssl/sha.h>
    #include <openssl/md5.h>
    ```
11. 在nginx解压目录中执行./configure命令 (注意解压目录与--prefix指定的目录不能是同一个目录，否则make install的时候会报错)
    ```
	./configure \
	--prefix=/usr/local/mysoft/nginx \
	--sbin-path=/usr/local/mysoft/nginx/sbin/nginx \
	--conf-path=/usr/local/mysoft/nginx/conf/nginx.conf \
	--error-log-path=/data/logs/nginx/error.log \
	--http-log-path=/data/logs/nginx/access.log \
	--pid-path=/usr/local/mysoft/nginx/logs/nginx.pid \
	--lock-path=/usr/local/mysoft/nginx/logs/nginx.lock \
	--with-http_realip_module \
	--user=xopsadmin \
	--group=wheel \
	--with-http_ssl_module \
	--with-http_addition_module \
	--with-http_sub_module \
	--with-http_dav_module \
	--with-http_flv_module \
	--with-http_mp4_module \
	--with-http_gunzip_module \
	--with-http_gzip_static_module \
	--with-http_random_index_module \
	--with-http_secure_link_module \
	--with-http_stub_status_module \
	--with-http_auth_request_module \
	--with-threads \
	--with-stream \
	--with-stream_ssl_module \
	--with-http_slice_module \
	--with-mail \
	--with-mail_ssl_module \
	--with-file-aio \
	--with-http_v2_module \
	--with-ipv6 \
	--with-pcre=/usr/local/mysoft/dependents/pcre-8.37 \
	--with-zlib=/usr/local/mysoft/dependents/zlib-1.2.12 \
	--with-openssl=/usr/local/mysoft/dependents/openssl-1.1.1n \
	--add-module=/usr/local/mysoft/modules/lua-nginx-module-0.10.13 \
	--add-module=/usr/local/mysoft/modules/ngx_devel_kit-0.3.0 \
	--add-module=/usr/local/mysoft/modules/nginx-module-vts-0.1.18 \
	--add-module=/usr/local/mysoft/modules/nginx_upstream_check_module-0.3.0 \
	--add-module=/usr/local/mysoft/modules/nginx-http-concat-1.2.2 \
	--add-module=/usr/local/mysoft/modules/nginx-sticky-module-ng
    ```
12. 安装nginx
    ```
    a. 在nginx解压目录中执行 make
    b. 在nginx解压目录中执行 make install
    ```
13. 查看nginx是否安装成功
    ```
    a. 进入目录/usr/local/mysoft/nginx/sbin
    b. 执行命令 ./nginx -v 查看版本号
    c. 执行命令 ./nginx -V 显示所有编译模块
    ```