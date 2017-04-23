(function() {
    'use strict';

    angular
        .module('kpicsApp')
        .controller('GroupDetailController', GroupDetailController);

    GroupDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Group'];

    function GroupDetailController($scope, $rootScope, $stateParams, previousState, entity, Group) {
        var vm = this;

        vm.group = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('kpicsApp:groupUpdate', function(event, result) {
            vm.group = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
