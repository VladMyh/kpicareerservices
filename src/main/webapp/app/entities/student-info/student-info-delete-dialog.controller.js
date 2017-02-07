(function() {
    'use strict';

    angular
        .module('kpicsApp')
        .controller('StudentInfoDeleteController',StudentInfoDeleteController);

    StudentInfoDeleteController.$inject = ['$uibModalInstance', 'entity', 'StudentInfo'];

    function StudentInfoDeleteController($uibModalInstance, entity, StudentInfo) {
        var vm = this;

        vm.studentInfo = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            StudentInfo.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
