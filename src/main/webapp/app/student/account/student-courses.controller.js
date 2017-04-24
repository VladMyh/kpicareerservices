(function() {
    'use strict';

    angular
        .module('kpicsApp')
        .controller('StudentCoursesController', StudentCoursesController);

    StudentCoursesController.$inject = ['$scope', 'Principal', 'LoginService', '$state'];

    function StudentCoursesController ($scope, Principal, LoginService, $state) {
        var vm = this;


    }
})();
