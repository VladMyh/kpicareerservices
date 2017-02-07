(function() {
    'use strict';

    angular
        .module('kpicsApp')
        .controller('StudentInfoDialogController', StudentInfoDialogController);

    StudentInfoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'StudentInfo'];

    function StudentInfoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, StudentInfo) {
        var vm = this;

        vm.studentInfo = entity;
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
            if (vm.studentInfo.id !== null) {
                StudentInfo.update(vm.studentInfo, onSaveSuccess, onSaveError);
            } else {
                StudentInfo.save(vm.studentInfo, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('kpicsApp:studentInfoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
