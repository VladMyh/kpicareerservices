(function () {
    'use strict';

    angular
        .module('kpicsApp')
        .factory('Register', Register);

    Register.$inject = ['$resource'];

    function Register ($resource) {

        var service = {
          registerStudent: registerStudent,
          registerTeacher: registerTeacher
        };

        return service;

        function registerStudent() {
            return $resource('api/student/register', {}, {});
        }

        function registerTeacher() {
            return $resource('api/teacher/register', {}, {});
        }
    }
})();
