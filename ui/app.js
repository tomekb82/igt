var app = angular.module('app', ['ui.grid','ui.grid.pagination']);

app.controller('StudentCtrl', ['$scope','StudentService', function ($scope,StudentService) {

console.log('test');
   var paginationOptions = {
     pageNumber: 1,
	 pageSize: 5,
	 sort: null
   };

   /*StudentService.getStudents(paginationOptions.pageNumber,
		   paginationOptions.pageSize).success(function(data){
	  $scope.gridOptions.data = data.content;
 	  $scope.gridOptions.totalItems = data.totalElements;
   });*/

   StudentService.getStudentsMock().then(function(data){
	  $scope.gridOptions.data = data.content;
 	  $scope.gridOptions.totalItems = data.totalElements;
   });


   $scope.gridOptions = {
    paginationPageSizes: [5, 10, 20],
    paginationPageSize: paginationOptions.pageSize,
    enableColumnMenus:false,
	useExternalPagination: true,
    columnDefs: [
      { name: 'id' },
      { name: 'name' },
      { name: 'gender' },
      { name: 'age' }
    ],
    onRegisterApi: function(gridApi) {
        $scope.gridApi = gridApi;
        gridApi.pagination.on.paginationChanged($scope, function (newPage, pageSize) {
          paginationOptions.pageNumber = newPage;
          paginationOptions.pageSize = pageSize;
          StudentService.getStudents(newPage,pageSize).success(function(data){
        	  $scope.gridOptions.data = data.content;
         	  $scope.gridOptions.totalItems = data.totalElements;
          });
        });
     }
  };
  
}]);

app.service('StudentService',['$http', '$q', function ($http,$q) {
	
	function getStudents(pageNumber,size) {
		pageNumber = pageNumber > 0?pageNumber - 1:0;
        	return  $http({
          		method: 'GET',
          		url: 'student/get?page='+pageNumber+'&size='+size
        	});
    	}
	function getStudentsMock() {
		return  $q.when({
                              "content": [
                                {
                                  "id": 1,
                                  "name": "Bryan",
                                  "gender": "Male",
                                  "age": 20
                                },
                                {
                                  "id": 2,
                                  "name": "Ben",
                                  "gender": "Male",
                                  "age": 22
                                }
                              ],
                              "totalPages": 10,
                              "totalElements": 20,
                              "last": false,
                              "first": true,
                              "numberOfElements": 2,
                              "sort": null,
                              "size": 2,
                              "number": 0
                            });
        }
	
    return {
    	getStudents:getStudents,
    	getStudentsMock: getStudentsMock
    };
	
}]);


