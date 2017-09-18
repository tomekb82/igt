
 class StudentService {
  
 	constructor($http, $q) {
    	angular.extend(this, {
 	    	$http,
 	    	$q
 	  	});
 	}

    getStudents(pageNumber,size) {
        pageNumber = pageNumber > 0?pageNumber - 1:0;
        return  this.$http({
          method: 'GET',
          url: 'student/get?page='+pageNumber+'&size='+size
        });
    }
    getStudentsMock() {
               return  this.$q.when({
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

 }

export default StudentService;





    
