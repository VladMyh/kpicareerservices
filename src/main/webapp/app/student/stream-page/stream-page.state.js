(function() {
    'use strict';

    angular
        .module('kpicsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('steam-page', {
            parent: 'app',
            url: '/stream',
            data: {
                authorities: ['ROLE_STUDENT']
            },
            views: {
                'content@': {
                    templateUrl: 'app/student/stream-page/stream-page.html',
                    controller: 'StudentStreamController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                    $translatePartialLoader.addPart('home');
                    return $translate.refresh();
                }]
            }
        })
        .state('steam-page.subject', {
            parent: 'steam-page',
            url: '/stream/subject',
            data: {
                authorities: ['ROLE_STUDENT']
            },
            views: {
                'content@': {
                    templateUrl: 'app/student/stream-page/stream-subject.html',
                    controller: 'StudentSubjectController',
                    controllerAs: 'vm'
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                    $translatePartialLoader.addPart('home');
                    return $translate.refresh();
                    }]
                }
            }
        });
    }
})();
