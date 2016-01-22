<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title>Upload File Request Page</title>
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container">${msg}
		<form method="POST" action="uploadFile" enctype="multipart/form-data">
			<div class="form-group">
				<label for="exampleInputEmail1">File to upload:</label> <input
					type="file" name="file"><br />
				<button type="submit" class="btn btn-default">Upload</button>
			</div>
		</form>
		<form method="POST" action="db" enctype="multipart/form-data">
			<div class="form-group">
				<label for="exampleInputEmail1">Url to upload:</label> <input
					type="text" name="url_dd"><br />
				<button type="submit" class="btn btn-default">Copy to
					Dropbox</button>
			</div>
		</form>
		<form method="POST" action="uploadFileUrl" enctype="multipart/form-data">
			<div class="form-group">
				<label for="exampleInputEmail1">Url to upload:</label> <input
					type="text" name="url_dd"><br />
				<button type="submit" class="btn btn-default">Copy to
					Own</button>
			</div>
		</form>
		<table class="table table-striped table-condensed">
			<tr>
				<th>File Name</th>
				<th>Size</th>
			</tr>
			<c:forEach var="file" items="${file}">
				<tr>
					<td><a href="<c:url value="/download?fname=${file.key}" />">${file.key}</a></td>
					<td style="text-align: right">${file.value}MB</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>