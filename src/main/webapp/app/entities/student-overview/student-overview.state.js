(function() {
    'use strict';

    angular
        .module('kpicsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('student-overview', {
            parent: 'entity',
            url: '/student-overview/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'kpicsApp.studentOverview.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/student-overview/student-overview.html',
                    controller: 'StudentOverviewController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('studentOverview');
                    return $translate.refresh();
                }]
                 ,
                 entity: ['$stateParams', 'StudentOverview', function($stateParams, StudentOverview) {
                     return StudentOverview.get({id: $stateParams.id}).$promise;
                 }]
            }
        })
    }
})();
