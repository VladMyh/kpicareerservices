(function() {
    'use strict';

    angular
        .module('kpicsApp')
        .controller('FacultyDetailController', FacultyDetailController);

    FacultyDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Faculty'];

    function FacultyDetailController($scope, $rootScope, $stateParams, previousState, entity, Faculty) {
        var vm = this;

        vm.faculty = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('kpicsApp:facultyUpdate', function(event, result) {
            vm.faculty = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
