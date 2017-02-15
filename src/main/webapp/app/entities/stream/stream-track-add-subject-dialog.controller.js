(function() {
    'use strict';

    angular
        .module('kpicsApp')
        .controller('TrackNewSubjectDialogController', TrackNewSubjectDialogController);

    TrackNewSubjectDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'track', 'stream', 'subject', 'Stream'];

    function TrackNewSubjectDialogController ($timeout, $scope, $stateParams, $uibModalInstance, track, stream, subject, Stream) {
        var vm = this;

        vm.track = track;
        vm.stream = stream;
        vm.subject = subject;
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
            Stream.addSubjectToTrack({id: vm.stream.id, trackId: vm.track.id}, vm.subject);
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
