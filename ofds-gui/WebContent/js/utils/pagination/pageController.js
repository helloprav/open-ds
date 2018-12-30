/**
 * 
 */
(function() {
    'use strict';


	angular.module('routerApp').controller('MainCtrl', ['$scope', 'myPagingData', function($scope, myPagingData) {
	
	  var entityList = myPagingData.entityList;
	  console.log('entityList: '+JSON.stringify(entityList));
	  var cols = [{
	    name: 'firstName',
	    orderDesc: false
	  }, {
	    name: 'lastName',
	    orderDesc: false
	  }];
	
	  $scope.totalItems = entityList.length;
	  $scope.currentPage = 2;
	  $scope.itemsPerPage = 2;
	
	  $scope.$watch('currentPage', function() {
	    setPagingData($scope.currentPage);
	  });
	
	  function setPagingData(page) {
		console.log('within setPagingData() ' + page);
		$scope.currentPage = page;
		var pagedData = entityList.slice((page - 1)
				* $scope.itemsPerPage, page * $scope.itemsPerPage);
		$scope.entityList = pagedData;
	}
	
	  $scope.sortData = function(sortCol) {
	    // make sure it a valid column
	    var column = cols.find(function(col) {
	      return col.name === sortCol;
	    });
	
	    if (!column) return;
	    
	    column.orderDesc = !column.orderDesc;
	
	    var order = !column.orderDesc ? 1 : -1;
	    entityList.sort(function(a, b) {
	      if (a[column.name] < b[column.name])
	        return -1 * order;
	      if (a[column.name] > b[column.name])
	        return 1 * order;
	      return 0;
	    });
	    
	    setPagingData($scope.currentPage);
	  };
	
	}])
})();
