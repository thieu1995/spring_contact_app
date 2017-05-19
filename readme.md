# Document

###1. Localization in java code using properties file
    http://memorynotfound.com/spring-mvc-internationalization-i18n-example/

###2. Plugin for Sass in Spring boot
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

###3. Resouces

####a. The front end
     normalize.css for ensuring our site renders the same across all browsers
     highlight.js to add support for code syntax highlighting.
     
     https://necolas.github.io/normalize.css/
     https://highlightjs.org/

###4. Minified anything like css, js... using wro4f(Web Resource Optimize for java) in spring boot
    https://github.com/sbuettner/spring-boot-autoconfigure-wro4j
    
###5. Minified (Check: Done)
    
    http://www.baeldung.com/maven-minification-of-js-and-css-assets
    
    1. Đầu tiên phải thêm plugin 
    2. Tiếp là config Maven : clean compile war:war
    3. Sau đó config plugin vừa thêm vào để nó nhận biết đầu vào và đầu ra.
    4. Thêm đoạn exclude để nó không minified những file đã được minified như thư viện bootstrap chẳng hạn.
    
    
    
    
    
    