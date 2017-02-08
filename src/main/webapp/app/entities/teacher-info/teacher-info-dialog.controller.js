(function() {
    'use strict';

    angular
        .module('kpicsApp')
        .controller('TeacherInfoDialogController', TeacherInfoDialogController);

    TeacherInfoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity',
                                         'TeacherInfo', 'Auth', '$translate'];

    function TeacherInfoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity,
                                          TeacherInfo, Auth, $translate) {
        var vm = this;

        vm.user = entity;
        vm.clear = clear;
        vm.save = save;
        vm.doNotMatch = null;
        vm.error = null;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.user.id !== null) {
                TeacherInfo.update(vm.user, onSaveSuccess, onSaveError);
            } else {
                if (vm.user.password !== vm.confirmPassword) {
                    vm.doNotMatch = 'ERROR';
                    vm.isSaving = false;
                }
                else {
                    vm.user.langKey = $translate.use();
                    vm.errorEmailExists = null;
                    vm.error = null;

                    Auth.createTeacherAccount(vm.user).then(function (result) {
                        onSaveSuccess(result);
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
        }

        function onSaveSuccess (result) {
            $scope.$emit('kpicsApp:teacherInfoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
