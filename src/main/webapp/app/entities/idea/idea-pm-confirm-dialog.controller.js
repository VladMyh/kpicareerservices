(function() {
    'use strict';

    angular
        .module('kpicsApp')
        .controller('IdeaPmConfirmController', IdeaPmConfirmController);

    IdeaPmConfirmController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Idea', 'Account'];

    function IdeaPmConfirmController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Idea, Account) {
        var vm = this;

        vm.idea = entity;
        vm.clear = clear;
        vm.save = save;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = false;
            if (vm.idea.id !== null) {
                console.log(Account.get());
                clear();
            }
            /*
            vm.isSaving = true;
            if (vm.idea.id !== null) {
                Idea.update(vm.idea, onSaveSuccess, onSaveError);
            } else {
                Idea.save(vm.idea, onSaveSuccess, onSaveError);
            }
            */
        }

        function onSaveSuccess (result) {
            //$scope.$emit('kpicsApp:ideaUpdate', result);
            //$uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }
    }
})();
