(function() {
    'use strict';

    angular
        .module('kpicsApp')
        .controller('StreamTrackDeleteController',StreamTrackDeleteController);

    StreamTrackDeleteController.$inject = ['$uibModalInstance', 'stream', '$stateParams', 'Stream'];

    function StreamTrackDeleteController($uibModalInstance, stream, $stateParams, Stream) {
        var vm = this;

        vm.stream = stream;
        vm.track = $stateParams.trackId;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Stream.deleteTrack({id: id, trackId: vm.track},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
