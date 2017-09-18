require('./student.scss');

import uiRouter from 'angular-ui-router';
import StudentComponent from './student.controller';
import StudentService from './student.service';
import uiGrid from 'angular-ui-grid';

const StudentModule = angular
    .module('app.student', [uiRouter, 'ui.grid'])
    .controller('StudentCtrl', StudentComponent.config.controller)
    .service('StudentService', StudentService)
    .component(StudentComponent.name, StudentComponent.config)
    .config( /*@ngInject*/ ($stateProvider) => {
      $stateProvider
  	   .state('student', {
  	  	  url: '/student',
  	  		template: StudentComponent.config.template,
  	  		controller: StudentComponent.config.controller,
  	  		controllerAs: StudentComponent.config.controllerAs
  	  });
    });

export default StudentModule;
