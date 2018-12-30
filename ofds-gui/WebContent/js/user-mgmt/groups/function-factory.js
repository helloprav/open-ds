angular.module('routerApp')
.factory('functionFactory', [
	'$http', 
	'$state',
	function($http, $state) {
		var o = {
			allFunctionList: [],
			functionn: {}
		};

		o.resetAll = function() {
			console.log("reset All called");
			o.allFunctionList = [];
			o.group = {};
		}

		o.get = function(id) {
			console.log("get called for id:"+id);
		    	var url = userMgmtCtx+"/groups/"+id;
			return $http.get(url).then(function(res) {
			console.log(JSON.stringify(res.data.data));
			    if(res.data.data.length ==1) {
				angular.copy(res.data.data[0], o.group);
			    }
			})
		};

		o.getAll = function() {
			console.log('getting all functions');
		    	var url = userMgmtCtx+"/functions";
	    	return $http({
	    		  method: 'GET',
	    		  url: url
	    		}).then(function successCallback(res) {
	    		    // this callback will be called asynchronously
	    		    // when the response is available
	    			console.log('functions.response:: '+JSON.stringify(res.data));
			    	o.allFunctionList = [];
					angular.copy(res.data.data, o.allFunctionList);
	    		  }, function errorCallback(response) {
	    		    // called asynchronously if an error occurs
	    		    // or server returns response with an error status.
	    		  });
		};

		o.create = function(user) {
			console.log("Creating User ");
			user.version = new Date().getTime();		
			if(user.skipFirstLine == undefined) {
				user.skipFirstLine = false;
			}
			if(user.active == undefined) {
				user.active = false;
			}
			
			return $http.post(userMgmtCtx+'/users', user)
			.success(function(data) {
				o.setAlert("success", "Successfully created user!");

				$state.go('user-mgmt.users');				
			})
			.error(function(data, status, headers, user) {
				console.log("Error creating! status = "+status+", data = "+data);
				o.setAlert("danger", "Error creating user!");
			});
		}
		
		o.update = function(config) {
			console.log("Updating config");
			return $http.put('/config/'+config._id, config)
			.success(function(data) {
				o.setAlert("success", "Successfully updated config!");
				
				$state.go('config.list');				
			})
			.error(function(data, status) {
				console.log("Error updating! status = "+status+", data = "+data);
				o.setAlert("danger", "Error updating config!");
			});
		}
		
		o.delete = function(id) {
			var confirmed = window.confirm("Are you sure you want to delete?");
			console.log("Deleting config "+id+" confirmed = "+confirmed);
			
			if(confirmed) {
				$http.delete('/config/'+id).success(function(data) {
					console.log("deleted");
					o.setAlert("success", "Successfully deleted config!");

				})
				.error(function(data, status) {
					console.log("Error deleting! status = "+status+", data = "+data);
					o.setAlert("danger", "Error deleting config!");

				});
			}
			$state.go('user-mgmt.list');
			$state.reload();
		}
		
		o.setAlert = function(type, msg) {
			console.log("Setting alert type %s, msg %s", type, msg);
			//reset the alert
			o.alerts=[];
			o.alerts.push({type: type, msg: msg});
		}
		return o;
	}
]) //end group factory
; 