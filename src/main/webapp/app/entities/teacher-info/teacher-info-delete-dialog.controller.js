(function() {
    'use strict';

    angular
        .module('kpicsApp')
        .controller('TeacherInfoDeleteController',TeacherInfoDeleteController);

    TeacherInfoDeleteController.$inject = ['$uibModalInstance', 'entity', 'TeacherInfo'];

    function TeacherInfoDeleteController($uibModalInstance, entity, TeacherInfo) {
        var vm = this;

        vm.teacherInfo = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TeacherInfo.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
