(function() {
    'use strict';

    angular
        .module('kpicsApp')
        .controller('ShowcaseController', ShowcaseController);

    ShowcaseController.$inject = ['$scope', '$state', 'TrackShowcase', 'students', 'trackDefinitions', 'teachers','ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams' ];

    function ShowcaseController ($scope, $state, TrackShowcase, students, trackDefinitions, teachers, ParseLinks, AlertService, paginationConstants, pagingParams ) {
        let vm = this;

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;

        vm.students = students;
        vm.trackDefinitions = trackDefinitions;
        vm.teachers = teachers;

        loadAll();


        function loadAll () {
            TrackShowcase.getUniqueTrackShowcases({
                page: pagingParams.page - 1,
                size: vm.itemsPerPage,
                sort: sort()
            }, onSuccess, onError);
            function sort() {
                let result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'name') {
                    result.push('name');
                }
                return result;
            }
            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                vm.queryCount = vm.totalItems;
                vm.trackShowcases = data;
                vm.page = pagingParams.page;
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function loadPage(page) {
            vm.page = page;
            vm.transition();
        }

        function transition() {
            $state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch
            });
        }
    }
})();
