(function() {
    'use strict';

    angular
        .module('kpicsApp')
        .controller('TeacherInfoDetailController', TeacherInfoDetailController);

    TeacherInfoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TeacherInfo'];

    function TeacherInfoDetailController($scope, $rootScope, $stateParams, previousState, entity, TeacherInfo) {
        var vm = this;

        vm.teacherInfo = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('kpicsApp:teacherInfoUpdate', function(event, result) {
            vm.teacherInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
