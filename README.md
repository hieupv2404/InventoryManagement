# InventoryManagement
**Inventory Management- Project OOP by Group 1**

## Yêu cầu chương trình
1. IDE IntelliJ Ultimate
2. Apache Tomcat
3. MySQL

## Cài đặt chương trình
1. Project SDK: SDK 11- Java version 11.x.x
2. Project language level: 11
3. Dùng file Script tên: database_mysql trong thư mục **..src\script** để tạo database. 
4. Configuaration:
  - Server: Tomcat Server
  - Deployment: [tên-dự-án].war exploded
  - Application context: **/**
  - URL After launch: http://localhost:[Port]/login
5. Đánh dấu thư mục **..\src\main\resources** là Resources Root
6. Chỉnh thông tin đăng nhập SQL tại: **..\src\main\resources\jdbc.properties**
  - jdbc.url= jdbc:mysql://localhost:3306/[tên database]
  - jdbc.username= [tên đăng nhập]
  - jdbc.password= [mật khẩu]

