(function() {
    'use strict';

    angular
        .module('kpicsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('student-skill', {
                parent: 'entity',
                url: '/student-skill?page&sort&search',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'kpicsApp.studentInfo.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/student-skill/student-skills.html',
                        controller: 'StudentSkillController',
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
                        $translatePartialLoader.addPart('studentSkill');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('student-skill.addSkill', {
                parent: 'student-skill',
                url: '/student-skill/{id}/newSkill',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/student-skill/student-skill-dialog.html',
                        controller: 'StudentSkillDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            skill: function () {
                                return {
                                    teacherId: null,
                                    name: null,
                                    trackName: null,
                                    academicYear: null,
                                    semester: null,
                                    mark: null,
                                    review: null
                                };
                            },
                            generatorParams: function () {
                                return{
                                    firstAcademicYear: 2010
                                };
                            },
                            student: ['StudentInfo', function(StudentInfo) {
                                return StudentInfo.get({id : $stateParams.id}).$promise;
                            }],
                            teachers: ['TeacherInfo', function (TeacherInfo) {
                                return TeacherInfo.query({size: 999}).$promise;
                            }],
                            trackDefinition: ['TrackDefinition', function(TrackDefinition) {
                                return TrackDefinition.query({size: 999}).$promise;
                            }]
                        }
                    }).result.then(function() {
                        $state.go('student-skill', null, { reload: 'student-skill' });
                    }, function() {
                        $state.go('student-skill');
                    });
                }]
            })
            .state('student-skill-detail', {
                parent: 'student-skill',
                url: '/student-skill/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'kpicsApp.studentOverview.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/student-skill/student-skill-detail.html',
                        controller: 'StudentSkillDetailController',
                        controllerAs: 'vm'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('studentOverview');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'StudentInfo', function($stateParams, StudentInfo) {
                        return StudentInfo.get({id: $stateParams.id}).$promise;
                    }],
                    previousState: ["$state", function ($state) {
                        let currentStateData = {
                            name: $state.current.name || 'student-skill',
                            params: $state.params,
                            url: $state.href($state.current.name, $state.params)
                        };
                        return currentStateData;
                    }]
                }
            })
            .state('student-skill-detail.addSkill', {
                parent: 'student-skill-detail',
                url: '/student-skill/{id}/newSkill',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/student-skill/student-skill-dialog.html',
                        controller: 'StudentSkillDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            skill: function () {
                                return {
                                    teacherId: null,
                                    name: null,
                                    trackName: null,
                                    academicYear: null,
                                    semester: null,
                                    mark: null,
                                    review: null
                                };
                            },
                            generatorParams: function () {
                                return{
                                    firstAcademicYear: 2010
                                };
                            },
                            student: ['StudentInfo', function(StudentInfo) {
                                return StudentInfo.get({id : $stateParams.id}).$promise;
                            }],
                            teachers: ['TeacherInfo', function (TeacherInfo) {
                                return TeacherInfo.query({size: 999}).$promise;
                            }],
                            trackDefinition: ['TrackDefinition', function(TrackDefinition) {
                                return TrackDefinition.query({size: 999}).$promise;
                            }]
                        }
                    }).result.then(function() {
                        $state.go('student-skill-detail', null, { reload: 'student-skill-detail' });
                    }, function() {
                        $state.go('student-skill-detail');
                    });
                }]
            })
            .state('student-skill-detail.edit', {
                parent: 'student-skill-detail',
                url: '/detail/edit',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/student-skill/student-skill-dialog.html',
                        controller: 'StudentSkillDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            skill: ['$state', function() {
                                return $state.selectedSkill.skill;
                            }],
                            generatorParams: function () {
                                return{
                                    firstAcademicYear: 2010
                                };
                            },
                            student: ['StudentInfo', function(StudentInfo) {
                                return StudentInfo.get({id : $stateParams.id}).$promise;
                            }],
                            teachers: ['TeacherInfo', function (TeacherInfo) {
                                return TeacherInfo.query({size: 999}).$promise;
                            }],
                            trackDefinition: ['TrackDefinition', function(TrackDefinition) {
                                return TrackDefinition.query({size: 999}).$promise;
                            }]
                        }
                    }).result.then(function() {
                        $state.go('student-skill-detail', null, {reload: 'student-skill-detail'});
                    }, function() {
                        $state.go('student-skill-detail');
                    });
                }]
            })
            .state('student-skill-detail.delete', {
                parent: 'student-skill-detail',
                url: '/detail/delete',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/student-skill/student-skill-delete-dialog.html',
                        controller: 'StudentSkillDeleteController',
                        controllerAs: 'vm',
                        size: 'md',
                        resolve: {
                            skill: ['$state', function() {
                                return $state.selectedSkill.skill;
                            }],
                            student: function() {
                                return {id : $stateParams.id};
                            }
                        }
                    }).result.then(function() {
                        $state.go('student-skill-detail', null, { reload: 'student-skill-detail' });
                    }, function() {
                        $state.go('^');
                    });
                }]
            });
    }
})();
