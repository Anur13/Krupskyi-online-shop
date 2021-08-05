<html>
<head>
    <title>Welcome!</title>
     <#include "styles/index.css">
</head>
<body>
<h1>Welcome!</h1>
<a href="products/add">Add product</a>
<table class="table">
   <thead>
    <tr class="tableRow">
        <th>Id</th>
        <th>Name</th>
        <th>Price</th>
        <th>Date of creation</th>
    </tr>
   </thead>
    <tbody>
       <#list products as product>
       <tr class="tableRow">
        <td>${product.id}</td>
        <td>${product.name}</td>
        <td>${product.price}</td>
        <td>${product.date}</td>
       </tr>
       </#list>
    </tbody>
</table>
</body>
</html>