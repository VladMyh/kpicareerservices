(function() {
    'use strict';

    angular
        .module('kpicsApp')
        .controller('TrackDefinitionDeleteController',TrackDefinitionDeleteController);

    TrackDefinitionDeleteController.$inject = ['$uibModalInstance', 'entity', 'TrackDefinition'];

    function TrackDefinitionDeleteController($uibModalInstance, entity, TrackDefinition) {
        var vm = this;

        vm.trackDefinition = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TrackDefinition.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
