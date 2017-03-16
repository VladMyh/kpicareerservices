(function() {
    'use strict';
    angular
        .module('kpicsApp')
        .factory('Faculty', Faculty);

    Faculty.$inject = ['$resource'];

    function Faculty ($resource) {
        var resourceUrl =  'api/faculties/:id';

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
            'createDepartment': {
                method: 'POST',
                url: 'api/faculties/:id/departments'
            },
            'updateDepartment': {
                method: 'PUT',
                url: 'api/faculties/:id/departments/:departmentId'
            },
            'deleteDepartment': {
                method: 'DELETE',
                url: 'api/faculties/:id/departments/:departmentId'
            },
            'getDepartment': {
                method: 'GET',
                url: 'api/faculties/:id/departments/:departmentId'
            }
        });
    }
})();
