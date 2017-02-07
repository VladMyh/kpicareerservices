(function() {
    'use strict';

    angular
        .module('kpicsApp')
        .controller('StudentInfoDetailController', StudentInfoDetailController);

    StudentInfoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'StudentInfo'];

    function StudentInfoDetailController($scope, $rootScope, $stateParams, previousState, entity, StudentInfo) {
        var vm = this;

        vm.studentInfo = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('kpicsApp:studentInfoUpdate', function(event, result) {
            vm.studentInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
