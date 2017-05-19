## Document

1. Localization in java code using properties file
    http://memorynotfound.com/spring-mvc-internationalization-i18n-example/

2. Plugin for Sass in Spring boot
    http://cleancodejava.com/auto-compile-sass-stylesheets-with-maven/

    Sass-Maven-Plugin

- Phải config Maven với lệnh : sass:watch (Tự động re-compile khi có thay đổi).
- Sau đó chạy Config của Maven trước. Rồi mới chạy project sau.

- Đầu vào:                      src/main/resources/sass/tên_file.scss
- Đầu ra sau khi compile:       src/main/resources/static/css/tên_file.css
- Đầu ra sau khi đưa vào war:   target/classes/static/css/tên_file.css

- Cho nên khi import trong file html có dạng:
       <link th:href="@{/css/tên_file.css}" rel="stylesheet" />
    Vì đường dẫn: target/classes/static : là sẵn có. Nên phải bắt đầu từ /css/tên_file.css

