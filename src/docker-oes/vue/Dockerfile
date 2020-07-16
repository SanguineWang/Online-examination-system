FROM nginx
# 将项目打包后的文件放到nginx容器的html目录
COPY dist/ /usr/share/nginx/html/
# 将nginx配置放到指定的位置
COPY default.conf /etc/nginx/conf.d/default.conf
