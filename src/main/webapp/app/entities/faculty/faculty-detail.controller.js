(function() {
    'use strict';

    angular
        .module('kpicsApp')
        .controller('FacultyDetailController', FacultyDetailController);

    FacultyDetailController.$inject = ['$scope', '$state', '$rootScope', '$stateParams', 'previousState', 'entity', 'Faculty'];

    function FacultyDetailController($scope, $state, $rootScope, $stateParams, previousState, entity, Faculty) {
        var vm = this;

        vm.faculty = entity;
        vm.previousState = previousState.name;
        vm.save = save;

        function save () {
            vm.isSaving = true;
            Faculty.update(vm.faculty, onSaveSuccess, onSaveError);
        }

        function onSaveSuccess (result) {
            $scope.$emit('kpicsApp:facultyUpdate', result);
            vm.isSaving = false;
            $state.reload();
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        var unsubscribe = $rootScope.$on('kpicsApp:facultyUpdate', function(event, result) {
            vm.faculty = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
