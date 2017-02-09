(function() {
    'use strict';

    angular
        .module('kpicsApp')
        .controller('TrackAddTeacherDialogController', TrackAddTeacherDialogController);

    TrackAddTeacherDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity',
        'Track', 'TeacherInfo'];

    function TrackAddTeacherDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Track, TeacherInfo) {
        var vm = this;

        vm.track = entity;
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
            if(vm.track.teacherIds.indexOf(teacherId) == -1) {
                vm.track.teacherIds.push(teacherId);

                if(vm.track.isActive == null) {
                    vm.track.isActive = false;
                }

                Track.update(vm.track,
                    function () {
                        $uibModalInstance.close(true);
                    });
            }
        }
    }
})();
