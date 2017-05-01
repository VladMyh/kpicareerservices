(function() {
    'use strict';

    angular
        .module('kpicsApp')
        .controller('IdeaUserConfirmController', IdeaUserConfirmController);

    IdeaUserConfirmController.$inject = ['$timeout', '$scope', '$stateParams', '$filter', 'AlertService', '$uibModalInstance', 'entity', 'Idea', 'Account'];

    function IdeaUserConfirmController ($timeout, $scope, $stateParams, $filter, AlertService, $uibModalInstance, entity, Idea, Account) {
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
                saveJoinUserToIdea(function (data) {
                    Idea.update(vm.idea, onSaveSuccess, onSaveError);
                });
            }
        }

        function saveJoinUserToIdea(fn){
            Account.get(function (data) {
                var user = vm.idea.userIds.indexOf(data.data.id);
                if(user != 2) {
                    vm.idea.userIds.push(data.data.id);
                    fn(null);
                } else {
                    var $translate = $filter('translate');
                    var errorMessage = $translate('kpicsApp.idea.user.error');
                    AlertService.error(errorMessage);
                }
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
