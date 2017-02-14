(function() {
    'use strict';

    angular
        .module('kpicsApp')
        .controller('StreamDialogController', StreamDialogController);

    StreamDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Stream'];

    function StreamDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Stream) {
        var vm = this;

        vm.stream = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.stream.id !== null) {
                Stream.update(vm.stream, onSaveSuccess, onSaveError);
            } else {
                Stream.save(vm.stream, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('kpicsApp:streamUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.startDate = false;
        vm.datePickerOpenStatus.endDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
