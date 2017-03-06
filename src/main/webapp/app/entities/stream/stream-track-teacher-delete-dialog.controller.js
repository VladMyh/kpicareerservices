(function() {
    'use strict';

    angular
        .module('kpicsApp')
        .controller('StreamTrackTeacherDeleteController',StreamTrackTeacherDeleteController);

    StreamTrackTeacherDeleteController.$inject = ['$uibModalInstance', 'stream', '$stateParams', 'Stream'];

    function StreamTrackTeacherDeleteController($uibModalInstance, stream, $stateParams, Stream) {
        var vm = this;

        vm.stream = stream;
        vm.trackId = $stateParams.trackId;
        vm.teacherId = $stateParams.teacherId;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Stream.deleteTeacher({id: id, trackId: vm.trackId, teacherId: vm.teacherId},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
