(function() {
    'use strict';

    angular
        .module('kpicsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('track', {
            parent: 'entity',
            url: '/track?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'kpicsApp.track.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/track/tracks.html',
                    controller: 'TrackController',
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
                    $translatePartialLoader.addPart('track');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('track-detail', {
            parent: 'track',
            url: '/track/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'kpicsApp.track.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/track/track-detail.html',
                    controller: 'TrackDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('track');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Track', function($stateParams, Track) {
                    return Track.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'track',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('track-detail.edit', {
            parent: 'track-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/track/stream-detail-newtrack.html',
                    controller: 'TrackDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Track', function(Track) {
                            return Track.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('track-detail.addTeacher', {
            parent: 'track-detail',
            url: '/addTeacher',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/track/track-add-teacher-dialog.html',
                    controller: 'TrackAddTeacherDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Track', function(Track) {
                            return Track.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('track-detail', null, { reload: 'track-detail' });
                }, function() {
                    $state.go('track-detail');
                });
            }]
        })
        .state('track.new', {
            parent: 'track',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/track/stream-detail-newtrack.html',
                    controller: 'TrackDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                isActive: false,
                                id: null,
                                teacherIds: [],
                                studentIds: []
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('track', null, { reload: 'track' });
                }, function() {
                    $state.go('track');
                });
            }]
        })
        .state('track.edit', {
            parent: 'track',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/track/stream-detail-newtrack.html',
                    controller: 'TrackDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Track', function(Track) {
                            return Track.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('track', null, { reload: 'track' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('track.delete', {
            parent: 'track',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/track/track-delete-dialog.html',
                    controller: 'TrackDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Track', function(Track) {
                            return Track.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('track', null, { reload: 'track' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
