/**
 * 
 */
routerApp.config(function($stateProvider, $urlRouterProvider) {

    $urlRouterProvider.otherwise('/login');

    $stateProvider

        // HOME STATES AND NESTED VIEWS ========================================
        .state('group-mgmt', {
            url: '/group-mgmt/groups',
            views: {
            	"": {
            		templateUrl: 'templates/user-mgmt/item-template.html',
		            controller: 'GroupsController',
		            resolve: {
						configsPromise: ['groupFactory', '$stateParams', function(groupFactory, $stateParams) {

							console.log('Hello: Item Template');
						    return groupFactory.panelTitle;
						}]
		            }
            	}
            },
            abstract: true
        })

        .state('group-mgmt.groups', {
            url: '/groups',
            templateUrl: 'templates/user-mgmt/groups/index.html',
            abstract: false
        })

        // nested list with custom controller
        .state('group-mgmt.groups-list', {
            url: '/list/{status}',
            params: {
            	status: {
            		value: 'active'
            	}
            },
            views: {
            	"cardTitle": {
            		template: 'LIST',
            	},
            	"item-header": {
            		templateUrl: 'templates/user-mgmt/groups/group-header-list.html',
		            controller: 'GroupsController',
            	},
            	"item-content": {
		            templateUrl: 'templates/user-mgmt/groups/groups-list.html',
		            controller: 'GroupsController',
		            resolve: {
						configsPromise: ['groupFactory', '$stateParams', function(groupFactory, $stateParams) {

							console.log('Hello: ');
						    var selectedStatus = 'false';
						    console.log('group-mgmt.groups.list: '+selectedStatus);
						    if($stateParams.status == 'active') {
						    	selectedStatus = 'true';
						    }
						    console.log('group-mgmt.groups.list: '+selectedStatus);
						    return groupFactory.getAll(selectedStatus);
						}]
		            }
            	},
            }
        })
        // nested view item
        .state('group-mgmt.groups-view', {
            url: '/{groupId}/view',
            views: {
            	"cardTitle": {
            		template: 'VIEW GROUP',
            	},
            	"item-header": {
            		templateUrl: 'templates/user-mgmt/groups/group-header-view.html',
		            controller: 'GroupsController',
            	},
            	"item-content": {
		            templateUrl: 'templates/user-mgmt/groups/group-view.html',
		            controller: 'GroupsController',
		            resolve: {
						configsPromise: ['groupFactory', '$stateParams', function(groupFactory, $stateParams) {

						    console.log('group-mgmt.groups.view:: groupFactory.get(groupId): '+$stateParams.groupId);
						    return groupFactory.get($stateParams.groupId);
						}]
		            }
            	},
            }
        })

        // nested view item
        .state('group-mgmt.groups-edit', {
            url: '/{groupId}/edit',
            views: {
            	"cardTitle": {
            		template: 'EDIT GROUP',
            	},
            	"item-header": {
            		templateUrl: 'templates/user-mgmt/groups/group-header-edit.html',
		            controller: 'GroupsController',
            	},
            	"item-content": {
		            templateUrl: 'templates/user-mgmt/groups/edit.html',
		            controller: 'GroupsController',
		            resolve: {
						configsPromise: ['groupFactory', '$stateParams', function(groupFactory, $stateParams) {
						    console.log('groupFactory.get($stateParams.groupId): '+$stateParams.groupId);
						    return groupFactory.get($stateParams.groupId);
						}],
						functionsPromise: ['functionFactory', '$stateParams', function(functionFactory, $stateParams) {
						    console.log('functionFactory.getAll()');
						    return functionFactory.getAll();
						}]
		            }
            	},
            }
        })

        // nested view item
        .state('group-mgmt.groups-new', {
            url: '/new',
            views: {
            	"cardTitle": {
            		template: 'CREATE GROUP',
            	},
            	"item-header": {
            		templateUrl: 'templates/user-mgmt/groups/group-header-create.html',
		            controller: 'GroupsController',
            	},
            	"item-content": {
		            templateUrl: 'templates/user-mgmt/groups/new.html',
		            controller: 'GroupsController',
		            resolve: {
						resetPromise: ['groupFactory', '$stateParams', function(groupFactory, $stateParams) {
						    console.log('group-mgmt.groups.new:: functionFactory.resetAll()');
						    return groupFactory.resetAll();
						}],
						functionsPromise: ['functionFactory', '$stateParams', function(functionFactory, $stateParams) {
						    console.log('group-mgmt.groups.new:: functionFactory.getAll()');
						    return functionFactory.getAll();
						}]
		            }
            	},
            }
        })

        // ABOUT PAGE AND MULTIPLE NAMED VIEWS =================================
});
