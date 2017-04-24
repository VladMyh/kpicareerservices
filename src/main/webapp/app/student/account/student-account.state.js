(function() {
    'use strict';

    angular
        .module('kpicsApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('student-courses', {
            parent: 'app',
            url: '/account/courses',
            data: {
                authorities: ['ROLE_STUDENT']
            },
            views: {
                'content@': {
                    templateUrl: 'app/student/account/student-courses.html',
                    controller: 'StudentCoursesController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate,$translatePartialLoader) {
                    $translatePartialLoader.addPart('home');
                    return $translate.refresh();
                }]
            }
        });
    }
})();
