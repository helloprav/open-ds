(function () {
    'use strict';

    angular.module('routerApp').controller('RegisterCtrl', function ($rootScope, $scope, $state, $http, $window, $location) {

        $scope.register = function() {


            if ($scope.login.username == ''
                || $scope.login.password == '') {

            	$scope.error = "Please enter username and password";
                return;
            }

            var loginJson = {};
            loginJson['email'] = $scope.login.email;
            loginJson['password'] = $scope.login.password;

            loginJson['firstName'] = $scope.login.firstName;
            loginJson['lastName'] = $scope.login.lastName;
            loginJson['mobile'] = $scope.login.mobile;
            loginJson['gender'] = 'male';
            loginJson['status'] = 'active';
            loginJson['role'] = 'student';
            
            console.log(JSON.stringify(loginJson));
            $scope.loginDetail = loginJson;
            $scope.response=[];
            $http(
                {
                    url : "/ofds/api/users/register",
                    method : "POST",
                    data : $scope.loginDetail,
                    headers: {
                        'Accept': 'application/json'
                    }
                })
                .success(
                    function(data, status, headers,
                             config) {

//                        console.log("status: "+status);
//                        console.log("headers: "+headers);
//                        console.log("config: "+config);
                        console.log("data: "+data.successMessage);

                        $scope.responseBody = data.successMessage;
                        console.log(JSON.stringify($scope.responseBody));

                        var statusCode = data.statusCode;
                        if (statusCode == 201 || status == 201) {

                        	$scope.message = "User registered successfully. Please login.";
                            $state.go('login');

                            //var currentloc = $location.absUrl();
                            //$window.location.href = "./index.html";
                        } else{

                        	$scope.error = "This will never be displayed";
                        }
                    })
                .error(
                    function(data, status, headers,
                             config) {
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
                        //$scope.error = "Invalid Credentials";
                    });
        }
    })
})();