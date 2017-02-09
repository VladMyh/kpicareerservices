(function() {
    'use strict';

    angular
        .module('kpicsApp')
        .controller('TrackDetailController', TrackDetailController);

    TrackDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Track'];

    function TrackDetailController($scope, $rootScope, $stateParams, previousState, entity, Track) {
        var vm = this;

        vm.track = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('kpicsApp:trackUpdate', function(event, result) {
            vm.track = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
