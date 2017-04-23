(function() {
    'use strict';

    angular
        .module('kpicsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('streamGroups', {
            parent: 'entity',
            url: '/streamGroups',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'kpicsApp.group.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/streamGroups/streamGroups.html',
                    controller: 'StreamGroupsController',
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
        .state('streamGroups.addGroup', {
                    parent: 'streamGroups',
                    url: '/stream/{streamId}/add',
                    data: {
                        authorities: ['ROLE_USER']
                    },
                    onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                        $uibModal.open({
                            templateUrl: 'app/entities/streamGroups/streamGroups-dialog.html',
                            controller: 'StreamGroupsDialogController',
                            controllerAs: 'vm',
                            backdrop: 'static',
                            size: 'lg',
                            resolve: {}
                        }).result.then(function() {
                            $state.go('streamGroups', null, { reload: 'streamGroups' });
                        }, function() {
                            $state.go('streamGroups');
                        });
                    }]
                });
    }

})();
