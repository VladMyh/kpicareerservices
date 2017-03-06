(function() {
    'use strict';

    angular
        .module('kpicsApp')
        .controller('StreamDetailController', StreamDetailController);

    StreamDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Stream', '$state'];

    function StreamDetailController($scope, $rootScope, $stateParams, previousState, entity, Stream, $state) {
        var vm = this;

        vm.stream = entity;
        vm.previousState = previousState.name;
        vm.getTeacher = getTeacher;
        vm.save = save;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.isTeacherUsed = isTeacherUsed;

        function getTeacher(arr, id) {
            return arr.find(function (t) {
                if(t.id === id) {
                    return t;
                }
            })
        }

        function isTeacherUsed(trackInfo, teacherId) {
            for (var i = 0; i < trackInfo.subjects.length; i++) {
                if (trackInfo.subjects[i].teacherId === teacherId) {
                    return true
                }
            }

            return false;
        }

        function save () {
            vm.isSaving = true;
            Stream.update(vm.stream, onSaveSuccess, onSaveError);
        }

        function onSaveSuccess (result) {
            $scope.$emit('kpicsApp:streamUpdate', result);
            vm.isSaving = false;
            $state.reload();
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }

        var unsubscribe = $rootScope.$on('kpicsApp:streamUpdate', function(event, result) {
            vm.stream = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
