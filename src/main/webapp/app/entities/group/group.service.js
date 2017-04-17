(function() {
    'use strict';
    angular
        .module('kpicsApp')
        .factory('Group', Group);

    Group.$inject = ['$resource'];

    function Group ($resource) {
        var resourceUrl =  'api/groups/:id';

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
            'getByFaculty': {
                method: 'GET',
                url: 'api/groups/faculty/:faculty',
                isArray: true
            },
            'getByFacultyAndDepartment': {
                method: 'GET',
                url: 'api/groups/faculty/:faculty/department/:department',
                isArray: true
            }
        });
    }
})();
