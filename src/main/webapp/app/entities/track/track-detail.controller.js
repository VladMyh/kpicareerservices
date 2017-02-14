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

        angular.element(document).ready(function () {
            Track.teachers(vm.track).then(onSuccess);
        });

        function onSuccess(data, headers) {
            vm.teachers = data;
        }
        function onError(error) {
            AlertService.error(error.data.message);
        }

        var unsubscribe = $rootScope.$on('kpicsApp:trackUpdate', function(event, result) {
            vm.track = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
