(function() {
    'use strict';

    angular
        .module('kpicsApp')
        .controller('StudentSkillDialogController', StudentSkillDialogController);

    StudentSkillDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'StudentInfo', 'student', 'teachers', 'skill', 'generatorParams', 'trackDefinition'];

    function StudentSkillDialogController ($timeout, $scope, $stateParams, $uibModalInstance, StudentInfo, student, teachers, skill, generatorParams, trackDefinition) {
        let vm = this;

        vm.StudentInfo = student;
        vm.teacher = teachers;
        vm.trackDefinition = trackDefinition;

        vm.skill = jQuery.extend({}, skill);

        vm.clear = clear;
        vm.save = save;
        vm.getSkillsByTrackName = getSkillsByTrackName;
        vm.clearSkillName = clearSkillName;

        vm.academicYears = generateAcademicYearsFromYear(generatorParams.firstAcademicYear);

        vm.isSkillExist = vm.skill.name && vm.skill.trackName;

        if (vm.isSkillExist) {
            prepareFormForExistedSkill();
        }
        else {
            prepareFormForNewSkill();
        }

        function prepareFormForExistedSkill() {
            if (!vm.academicYears.includes(skill.academicYear))
                vm.academicYears.push(skill.academicYear);

            addSkillToTrackDefinitionIfNotContains(skill);

            vm.skill.semester = vm.skill.semester + '';
        }

        function prepareFormForNewSkill() {
            vm.skill.academicYear = vm.academicYears[0] || null;
            vm.skill.semester = predictSemester();
        }

        function predictSemester(){
            let currentMonth = new Date().getMonth();
            return ((currentMonth > 1 && currentMonth < 8) ? 2 : 1) + '';
        }

        function generateAcademicYearsFromYear(firstYear) {
            let academicYears = [],
                currentYear = new Date().getFullYear(),
                currentMonth = new Date().getMonth();

            let lastYear = (currentMonth > 8) ? currentYear + 1 : currentYear;

            if (lastYear > firstYear){
                for(let year = lastYear; year > firstYear; year--){
                    academicYears.push(year - 1 + '-' + year);
                }
            }
            return academicYears;
        }

        function addSkillToTrackDefinitionIfNotContains(skill) {
            let trackDefinitionNameArray = vm.trackDefinition.map(function(a) {return a.name;});

            if (!trackDefinitionNameArray.includes(skill.trackName)){
                let trackDef = {
                    id: null,
                    name: skill.trackName,
                    skillDefinitions: [skill.name],
                };

                vm.trackDefinition.push(trackDef);
            }
            else {
                for (let trackIndex in vm.trackDefinition){
                    let trackDef = vm.trackDefinition[trackIndex];

                    if(skill.trackName === trackDef.name){
                        if (!trackDef.skillDefinitions.includes(skill.name)){
                            trackDef.skillDefinitions.push(skill.name);
                        }
                        break;
                    }
                }
            }
        }

        $timeout(function (){
            angular.element('.form-group:eq(0)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;

            if (vm.isSkillExist)
                StudentInfo.updateSkill({id: vm.StudentInfo.id}, [skill, vm.skill], onSaveSuccess, onSaveError);
            else
                StudentInfo.addSkillToStudent({id: vm.StudentInfo.id}, vm.skill, onSaveSuccess, onSaveError);
        }

        function onSaveSuccess (result) {
            $scope.$emit('kpicsApp:StudentSkillUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        function getSkillsByTrackName(name) {
            if (vm.trackDefinition && name)
                return vm.trackDefinition.find(function(a) {return a.name === name;}).skillDefinitions;
        }
        function clearSkillName() {
            vm.skill.name = null;
        }
    }
})();
