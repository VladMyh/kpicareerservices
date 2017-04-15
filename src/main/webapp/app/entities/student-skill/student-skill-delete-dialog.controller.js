(function() {
    'use strict';

    angular
        .module('kpicsApp')
        .controller('StudentSkillDeleteController',StudentSkillDeleteController);

    StudentSkillDeleteController.$inject = ['$uibModalInstance', 'skill', 'student', 'StudentInfo'];

    function StudentSkillDeleteController($uibModalInstance, skill, student, StudentInfo) {
        let vm = this;

        vm.skill = skill;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (skill) {
            StudentInfo.deleteSkill({id: student.id}, skill,
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
