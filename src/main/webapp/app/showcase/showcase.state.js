(function() {
    'use strict';

    angular
        .module('kpicsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('showcase', {
                parent: 'app',
                url: '/showcase',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'kpicsApp.showcase.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/showcase/showcase.html',
                        controller: 'ShowcaseController',
                        controllerAs: 'vm'
                    }
                },
                params: {
                    page: {
                        value: '1',
                        squash: true
                    },
                    sort: {
                        value: 'name,asc',
                        squash: true
                    },
                    search: null
                },
                resolve: {
                    pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                        return {
                            page: PaginationUtil.parsePage($stateParams.page),
                            sort: $stateParams.sort,
                            predicate: PaginationUtil.parsePredicate($stateParams.sort),
                            ascending: PaginationUtil.parseAscending($stateParams.sort),
                            search: $stateParams.search
                        };
                    }],
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('showcase');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    students: ['StudentInfo', function(StudentInfo) {
                        return StudentInfo.query({size: 999}).$promise;
                    }],
                    teachers: ['TeacherInfo', function (TeacherInfo) {
                        return TeacherInfo.query({size: 999}).$promise;
                    }],
                    trackDefinitions: ['TrackDefinition', function(TrackDefinition) {
                        return TrackDefinition.query({size: 999}).$promise;
                    }]
                }
            })
            .state('showcase.tracks',{
                parent: 'app',
                url: '/tracks',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'kpicsApp.trackShowcase.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/showcase/showcase-tracks.html',
                        controller: 'ShowcaseTracksController',
                        controllerAs: 'vm'
                    }
                },
                params: {
                    page: {
                        value: '1',
                        squash: true
                    },
                    sort: {
                        value: 'id,asc',
                        squash: true
                    },
                    search: null
                },
                resolve: {
                    pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                        return {
                            page: PaginationUtil.parsePage($stateParams.page),
                            sort: $stateParams.sort,
                            predicate: PaginationUtil.parsePredicate($stateParams.sort),
                            ascending: PaginationUtil.parseAscending($stateParams.sort),
                            search: $stateParams.search
                        };
                    }],
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('showcase');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    previousState: function () {
                        return null;
                    }
                }
            })
            .state('showcase.tracks.faculty',{
                parent: 'showcase',
                url: '/faculty',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'kpicsApp.trackShowcase.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/showcase/showcase-tracks.html',
                        controller: 'ShowcaseTracksController',
                        controllerAs: 'vm'
                    }
                },
                params: {
                    name: null,
                    academicYear: null,
                    page: {
                        value: '1',
                        squash: true
                    },
                    sort: {
                        value: 'id,asc',
                        squash: true
                    },
                    search: null
                },
                resolve: {
                    pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                        return {
                            page: PaginationUtil.parsePage($stateParams.page),
                            sort: $stateParams.sort,
                            predicate: PaginationUtil.parsePredicate($stateParams.sort),
                            ascending: PaginationUtil.parseAscending($stateParams.sort),
                            search: $stateParams.search
                        };
                    }],
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('showcase');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    previousState: ["$state", function ($state) {
                        let currentStateData = {
                            name: $state.current.name || 'showcase',
                            params: $state.params,
                            url: $state.href($state.current.name, $state.params)
                        };
                        return currentStateData;
                    }]
                }
            })
            .state('showcase.tracks.students',{
                parent: 'showcase',
                url: '/students',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'kpicsApp.showcase.forStudents.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/showcase/showcase-students.html',
                        controller: 'ShowcaseStudentsController',
                        controllerAs: 'vm'
                    }
                },
                params: {
                    trackName: null,
                    academicYear: null,
                    page: {
                        value: '1',
                        squash: true
                    },
                    sort: {
                        value: 'id,asc',
                        squash: true
                    },
                    search: null
                },
                resolve: {
                    pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                        return {
                            page: PaginationUtil.parsePage($stateParams.page),
                            sort: $stateParams.sort,
                            predicate: PaginationUtil.parsePredicate($stateParams.sort),
                            ascending: PaginationUtil.parseAscending($stateParams.sort),
                            search: $stateParams.search
                        };
                    }],
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('showcase');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    previousState: ["$state", function ($state) {
                        let currentStateData = {
                            name: $state.current.name || 'showcase',
                            params: $state.params,
                            url: $state.href($state.current.name, $state.params)
                        };
                        return currentStateData;
                    }]
                }
            })
            .state('showcase.tracks.faculty.students',{
                parent: 'showcase.tracks',
                url: '/students',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'kpicsApp.showcase.forStudents.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/showcase/showcase-students.html',
                        controller: 'ShowcaseStudentsController',
                        controllerAs: 'vm'
                    }
                },
                params: {
                    trackName: null,
                    academicYear: null,
                    faculty: null,
                    page: {
                        value: '1',
                        squash: true
                    },
                    sort: {
                        value: 'id,asc',
                        squash: true
                    },
                    search: null
                },
                resolve: {
                    pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                        return {
                            page: PaginationUtil.parsePage($stateParams.page),
                            sort: $stateParams.sort,
                            predicate: PaginationUtil.parsePredicate($stateParams.sort),
                            ascending: PaginationUtil.parseAscending($stateParams.sort),
                            search: $stateParams.search
                        };
                    }],
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('showcase');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }],
                    previousState: ["$state", function ($state) {
                        let currentStateData = {
                            name: $state.current.name || 'showcase',
                            params: $state.params,
                            url: $state.href($state.current.name, $state.params)
                        };
                        return currentStateData;
                    }]
                }
            })
            .state('showcase.student-skills', {
                parent: 'showcase',
                url: '/student/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'kpicsApp.showcase.students.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/showcase/showcase-student-skills.html',
                        controller: 'ShowcaseStudentSkillsController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('studentOverview');
                        $translatePartialLoader.addPart('showcase');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'StudentInfo', function($stateParams, StudentInfo) {
                        return StudentInfo.get({id: $stateParams.id}).$promise;
                    }],
                    previousState: ["$state", function ($state) {
                        let currentStateData = {
                            name: $state.current.name || 'showcase',
                            params: $state.params,
                            url: $state.href($state.current.name, $state.params)
                        };
                        return currentStateData;
                    }]
                }
            })
    }
})();
