(function() {
    'use strict';

    angular
        .module('kpicsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('faculty', {
            parent: 'entity',
            url: '/faculty?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'kpicsApp.faculty.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/faculty/faculties.html',
                    controller: 'FacultyController',
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
                    $translatePartialLoader.addPart('faculty');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('faculty-detail', {
            parent: 'faculty',
            url: '/faculty/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'kpicsApp.faculty.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/faculty/faculty-detail.html',
                    controller: 'FacultyDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('faculty');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Faculty', function($stateParams, Faculty) {
                    return Faculty.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'faculty',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('faculty-detail.edit', {
            parent: 'faculty-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/faculty/faculty-dialog.html',
                    controller: 'FacultyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Faculty', function(Faculty) {
                            return Faculty.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('faculty.new', {
            parent: 'faculty',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/faculty/faculty-dialog.html',
                    controller: 'FacultyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('faculty', null, { reload: 'faculty' });
                }, function() {
                    $state.go('faculty');
                });
            }]
        })
        .state('faculty.edit', {
            parent: 'faculty',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/faculty/faculty-dialog.html',
                    controller: 'FacultyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Faculty', function(Faculty) {
                            return Faculty.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('faculty', null, { reload: 'faculty' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('faculty.delete', {
            parent: 'faculty',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/faculty/faculty-delete-dialog.html',
                    controller: 'FacultyDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Faculty', function(Faculty) {
                            return Faculty.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('faculty', null, { reload: 'faculty' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
