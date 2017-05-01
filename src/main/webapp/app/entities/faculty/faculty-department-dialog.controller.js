(function() {
    'use strict';

    angular
        .module('kpicsApp')
        .controller('FacultyDepartmentDialogController', FacultyDepartmentDialogController);

    FacultyDepartmentDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance',
    'faculty', 'department', 'Faculty'];

    function FacultyDepartmentDialogController ($timeout, $scope, $stateParams, $uibModalInstance, faculty, department, Faculty) {
        var vm = this;

        vm.faculty = faculty;
        vm.department = department;
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
            if (vm.department.id !== null) {
                Faculty.updateDepartment({id: vm.faculty.id, departmentId: vm.department.id}, vm.department,  onSaveSuccess, onSaveError);
            } else {
                Faculty.createDepartment({id: vm.faculty.id}, vm.department, onSaveSuccess, onSaveError);
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
