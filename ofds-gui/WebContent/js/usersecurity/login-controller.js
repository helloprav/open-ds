(function () {
    'use strict';

    angular.module('routerApp').controller('LoginCtrl', function ($rootScope, $scope, $state, $http, $window, $location) {

        $scope.loginUser = function () {

            if ($scope.login.username == ''
                || $scope.login.password == '') {

            	$scope.error = "Please enter username and password";
                return;
            }

            var loginJson = {};
            loginJson['username'] = $scope.login.username;
            loginJson['password'] = $scope.login.password;

            console.log(JSON.stringify(loginJson));
            $scope.loginDetail = loginJson;
            $scope.response=[];
            $http({
                url : userMgmtCtx+"/auth/login",
                method : "POST",
                data : $scope.loginDetail
			}).then(function successCallback(response) {
				// this callback will be called asynchronously
				// when the response is available
                console.log("response: "+response);
                console.log(JSON.stringify(response));
                $scope.responseBody = response.data;
                console.log(JSON.stringify($scope.responseBody));
                var statusCode = response.status;
                console.log("statusCode: "+statusCode);
                if (statusCode == 200) {
                    $scope.userID = $scope.responseBody.userID;
                    $window.localStorage.setItem("saved", $scope.responseBody);
                    $window.localStorage.setItem("loginuserid", $scope.userID);
                    if($window.localStorage.userName!=""||$window.localStorage.password!="")
                    {
                        $window.localStorage.userName = "";
                        $window.localStorage.password = "";
                        console.log("reset password");
                        $window.localStorage.password = $scope.password;
                        $window.localStorage.userName = $scope.userName;
                        console.log("password reset completed");
                    }
                    $state.go('home');
                    //var currentloc = $location.absUrl();
                    //$window.location.href = "./index.html";
                } else if(statusCode == 400) {
                    if(undefined != response.data.errorList) {

                        $scope.error = response.data.errorList;
                    } else if($scope.responseBody.errorMessage) {
                		$scope.error = $scope.responseBody.errorMessage;
                	} else {
                		$scope.error = "There is some error in system. Please re-try after some time.";
                	}
                } else {
                    console.log("System Error: "+statusCode);
                	$scope.error = "There is some error in system. Please re-try after some time.";
                }

			}, function errorCallback(response) {
				// called asynchronously if an error occurs
				// or server returns response with an error
				// status.
                console.log("data: "+data);
                console.log("data.statusCode: "+data.statusCode);
                console.log("status: "+status);
                console.log("headers: "+headers);
                console.log("config: "+config);

                console.log("Within failure error ::::::::::::"+data.responseBody);
                if(undefined == data.errorList) {
                	
                    $scope.error = data.errorMessage;
                } else {
                	
                    $scope.error = data.errorList;
                }
			});
        };

        $scope.test = function () {
            var test = true;
        };
    })
})();