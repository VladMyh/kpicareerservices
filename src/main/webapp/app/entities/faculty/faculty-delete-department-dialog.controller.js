(function() {
    'use strict';

    angular
        .module('kpicsApp')
        .controller('FacultyDepartmentDeleteController',FacultyDepartmentDeleteController);

    FacultyDepartmentDeleteController.$inject = ['$uibModalInstance', 'faculty', 'department', 'Faculty', 'AlertService'];

    function FacultyDepartmentDeleteController($uibModalInstance, faculty, department, Faculty, AlertService) {
        var vm = this;

        vm.faculty = faculty;
        vm.department = department;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Faculty.deleteDepartment({id: vm.faculty.id, departmentId: vm.department.id});
            $uibModalInstance.close(true);
        }
    }
})();
