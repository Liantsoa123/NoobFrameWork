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

Class
```java
@Controller
public class Emp {
    @Required
    String name;
    @Numerique
    int age;
    @Date
    java.sql.Date naissance;
    @Email
    String email;
}
```
Controller
```java
  @Controller
  public class empCtrl {
    @Get("/emp/insert")
    public static Modelview insertEmp(@RequestParamObject("Emp") Emp emp ,  @RequestParam("firstName") String firstName) {
        Modelview modelview = new Modelview();
        modelview.setUrl("../WEB-INF/views/emp.jsp");
        modelview.add("emp", emp);
        modelview.add("firstName", firstName);
	modelview.add("url", "/emp/showForm");
        return modelview;
    }

}

```

From 
```jsp

<%@ page import="java.util.HashMap" %><%
    HashMap<String , String> error = new HashMap<String , String>();
    if(request.getAttribute("error") != null){
        error = (HashMap<String , String>) request.getAttribute("error");
    }
%>

  <h3>Test Validation</h3>
    <form action="./validation" method="Post">
        <p style="color:red"><%= error.get("name") != null ? error.get("name") : "" %></p>
        <label for="name">Name</label>
        <input type="text" id="name" name="Emp.name" value="<%= request.getParameter("name") != null ? request.getParameter("name") : "" %>">
    
        <p style="color:red"><%= error.get("age") != null ? error.get("age") : "" %></p>
        <label for="age">Age</label>
        <input type="text" id="age" name="Emp.age" value="<%= request.getParameter("age") != null ? request.getParameter("age") : "" %>">
    
        <p style="color:red"><%= error.get("naissance") != null ? error.get("naissance") : "" %></p>
        <label for="naissance">Naissance</label>
        <input type="text" id="naissance" name="Emp.naissance" value="<%= request.getParameter("naissance") != null ? request.getParameter("naissance") : "" %>">
    
        <p style="color:red"><%= error.get("email") != null ? error.get("email") : "" %></p>
        <label for="email">Email</label>
        <input type="text" id="email" name="Emp.email" value="<%= request.getParameter("email") != null ? request.getParameter("email") : "" %>">
    
        <input type="submit" value="OK">
    </form>

```

## License

Distributed under the MIT License. See [LICENSE](./LICENSE)for more information.

## Contact

If you have any questions, please feel free to contact me at  [rakotonanaharyliantsoafan@gmail.com](mailto:rakotonanaharyliantsoafan@gmail.com)
