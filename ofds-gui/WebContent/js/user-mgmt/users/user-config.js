/**
 * 
 */
routerApp.config(function($stateProvider, $urlRouterProvider) {

    //$urlRouterProvider.otherwise('/login');

    $stateProvider

        // HOME STATES AND NESTED VIEWS ========================================
        .state('user-mgmt', {
            url: '/user-mgmt',
            templateUrl: 'templates/user-mgmt/placeHolder.html',
            abstract: true
        })
    
        .state('user-mgmt.users', {
            url: '/users',
            templateUrl: 'templates/user-mgmt/users/index.html',
        })

        // nested list with custom controller
        .state('user-mgmt.users.list', {
            url: '/list/{role}',
            templateUrl: 'templates/user-mgmt/users/users-list.html',
            controller: 'UsersController',
            params: {
            	role: {
            		value: 'all'
            	}
            },
            resolve: {
            	configsPromise: ['configFactory', '$stateParams', function(configFactory, $stateParams) {

				    var selectedRole = $stateParams.role;
				    console.log('user-mgmt.users.list: '+selectedRole);
				    if(selectedRole == null || selectedRole == '') {
				    	selectedRole = 'all';
				    }
				    console.log('user-mgmt.users.list: '+selectedRole);
				    return configFactory.getUsersByRole(selectedRole);
            	}],
		    	userRolePromise: ['configFactory', function(configFactory) {
		    	    console.log('user-mgmt.users.edit:::getUserRole ');
		    		return configFactory.getUserRole();
		    	}]
            }
        })

        // nested view item
        .state('user-mgmt.users.view', {
            url: '/{userId}/view',
            templateUrl: 'templates/user-mgmt/users/user-view.html',
            controller: 'UsersController',
            resolve: {
				userPromise: ['configFactory', '$stateParams', function(configFactory, $stateParams) {
				    console.log('user-mgmt.users.view::: '+$stateParams.userId);
				    return configFactory.get($stateParams.userId);
				}]
            }
        })

        // nested view item
        .state('user-mgmt.users.edit', {
            url: '/{userId}/edit',
            templateUrl: 'templates/user-mgmt/users/edit.html',
            controller: 'UsersController',
            resolve: {
				userPromise: ['configFactory', '$stateParams', function(configFactory, $stateParams) {
				    console.log('user-mgmt.users.edit::: '+$stateParams.userId);
				    return configFactory.get($stateParams.userId);
				}],
				configsPromise: ['groupFactory', function(groupFactory) {
				    console.log('groupFactory.getAll ');
				    return groupFactory.getAll();
				}],
		       	userRolePromise: ['configFactory', function(configFactory) {
		        	console.log('user-mgmt.users.edit:::getUserRole ');
		        	return configFactory.getUserRole();
				}],
		        userGenderPromise: ['configFactory', function(configFactory) {
		        	console.log('user-mgmt.users.edit:::getUserGender ');
		        	return configFactory.getUserGender();
		        }]
            }
        })

        // nested list with custom controller
        .state('user-mgmt.users.new', {
            url: '/new',
            templateUrl: 'templates/user-mgmt/users/new.html',
            controller: 'UsersController',
            resolve: {
				configsPromise: ['configFactory', function(configFactory) {
					return configFactory.resetAll();
				}],
	        	userRolePromise: ['configFactory', function(configFactory) {
	        	    console.log('user-mgmt.users.edit:::getUserRole ');
	        		return configFactory.getUserRole();
				}],
	        	userGenderPromise: ['configFactory', function(configFactory) {
	        	    console.log('user-mgmt.users.edit:::getUserGender ');
	        		return configFactory.getUserGender();
	        	}]
            }
        })

        // ABOUT PAGE AND MULTIPLE NAMED VIEWS =================================
});
