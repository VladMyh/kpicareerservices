(function() {
    'use strict';

    angular
        .module('kpicsApp')
        .controller('IdeaUserConfirmController', IdeaUserConfirmController);

    IdeaUserConfirmController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Idea', 'Account'];

    function IdeaUserConfirmController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Idea, Account) {
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
                /*
                saveProjectManager(function (data) {
                    Idea.update(vm.idea, onSaveSuccess, onSaveError);
                });
                */
            }
        }

        function saveProjectManager(fn){
            Account.get(function (data) {
                vm.idea.projectManagerId = data.data.id;
                vm.idea.ideaHasPM = true;
                vm.idea.isIdeaHasPM = true;
                console.log(vm.idea);

                fn(null);
            });
        }

        function onSaveSuccess (result) {
            vm.isSaving = false;
            clear();
        }

        function onSaveError () {
            vm.isSaving = false;
        }
    }
})();
