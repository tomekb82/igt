import uiRouter from 'angular-ui-router';
import uiGrid from 'angular-ui-grid';
import lodash from 'lodash';

function getModuleName(module) { return module.name || module.default.name; }

const appDependencies = [
  'ui.router',
  'ui.grid',
  'ui.grid.pagination',
  'ngMaterial'
];

const appModules = [
  //Views
  require('./student/student.module.js')
];

angular
  .module('app', appDependencies.concat(appModules.map(getModuleName)))
  .config( /*@ngInject*/ ($stateProvider, $urlRouterProvider, $mdThemingProvider) => {
    $urlRouterProvider.otherwise('/student');  

    $mdThemingProvider.theme('default')
      .primaryPalette('blue')
      .accentPalette('orange');

  });

