(function() {
    'use strict';

    angular
        .module('kpicsApp')
        .controller('StudentSkillDetailController', StudentSkillDetailController);

    StudentSkillDetailController.$inject = ['$scope', '$state', '$rootScope', '$stateParams', 'previousState', 'entity', 'StudentInfo'];

    function StudentSkillDetailController ($scope, $state, $rootScope, $stateParams,  previousState, entity, StudentInfo) {

        let vm = this;



        vm.studentInfo = entity;
        vm.previousState = previousState.name;

        vm.tracks = groupSkillsToTracks(vm.studentInfo.skills);

        let unsubscribe = $rootScope.$on('kpicsApp:studentSkillUpdate', function(event, result) {
            vm.studentInfo = result;
        });
        $scope.$on('$destroy', unsubscribe);

        vm.edit = function (skill) {
            $state.selectedSkill = skill;
            $state.go('student-skill-detail.edit');
        };

        vm.delete = function (skill) {
            $state.selectedSkill = skill;
            $state.go('student-skill-detail.delete');
        };

        function groupSkillsToTracks(skills){
            let tracks = {};

            for (let i in skills){
                let skill = skills[i];

                tracks[skill.trackName] = tracks[skill.trackName] || [];

                let academicYears = skill.academicYear.split('-'),
                    startDate = academicYears[0],
                    endDate   = academicYears[1];


                tracks[skill.trackName].skills = tracks[skill.trackName].skills || [];
                tracks[skill.trackName].skills.push(skill);


                tracks[skill.trackName].marks = tracks[skill.trackName].marks || [];
                tracks[skill.trackName].marks.push(skill.mark);


                tracks[skill.trackName].startDate = tracks[skill.trackName].startDate || startDate;
                tracks[skill.trackName].endDate = tracks[skill.trackName].endDate || endDate;
                tracks[skill.trackName].startDate = (startDate < tracks[skill.trackName].startDate) ? startDate : tracks[skill.trackName].startDate;
                tracks[skill.trackName].endDate = (endDate > tracks[skill.trackName].endDate) ? endDate : tracks[skill.trackName].endDate;
            }

            for (let trackName in tracks){
                let marksSum = tracks[trackName].marks.reduce(function(a, b) {return a + b}, 0);
                tracks[trackName].name = trackName;
                tracks[trackName].mark = marksSum / tracks[trackName].marks.length;

                tracks[trackName].skills.sort(compareSkills);
            }

            return tracks;
        }

        function compareSkills(a,b){
            if (!a.academicYear || !b.academicYear)
                return 0;

            if (a.academicYear === b.academicYear)
                return a.semester > b.semester;

            if (a.academicYear > b.academicYear)
                return 1;
            else
                return -1;
        }
    }
})();
