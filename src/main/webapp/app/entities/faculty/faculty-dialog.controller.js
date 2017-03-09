(function() {
    'use strict';

    angular
        .module('kpicsApp')
        .controller('FacultyDialogController', FacultyDialogController);

    FacultyDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Faculty'];

    function FacultyDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Faculty) {
        var vm = this;

        vm.faculty = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.faculty.id !== null) {
                Faculty.update(vm.faculty, onSaveSuccess, onSaveError);
            } else {
                Faculty.save(vm.faculty, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('kpicsApp:facultyUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
