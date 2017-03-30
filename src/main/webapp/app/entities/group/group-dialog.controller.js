(function() {
    'use strict';

    angular
        .module('kpicsApp')
        .controller('GroupDialogController', GroupDialogController);

    GroupDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity',
    'Group', 'Faculty'];

    function GroupDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Group, Faculty) {
        var vm = this;

        vm.group = entity;
        vm.clear = clear;
        vm.save = save;
        vm.faculties = Faculty.query(onFacultySuccess);
        vm.departments = null;
        vm.onSelectChange = onSelectChange;
        vm.selectedFaculty = null;
        vm.selectedDepartment = null;

        function onSelectChange() {
            for(var i = 0; i < vm.faculties.length; i++) {
                if(vm.faculties[i].name === vm.selectedFaculty.name) {
                    vm.departments = vm.faculties[i].departments;

                    console.log(vm.selectedFaculty);
                    console.log(vm.selectedDepartment);
                }
            }
        }

        function onFacultySuccess() {
            vm.departments = vm.faculties[0].departments;
        }

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            vm.group.faculty = vm.selectedFaculty.name;
            vm.group.department = vm.selectedDepartment.name;
            if (vm.group.id !== null) {
                Group.update(vm.group, onSaveSuccess, onSaveError);
            } else {
                Group.save(vm.group, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('kpicsApp:groupUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
