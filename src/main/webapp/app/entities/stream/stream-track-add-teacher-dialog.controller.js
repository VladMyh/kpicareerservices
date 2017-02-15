(function() {
    'use strict';

    angular
        .module('kpicsApp')
        .controller('TrackAddTeacherDialogController', TrackAddTeacherDialogController);

    TrackAddTeacherDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'stream', 'track',
        'Stream', 'TeacherInfo'];

    function TrackAddTeacherDialogController ($timeout, $scope, $stateParams, $uibModalInstance, stream, track, Stream, TeacherInfo) {
        var vm = this;

        vm.stream = stream;
        vm.track = track;
        vm.clear = clear;
        vm.search = search;
        vm.addTeacher = addTeacher;
        vm.query = null;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function search () {
            if(vm.query !== null && vm.query !== '') {
                TeacherInfo.find({query: vm.query}, success, error);
            }
            function success(data, headers) {
                vm.teachers = data;
            }
            function error(error) {}
        }

        function onSaveSuccess (result) {
            $scope.$emit('kpicsApp:trackUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {}

        function addTeacher(teacherId) {
            if(vm.track.teachers.indexOf(teacherId) == -1) {
                Stream.addTeacherToTrack({id: vm.stream.id, trackId: vm.track.id}, teacherId,
                    function () {
                        $uibModalInstance.close(true);
                    });
            }
        }
    }
})();
