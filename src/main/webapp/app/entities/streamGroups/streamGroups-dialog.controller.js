(function() {
    'use strict';

    angular
        .module('kpicsApp')
        .controller('StreamGroupsDialogController', StreamGroupsDialogController);

    StreamGroupsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance',
        'Stream', 'Group', 'Faculty'];

    function StreamGroupsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, Stream, Group, Faculty) {
        var vm = this;

        vm.clear = clear;
        vm.faculties = Faculty.query(onFacultySuccess);
        vm.departments = null;
        vm.onFacultyChange = onFacultyChange;
        vm.onDepartmentChange = onDepartmentChange;
        vm.selectedFaculty = null;
        vm.selectedDepartment = null;
        vm.groups = null;
        vm.streamId = $stateParams.streamId;
        vm.addGroupToStream = addGroupToStream;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function onFacultyChange() {
            for(var i = 0; i < vm.faculties.length; i++) {
                if(vm.faculties[i].name === vm.selectedFaculty.name) {
                    vm.departments = vm.faculties[i].departments;

                    Group.getByFaculty({faculty: vm.selectedFaculty.name}, onSuccess);
                }
            }
        }

        function onDepartmentChange() {
            if(vm.selectedDepartment !== null) {
                Group.getByFacultyAndDepartment({faculty: vm.selectedFaculty.name,
                                                 department: vm.selectedDepartment.name}, onSuccess);
            }
        }

        function onSuccess(data) {
            vm.groups = data;
        }

        function onFacultySuccess() {
            vm.departments = vm.faculties[0].departments;
        }

        function addGroupToStream(groupId) {
            Stream.addGroup({id: vm.streamId}, groupId);

            clear();
        }
    }
})();
