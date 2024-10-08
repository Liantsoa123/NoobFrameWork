  # NoobFrameWork

  ## Introduction
Welcome to the Java Framework! This framework is designed to simplify the development of Java applications by providing a robust set of tools and libraries. Whether you are building a web application, a desktop application, or a microservice, this framework will help you get started quickly and efficiently.

  ## Features
  * **Modular Design:** Easily extend and customize the framework to meet your needs.
  * **MVC Architecture:** Provides a clear separation between the Model, View, and Controller layers.
  * **Databases Integration:** 
  
  ## Prerequisites
  Before you begin, ensure you have met the following requirements:
  * **Java Development KIt (JDK):** JDK 8 or higher
  * **IDE:** An integrated development environment like IntelliJ IDEA, Eclipse, or NetBeans.

  ## Installation 
  Build the project into jar using the script 
  * **For Windows:**  build.bat
  * **For Linux:** build.sh

  ## Usage
  * Add this file jar  into your lib 
  * Add the framework dependency to your "web.xml"
```xml
<servlet>
    <servlet-name>dispatcherServlet</servlet-name>
    <servlet-class>mg.noobframework.controller.FrontController</servlet-class>
    <load-on-startup>1</load-on-startup>
    <init-param>
      <param-name>controller_dir</param-name>
      <param-value>controller</param-value>
    </init-param>
</servlet>

<servlet-mapping>
    <servlet-name>dispatcherServlet</servlet-name>
    <url-pattern>/</url-pattern>
</servlet-mapping>
```

  ## Example
```java
  @Controller
  public class empCtrl {
    @Get("/emp/insert")
    public static Modelview insertEmp(@RequestParamObject("Emp") Emp emp ,  @RequestParam("firstName") String firstName) {
        Modelview modelview = new Modelview();
        modelview.setUrl("../WEB-INF/views/emp.jsp");
        modelview.add("emp", emp);
        modelview.add("firstName", firstName);
        return modelview;
    }

}

```
## License
Distributed under the MIT License. See [LICENSE](./LICENSE)for more information.

## Contact
If you have any questions, please feel free to contact me at  [rakotonanaharyliantsoafan@gmail.com](mailto:rakotonanaharyliantsoafan@gmail.com)
