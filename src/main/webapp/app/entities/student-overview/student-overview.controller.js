(function() {
    'use strict';

    angular
        .module('kpicsApp')
        .controller('StudentOverviewController', StudentOverviewController);

    StudentOverviewController.$inject = ['$scope', '$state', 'entity', 'StudentOverview'];

    function StudentOverviewController ($scope, $state, entity, StudentOverview) {

        var vm = this;

        vm.studentInfo = entity;


        //vm.loadPage = loadPage;

        //vm.transition = transition;

        //loadAll();
        generatePageInfo();


        function generatePageInfo() {
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
                    academicYearStarted: 2015,
                    academicYearEnded: 2016,
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
                    academicYearStarted: 2014,
                    academicYearEnded: 2015,
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
                    academicYearStarted: 2013,
                    academicYearEnded: 2015,
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
                    academicYearStarted: 2012,
                    academicYearEnded: 2014,
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
                    academicYearStarted: 2016,
                    academicYearEnded: 2017,
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
        }
    }
})();
