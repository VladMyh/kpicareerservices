(function() {
    'use strict';

    angular
        .module('kpicsApp')
        .controller('StreamDeleteController',StreamDeleteController);

    StreamDeleteController.$inject = ['$uibModalInstance', 'entity', 'Stream'];

    function StreamDeleteController($uibModalInstance, entity, Stream) {
        var vm = this;

        vm.stream = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Stream.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
