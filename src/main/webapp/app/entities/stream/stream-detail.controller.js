(function() {
    'use strict';

    angular
        .module('kpicsApp')
        .controller('StreamDetailController', StreamDetailController);

    StreamDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Stream'];

    function StreamDetailController($scope, $rootScope, $stateParams, previousState, entity, Stream) {
        var vm = this;

        vm.stream = entity;
        vm.previousState = previousState.name;
        vm.getTeacher = getTeacher;

        function getTeacher(arr, id) {
            return arr.find(function (t) {
                if(t.id === id) {
                    return t;
                }
            })
        }

        var unsubscribe = $rootScope.$on('kpicsApp:streamUpdate', function(event, result) {
            vm.stream = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
