(function() {
    'use strict';

    angular.module('routerApp').controller(
	    'UsersController',
	    function($rootScope, $scope, $state, $http, $window, $location, $stateParams,
		    configFactory) {

		console.log('within UsersController.....$stateParams.role: '+$stateParams.role);
		$scope.entityList = configFactory.entityList.data;
		$scope.configs = configFactory.configs;
		$scope.user = configFactory.user;
		$scope.alerts = configFactory.alerts;
		$scope.userRoles = configFactory.userRoles;
		$scope.userGenders = configFactory.userGenders;
		$scope.displayedCollection = [].concat($scope.configs);
		//$scope.selectedRole = $stateParams.role;
		if($stateParams.role != undefined) {
		    //$scope.selectedRole = true;
		    $scope.selectedRole = {
		    	value: $stateParams.role
		    };
		}
		if($stateParams.role == undefined) {
		    //$scope.selectedRole = true;
		    $scope.selectedRole = {
			value: 'NIL'
		    };
		}
		console.log("$scope.selectedRole.value = "+$scope.selectedRole.value);
		console.log("Alerts size = "+$scope.alerts.length);


		$scope.createUser = function() {
		    configFactory.create($scope.user);
		};

		$scope.updateUser = function() {
		    console.log('updateUser Called');
		    //$scope.updateGroupList();
		    configFactory.update($scope.user);
		};

		$scope.updateGroupList = function() {
		    console.log('updateGroupList Called');
		    $scope.user.otherData.groupList = [];
		    var len = $scope.functionids.length;
		    if (len > 0) {
			for (var i = 0; i < len; i++) {
			    var jsonData = {};
			    jsonData["id"] = $scope.functionids[i];
			    $scope.group.functionList.push(jsonData);
			}
		    }
		    console.log('Group JSON: '+JSON.stringify($scope.group));
		}
	    })
})();

function findEntity($http, $scope, url) {
    console.log("Within findEntity Utils");
    $http({
	url : url,
	method : "GET"
    }).success(function(responseBody, status, headers, config) {
	if (responseBody.statusCode == 200) {

	    console.log("response :::::::::::::" + responseBody.data);
	    if (responseBody.data != null) {
		$scope.entityList = responseBody.data;
		$scope.entityCount = responseBody.data.length;
	    }
	    console.log("entityCount :::::::::" + $scope.entityCount);

	    if ($scope.entityCount == 0) {
		$scope.error = "No Records Found";
	    }
	} else {
	    // alert(responseBody.data);
	}

    }).error(function(responseBody, status, headers, config) {
	console.log(responseBody);
	alert("Failed : " + responseBody);
	$scope.status = status;
    });
}
