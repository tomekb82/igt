class StudentCtrl {

    /*@ngInject*/
    constructor($scope, StudentService) {
       
        angular.extend(this,{ 
            $scope,
            StudentService,
            title:'Search'
        });

        /*StudentService.getStudents(paginationOptions.pageNumber,paginationOptions.pageSize)
        .success(function(data){
            $scope.gridOptions.data = data.content;
            $scope.gridOptions.totalItems = data.totalElements;
        });*/

        this.StudentService.getStudentsMock()
        .then( (data) => {
            this.gridOptions.data = data.content;
            this.gridOptions.totalItems = data.totalElements;
            console.log(this.gridOptions);
        });
        
        this.paginationOptions = {
            pageNumber: 1,
            pageSize: 5,
            sort: null
        };

        this.gridOptions = {
            paginationPageSizes: [5, 10, 20],
            paginationPageSize: this.paginationOptions.pageSize,
            enableColumnMenus:false,
            useExternalPagination: true,
            columnDefs: [
                { name: 'id' },
                { name: 'name' },
                { name: 'gender' },
                { name: 'age' }
            ],
            onRegisterApi: (gridApi)  => {
                this.gridApi = gridApi;
                console.log(gridApi);
                gridApi.pagination.on.paginationChanged(this, function (newPage, pageSize) {
                    this.paginationOptions.pageNumber = newPage;
                    this.paginationOptions.pageSize = pageSize;
                    this.StudentService.getStudents(newPage,pageSize).success(function(data){
                        this.gridOptions.data = data.content;
                        this.gridOptions.totalItems = data.totalElements;
                    });
                });
            }
        };

    }
}
    

export default {
    name: 'studentComponent',
    config: {
        controller: StudentCtrl,
        controllerAs: 'ctrl',
        template: require('./student.html')
    }
};
