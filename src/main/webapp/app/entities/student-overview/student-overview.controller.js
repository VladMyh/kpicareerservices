(function() {
    'use strict';

    angular
        .module('kpicsApp')
        .controller('StudentOverviewController', StudentOverviewController);

    StudentOverviewController.$inject = ['$scope', '$state', 'StudentOverview', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams'];

    function StudentOverviewController ($scope, $state, StudentInfo, ParseLinks, AlertService, paginationConstants, pagingParams) {
        var vm = this;

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;

        loadAll();

        vm.studentInfo.id = 1000;
        vm.studentInfo.firstName = "Petro";
        vm.studentInfo.lastName = "Bizeshenko";
        vm.studentInfo.email = "petro2000@ukr.net";
        vm.studentInfo.linkedin = "http:\\someLinkedIn";
        vm.studentInfo.github = "http:\\someGitHub";
        vm.studentInfo.activeted = true;
        vm.studentInfo.faculty = "IOT";
        vm.studentInfo.department = "TK";
        vm.studentInfo.group = "IT-32";
        vm.studentInfo.about = "Some about info. Information technology (IT) is the application of computers to store, study, retrieve, transmit, and manipulate data, or information, often in the context of a business or other enterprise. IT is considered a subset of information and communications technology (ICT). In 2012, Zuppo proposed an ICT hierarchy where each hierarchy level \"contain some degree of commonality in that they are related to technologies that facilitate the transfer of information and various types of electronically mediated communications.\"";

        function loadAll () {
            StudentInfo.query({
                page: pagingParams.page - 1,
                size: vm.itemsPerPage,
                sort: sort()
            }, onSuccess, onError);
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }
            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                vm.queryCount = vm.totalItems;
                vm.studentInfos = data;
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
