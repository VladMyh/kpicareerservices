(function() {
    'use strict';

    angular
        .module('kpicsApp')
        .controller('StudentSubjectController', StudentSubjectController);

    StudentSubjectController.$inject = ['$scope', 'Principal', 'LoginService', '$state'];

    function StudentSubjectController ($scope, Principal, LoginService, $state) {
        var vm = this;


    }
})();
