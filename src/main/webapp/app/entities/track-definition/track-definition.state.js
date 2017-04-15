(function() {
    'use strict';

    angular
        .module('kpicsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('track-definition', {
            parent: 'entity',
            url: '/track-definition?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'kpicsApp.trackDefinition.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/track-definition/track-definitions.html',
                    controller: 'TrackDefinitionController',
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
                    $translatePartialLoader.addPart('trackDefinition');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('track-definition-detail', {
            parent: 'track-definition',
            url: '/track-definition/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'kpicsApp.trackDefinition.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/track-definition/track-definition-detail.html',
                    controller: 'TrackDefinitionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('trackDefinition');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TrackDefinition', function($stateParams, TrackDefinition) {
                    return TrackDefinition.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    let currentStateData = {
                        name: $state.current.name || 'track-definition',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('track-definition-detail.edit', {
            parent: 'track-definition-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/track-definition/track-definition-dialog.html',
                    controller: 'TrackDefinitionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TrackDefinition', function(TrackDefinition) {
                            return TrackDefinition.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('track-definition.new', {
            parent: 'track-definition',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal   ) {
                $uibModal.open({
                    templateUrl: 'app/entities/track-definition/track-definition-dialog.html',
                    controller: 'TrackDefinitionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                skillDefinitions: [],
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('track-definition', null, { reload: 'track-definition' });
                }, function() {
                    $state.go('track-definition');
                });
            }]
        })
        .state('track-definition.edit', {
            parent: 'track-definition',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/track-definition/track-definition-dialog.html',
                    controller: 'TrackDefinitionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TrackDefinition', function(TrackDefinition) {
                            return TrackDefinition.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('track-definition', null, { reload: 'track-definition' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('track-definition.delete', {
            parent: 'track-definition',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/track-definition/track-definition-delete-dialog.html',
                    controller: 'TrackDefinitionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TrackDefinition', function(TrackDefinition) {
                            return TrackDefinition.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('track-definition', null, { reload: 'track-definition' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
