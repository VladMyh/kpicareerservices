(function() {
    'use strict';

    angular
        .module('kpicsApp')
        .controller('StudentOverviewController', StudentOverviewController);

    StudentOverviewController.$inject = ['$scope', '$state', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams'];

    function StudentOverviewController ($scope, $state, ParseLinks, AlertService, paginationConstants, pagingParams) {
        var vm = this;

        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;

        loadAll();

        vm.studentInfo = {};
        vm.studentInfo.id = 1000;
        vm.studentInfo.firstName = "Petro";
        vm.studentInfo.lastName = "Bizeshko";
        vm.studentInfo.email = "petro2000@ukr.net";
        vm.studentInfo.linkedin = "http:\\someLinkedIn";
        vm.studentInfo.github = "http:\\someGitHub";
        vm.studentInfo.activeted = true;
        vm.studentInfo.faculty = "IOT";
        vm.studentInfo.department = "TK";
        vm.studentInfo.group = "IT-32";

        vm.studentInfo.avatar = "https://cdn.worldvectorlogo.com/logos/jhipster.svg";

        vm.studentInfo.about = "Some about info. Information technology (IT) is the application of computers to store, study, retrieve, transmit, and manipulate data, or information, often in the context of a business or other enterprise. IT is considered a subset of information and communications technology (ICT). In 2012, Zuppo proposed an ICT hierarchy where each hierarchy level \"contain some degree of commonality in that they are related to technologies that facilitate the transfer of information and various types of electronically mediated communications.\"";

        vm.description = "This course introduces the scientific principles behind food safety and sanitation practices as well as practical and effective methods you can implement in your plant to keep your products safe.";

        vm.studentInfo.tracks = [
            {
                name: "C#",
                mark: 85,
                skills: [
                    {
                        name: "Level 1 (.Net Core)",
                        description: ".Net Core. " + vm.description ,
                        mark: 53
                    },
                    {
                        name: "Level 2 (ADO)",
                        description: "ADO. "+ vm.description ,
                        mark: 21

                    },
                    {
                        name: "Level 3 (ASP .NET.)",
                        description: "ASP .NET." + vm.description,
                        mark: 80
                    }
                ]
            },
            {
                name: "JAVA",
                mark: 67,
                skills: [
                    {
                        name: "Level 1 (Java Core)",
                        description: "Java Core. " + vm.description ,
                        mark: 43
                    },
                    {
                        name: "Level 2 (JAVA EE)",
                        description: "JAVA EE. "+ vm.description ,
                        mark: 82

                    },
                    {
                        name: "Level 3 (SPRING MVC)",
                        description: "SPRING MVC. " + vm.description,
                        mark: 27
                    }
                ]
            },
            {
                name:"JavaScript",
                mark:58,
                skills: [
                    {
                        name: "Level 1 (JS Core)",
                        description: "JS Core. " + vm.description ,
                        mark: 53
                    },
                    {
                        name: "Level 2 (ECMAScript)",
                        description: "ECMAScript. "+ vm.description ,
                        mark: 72

                    }
                ]
            },
            {
                name:"C++",
                mark:42,
                skills: [
                    {
                        name: "Level 1 (Core)",
                        description: "Core. " + vm.description ,
                        mark: 78
                    },
                    {
                        name: "Level 2 (Apps dev)",
                        description: "Apps dev. "+ vm.description ,
                        mark: 20

                    },
                    {
                        name: "Level 3 (Apps dev pro)",
                        description: "Apps dev pro. " + vm.description,
                        mark: 55
                    }
                ]
            },
            {
                name:"Assembler",
                mark:8,
                skills: [
                    {
                        name: "Level 1 (FLAGS)",
                        description: "FLAGS. " + vm.description ,
                        mark: 25
                    },
                    {
                        name: "Level 2 (MOV|JUMP)",
                        description: "MOV|JUMP. "+ vm.description ,
                        mark: 39

                    },
                    {
                        name: "Level 3 (NE|NZ)",
                        description: "NE|NZ." + vm.description,
                        mark: 17
                    }
                ]
            }
        ];


        function loadAll () {

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
