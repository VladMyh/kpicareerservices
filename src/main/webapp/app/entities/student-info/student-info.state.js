(function() {
    'use strict';

    angular
        .module('kpicsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('student-info', {
            parent: 'entity',
            url: '/student-info?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'kpicsApp.studentInfo.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/student-info/student-infos.html',
                    controller: 'StudentInfoController',
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
                    $translatePartialLoader.addPart('studentInfo');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('student-info-detail', {
            parent: 'student-info',
            url: '/student-info/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'kpicsApp.studentInfo.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/student-info/student-info-detail.html',
                    controller: 'StudentInfoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('studentInfo');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'StudentInfo', function($stateParams, StudentInfo) {
                    return StudentInfo.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'student-info',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('student-info-detail.edit', {
            parent: 'student-info-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student-info/student-info-dialog.html',
                    controller: 'StudentInfoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['StudentInfo', function(StudentInfo) {
                            return StudentInfo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('student-info.new', {
            parent: 'student-info',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student-info/student-info-dialog.html',
                    controller: 'StudentInfoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                faculty: null,
                                department: null,
                                group: null,
                                github: null,
                                about: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('student-info', null, { reload: 'student-info' });
                }, function() {
                    $state.go('student-info');
                });
            }]
        })
        .state('student-info.edit', {
            parent: 'student-info',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student-info/student-info-dialog.html',
                    controller: 'StudentInfoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['StudentInfo', function(StudentInfo) {
                            return StudentInfo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('student-info', null, { reload: 'student-info' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('student-info.delete', {
            parent: 'student-info',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/student-info/student-info-delete-dialog.html',
                    controller: 'StudentInfoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['StudentInfo', function(StudentInfo) {
                            return StudentInfo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('student-info', null, { reload: 'student-info' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
