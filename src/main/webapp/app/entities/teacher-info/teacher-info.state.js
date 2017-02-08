(function() {
    'use strict';

    angular
        .module('kpicsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('teacher-info', {
            parent: 'entity',
            url: '/teacher-info?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'kpicsApp.teacherInfo.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/teacher-info/teacher-infos.html',
                    controller: 'TeacherInfoController',
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
                    $translatePartialLoader.addPart('teacherInfo');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('teacher-info-detail', {
            parent: 'teacher-info',
            url: '/teacher-info/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'kpicsApp.teacherInfo.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/teacher-info/teacher-info-detail.html',
                    controller: 'TeacherInfoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('teacherInfo');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TeacherInfo', function($stateParams, TeacherInfo) {
                    return TeacherInfo.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'teacher-info',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('teacher-info-detail.edit', {
            parent: 'teacher-info-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/teacher-info/teacher-info-dialog.html',
                    controller: 'TeacherInfoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TeacherInfo', function(TeacherInfo) {
                            return TeacherInfo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('teacher-info.new', {
            parent: 'teacher-info',
            url: '/new',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/teacher-info/teacher-info-dialog.html',
                    controller: 'TeacherInfoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                id: null,
                                firstName: null,
                                lastName: null,
                                email: null,
                                linkedin: null,
                                password: null,
                                teacherInfo: {
                                    faculty: null,
                                    department: null,
                                    about: null
                                }
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('teacher-info', null, { reload: 'teacher-info' });
                }, function() {
                    $state.go('teacher-info');
                });
            }]
        })
        .state('teacher-info.edit', {
            parent: 'teacher-info',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/teacher-info/teacher-info-dialog.html',
                    controller: 'TeacherInfoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TeacherInfo', function(TeacherInfo) {
                            return TeacherInfo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('teacher-info', null, { reload: 'teacher-info' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('teacher-info.delete', {
            parent: 'teacher-info',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_ADMIN']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/teacher-info/teacher-info-delete-dialog.html',
                    controller: 'TeacherInfoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TeacherInfo', function(TeacherInfo) {
                            return TeacherInfo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('teacher-info', null, { reload: 'teacher-info' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
