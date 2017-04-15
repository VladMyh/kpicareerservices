(function() {
    'use strict';

    angular
        .module('kpicsApp')
        .controller('TrackDefinitionDetailController', TrackDefinitionDetailController);

    TrackDefinitionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TrackDefinition'];

    function TrackDefinitionDetailController($scope, $rootScope, $stateParams, previousState, entity, TrackDefinition) {
        var vm = this;

        vm.trackDefinition = entity;
        vm.previousState = previousState.name;


        var unsubscribe = $rootScope.$on('kpicsApp:trackDefinitionUpdate', function(event, result) {
            vm.trackDefinition = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
