(function() {
    'use strict';

    angular
        .module('kpicsApp')
        .controller('StreamTrackSubjectDeleteController',StreamTrackSubjectDeleteController);

    StreamTrackSubjectDeleteController.$inject = ['$uibModalInstance', 'stream', '$stateParams', 'Stream'];

    function StreamTrackSubjectDeleteController($uibModalInstance, stream, $stateParams, Stream) {
        var vm = this;

        vm.stream = stream;
        vm.track = $stateParams.trackId;
        vm.subject = $stateParams.subjectId;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Stream.deleteSubject({id: id, trackId: vm.track, subjectId: vm.subject},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
