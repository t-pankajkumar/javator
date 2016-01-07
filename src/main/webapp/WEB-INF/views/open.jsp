<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false"%>
<html>
<head>
<title>Home</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.8/angular.min.js"></script>
<script>
	var app = angular.module('myApp', []);
	app.controller('customersCtrl', function($scope, $http) {
		$scope.da = "{'msgs':['s', 'xcv', 'asd', 'sdf']}";
		$scope.send = function(msg) {
			$http.get('asncMsg?msg=' + $scope.msg1).then(function(response) {
				$scope.dd = JSON.parse(JSON.stringify(response.data.msgs));
			});
		}
	});
</script>
</head>
<body>
	<h1>${msg}</h1>
	<div ng-app="myApp" ng-controller="customersCtrl">
		<ul ng-repeat="ccc in dd track by $index">
			<li>{{ccc}}</li>
		</ul>
		<form action="" method="get">
			<input id="msg" name="msg" ng-model="msg1"></input>
			<button type="button" ng-click="send()">Send</button>
		</form>
		<form action="reset" method="get">
			<button type="submit">Reset</button>
		</form>
	</div>
</body>
</html>
