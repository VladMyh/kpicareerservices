(function() {
    'use strict';

    angular
        .module('kpicsApp')
        .controller('StreamNewTrackDialogController', StreamNewTrackDialogController);

    StreamNewTrackDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'track', 'entity', 'Stream'];

    function StreamNewTrackDialogController ($timeout, $scope, $stateParams, $uibModalInstance, track, entity, Stream) {
        var vm = this;

        vm.track = track;
        vm.stream = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            Stream.addTrack({id: vm.stream.id}, track);
            $uibModalInstance.close(true);
        }

        function onSaveSuccess (result) {
            $scope.$emit('kpicsApp:trackUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }
    }
})();
