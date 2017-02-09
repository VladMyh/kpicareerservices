(function() {
    'use strict';

    angular
        .module('kpicsApp')
        .controller('TrackDialogController', TrackDialogController);

    TrackDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Track'];

    function TrackDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Track) {
        var vm = this;

        vm.track = entity;
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
            if (vm.track.id !== null) {
                Track.update(vm.track, onSaveSuccess, onSaveError);
            } else {
                Track.save(vm.track, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('kpicsApp:trackUpdate', result);
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
