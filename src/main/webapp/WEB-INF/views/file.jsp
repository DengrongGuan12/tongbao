<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
    <title>Home</title>
</head>
<body>
    <h1>Please upload a file</h1>
    <!--   enctype(编码格式)必须为multipart/form-data  -->
            <form method="post" action="http://120.27.112.9:8080/tongbao/user/uploadPicture" enctype="multipart/form-data">
                <input type="text" name="name"/>
                <input type="file" name="file"/>
                <input type="submit"/>
            </form>
</body>
</html>