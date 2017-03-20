(function() {
    'use strict';
    angular
        .module('kpicsApp')
        .factory('StudentOverview', StudentOverview);

    StudentOverview.$inject = ['$resource'];

    function StudentOverview ($resource) {
        let resourceUrl =  'api/student-overview/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' },
            'find' : {
                url: 'api/student-overviews/find/:query',
                method:'GET',
                isArray: true
            }
        });
    }
})();
