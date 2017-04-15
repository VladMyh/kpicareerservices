(function() {
    'use strict';

    angular
        .module('kpicsApp')
        .controller('TrackDefinitionDialogController', TrackDefinitionDialogController);

    TrackDefinitionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TrackDefinition', 'AlertService'];

    function TrackDefinitionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TrackDefinition, AlertService) {
        let vm = this;

        vm.trackDefinition = entity;
        vm.clear = clear;
        vm.save = save;
        vm.addSkillDefinition = addSkillDefinition;
        vm.removeSkillDefinition = removeSkillDefinition;


        function addSkillDefinition () {
            if (vm.skillDefinition && !vm.skillDefinition.isEmpty()) {
                if (!vm.trackDefinition.skillDefinitions.includes(vm.skillDefinition)) {
                    vm.trackDefinition.skillDefinitions.push(vm.skillDefinition);

                    vm.skillDefinition = null;
                }
                else {
                    AlertService.error("kpicsApp.trackDefinition.addSkillDefinition.alreadyAdded", {param: vm.skillDefinition});
                }
            }
            else {
                AlertService.error("kpicsApp.trackDefinition.addSkillDefinition.required");
            }
        }

        function removeSkillDefinition(skillDefinition) {

            let skillDefinitionIndex = vm.trackDefinition.skillDefinitions.indexOf(skillDefinition.skillDefinition);

            if (skillDefinitionIndex > -1)
            vm.trackDefinition.skillDefinitions.splice(skillDefinitionIndex, 1);

            // for(var i = vm.trackDefinition.skillDefinitions.length - 1; i >= 0; i--) {
            //     console.dir(vm.trackDefinition.skillDefinitions[i].SkillDefinition);
            //     console.dir(skillDefinition);
            //     console.log("--------");
            //
            //     if(vm.trackDefinition.skillDefinitions[i] == skillDefinition) {
            //         vm.trackDefinition.skillDefinitions.splice(i, 1);
            //         break;
            //     }
            // }
        }

        String.prototype.isEmpty = function() {
            return (this.length === 0 || !this.trim());
        };

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.trackDefinition.id !== null) {
                TrackDefinition.update(vm.trackDefinition, onSaveSuccess, onSaveError);
            } else {
                TrackDefinition.save(vm.trackDefinition, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('kpicsApp:trackDefinitionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }
    }
})();
