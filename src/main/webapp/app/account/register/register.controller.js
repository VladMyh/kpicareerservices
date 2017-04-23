(function() {
    'use strict';

    angular
        .module('kpicsApp')
        .controller('RegisterController', RegisterController);


    RegisterController.$inject = ['$translate', '$timeout', 'Auth', 'LoginService', 'Faculty'];

    function RegisterController ($translate, $timeout, Auth, LoginService, Faculty) {
        var vm = this;

        vm.doNotMatch = null;
        vm.error = null;
        vm.errorUserExists = null;
        vm.login = LoginService.open;
        vm.register = register;
        vm.registerAccount = {};
        vm.registerAccount.studentInfo = {};
        vm.success = null;
        vm.faculties = Faculty.query(onFacultySuccess);
        vm.departments = null;
        vm.onSelectChange = onSelectChange;
        vm.selectedFaculty = null;
        vm.selectedDepartment = null;

        $timeout(function (){angular.element('#login').focus();});

        function register () {
            if (vm.registerAccount.password !== vm.confirmPassword) {
                vm.doNotMatch = 'ERROR';
            } else {
                vm.registerAccount.langKey = $translate.use();
                vm.registerAccount.studentInfo.faculty = vm.selectedFaculty.name;
                vm.registerAccount.studentInfo.department = vm.selectedDepartment.name;
                vm.doNotMatch = null;
                vm.error = null;
                vm.errorUserExists = null;
                vm.errorEmailExists = null;

                Auth.createStudentAccount(vm.registerAccount).then(function () {
                    vm.success = 'OK';
                }).catch(function (response) {
                    vm.success = null;
                    if (response.status === 400 && response.data === 'e-mail address already in use') {
                        vm.errorEmailExists = 'ERROR';
                    } else {
                        vm.error = 'ERROR';
                    }
                });
            }
        }

        function onSelectChange() {
            for(var i = 0; i < vm.faculties.length; i++) {
                if(vm.faculties[i].name === vm.selectedFaculty.name) {
                    vm.departments = vm.faculties[i].departments;
                }
            }
        }

        function onFacultySuccess() {
            vm.departments = vm.faculties[0].departments;
        }
    }
})();
