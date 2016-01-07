<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title>Home</title>
</head>
<body>
	<h1>Hello world!</h1>

	<P>The time on the server is ${serverTime}.</P>
	<form method="POST" action="ss" enctype="multipart/form-data">
		File to upload: <input type="file" name="file"><br /> <br /> <br /> <input type="submit"
			value="Upload"> Press here to upload the file!
	</form>
</body>
</html>
