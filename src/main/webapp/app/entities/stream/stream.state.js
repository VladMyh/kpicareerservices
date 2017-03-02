(function() {
    'use strict';

    angular
        .module('kpicsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('stream', {
            parent: 'entity',
            url: '/stream?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'kpicsApp.stream.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/stream/streams.html',
                    controller: 'StreamController',
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
                    $translatePartialLoader.addPart('stream');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('stream-detail', {
            parent: 'stream',
            url: '/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'kpicsApp.stream.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/stream/stream-detail.html',
                    controller: 'StreamDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('stream');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Stream', function($stateParams, Stream) {
                    return Stream.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'stream',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('stream-detail.edit', {
            parent: 'stream-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/stream/stream-dialog.html',
                    controller: 'StreamDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Stream', function(Stream) {
                            return Stream.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('stream.new', {
            parent: 'stream',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/stream/stream-dialog.html',
                    controller: 'StreamDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                startDate: null,
                                endDate: null,
                                id: null,
                                description: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('stream', null, { reload: 'stream' });
                }, function() {
                    $state.go('stream');
                });
            }]
        })
        .state('stream.delete', {
            parent: 'stream',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/stream/stream-delete-dialog.html',
                    controller: 'StreamDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Stream', function(Stream) {
                            return Stream.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('stream', null, { reload: 'stream' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
            .state('stream-detail.deleteTrack', {
                parent: 'stream-detail',
                url: '/track/{trackId}/delete',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/stream/stream-track-delete-dialog.html',
                        controller: 'StreamTrackDeleteController',
                        controllerAs: 'vm',
                        size: 'md',
                        resolve: {
                            stream: ['Stream', function(Stream) {
                                return Stream.get({id : $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function() {
                        $state.go('stream-detail', null, { reload: 'stream-detail' });
                    }, function() {
                        $state.go('^');
                    });
                }]
            })
            .state('stream-detail.deleteSubject', {
                parent: 'stream-detail',
                url: '/track/{trackId}/subject/{subjectId}/delete',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/stream/stream-track-subject-delete-dialog.html',
                        controller: 'StreamTrackSubjectDeleteController',
                        controllerAs: 'vm',
                        size: 'md',
                        resolve: {
                            stream: ['Stream', function(Stream) {
                                return Stream.get({id : $stateParams.id}).$promise;
                            }]
                        }
                    }).result.then(function() {
                        $state.go('stream-detail', null, { reload: 'stream-detail' });
                    }, function() {
                        $state.go('^');
                    });
                }]
            })
        .state('stream-detail.newTrack', {
            parent: 'stream-detail',
            url: '/newTrack',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/stream/stream-detail-newtrack.html',
                    controller: 'StreamNewTrackDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        track: function () {
                            return {
                                name: null,
                                description: null,
                                id: null,
                                teachers: [],
                                subjects: []
                            };
                        },
                        entity: ['Stream', function(Stream) {
                            return Stream.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('stream-detail', null, { reload: 'stream-detail' });
                }, function() {
                    $state.go('stream-detail');
                });
                }]
            })
            .state('stream-detail.addTeacher', {
                parent: 'stream-detail',
                url: '/track/{trackId}/addTeacher',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/stream/stream-track-add-teacher-dialog.html',
                        controller: 'TrackAddTeacherDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            stream: ['Stream', function(Stream) {
                                return Stream.get({id : $stateParams.id}).$promise;
                            }],
                            track: ['Stream', function(Stream) {
                                return Stream.getTrack({id: $stateParams.id, trackId: $stateParams.trackId}).$promise;
                            }]
                        }
                    }).result.then(function() {
                        $state.go('stream-detail', null, { reload: 'stream-detail' });
                    }, function() {
                        $state.go('stream-detail');
                    });
                }]
            })
            .state('stream-detail.addSubject', {
                parent: 'stream-detail',
                url: '/track/{trackId}/newSubject',
                data: {
                    authorities: ['ROLE_USER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/stream/stream-track-add-subject-dialog.html',
                        controller: 'TrackNewSubjectDialogController',
                        controllerAs: 'vm',
                        backdrop: 'static',
                        size: 'lg',
                        resolve: {
                            subject: function () {
                                return {
                                    name: null,
                                    id: null,
                                    teacherId: null,
                                    subjects: null
                                };
                            },
                            stream: ['Stream', function(Stream) {
                                return Stream.get({id : $stateParams.id}).$promise;
                            }],
                            track: ['Stream', function(Stream) {
                                return Stream.getTrack({id: $stateParams.id, trackId: $stateParams.trackId}).$promise;
                            }]
                        }
                    }).result.then(function() {
                        $state.go('stream-detail', null, { reload: 'stream-detail' });
                    }, function() {
                        $state.go('stream-detail');
                    });
                }]
            });
    }

})();
